// CatfoOD 2008-10-31 ����08:13:08

package web.servlet;

/**
 * ����˽ű�ʵ�ֵĽӿ�
 */
public interface IServlet {
	/**
	 * GET �����������������
	 */
	void doGet(IServletRequest req, IServletResponse resp);
	
	/**
	 * POST �����������������
	 */
	void doPost(IServletRequest req, IServletResponse resp);
}
