/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;

@SuppressWarnings("serial")
public class ErrorExecuteRequestException extends SimpleRestException {

    private static final String MESSAGE = "Error execute request";

    public ErrorExecuteRequestException(CloseableHttpClient httpclient,
            HttpRequest request, Exception cause) {
        super(MESSAGE, cause);
        addContextValue("client", httpclient);
        addContextValue("request", request);
    }

}
