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

import static com.anrisoftware.simplerest.core.AbstractRepeatSimpleGetWorkerLogger._.repeatRequestsSleep;

import javax.inject.Singleton;

import org.joda.time.Duration;

import com.anrisoftware.globalpom.log.AbstractLogger;

/**
 * Logging for {@link AbstractRepeatSimpleGetWorker}.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 0.4
 */
@Singleton
final class AbstractRepeatSimpleGetWorkerLogger extends AbstractLogger {

    enum _ {

        repeatRequestsSleep("Repeat response {} wait {} for {}");

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
     * Sets the context of the logger to {@link AbstractRepeatSimpleGetWorker}.
     */
    public AbstractRepeatSimpleGetWorkerLogger() {
        super(AbstractRepeatSimpleGetWorker.class);
    }

    void repeatRequestsSleep(Object parent, int count, Duration wait) {
        debug(repeatRequestsSleep, count, wait, parent);
    }

}
