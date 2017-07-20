package org.apache.playframework.logging;


import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.playframework.util.Request;

/**
 * Created by hotusm 2017/2/16.
 */
@Plugin(name = "MsgIdPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"msgId" })
public class MsgIdPatternConverter extends LogEventPatternConverter {

    private static final MsgIdPatternConverter INSTANCE = new MsgIdPatternConverter();

    public static MsgIdPatternConverter newInstance(
            final String[] options) {
        return INSTANCE;
    }

    private MsgIdPatternConverter(){
    	super("MsgId", "msgId");
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        toAppendTo.append(Request.getId());
    }

}
