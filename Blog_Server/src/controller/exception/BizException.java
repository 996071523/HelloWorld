package controller.exception;

/**
 * 异常类
 * @author Administrator
 *
 */
public class BizException extends Exception{

	
	private static final long serialVersionUID = 1L;//解除一个警告

	public BizException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BizException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BizException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
