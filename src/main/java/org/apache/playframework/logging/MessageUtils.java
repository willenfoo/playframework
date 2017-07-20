package org.apache.playframework.logging;

import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.MessageFactory2;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.MessageFactory2Adapter;

public class MessageUtils {

	private static MessageFactory2 messageFactory;
	
	static {
		messageFactory = createDefaultMessageFactory();
	}
	
	private static MessageFactory2 createDefaultMessageFactory() {
        try {
            final MessageFactory result = AbstractLogger.DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
            return narrow(result);
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
	
	private static MessageFactory2 narrow(final MessageFactory result) {
        if (result instanceof MessageFactory2) {
            return (MessageFactory2) result;
        }
        return new MessageFactory2Adapter(result);
    }
	
	public static String formatMessage(final String msgPattern, final Object... params) {
		return messageFactory.newMessage(msgPattern, params).getFormattedMessage();
    }
}
