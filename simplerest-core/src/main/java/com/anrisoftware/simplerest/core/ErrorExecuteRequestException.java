/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

@SuppressWarnings("serial")
public class ErrorExecuteRequestException extends SimpleRestException {

    private static final String HTTP_REQUEST_LABEL = "HTTP-request";

    private static final String HTTP_CLIENT_LABEL = "HTTP-client";

    private static final String MESSAGE = "Error execute request";

    public ErrorExecuteRequestException(CloseableHttpClient httpclient, HttpGet httpget,
            Exception cause) {
        super(MESSAGE, cause);
        addContextValue(HTTP_CLIENT_LABEL, httpclient);
        addContextValue(HTTP_REQUEST_LABEL, httpget);
    }

}
