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

import javax.inject.Inject;

import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.joda.time.Duration;

/**
 * Makes a GET request to the REST API and repeats the request on receiving the
 * 429/HTTP-too many requests code.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.4
 */
public abstract class AbstractRepeatSimpleGetWorker<T> extends
        AbstractSimpleGetWorker<T> {

    @Inject
    private AbstractRepeatSimpleGetWorkerLogger log;

    private Duration requestsRepeateWaitDuration;

    private Duration requestsRepeateWait;

    private int requestsRepeateCount;

    private int requestsRepeateMax;

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
    protected AbstractRepeatSimpleGetWorker(Object parent, URI requestUri,
            CloseableHttpClient httpClient, ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, httpClient, parseResponse, parseErrorResponse);
        this.requestsRepeateMax = 3;
        this.requestsRepeateWaitDuration = Duration.millis(500);
    }

    public void setRequestsRepeateWaitDuration(Duration duration) {
        this.requestsRepeateWaitDuration = duration;
    }

    public void setRequestsRepeateMax(int requestsRepeateMax) {
        this.requestsRepeateMax = requestsRepeateMax;
    }

    @Override
    public T retrieveData() throws SimpleRestException {
        resetRequestsRepeate();
        T data = super.retrieveData();
        resetRequestsRepeate();
        return data;
    }

    @Override
    protected T repeatRequest(CloseableHttpResponse previouslyResponse,
            HttpRequest previouslyRequest, StatusLine previouslyStatusLine)
            throws SimpleRestException {
        int repeateCount = requestsRepeateCount;
        int repeateMax = requestsRepeateMax;
        Duration repeateWait = requestsRepeateWait;
        if (repeateCount == repeateMax) {
            throw parseErrorMessage(previouslyRequest, previouslyResponse,
                    previouslyStatusLine);
        }
        try {
            log.repeatRequestsSleep(parent, repeateCount, repeateWait);
            Thread.sleep(repeateWait.getMillis());
        } catch (InterruptedException e) {
            return null;
        }
        CloseableHttpClient httpclient = createHttpClient();
        HttpGet httpget = createHttpGet();
        CloseableHttpResponse response = executeRequest(httpclient, httpget);
        StatusLine statusLine = response.getStatusLine();
        repeateCount++;
        repeateWait = repeateWait.plus(requestsRepeateWaitDuration);
        this.requestsRepeateCount = repeateCount;
        this.requestsRepeateWait = repeateWait;
        return parseResponse(response, httpget, statusLine);
    }

    private void resetRequestsRepeate() {
        this.requestsRepeateCount = 0;
        this.requestsRepeateWait = requestsRepeateWaitDuration;
    }
}
