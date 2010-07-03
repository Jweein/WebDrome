package web.server;

// CatfoOD 2008.3.11

public final class CommonInfo {
	private CommonInfo() {}
	
	public static String[] defaultIndexfile = {
			"index.htm", "index.html", 
	};
	
	/** ��ǰʹ�õ����� */
	public static String language = "Chinese.txt";
	
	/** Ĭ�ϵ���ҳ�ļ� */
	public static String defaultRootPath = "default";
	
	/** ��վ��ҳ���ļ��� */
	public static String webRootPath = "website";
	
	/** �����ļ�����ʹ�õ�����ڴ��� KB */
	public static long maxMemoryUse = 50*1024*1024;
	
	/** ����������������� */
	public static int maxConnect = 1000;
	
	/** ����ÿ��IP�����������,Ϊ'0'������ ʵ��ԭ��ο�LinkedIPArray���˵�� */
	public static int ipConnectLimit = 5;
	
	/** һ���ļ���������ʱ�� not use */
	public static int cacheFileOutTime = 10*1000;
	
	/** �������� ��λ:KB/s Ϊ'0'���������� */
	public static int downSpeedLimit = 0;
	
	/** �����ļ���������С */
	public static int writeBufferSize = 1024*5;
	
	/** �������������Ӻ�,�ȴ��ͻ�������Ϣ�ĳ�ʱ ���� */
	public static int socketReadOuttime = 15000;
	
	/** ������Ĭ�϶˿�,���ȼ������������ */
	public static int serverPort = 80;
	
	/** ״̬����ˢ����Ϣ�ļ�� ���� */
	public static int refurbishSpace = 5000;
	
	/** �������GUIģʽ��Ϣ������ʾ���ı��������� */
	public static int maxMessageLine = 5000;
	
	/** С�������С���ļ�������,����ֱ�ӷ���Ӳ�� KB*/
	public static int maxCachedFileLength = 5*1024*1024;
	
	/** ÿ����nСʱ,�ļ�������������������õ����ļ� Hour*/
	public static int clearCacheTime = 3;
	
	/** �Ƿ�֧��PHP CGI */
	public static boolean phpSupport = true;
	
	/** �Ƿ����ļ���չ������ */
	public static boolean filterEnable = false;
	
	/** �Ƿ���CGIϵͳ */
	public static boolean cgiEnable = false;
	
	/** �Ƿ��ӡ����־�ļ� */
	public static boolean printLogFile = true;
	
	/** �Ƿ������������� */
	public static boolean vHostEnable = false;
	
	// ----------------------·������-----------------------//
	
	/**  ϵͳ�����ļ��� */
	public final static String systemPath = "etc";
	
	/**  ϵͳ�ļ�����	·�� */
	public final static String systemFile = "system.conf";
	
	/** ���԰������ļ� ·�� */
	public final static String languagePath = "language";
	
	/** �������������ļ� */
	public static String virtualHost = "host.conf";
	
	/** CGI �����ļ� */
	public static String cgiConf = "cgi.conf";
	
	/** �����������ļ� */
	public static String exclude = "exclude.conf";
	
	/** ϵͳ��־�ļ��� */
	public static String logPath = "log";
	
	/** Mime �ļ����������ļ� */
	public static String miniTypeConf = "mime.types.conf";
	
	/** ��ʼ�� */
	public static void init() {
		try {
			ReadConfig rc = new ReadConfig("web.server.CommonInfo");
			rc.readFromfile(systemFile);
		} catch (Exception e) {
			System.out.println("��ȡϵͳ�����ļ�����:"+e);
		}
	}
	
	/** ע��Ĭ�ϵ���ҳ�ļ� */
	public static void registerIndexType(String[] name) {
		if (name!=null && name.length>0) {
			String[] news = new String[name.length + defaultIndexfile.length];
			int p = 0;
			for (int i=0; i<defaultIndexfile.length; ++i) {
				news[p++] = defaultIndexfile[i];
			}
			for (int i=0; i<name.length; ++i) {
				news[p++] = name[i];
			}
			defaultIndexfile = news;
		}
	}
}
