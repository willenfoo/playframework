package org.apache.playframework.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.apache.playframework.util.Request;

/**
 * logback日志
 */
public class MsgIdConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        return Request.getId();
    }
}