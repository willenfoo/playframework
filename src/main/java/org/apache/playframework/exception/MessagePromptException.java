package org.apache.playframework.exception;

/**
 * Runtime exception for XML handling.
 * 
 * @author carver
 * @since 1.0, Jun 12, 2007
 */
public class MessagePromptException extends RuntimeException {

	private static final long serialVersionUID = 381260478228427716L;

	private String code;
	
	private String message;
	
	public MessagePromptException(String code) {
		this.code = code;
	}
	
	public MessagePromptException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
