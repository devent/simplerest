package com.anrisoftware.simplerest.core;

import java.net.URI;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Makes a PUT request to the REST API using a pooled HTTP client.
 *
 * @param <T>
 *            the type of the requests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public abstract class AbstractPoolingSimplePutWorker<T> extends
        AbstractSimplePutWorker<T> {

    private CloseableHttpClient httpClient;

    protected AbstractPoolingSimplePutWorker(Object parent, URI requestUri,
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
