package web.server;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import web.servlet.ServletManager;

/**
 * @author CatfoOD
 */
public class SocketLink implements Runnable, IResponsion {
	
	private Socket soket;
	private volatile boolean stop = false;
	
	private InputStream in;
	private OutputStream out;
	private File sendfile;
		
	public SocketLink(Socket s) throws IOException {
		soket = s;
		soket.setSoTimeout(CommonInfo.socketReadOuttime);
		in  = s.getInputStream();
		out = s.getOutputStream();
		
		new Thread(this).start();
	}
	
	/** һ��web���ӵ������� */
	public void run() {
		out(f(soket)+Language.socketLinked);
		HttpHeadAnalyse hha = null;
		/** ����ͻ��˷����ر���ϢΪtrue */
		boolean clientisClosed = false;
		
		// ����ѭ��ǰ����connect++
		++connect;
		do { // ��Ϣѭ���Ŀ�ʼ, ֱ���ͻ��˷��͹ر����ӵ�ͷ��,��û����������Ϣʱ,ѭ�����˳�.
			
			try {
				hha = null;
				hha = new HttpHeadAnalyse(soket);
			} catch (SocketTimeoutException e) {
				LogSystem.error(f(soket)+
						Language.socketTimeout+','+Language.finishListen+".");
				break;
			} catch (IOException e) {
				LogSystem.error(f(soket)+
						Language.clientClose+','+Language.finishListen+".");
				break;
			}
			
			if (hha!=null) {
				if (!clientisClosed) clientisClosed = hha.isCloseConnect();
				
				hha.setBasePath( VirtualHostSystem.getVhost(hha) );
				
				if (ServletManager.request(hha)) {
					continue;
				}
				
				if (hha.isGET()) {
					sendfile = hha.getRequestFile();
					if ( sendfile!=null ) {
						try {
							if (Cgi_Manage.get().isCgi(hha)) {
								// �Խű��ļ�������
								Cgi_Manage.get().request(hha, this);
							} else {
								// ����ͨ�ļ�������,�ȹ���
								FilterSystem.exclude(sendfile);
								FileCacheManage.get().request(hha, this);
							}
							// ����Ĵ���������ɹ���ִ��
							++connect;
							// ����ɹ�����ѭ�����ȴ��ص�,
							continue;
							
						} catch(HTTPException e) {
							error(f(soket)+e);
							hha.error(e.getHttpErrorCode(), e.toString());
							break;
							
						} catch(Exception e) {
							error(Language.requestError+":"+e);
							// ��ת������Ĵ���--Ӧ��һ��404����
						}
					}
					
					hha.error(RequestErrCode.E404, hha.getRequestURI());
					
					error(f(soket)+Language.requestError+","+
							Language.cannotFindFile+":"+hha.getRequestURI());
					
					LogSystem.httpHead(hha);
					break;
					
				} else if (hha.isPOST()) {
					try {
						Cgi_Manage.get().request(hha, this);
						++connect;
						continue;
						
					} catch(HTTPException e) {
						error(f(soket)+e);
						hha.error(e.getHttpErrorCode(), e.toString());
						break;
					
					} catch (Exception e) {
						error(Language.requestError+":"+e);
						// ������δ֪�����Ӧ������Ĵ���--404����
					}
					hha.error(RequestErrCode.E404, hha.getRequestURI());
					LogSystem.httpHead(hha);
				} else {
					error(Language.unknowRequest+":"+f(soket));
				}
			} else {
				error(f(soket)+Language.readHttpHeadError);
			}
			// ----- ����Ĵ����ڳ���ʱִ��,
			// ���������˳�
			break;
			// -----
		} while( (!clientisClosed) );
		// �˳�ѭ��ʱ���� connect--;
		--connect;
		closeConnect();
	}
	
	/** 
	 * ���Թر�����,�����Ϣ������Ϊ��,�Ҵ��ڳ�ʱ״̬<br>
	 * closeConnect()ͨ����������̵߳�����(connect����)
	 * ���ж��Ƿ�Ӧ�ùر��׽���
	 */
	private void closeConnect() {
		if (connect<=0) {
			out(f(soket)+Language.socketClosed);
			try {
				in.close();
				out.close();
				soket.close();
			}catch(Exception ee){}
			stop = true;
		}
	}
	/** 
	 * ���Ǻܹؼ����ս����,С�ĵ�������!!! 
	 * �������ķ�������'�ɶ�'����
	 * 
	 * ÿ��һ���µ�<b>�����߳�</b>������,connect��һ,
	 * ��<b>�����߳�</b>�˳�,connect��һ
	 */
	private volatile int connect = 0;
	
	/** �����������Ƿ��Ѿ����� */
	public boolean isDisconnect() {
		return stop;
	}
	
	/** �ļ����������Ļص����� */
	public void responsion(Object o) {
		if ( (o!=null) && (!stop) ) {
			if (o instanceof InputStream) {
				sendThread.send( (InputStream)o );
				// �ص��ɹ�,��������
				return;
			} else {
				throw new IllegalArgumentException("responsion unsupport class:"+o);
			}
		}
		--connect;
		closeConnect();
	}
	private SendThread sendThread = new SendThread();
	
	/** ��ʼ�����ļ� */
	private class SendThread extends Thread {
		private List inQueue;

		private SendThread() {
			inQueue = new LinkedList();
			this.start();
		}
		
		public void send(InputStream in) {
			inQueue.add(in);
		}
		
		public boolean isEmpty() {
			return inQueue.isEmpty();
		}
		
		public void run() {
			// ����������Ҫ�ı���
			final boolean SPEEDLIMIT = !(CommonInfo.downSpeedLimit<=0);
			final int waitTime = (int)(SPEEDLIMIT ?
				(float)CommonInfo.writeBufferSize/
				(CommonInfo.downSpeedLimit*1024)*1000 : 0);
			long useTime = 0;
			
			while (!stop) {
				while (inQueue.isEmpty()) {
					try {
						if (stop) return;
						sleep(50);
					} catch (InterruptedException e) {}
				}
				InputStream fin = (InputStream)inQueue.get(0);
				inQueue.remove(0); // ģ�¶���
	
				byte[] buffer = new byte[CommonInfo.writeBufferSize];
				try {
					out(f(soket)+Language.sendFile+":"+sendfile);
					int len = fin.read(buffer);
					while (len>0) {
						out.write(buffer, 0, len);
						len = fin.read(buffer);
						
						if (SPEEDLIMIT) {
							useTime = System.currentTimeMillis() - useTime;
							if (useTime<waitTime) {
								try {
									Thread.sleep(waitTime-useTime);
								} catch (InterruptedException e) {}
							}
							useTime = System.currentTimeMillis();
						}
					}
					
				} catch (IOException e) {
					error(f(soket)+Language.sendFileError+":"+
							sendfile+" "+Language.clientClose+".");
				//	error("��ϸ��Ϣ:"+e.getLocalizedMessage());
				} finally {
					try {
						fin.close();
						--connect;
						closeConnect();
					} catch (IOException e) {}
				}
			}
			return;
		}
	}
	
	public InetAddress getInetAddress() {
		return soket.getInetAddress();
	}
	
	/** ͨ��LogSystem��ӡ��Ϣ */
	private final void out(Object o) {
		LogSystem.message(o.toString());
	}
	/** ͨ��LogSystem��ӡ������Ϣ */
	private final void error(Object o) {
		LogSystem.error(o.toString());
	}
	/** ��ʽ��Socket����� */
	private final String f(Socket s) {
		return s.getRemoteSocketAddress()+" ";
	}
}
