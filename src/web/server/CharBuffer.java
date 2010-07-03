package web.server;
// CatfoOD 2008.3.26

/**
 * �ַ�����
 */
public class CharBuffer {
	private int DEFAULTLEN = 30;
	private byte[] buff = new byte[0];
	private int point = 0;
	
	/**
	 * ����Ĭ�ϵĻ���������:30 
	 */
	public CharBuffer() {
		this(30);
	}
	
	/**
	 * ����һ���ַ�������,����Ϊlen
	 * @param len - �������ĳ���
	 */
	public CharBuffer(int len) {
		buff = new byte[len];
		DEFAULTLEN = len;
	}
	
	/**
	 * ���ַ���ӵ�ĩβ
	 * @param c - Ҫ��ӵ��ַ�
	 */
	public void append(byte c) {
		if (point>=buff.length) {
			reAllotArray();
		}
		buff[point++] = c;
	}
	
	/**
	 * ���ַ���ӵ�ĩβ
	 */
	public void append(char c) {
		append((byte)c);
	}
	
	/**
	 * ���ַ���ӵ�������ĩβ
	 */
	public void append(int c) {
		append((byte)c);
	}
	
	private void reAllotArray() {
		byte[] newbuff = new byte[buff.length*2];
		copy(newbuff, buff);
		buff = newbuff;
		// ��������!!
		System.gc();
	}
	
	private void copy(byte[] src, final byte[] dec) {
		if (src.length<dec.length) 
			throw new IllegalArgumentException(Language.arrayException+".");
		
		for (int i=0; i<dec.length; ++i) {
			src[i] = dec[i];
		}
	}
	
	/**
	 * �ַ����������ַ�����ʾ
	 */
	public String toString() {
		return new String(buff, 0, point);
	}
	
	/**
	 * ��ջ�����,ѭ������???
	 */
	public void delete() {
		buff = new byte[DEFAULTLEN];
		point = 0;
		System.gc();
	}
}
