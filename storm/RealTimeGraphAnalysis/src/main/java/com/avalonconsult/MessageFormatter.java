package com.avalonconsult;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.avalonconsult.Formatter;

/**
 * Created by adam on 2/17/15.
 */
public class MessageFormatter implements Formatter {
    @Override
    public String format(ILoggingEvent event) {
        return event.getFormattedMessage();
    }
}
