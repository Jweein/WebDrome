package web.server;
// CatfoOD 2008.3.23

import java.io.File;

/** CGI ʵ�ֽӿ� */
public interface ICgi extends IRequest {
	/**�ű��ļ�ʹ�õ���չ��*/
	public String[] getExpandName();
	/**���ָ�����ļ��Ƿ��ܱ���ǰ��cgi����*/
	public boolean canDisposal(File f);
	/**ע��Ĭ�ϵ���ҳ�ļ�*/
	public String[] registerIndexFile();
}
