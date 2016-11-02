package org.apache.playframework.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.playframework.util.Request;

/**
 * The Slf4jLogger implementation of Logger.
 */
public class Slf4jLogger implements Logger, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Slf4jLogger(org.slf4j.Logger impl) {
		_impl = impl;
	}

	
	public String getName() {
		return _impl.getName();
	}

	
	public void trace(String message) {
		_impl.trace(Request.getId() + message);
	}

	
	public void trace(String format, Object... args) {
		_impl.trace(Request.getId() + format, args);
	}

	
	public boolean isTraceEnabled() {
		return _impl.isTraceEnabled();
	}

	
	public void debug(String message) {
		_impl.debug(Request.getId() + message);
	}

	
	public void debug(String format, Object... args) {
		_impl.debug(Request.getId() + format, args);
	}

	
	public boolean isDebugEnabled() {
		return _impl.isDebugEnabled();
	}

	
	public void info(String message) {
		_impl.info(Request.getId() + message);
	}

	
	public void info(String format, Object... args) {
		_impl.info(Request.getId() + format, args);
	}

	
	public boolean isInfoEnabled() {
		return _impl.isInfoEnabled();
	}

	
	public void warn(String message) {
		_impl.warn(Request.getId() + message);
	}

	
	public void warn(String format, Object... args) {
		_impl.warn(Request.getId() + format, args);
	}

	
	public boolean isWarnEnabled() {
		return _impl.isWarnEnabled();
	}

	
	public void error(String message) {
		_impl.error(Request.getId() + message);
	}

	
	public void error(String format, Object... args) {
		_impl.error(Request.getId() + format, args);
	}

	
	public void error(Exception ex) {
		_impl.error(Request.getId(), ex);
	}

	
	public void error(String message, Exception ex) {
		_impl.error(Request.getId() + message, ex);
	}

	public static String estacktack2Str(Exception ex) {
		PrintStream ps = null;
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ps = new PrintStream(bao);
			ex.printStackTrace(ps);
			return bao.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}

	
	public boolean isErrorEnabled() {
		return _impl.isErrorEnabled();
	}

	//
	// private fields
	//

	private org.slf4j.Logger _impl;
}
