/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.commons.lang3.exception.ContextedException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * Base exception for all simple REST errors.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("serial")
public class SimpleRestException extends ContextedException {

    protected SimpleRestException(String message, Throwable cause,
            ExceptionContext context) {
        super(message, cause, context);
    }

    protected SimpleRestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected SimpleRestException(String message) {
        super(message);
    }

    protected SimpleRestException(Throwable cause) {
        super(cause);
    }

}
