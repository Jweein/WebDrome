package web.server;
// CatfoOD 2008-3-30

public abstract class HTTPException extends Exception {
	private String errorMessage;
	
	public HTTPException(String mess) {
		errorMessage = mess;
	}
	public HTTPException() {
		errorMessage = "HTTP Error";
	}
	public String toString() {
		return errorMessage;
	}
	/**
	 * ÿ���̳еĴ��������ʵ���������,��ȷ�������ԭ��
	 * @return Http�Ĵ������,�ο�<b>RequestErrCode</b>
	 */
	public abstract int getHttpErrorCode();
}

/** �����Range��ʽ */
class BadRangeException extends HTTPException {
	public BadRangeException() {}
	public BadRangeException(String s) {
		super(s);
	}

	public int getHttpErrorCode() {
		return RequestErrCode.E416;
	}
}

/** Post�������� */
class CgiCannotSupport extends HTTPException {
	public CgiCannotSupport() {}
	public CgiCannotSupport(String s) {
		super(s);
	}

	public int getHttpErrorCode() {
		return RequestErrCode.E406;
	}
}

/** ���˴��� */
class FilterException extends HTTPException {
	public FilterException() {}
	public FilterException(String s) {
		super(s);
	}

	public int getHttpErrorCode() {
		return RequestErrCode.E403;
	}
}

/** ��CGI�������ʱ�׳�����쳣 */
class CgiRequestException extends HTTPException {
	public CgiRequestException() {}
	public CgiRequestException(String s) {
		super(s);
	}

	public int getHttpErrorCode() {
		return RequestErrCode.E400;
	}
}

class CgiTimeOutException extends HTTPException {
	public CgiTimeOutException() {}
	public CgiTimeOutException(String s) {
		super(s);
	}

	public int getHttpErrorCode() {
		return RequestErrCode.E504;
	}
}

