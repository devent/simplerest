/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-oanda-rest.
 *
 * simplerest-oanda-rest is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-oanda-rest is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-oanda-rest. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.oanda.rest;

import static com.anrisoftware.simplerest.oanda.rest.SimpleRequestWorkerLogger._.parsed_objects;

import javax.inject.Singleton;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link SimpleRequestWorker}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 0.3
 */
@Singleton
final class SimpleRequestWorkerLogger extends AbstractLogger {

    enum _ {

        parsed_objects("Parsed {} objects for {}");

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
     * Sets the context of the logger to {@link SimpleRequestWorker}.
     */
    public SimpleRequestWorkerLogger() {
        super(SimpleRequestWorker.class);
    }

    void parsedObjects(Object parent, int size) {
        debug(parsed_objects, size, parent);
    }

}
