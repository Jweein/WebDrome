package web.server;
// CatfoOD 2008-3-28

public class MimeTypes {
	private MimeTypes() {}
	
	private static ConfArray[] ca = new ConfArray[0];
	
	public final static void init() {
		ca = ReadConfig.readToConfArray(CommonInfo.miniTypeConf);
	}
	
	/**
	 * Ѱ��ָ���ļ���Mini����
	 * @param hha - ��Ϣͷ��װ��
	 * @return - Mini�����ַ���,�Ҳ�������null
	 */
	public final static String getMimeName(HttpHeadAnalyse hha) {
		String s = hha.getRequestFile().getName();
		int begin = s.lastIndexOf('.');
		if (begin>=0 && begin<s.length()-1) {
			s = s.substring(begin+1);
			for (int i=0; i<ca.length; ++i) {
				if (ca[i].findSub(s)) {
					return ca[i].getName();
				}
			}
		}
		return null;
	}
}
