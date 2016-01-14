/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;

@SuppressWarnings("serial")
public class ErrorResponseDataException extends SimpleRestException {

    private static final String MESSAGE = "Error response";

    public ErrorResponseDataException(HttpRequest request, StatusLine statusLine) {
        super(MESSAGE);
        addContextValue("requests", request);
        addContextValue("response", statusLine);
    }

}
