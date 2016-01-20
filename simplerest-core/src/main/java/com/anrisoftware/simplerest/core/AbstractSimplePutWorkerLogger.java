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

import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.parse_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.retrieve_get_response;
import static com.anrisoftware.simplerest.core.AbstractSimpleWorkerLogger._.retrieve_response;

import javax.inject.Singleton;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractSimplePutWorker}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 1.0
 */
@Singleton
final class AbstractSimplePutWorkerLogger extends AbstractLogger {

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
     * Sets the context of the logger to {@link AbstractSimplePutWorker}.
     */
    public AbstractSimplePutWorkerLogger() {
        super(AbstractSimplePutWorker.class);
    }

    void retrieveResponse(Object parent, CloseableHttpResponse response,
            HttpPut httpput) {
        if (isTraceEnabled()) {
            trace(retrieve_get_response, httpput, response, parent);
        } else {
            debug(retrieve_response, response, parent);
        }
    }

    void parseResponse(Object parent, CloseableHttpResponse response) {
        debug(parse_response, response, parent);
    }

}
