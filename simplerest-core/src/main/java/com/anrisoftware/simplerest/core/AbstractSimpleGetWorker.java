/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Makes a GET request to the REST API.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractSimpleGetWorker<T> extends
        AbstractSimpleWorker<T> {

    protected AbstractSimpleGetWorker(Object parent, URI requestUri,
            ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, parseResponse, parseErrorResponse);
    }

    /**
     * Makes the request and retrieves the data.
     *
     * @return the data.
     *
     * @throws BadResponseException
     * @throws ErrorParseResponseException
     * @throws ErrorExecuteRequestException
     * @throws ErrorCloseResponseException
     * @throws ErrorResponseDataException
     */
    public T retrieveData() throws BadResponseException,
            ErrorParseResponseException, ErrorExecuteRequestException,
            ErrorCloseResponseException, ErrorResponseDataException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpGet httpget = new HttpGet(requestUri);
        for (Header header : headers) {
            httpget.addHeader(header);
        }
        CloseableHttpResponse response = executeRequest(httpclient, httpget);
        StatusLine statusLine = response.getStatusLine();
        return parseResponse(response, httpget, statusLine);
    }

}