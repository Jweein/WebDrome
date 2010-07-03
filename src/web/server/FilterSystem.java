package web.server;
// CatfoOD 2008.3.25

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * �ļ�������,���� CommonInfo.exclude ��ָ�����ļ����ع�����,
 * ������ֻ����ͨ���ļ������й�������,�������˶�cgi�ļ�������.
 * <pre>
 * �����ļ��ĸ�ʽ:
 *	ע������:"# | // | ;"��ʼ
 *	ÿ�а���Ҫ�˳�����չ��,�����ִ�Сд
 * <pre>
 */
public final class FilterSystem {
	private static String[] exclude = new String[0];
	
	public final static void init() {
		if (!CommonInfo.filterEnable) return;
		ConfArray[] ca = ReadConfig.readToConfArray(CommonInfo.exclude);

		exclude = new String[ca.length];
		for (int i=0; i<exclude.length; ++i) {
			exclude[i] = ca[i].getName();
		}
	}
	
	/**
	 * �����ַ���
	 * @param s - Ҫ���˵��ַ���
	 * @throws FilterException - �����˹ؼ������ַ����г���,�׳�����쳣
	 */
	public final static void exclude(String s) throws FilterException {
		if (!CommonInfo.filterEnable) return;
		for (int i=0; i<exclude.length; ++i) {
			if (s.toLowerCase().endsWith( exclude[i].toLowerCase() )) {
				throw new FilterException(s+" "+Language.fileinFilterListRebut+".");
			}
		}
	}
	
	/**
	 * �����ļ�
	 * @param s - Ҫ���˵��ļ�
	 * @throws Exception - �����˹ؼ������ļ����г���,�׳�����쳣
	 */
	public final static void exclude(File f) throws FilterException {
		if (!CommonInfo.filterEnable) return;
		exclude(f.getName());
	}

	/**
	 * �ַ����Ƿ���ע����
	 * @param s - Ҫ���Ե��ַ���
	 * @return ��ע���з���true
	 */
	private final static boolean isCommentary(String s) {
		final String[] comm = {"//", "#", ";"};
		return (s.startsWith(comm[0])||
				s.startsWith(comm[1])||
				s.startsWith(comm[2]) );
	}
}
