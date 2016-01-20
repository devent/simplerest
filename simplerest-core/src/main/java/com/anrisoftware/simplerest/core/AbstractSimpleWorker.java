/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-core.
 *
 * simplerest-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.core;

import static com.anrisoftware.simplerest.core.BasicDefaultHeader.GZIP_COMPRESSION;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class AbstractSimpleWorker<T> {

    protected final URI requestUri;

    protected final Object parent;

    protected final ParseResponse<T> parseResponse;

    protected final ParseResponse<? extends Message> parseErrorResponse;

    protected RestAccount account;

    protected List<Header> headers;

    @Inject
    private AbstractSimpleWorkerLogger log;

    protected AbstractSimpleWorker(Object parent, URI requestUri,
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
     * Creates the HTTP client.
     *
     * @return the {@link CloseableHttpClient} client.
     */
    protected CloseableHttpClient createHttpClient() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient;
    }

    /**
     * Parse the response.
     *
     * @param response
     *            the {@link CloseableHttpResponse}.
     *
     * @param request
     *            the {@link HttpRequest}.
     *
     * @param statusLine
     *            the {@link StatusLine}.
     *
     * @return the response or {@code null} if the status code equals to
     *         SC_NO_CONTENT.
     *
     * @throws BadResponseException
     * @throws ErrorParseResponseException
     * @throws ErrorCloseResponseException
     * @throws ErrorResponseDataException
     */
    protected T parseResponse(CloseableHttpResponse response,
            HttpRequest request, StatusLine statusLine)
            throws BadResponseException, ErrorParseResponseException,
            ErrorCloseResponseException, ErrorResponseDataException {
        log.retrieveResponse(parent, response, request);
        switch (statusLine.getStatusCode()) {
        case SC_NO_CONTENT:
            return null;
        case SC_BAD_REQUEST:
            throw parseErrorMessage(request, response, statusLine);
        default:
            if (statusLine.getStatusCode() >= SC_OK
                    && statusLine.getStatusCode() < 300) {
                return retrieveResponse(response);
            }
        }
        throw new ErrorResponseDataException(request, statusLine);
    }

    /**
     * Execute the request.
     *
     * @param httpclient
     *            the {@link CloseableHttpClient}.
     *
     * @param request
     *            the {@link HttpRequest}.
     * @return the {@link CloseableHttpResponse}.
     *
     * @throws ErrorExecuteRequestException
     */
    protected CloseableHttpResponse executeRequest(
            CloseableHttpClient httpclient, HttpUriRequest request)
            throws ErrorExecuteRequestException {
        try {
            return httpclient.execute(request);
        } catch (ClientProtocolException e) {
            throw new ErrorExecuteRequestException(httpclient, request, e);
        } catch (IOException e) {
            throw new ErrorExecuteRequestException(httpclient, request, e);
        }
    }

    private BadResponseException parseErrorMessage(HttpRequest request,
            CloseableHttpResponse response, StatusLine statusLine) {
        try {
            Message message = parseErrorResponse.parse(response);
            return new BadResponseException(request, statusLine, message);
        } catch (IOException e) {
            return null;
        }
    }

    private T retrieveResponse(CloseableHttpResponse response)
            throws ErrorParseResponseException, ErrorCloseResponseException {
        T data;
        try {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
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
