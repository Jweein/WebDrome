package web.server;
import java.io.File;

// CatfoOD 2008-4-2

/**
 * �洢�����ַ���
 */
public final class Language {
	private Language() {}
	
	public static String parameterError = "��������";
	public static String second = "��";
	public static String hour	= "Сʱ";
	public static String error	= "����";
	public static String request= "����";
	
	// AboutDialog	--------
	public static String about 		= "����"; 
	public static String closeButton= "�ر�";
	public static String explainS1 	= "����Ϊ������,��������ַ�ʹ��";
	public static String explainS2 	= "������ʹ�ñ�������ɵ��κ���ʧ,���߲������κ�����";
	
	// Cgi_Manage	--------
	public static String cgiRequestError = "Cgi_Manageδ֪������";
	
	// CgiBase		--------
	public static String cgiSevereError			= "cgi���ش���";
	public static String cgiSupportClosed		= "(CGI)��֧�ֱ��ر�";
	public static String cgiError 				= "cgi�������";
	public static String cgiErrorFileNotFound	= "������ļ�������";
	
	// CharBuffer	--------
	public static String arrayException = "Դ���鳤��̫��";
	
	// FileCache	--------
	public static String fileRecaches 			= "�ļ������������»���";
	public static String recacheOver 			= "�ļ��������";
	public static String canNotRecacheMessage 	= "�����ػ���,����һ���߳����ڷ����������";
	public static String fileChangedRangefall 	= "�ļ�������,��ΧʧЧ,���������µݽ�";
	public static String rangeFormatError 		= "��Χ�������Ϸ�";
	
	// FileCacheManage	--------
	public static String freeMemory 			= "�����ڴ�";
	public static String usedMemory 			= "�ڴ�����";
	public static String fileCachedCount 		= "�ļ���������";
	public static String MemoryFullFreeCache 	= "�ڴ治���ͷ��ڴ�";
	public static String timeUPfreeCache 		= "Сʱ,������";
	public static String clientClose 			= "�ͻ��˹ر�������";
	public static String unknowError 			= "δ֪�Ĵ���";
	
	// FileCacheMonitorWindow	--------
	public static String reBrushList 		= "ˢ���б�";
	public static String table_filename 	= "�ļ���";
	public static String table_cachetime 	= "����ʱ��(��)";
	public static String table_usecount 	= "ʹ�ô���";
	public static String table_refcount 	= "���ü���";
	public static String table_useMem 		= "ռ���ڴ�(�ֽ�)";
	public static String table_state 		= "״̬";
	
	// FilterSystem	--------
	public static String fileinFilterListRebut = "�ڹ����б���,���󲵻�";
	
	// HttpHeadAnalyse	--------
	public static String URLDecoderException	= "URL�������"; 
	public static String unableCRLF 			= "���͵���Ϣ�в�����CR,LF";
	public static String lineEndnotisCRLF 		= "��ǰ�в���CRLF��β";
	public static String outputMessageException = "��Ϣ���Ѿ�����,���ܼ���д����Ϣͷ";
	
	// LinkedIPArray	--------
	public static String atLinkLimit 	= "�ﵽ��������";
	public static String UPException 	= "ʹ�ô����Ѿ��ﵽ����";
	public static String DownException	= "ʹ�ô����Ѿ�Ϊ0,���ܼ����ͷ�";
	
	// LogSystem	--------
	public static String startAt 			= "start At";
	public static String writeLogfileError 	= "����д����־�ļ�";
	public static String dialog_error 		= "������Ϣ";
	public static String dialog_link 		= "������Ϣ";
	public static String dialog_httphead 	= "��Ч��Http��������";
	public static String dialog_state 		= "ϵͳ״̬";
	public static String dialog_cache 		= "�ļ�����״̬";
	
	// ReadConfig	--------
	public static String unsupportConfigCommand = "�ļ��д��������,��֧�ֵ�����";
	public static String unsupporttype 			= "can not find type";
	
	// ServerMachine	--------
	public static String portinUseError 	= "�˿��Ѿ���ռ��,���˳���س���������";
	public static String serverAddress		= "��������ַ";
	public static String serverPort			= "�������˿�";
	public static String serverBeginAt		= "��ʼ������";
	public static String closedWinExitpro	= "�ر�������ڷ��������˳�";
	public static String serverAcceptError	= "������������������";
	public static String cannotChangePort 	= "��������������,���ܸı�˿�";
	public static String cannotChangeMode 	= "��������������,���ܸı���ʾģʽ";
	public static String ITI_linkcount 	= "��ǰ������";
	public static String ITI_totallink 	= "�ܹ����ܵ�����";
	public static String ITI_runtime 	= "ϵͳ���е�ʱ��";
	public static String ITI_threadcont = "ʹ�õ��߳�";
	
	// SocketLink	--------
	public static String socketLinked 		= "������";
	public static String socketTimeout 		= "���ӳ�ʱ";
	public static String finishListen		= "�����Կͻ��˵ļ���"; 
	public static String requestError 		= "�������";
	public static String cannotFindFile		= "�Ҳ����ļ�";
	public static String unknowRequest 		= "δ֪������";
	public static String readHttpHeadError 	= "��ȡHTTPͷ���ݳ���";
	public static String socketClosed 		= "�ر�������";
	public static String sendFileError 		= "�����ļ�ʧ��";
	public static String sendFile 			= "�����ļ�";
	
	// SystemIcon	--------
	public static String exitWebServer 		= "�˳�Web������";
	public static String systemIconinitError= "ϵͳͼ�겻������";
	public static String systemOnExit 		= "ϵͳ�����˳�";
	public static String plaseUseTaskManageOnExit 	= "�˳�����ϵͳ�Ľ��̹�����";
	
	// TableMessageWindow	--------
	public static String tablecol_statename = "����������";
	public static String tablecol_state 	= "״̬";
	
	/** ��ʼ�������������� */
	public static void init() {
		String localLanguagefile = 	CommonInfo.languagePath + 
									File.separatorChar +
									CommonInfo.language;
		try {
			ReadConfig rc = new ReadConfig("web.server.Language");
			rc.readFromfile(localLanguagefile, false, true);
		} catch (Exception e) {
			System.out.println("��ȡ���������ļ�����:"+e);
		}
	}
}
