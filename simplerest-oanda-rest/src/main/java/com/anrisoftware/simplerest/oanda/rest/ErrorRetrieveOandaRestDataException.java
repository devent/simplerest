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

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.oanda.api.OandaErrorException;

@SuppressWarnings("serial")
public class ErrorRetrieveOandaRestDataException extends OandaErrorException {

    public ErrorRetrieveOandaRestDataException(SimpleRestException e) {
        super("Error retrieve OANDA REST data", e);
    }

}
