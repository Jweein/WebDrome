package web.server;
// CatfoOD 2008.3.13

public interface ICacheState {
	/** �ļ���*/
	public String getFilename();
	/** ����ʱ��(��)*/
	public int cacheTime();
	/** ʹ�ô���*/
	public int getUseCount();
	/** ��ǰ���ô���*/
	public int referenceCount();
	/** ռ���ڴ�(�ֽ�)*/
	public int useMemory();
	/** ״̬*/
	public String state();
}
