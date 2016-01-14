/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;

@SuppressWarnings("serial")
public final class BadResponseException extends SimpleRestException {

    private static final String MESSAGE = "Bad response";

    public BadResponseException(HttpRequest request, StatusLine statusLine,
            Message message) {
        super(MESSAGE);
        addContextValue("request", request);
        addContextValue("response", statusLine);
        addContextValue("message", message);
    }

}
