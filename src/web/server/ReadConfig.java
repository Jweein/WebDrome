package web.server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// CatfoOD 2008.3.28

/**
 * ��ȡ�����ļ�����Ĺ�����
 * 
 * �����ļ��еĸ�ʽ:(BNF)
 * 		ע����	= "# | // | ;" CHAR
 * 		ÿ����Ч��= ������ *SP ��ֵ
 * 		������	= *CHAR
 * 		��ֵ		= *CHAR
 */
public class ReadConfig {

	private final static String[] comm = {"//", "#", ";"};
	private final static String PATH = CommonInfo.systemPath + File.separatorChar;
	private Class c;
	private Object ref;
	
	/**
	 * �����õ��������Ϊ�Ǿ�̬������
	 * @param cla - Ҫ�����õ���
	 */
	public ReadConfig(Class cla) {
		c = cla;
		ref = null;
	}
	
	/**
	 * �����õ��������Ϊ��ʵ��������
	 * @param o - Ҫ�����õ�ʵ��
	 */
	public ReadConfig(Object o) {
		c = o.getClass();
		ref = o;
	}
	
	/**
	 * �����õ��������Ϊ�Ǿ�̬������
	 * @param cla - Ҫ�������������
	 */
	public ReadConfig(String s) throws ClassNotFoundException {
		c = Class.forName(s);
		ref = null;
	}
	
	/**
	 * ��ȡ�����ļ�����������,��ʹ��Ĭ�ϵ�ϵͳ�ļ�·��,�ο�Ĭ�ϵ�ϵͳ·��CommonInfo.systemPath
	 * @param filename �����ļ���
	 */
	public static final ConfArray[] readToConfArray(String filename) {
		return readToConfArray(filename, true);
	}
	
	/** 
	 * ��ȡ�����ļ�����������
	 * @param defaultpath - ���Ϊtrue,ʹ��Ĭ�ϵ�ϵͳ����·���ο�CommonInfo.systemPath<br>
	 *						����,·���Ӱ�װ�ļ��п�ʼ����
	 */
	public static final ConfArray[] 
	readToConfArray(String filename, boolean defaultpath) 
	{
		String finalFile;
		if (defaultpath) {
			finalFile = PATH+filename;
		} else {
			finalFile = filename;
		}
		return readToConfArray(new File(finalFile));
	}
	
