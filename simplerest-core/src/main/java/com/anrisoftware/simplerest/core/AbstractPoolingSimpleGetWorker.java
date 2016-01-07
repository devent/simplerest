package com.anrisoftware.simplerest.core;

import java.net.URI;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Makes a GET request to the REST API using a pooled HTTP client.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractPoolingSimpleGetWorker<T> extends
        AbstractSimpleGetWorker<T> {

    private CloseableHttpClient httpClient;

    protected AbstractPoolingSimpleGetWorker(Object parent, URI requestUri,
            ParseResponse<T> parseResponse,
            ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, parseResponse, parseErrorResponse);
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    protected CloseableHttpClient createHttpClient() {
        return httpClient;
    }
}
