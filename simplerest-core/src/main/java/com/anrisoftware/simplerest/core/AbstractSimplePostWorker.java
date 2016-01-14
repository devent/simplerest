/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Makes a POST request to the REST API.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractSimplePostWorker<T> extends
        AbstractSimpleWorker<T> {

    protected AbstractSimplePostWorker(Object parent, URI requestUri,
            ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, parseResponse, parseErrorResponse);
    }

    /**
     * Makes the request and sends the data and retrieves the response.
     *
     * @param entity
     *            the {@link HttpEntity} entity.
     *
     * @return the response.
     *
     * @throws BadResponseException
     * @throws ErrorParseResponseException
     * @throws ErrorExecuteRequestException
     * @throws ErrorCloseResponseException
     * @throws ErrorResponseDataException
     */
    public T sendData(HttpEntity entity) throws BadResponseException,
            ErrorParseResponseException, ErrorExecuteRequestException,
            ErrorCloseResponseException, ErrorResponseDataException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpPost httpput = new HttpPost(requestUri);
        httpput.setEntity(entity);
        for (Header header : headers) {
            httpput.addHeader(header);
        }
        CloseableHttpResponse response = executeRequest(httpclient, httpput);
        StatusLine statusLine = response.getStatusLine();
        return parseResponse(response, httpput, statusLine);
    }

    public HttpEntity createPostParametersEntity(
            List<NameValuePair> postParameters)
            throws ErrorCreatePostParametersEntity {
        try {
            return new UrlEncodedFormEntity(postParameters);
        } catch (UnsupportedEncodingException e) {
            throw new ErrorCreatePostParametersEntity(e);
        }
    }

}
