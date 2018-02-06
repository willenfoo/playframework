package org.apache.playframework.exception;

import org.apache.playframework.enums.ErrorCode;

/**
 * Runtime exception for XML handling.
 * 
 * @author carver
 * @since 1.0, Jun 12, 2007
 */
public class RestServiceException extends RuntimeException {

	private static final long serialVersionUID = 381260478228427716L;

	private String code;
	
	private String message;

	public RestServiceException(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMsg();
	}

	public RestServiceException(String code) {
		this.code = code;
	}
	
	public RestServiceException(String code, String message) {
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

}
