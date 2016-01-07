/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import org.apache.http.client.methods.CloseableHttpResponse;

@SuppressWarnings("serial")
public class BadEntityFromResponseException extends SimpleRestException {

    private static final String HTTP_RESPONSE_LABEL = "HTTP-response";

    private static final String MESSAGE = "No entity from response";

    public BadEntityFromResponseException(CloseableHttpResponse response) {
        super(MESSAGE);
        addContextValue(HTTP_RESPONSE_LABEL, response);
    }

}
