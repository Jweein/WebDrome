package web.server;
// CatfoOD 2008.3.12

/** 
 * ����ӿ�.<br>
 * ʵ��IRequest�ӿڵ���<b>����ͨ��</b>"request"������"IResponsion"�����ص����������.<br>
 * �������Ķ���û������,Ҫô������ʱ�׳��쳣,Ҫô�ڻص�ʱ,����Ϊnull;
 */
public interface IRequest {
	/** 
	 * ���������ʵ������ӿ�,������������ɹ����ú�
	 * ���������������,���ȴ����ص�--ͨ��"IResponsion"�ӿ�
	 */
	public void request(Object o, IResponsion ir) throws Exception;
}
