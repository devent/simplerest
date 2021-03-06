/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-owncloud-ocs.
 *
 * simplerest-owncloud-ocs is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-owncloud-ocs is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-owncloud-ocs. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.owncloud;

import java.net.URISyntaxException;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

@SuppressWarnings("serial")
public class BuildAccountURIException extends ContextedRuntimeException {

    public BuildAccountURIException(URISyntaxException e, String string) {
        super("Invalid account URI", e);
        addContextValue("uri", string);
    }

    public BuildAccountURIException(URISyntaxException e) {
        super("Invalid account URI", e);
    }

}
