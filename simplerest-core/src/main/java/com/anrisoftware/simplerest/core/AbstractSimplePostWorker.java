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

    /**
     *
     * @param parent
     *            the worker {@link Object} parent.
     *
     * @param requestUri
     *            the requests {@link URI} URI.
     *
     * @param httpClient
     *            the {@link CloseableHttpClient} HTTP client or {@code null}.
     *
     * @param parseResponse
     *            the {@link ParseResponse} to parse the response.
     *
     * @param parseErrorResponse
     *            the {@link ParseResponse} to parse the error response.
     */
    protected AbstractSimplePostWorker(Object parent, URI requestUri,
            CloseableHttpClient httpClient, ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, httpClient, parseResponse, parseErrorResponse);
    }

    /**
     * Makes the request and sends the data and retrieves the response.
     *
     * @param entity
     *            the {@link HttpEntity} entity.
     *
     * @return the response.
     *
     * @throws SimpleRestException
     */
    public T sendData(HttpEntity entity) throws SimpleRestException {
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
