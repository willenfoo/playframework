package org.apache.playframework.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
 

public class ResponseUtils {
	
	/**
	 *回调Js
	 */
	public static void callBackJs(HttpServletResponse response, String methodParameter,boolean parent) {
		String text = null;
		if (parent) {
			text = "<script>window.parent."+methodParameter+";</script>";
		} else {
			text = "<script>window."+methodParameter+";</script>";
		}
		renderHtml(response, text);
	}
	
	/**
	 * 回调Js
	 */
	public static void callBackJs(HttpServletResponse response, String methodParameter) {
		callBackJs(response, methodParameter, false);
	}

	/**
	 * 直接输出纯HTML
	 */
	public static void renderHtml(HttpServletResponse response, String text) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			//          不加上这几句，就不能刷新数据
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setContentType("text/html;charset=UTF-8");
			out.write(text);
		} catch (IOException e) {
			 
		} finally {
			if (out != null) {
				out.flush();
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 直接输出纯XML
	 */
	public static void renderXML(HttpServletResponse response, String text) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			//          不加上这几句，就不能刷新数据
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setContentType("text/xml;charset=UTF-8");
			out.write(text);
		} catch (IOException e) {
			 
		} finally {
			if (out != null) {
				out.flush();
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 直接输出纯字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			//          不加上这几句，就不能刷新数据
			response.setHeader("Cache-Control", "no-cache, must-revalidate");
			response.setContentType("text/plain;charset=UTF-8");
			out.write(text);
		} catch (IOException e) {
			 
		} finally {
			if (out != null) {
				out.flush();
				out.close();
				out = null;
			}
		}
	}

}
