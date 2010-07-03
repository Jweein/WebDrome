package web.server;
import java.io.File;

// CatfoOD 2008-3-28

public class VirtualHostSystem {
	private VirtualHostSystem() {}
	
	private static vhost[] hosts = new vhost[0];
	
	private static final String PATH = CommonInfo.webRootPath + File.separatorChar;
	private static final String defaultpath = PATH + CommonInfo.defaultRootPath;
	
	public final static void init() {
		if (!CommonInfo.vHostEnable) return;
		ConfArray[] ca = ReadConfig.readToConfArray(CommonInfo.virtualHost);
		hosts = new vhost[ca.length];
		int hInx = 0;
		for (int i=0; i<ca.length; ++i) {
			String sFile = PATH+ca[i].getSub(0);
			if (sFile!=null) {
				File f = new File(sFile);
				if (f.isDirectory()) {
					hosts[hInx] = new vhost();
					hosts[hInx].host = ca[i].getName();
					hosts[hInx].path = f;
					++hInx;
				}
			}
		}
	}
	
	/**
	 * ��������ͷ���host���ñ����ļ��е�ӳ��,һ���᷵����Ч��·��
	 * @param hha - ����ͷ
	 * @return - ����·��String;
	 */
	public final static String getVhost(HttpHeadAnalyse hha) {
		if (!CommonInfo.vHostEnable) return defaultpath;
		String host = hha.getHost();
		if (host!=null) {
			for (int i=0; i<hosts.length; ++i) {
				if (hosts[i]==null) break;
				if (hosts[i].host.compareToIgnoreCase(host)==0) {
					return hosts[i].path.getPath();
				}
			}
		}
		return defaultpath;
	}
}

/** �����������ݷ�װ */
class vhost {
	public String host;
	public File path;
}
