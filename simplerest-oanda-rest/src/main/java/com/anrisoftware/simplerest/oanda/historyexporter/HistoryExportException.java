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
package com.anrisoftware.simplerest.oanda.historyexporter;

import java.io.IOException;

import org.apache.commons.lang3.exception.DefaultExceptionContext;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * Exception thrown if there was an error exporting the history data.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 0.3
 */
@SuppressWarnings("serial")
public class HistoryExportException extends IOException {

    private final ExceptionContext exceptionContext;

    /**
     * @see Exception#Exception(String, Throwable)
     */
    protected HistoryExportException(String message, Throwable cause) {
        super(message, cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see Exception#Exception(String)
     */
    protected HistoryExportException(String message) {
        super(message);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see Exception#Exception(String, Throwable)
     */
    protected HistoryExportException(Object message, Throwable cause) {
        super(message.toString(), cause);
        this.exceptionContext = new DefaultExceptionContext();
    }

    /**
     * @see Exception#Exception(String)
     */
    protected HistoryExportException(Object message) {
        super(message.toString());
        this.exceptionContext = new DefaultExceptionContext();
    }

    public HistoryExportException addContextValue(String label, Object value) {
        exceptionContext.addContextValue(label, value);
        return this;
    }

    @Override
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    public String getFormattedExceptionMessage(String baseMessage) {
        return exceptionContext.getFormattedExceptionMessage(baseMessage);
    }

}