	/** ��ȡ�����ļ����������� */
	public static final ConfArray[] readToConfArray(File conffile) {
		try{
			ArrayList list = new ArrayList();
			
			BufferedReader in = new BufferedReader(
								new FileReader( conffile ) );
			String s = in.readLine();
			while (s!=null) {
				String[] conf = s.split("\t| ");
				if (conf.length>0 && 
					(!isCommentary(conf[0])) && conf[0].trim().length()>0) 
				{
					ConfArray ca = new ConfArray(conf[0]);
					int next = 1;
					while (next<conf.length) {
						if (conf[next].length()!=0) {
							ca.add(conf[next]);
						}
						++next;
					}
					list.add(ca);
				}
				s = in.readLine();
			}
			// ��������
			ConfArray[] confarr = new ConfArray[list.size()];
			for (int i=0; i<confarr.length; ++i) {
				confarr[i] = (ConfArray)list.get(i);
			}
			list.clear();
			return confarr;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ConfArray[0];
	}
	
	/**
	 * ��ָ�����ļ���ȡ���õ�������,�����ļ���·������CommonInfo.systemPath����
	 * @param s - �ļ����ļ��������׳�����쳣
	 * @throws FileNotFoundException
	 */
	public void readFromfile(String s) throws FileNotFoundException 
	{
		readFromfile(s, true, false);
	}
	
	/**
	 * ��ָ�����ļ���ȡ���õ�������,�����ļ���·������CommonInfo.systemPath����
	 * @param s - �ļ���
	 * @param defaultFile - ���Ϊfalse,��ʹ��Ĭ�ϵ������ļ�·��
	 * @param unitePare - �Ƿ��ÿһ�г���������Ĳ����ϲ�Ϊһ������
	 * @throws FileNotFoundException - �ļ��������׳�����쳣
	 */
	public void readFromfile(String s, boolean defaultFile, boolean unitePare) 
	throws FileNotFoundException 
	{
		File f;
		if (defaultFile) {
			f = new File(PATH+s);
		} else {
			f = new File(s);
		}
		if (!f.isFile()) throw new FileNotFoundException(f.toString());
		
		read(f, (unitePare? 2 : 0));
	}
	
	private void read(File confFile, int splitLimit) {
		try {
		BufferedReader in = new BufferedReader(
							new FileReader( confFile ) );
		String s = in.readLine();
		while (s!=null) {
			String[] conf = s.split("\t| ",splitLimit);
			if (conf.length>1 && (!isCommentary(conf[0])) ) {
				try {
					int next = 0;
					while (conf[++next].length()==0);
					set(conf[0], conf[next].trim());
				}catch(Exception e) {
					LogSystem.error(confFile.getName()+
							Language.unsupportConfigCommand+":"+
							conf[0]+"\n"+e);
				}
			}
			s = in.readLine();
		}
		} catch(Exception ee) {
			System.out.println(ee);
		}
	}
	
	/**
	 * ��ָ����������Ϊָ����ֵ
	 * @param parmname - ����
	 * @param value - ֵ
	 * @throws Exception - �����ڵ���,���׳�����쳣
	 */
	private final void set(String parmname, String value ) throws Exception {
		Field fs = c.getDeclaredField(parmname.trim());
		set(fs, value);
	}
	
	private final void set(Field f, String s) throws Exception {
		Object o = null;
		switch ( typeMap(f) ) {
		case 0:
			o = Boolean.valueOf(s); break;
		case 1:
			o = Byte.valueOf(s); break;
		case 2:
		case 3:
			// �ݲ�֧�� char short
			break;
		case 4:
			o = Integer.valueOf(s); break;
		case 5:
			o = Long.valueOf(s); break;
		case 6:
			o = Float.valueOf(s); break;
		case 7:
			o = Double.valueOf(s); break;
		default:
			o = s;
		}
		if (o==null) throw new Exception();
		f.set(ref, o);
	}

	private final int typeMap(Field f) throws Exception {
		String s = f.getType().toString();
		final String[] type = { "boolean", "byte", "char",  "short", 
								"int",     "long", "float", "double", 
								"class java.lang.String"};
		for (int i=0; i<type.length; ++i) {
			if (s.equalsIgnoreCase(type[i])) return i;
		}
		throw new Exception(Language.unsupporttype+":"+s);
	}
	
	/**
	 * �ַ����Ƿ���ע����
	 * @param s - Ҫ���Ե��ַ���
	 * @return ��ע���з���true
	 */
	private final static boolean isCommentary(String s) {
		return (s.startsWith(comm[0])||
				s.startsWith(comm[1])||
				s.startsWith(comm[2]) );
	}
}

/**
 * �������ݰ�װ��
 */
final class ConfArray {
	/** ���õ����� */
	private String name;
	/** ���õ�����,Сд */
	private List sub;
	
	public ConfArray(String mainName) {
		sub = new ArrayList();
		name = mainName;
	}
	/** �ҵ�ָ�������ݷ���true */
	public boolean findSub(String key) {
		return sub.contains(key.toLowerCase());
	}
	/** ���һ�����ת��ΪСд */
	public void add(String key) {
		sub.add(key.toLowerCase());
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * �õ�ָ��λ�õ��Ӽ�
	 * @param index - ����
	 * @return ����������Χ����null,���򷵻�����������Ŀ
	 */
	public String getSub(int index) {
		if (index<sub.size() && index>=0) {
			return (String)sub.get(index);
		}else{
			return null;
		}
	}
	
	/** ���ص�һ������ */
	public String getSub() {
		return getSub(0);
	}
	
	public int getSize() {
		return sub.size();
	}
	
	public String[] getSubs() {
		Object[] os = sub.toArray();
		String[] s = new String[os.length];
		for (int i=0; i<s.length; ++i) {
			s[i] = os[i].toString();
		}
		return s;
	}
	
	public String toString() {
		return name + sub.toString();
	}
}
