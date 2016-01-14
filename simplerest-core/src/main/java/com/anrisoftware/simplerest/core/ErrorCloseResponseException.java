/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;

@SuppressWarnings("serial")
public class ErrorCloseResponseException extends SimpleRestException {

    private static final String MESSAGE = "Error close response";

    public ErrorCloseResponseException(CloseableHttpResponse response,
            IOException e) {
        super(MESSAGE, e);
        addContextValue("response", response);
    }

}
