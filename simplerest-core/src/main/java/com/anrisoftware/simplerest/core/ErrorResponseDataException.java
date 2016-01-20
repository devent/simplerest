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

import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;

@SuppressWarnings("serial")
public class ErrorResponseDataException extends SimpleRestException {

    private static final String MESSAGE = "Error response";

    public ErrorResponseDataException(HttpRequest request, StatusLine statusLine) {
        super(MESSAGE);
        addContextValue("requests", request);
        addContextValue("response", statusLine);
    }

}
