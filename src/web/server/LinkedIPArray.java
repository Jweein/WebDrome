package web.server;
// CatfoOD 2008.3.30

import java.util.ArrayList;
import java.net.InetAddress;
	
/**
 * ������������.<br>
 * <pre>
	HTTP/1.1 [8.2.4] ����������ر�����ʱ�ͻ��˵���Ϊ:
	
	���HTTP/1.1 �ͻ��˷���һ��������Ϣ�����������Ϣ��������ֵΪ"100-continue"
	��Expect����ͷ��,���ҿͻ���ֱ����HTTP/1.1Դ���������������ҿͻ����ڽ��յ�����
	����״̬��Ӧ֮ǰ���������ӵĹرգ���ô�ͻ���Ӧ�����Դ�����������ʱ���ͻ��˿���
	����������㷨����ÿɿ�����Ӧ��
	
	1�� �����������һ�����ӡ� 
	2�� ����������ͷ��
	3�� ��ʼ������R��ʹR��ֵΪͨ��������������ʱ��Ĺ���ֵ��������ڽ������ӵ�ʱ�䣩��
	    �����޷���������ʱ��ʱ��Ϊһ����ֵ5�롣
	4�� ����T=R*��2**N����NΪ��ǰ��������Ĵ�����
	5�� �ȴ�������������Ӧ�����ǵȴ�T�루������ʱ��϶̵ģ���
	6�� ��û�ȵ�������Ӧ��T������������Ϣ���塣 
	7�� ���ͻ��˷������ӱ���ǰ�رգ�ת����1����ֱ�����󱻽��ܣ����յ�������Ӧ��
	    �����û����ͷ�����ֹ�����Թ��̡�
	    
	��������ϣ��ͻ���������յ��������ĳ�����Ӧ���ͻ���
		--- ��Ӧ�ټ����������� ���� 
		--- Ӧ�ùر���������ͻ���û����ɷ���������Ϣ�� 
   </pre>
   �����������,��ʵ��ip����������
 */
public final class LinkedIPArray {
	private ArrayList list;
	private int connectLimit;
	
	/**
	 * �½���������
	 * @param limit - ����ÿ��IP����������
	 */
	public LinkedIPArray(int limit) {
		list = new ArrayList();
		connectLimit = limit;
	}
	
	/**
	 * ���Ӷ�ip�����ü���
	 * @return boolean - δ�����������ƣ�����true������ip�����ü���+1
	 */
	public synchronized boolean addALink(InetAddress ip) {
		if (ip!=null) {
			IPUse uip = findIP(ip);
			if (uip!=null) {
				if  ( !uip.isLimit() ) {
					uip.add();
					if (uip.usedCount>=connectLimit) {
						LogSystem.error(ip+" "+Language.atLinkLimit+".");
					}
					return true;
				}
				// else return false �����
			} else {
				list.add(new IPUse(ip));
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * ָ���������Ѿ��������ͷ����ip
	 */
	public synchronized void relaseALink(InetAddress ip) {
		if (ip!=null) {
			IPUse uip = findIP(ip);
			if (uip!=null) {
				uip.release();
				if (uip.notUsed()) {
					list.remove(uip);
				}	
			}
			// else donothing
		} else {
			throw new NullPointerException();
		}
	}
	
	private IPUse findIP(InetAddress ia) {
		for (int i=0; i<list.size(); ++i) {
			if ( list.get(i).equals(ia) ) {
				return (IPUse)list.get(i);
			}
		}
		return null;
	}
	
	// inner class;
	private class IPUse {
		private InetAddress iadd;
		private int usedCount;
		
		public IPUse(InetAddress i) {
			iadd = i;
			usedCount = 1;
		}
		
		public int getUseCount() {
			return usedCount;
		}
		public InetAddress getInetAddress() {
			return iadd;
		}	
		public void add() {
			if (usedCount<connectLimit) {
				++usedCount;
			} else {
				throw new IllegalStateException(Language.UPException);
			}
		}
		public void release() {
			if (usedCount>0) {
				--usedCount;
			} else {
				throw new IllegalStateException(Language.DownException);
			}
		}	
		public boolean notUsed() {
			return usedCount==0;
		}
		public boolean equals(Object o) {
			return iadd.equals(o);
		}
		public boolean isLimit() {
			return usedCount>=connectLimit;
		}
	}
}
