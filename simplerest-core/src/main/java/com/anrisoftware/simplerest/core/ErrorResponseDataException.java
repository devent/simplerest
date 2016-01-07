/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;

@SuppressWarnings("serial")
public class ErrorResponseDataException extends SimpleRestException {

    private static final String MESSAGE = "Error response";

    public ErrorResponseDataException(HttpGet httpget, StatusLine statusLine) {
        super(MESSAGE);
        addContextValue("HTTP-GET", httpget);
        addContextValue("response", statusLine);
    }

}
