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

import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Makes a PUT request to the REST API.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractSimplePutWorker<T> extends
        AbstractSimpleWorker<T> {

    protected AbstractSimplePutWorker(Object parent, URI requestUri,
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
        HttpPut httpput = new HttpPut(requestUri);
        httpput.setEntity(entity);
        for (Header header : headers) {
            httpput.addHeader(header);
        }
        CloseableHttpResponse response = executeRequest(httpclient, httpput);
        StatusLine statusLine = response.getStatusLine();
        return parseResponse(response, httpput, statusLine);
    }

}
