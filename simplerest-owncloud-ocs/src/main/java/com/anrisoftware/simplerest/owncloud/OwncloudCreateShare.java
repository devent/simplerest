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
import com.anrisoftware.simplerest.ocs.ShareResultMessage;

/**
 * Creates a new share.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public interface OwncloudCreateShare extends Callable<ShareResultMessage> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OwncloudAccount getAccount();

    /**
     * Returns the path to the file or folder.
     *
     * @return the {@link String} path.
     */
    String getPath();

    /**
     * Returns the share type.
     *
     * @return the {@link ShareType} type.
     */
    ShareType getType();

    /**
     * Returns with whom to share.
     *
     * @return the {@link String} user or group id or {@code null}.
     */
    String getShareWith();

    /**
     * Returns with allow public upload to a public shared folder.
     *
     * @return {@code true} to allow public upload or {@code null}.
     */
    Boolean isPublicUpload();

    /**
     * Returns the password.
     *
     * @return {@code String} password or {@code null}.
     */
    String getPassword();

    /**
     * Returns the permissions.
     *
     * @return {@code Integer} permissions or {@code null}.
     */
    Integer getPermissions();

    /**
     * Creates the share.
     *
     * @return this {@link ShareResultMessage}.
     */
    @Override
    ShareResultMessage call() throws SimpleRestException;

}
