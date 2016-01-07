/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import static com.anrisoftware.simplerest.core.BasicDefaultHeader.GZIP_COMPRESSION;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

/**
 * Makes a GET request to the REST API.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractSimpleGetWorker<T> {

    private final URI requestUri;

    private final Object parent;

    private final ParseResponse<T> parseResponse;

    private final ParseResponse<? extends Message> parseErrorResponse;

    private RestAccount account;

    private List<Header> headers;

    @Inject
    private AbstractSimpleRequestWorkerLogger log;

    protected AbstractSimpleGetWorker(Object parent, URI requestUri,
            ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        this.parent = parent;
        this.requestUri = requestUri;
        this.parseResponse = parseResponse;
        this.parseErrorResponse = parseErrorResponse;
        this.headers = new ArrayList<Header>();
    }

    public Object getParent() {
        return parent;
    }

    public void setAccount(RestAccount account) {
        this.account = account;
    }

    public RestAccount getAccount() {
        return account;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        headers.add(new BasicHeader(name, value));
    }

    public void setCompressed(boolean compressed) {
        if (compressed) {
            headers.add(GZIP_COMPRESSION.getHeader());
        } else {
            headers.remove(GZIP_COMPRESSION.getHeader());
        }
    }

    /**
     * Makes the request and retrieves the data.
     *
     * @return the data.
     *
     * @throws BadResponseException
     * @throws BadEntityFromResponseException
     * @throws ErrorParseResponseException
     * @throws ErrorExecuteRequestException
     * @throws ErrorCloseResponseException
     * @throws ErrorResponseDataException
     */
    public T retrieveData() throws BadResponseException,
            ErrorParseResponseException, ErrorExecuteRequestException,
            ErrorCloseResponseException, BadEntityFromResponseException,
            ErrorResponseDataException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpGet httpget = new HttpGet(requestUri);
        for (Header header : headers) {
            httpget.addHeader(header);
        }
        CloseableHttpResponse response = execureRequest(httpclient, httpget);
        StatusLine statusLine = response.getStatusLine();
        switch (statusLine.getStatusCode()) {
        case SC_OK:
            log.retrieveResponse(parent, response, httpget);
            return retrieveResponse(response);
        case SC_BAD_REQUEST:
            throw parseErrorMessage(httpget, response, statusLine);
        default:
            throw new ErrorResponseDataException(httpget, statusLine);
        }
    }

    /**
     * Creates the HTTP client.
     *
     * @return the {@link CloseableHttpClient} client.
     */
    protected CloseableHttpClient createHttpClient() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient;
    }

    private BadResponseException parseErrorMessage(HttpGet httpget,
            CloseableHttpResponse response, StatusLine statusLine) {
        try {
            Message message = parseErrorResponse.parse(response);
            return new BadResponseException(httpget, statusLine, message);
        } catch (IOException e) {
            return null;
        }
    }

    private CloseableHttpResponse execureRequest(
            CloseableHttpClient httpclient, HttpGet httpget)
            throws ErrorExecuteRequestException {
        try {
            return httpclient.execute(httpget);
        } catch (ClientProtocolException e) {
            throw new ErrorExecuteRequestException(httpclient, httpget, e);
        } catch (IOException e) {
            throw new ErrorExecuteRequestException(httpclient, httpget, e);
        }
    }

    private T retrieveResponse(CloseableHttpResponse response)
            throws ErrorParseResponseException, ErrorCloseResponseException,
            BadEntityFromResponseException {
        T data;
        try {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new BadEntityFromResponseException(response);
            }
            try {
                log.parseResponse(parent, response);
                data = parseResponse.parse(response);
            } catch (IOException e) {
                throw new ErrorParseResponseException(response, e);
            }
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                throw new ErrorCloseResponseException(response, e);
            }
        }
        return data;
    }

}
