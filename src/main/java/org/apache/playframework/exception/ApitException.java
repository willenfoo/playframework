package org.apache.playframework.exception;

import org.apache.playframework.enums.ErrorCode;

/**
 * Runtime exception for XML handling.
 * 
 * @author carver
 * @since 1.0, Jun 12, 2007
 */
public class ApitException extends RuntimeException {

	private static final long serialVersionUID = 381260478228427716L;

	private String code;
	
	private String message;

	private ErrorCode errorCode;

	public ApitException(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMsg();
		this.errorCode = errorCode;
	}

	public ApitException(String code) {
		this.code = code;
	}
	
	public ApitException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
