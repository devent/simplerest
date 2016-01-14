/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.parse_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.retrieve_get_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.retrieve_response;

import javax.inject.Singleton;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractSimpleWorker}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
final class AbstractSimpleWorkerLogger extends AbstractLogger {

    enum _ {

        retrieve_response("Retrieve response {} for {}"),

        retrieve_get_response("Retrieve for get {} response {} for {}"),

        parse_response("Parse response {} for {}");

        private String name;

        private _(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Sets the context of the logger to {@link AbstractSimpleWorker}.
     */
    public AbstractSimpleWorkerLogger() {
        super(AbstractSimpleWorker.class);
    }

    void retrieveResponse(Object parent, CloseableHttpResponse response,
            HttpRequest request) {
        if (isTraceEnabled()) {
            trace(retrieve_get_response, request, response, parent);
        } else {
            debug(retrieve_response, response, parent);
        }
    }

    void parseResponse(Object parent, CloseableHttpResponse response) {
        debug(parse_response, response, parent);
    }

}
