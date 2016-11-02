package org.apache.playframework.log;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;



public class LogUtils {
	
	public static void error(Logger logger,String message){
		logger.error( message);
	}
	
	public static void error(Logger logger,Exception ex){
		logger.error( estacktack2Str(ex));
	}
	
	public static String estacktack2Str(Exception ex){
		PrintStream ps = null;
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ps = new PrintStream(bao);
			ex.printStackTrace(ps);
			return bao.toString("utf-8");
		} catch (UnsupportedEncodingException e) {
			return e.getMessage();
		} finally {
			if(ps!=null){
				ps.close();
			}
		}
	}
	
	public static void trace(Logger logger,String message){
		logger.info( message);
	}
	
	public static void timeused(Logger logger,String point,long start){
		logger.info(point + " timeused: "+(System.currentTimeMillis()-start));
	}
}
