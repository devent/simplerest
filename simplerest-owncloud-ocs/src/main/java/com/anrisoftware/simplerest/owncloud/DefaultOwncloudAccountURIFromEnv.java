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

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;

import org.apache.commons.lang3.builder.Builder;

/**
 * Constructs the Owncloud account URI from environment variables.
 * <ul>
 * <li>{@code "user.owncloud.account.user"}
 * <li>{@code "user.owncloud.account.password"}
 * <li>{@code "user.owncloud.account.baseuri"}
 * <li>or {@code "user.owncloud.uri"}
 * </ul>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.2
 */
@Singleton
public class DefaultOwncloudAccountURIFromEnv implements Builder<URI> {

    /**
     * Checks if the Owncloud account environment variables are available.
     *
     * @return {@code true} if available.
     */
    public static boolean haveOwncloudAccountEnv() {
        return haveCredentials() || haveUri();
    }

    public static boolean haveUri() {
        return owncloudUri != null;
    }

    public static boolean haveCredentials() {
        return owncloudAccountUser != null && owncloudAccountPassword != null
                && owncloudBaseUri != null;
    }

    /**
     * Creates the Owncloud account URI from the environment variables.
     *
     * @return the {@link URI} URI.
     *
     * @throws BuildAccountURIException
     *             if the URI is invalid.
     */
    @Override
    public URI build() {
        if (haveCredentials()) {
            return fromCredentials();
        } else {
            return fromUri();
        }
    }

    private URI fromUri() {
        try {
            return new URI(owncloudUri);
        } catch (URISyntaxException e) {
            throw new BuildAccountURIException(e, owncloudBaseUri);
        }
    }

    private URI fromCredentials() {
        try {
            URI base = new URI(owncloudBaseUri);
            String string = format("%s://%s:%s@%s/%s", base.getScheme(),
                    owncloudAccountUser, owncloudAccountPassword,
                    base.getHost(), base.getPath());
            try {
                return new URI(string);
            } catch (URISyntaxException e) {
                throw new BuildAccountURIException(e, string);
            }
        } catch (URISyntaxException e) {
            throw new BuildAccountURIException(e, owncloudBaseUri);
        }
    }

    public static final String OWNCLOUD_ACCOUNT_USER_PROPERTY = "user.owncloud.account.user";

    public static final String OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY = "user.owncloud.account.password";

    public static final String OWNCLOUD_ACCOUNT_BASEURI_PROPERTY = "user.owncloud.account.baseuri";

    public static final String OWNCLOUD_URI_PROPERTY = "user.owncloud.uri";

    public static final String owncloudAccountUser = System
            .getProperty(OWNCLOUD_ACCOUNT_USER_PROPERTY);

    public static final String owncloudAccountPassword = System
            .getProperty(OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY);

    public static final String owncloudBaseUri = System
            .getProperty(OWNCLOUD_ACCOUNT_BASEURI_PROPERTY);

    public static final String owncloudUri = System
            .getProperty(OWNCLOUD_URI_PROPERTY);

}
