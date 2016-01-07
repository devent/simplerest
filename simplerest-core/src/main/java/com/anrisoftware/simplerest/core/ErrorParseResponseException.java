/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.HttpResponse;

@SuppressWarnings("serial")
public class ErrorParseResponseException extends SimpleRestException {

    private static final String MESSAGE = "Error parse Json response";

    public ErrorParseResponseException(HttpResponse response, Exception cause) {
        super(MESSAGE, cause);
        addContextValue("HTTP-response", response);
    }

}
