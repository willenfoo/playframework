package org.apache.playframework.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.playframework.util.Request;

/**
 * logback，日志关键字
 */
public class LogKeywordConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        return Request.getLogKeyword();
    }
    
}