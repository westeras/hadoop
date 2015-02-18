package com.avalonconsult;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Created by adam on 2/17/15.
 */
public interface Formatter {
    String format(ILoggingEvent event);
}
