/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import static com.anrisoftware.simplerest.core.AbstractSimpleRequestWorkerLogger._.parse_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleRequestWorkerLogger._.retrieve_get_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleRequestWorkerLogger._.retrieve_response;

import javax.inject.Singleton;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractSimpleGetWorker}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
final class AbstractSimpleRequestWorkerLogger extends AbstractLogger {

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
     * Sets the context of the logger to {@link AbstractSimpleGetWorker}.
     */
    public AbstractSimpleRequestWorkerLogger() {
        super(AbstractSimpleGetWorker.class);
    }

    void retrieveResponse(Object parent, CloseableHttpResponse response,
            HttpGet httpget) {
        if (isTraceEnabled()) {
            trace(retrieve_get_response, httpget, response, parent);
        } else {
            debug(retrieve_response, response, parent);
        }
    }

    void parseResponse(Object parent, CloseableHttpResponse response) {
        debug(parse_response, response, parent);
    }

}
