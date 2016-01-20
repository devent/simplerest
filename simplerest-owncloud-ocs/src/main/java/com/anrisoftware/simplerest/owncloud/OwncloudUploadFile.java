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

import java.util.concurrent.Callable;

import com.anrisoftware.simplerest.core.SimpleRestException;

/**
 * Uploads a file to the Owncloud server.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudUploadFile extends Callable<OwncloudUploadFile> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OwncloudAccount getAccount();

    /**
     * Uploads the file.
     *
     * @return this {@link OwncloudUploadFile}.
     */
    @Override
    OwncloudUploadFile call() throws SimpleRestException;
}
