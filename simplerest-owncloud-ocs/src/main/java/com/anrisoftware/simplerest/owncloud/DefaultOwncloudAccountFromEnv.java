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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.builder.Builder;

import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccount.DefaultOwncloudAccountFactory;

/**
 * Constructs the default Owncloud account from environment variables.
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
public class DefaultOwncloudAccountFromEnv implements Builder<OwncloudAccount> {

    /**
     * Checks if the Owncloud account environment variables are available.
     *
     * @return {@code true} if available.
     */
    public static boolean haveOwncloudAccountEnv() {
        return DefaultOwncloudAccountURIFromEnv.haveOwncloudAccountEnv();
    }

    public static boolean haveUri() {
        return DefaultOwncloudAccountURIFromEnv.haveUri();
    }

    public static boolean haveCredentials() {
        return DefaultOwncloudAccountURIFromEnv.haveCredentials();
    }

    @Inject
    private DefaultOwncloudAccountFactory accountFactory;

    /**
     * Creates the Oanda account from the environment variables.
     *
     * @return the {@link OandaAccount} account.
     *
     * @throws BuildAccountURIException
     *             if the URI is invalid.
     */
    @Override
    public OwncloudAccount build() {
        if (haveCredentials()) {
            return fromCredentials();
        } else {
            return fromUri();
        }
    }

    private OwncloudAccount fromUri() {
        try {
            return accountFactory.create(new URI(owncloudUri));
        } catch (URISyntaxException e) {
            throw new BuildAccountURIException(e, owncloudUri);
        }
    }

    private OwncloudAccount fromCredentials() {
        try {
            DefaultOwncloudAccount account = accountFactory.create();
            account.setUser(owncloudAccountUser);
            account.setPassword(owncloudAccountPassword);
            account.setBaseUri(new URI(owncloudBaseUri));
            return account;
        } catch (URISyntaxException e) {
            throw new BuildAccountURIException(e);
        }
    }

    public static final String OWNCLOUD_ACCOUNT_USER_PROPERTY = DefaultOwncloudAccountURIFromEnv.OWNCLOUD_ACCOUNT_USER_PROPERTY;

    public static final String OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY = DefaultOwncloudAccountURIFromEnv.OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY;

    public static final String OWNCLOUD_ACCOUNT_BASEURI_PROPERTY = DefaultOwncloudAccountURIFromEnv.OWNCLOUD_ACCOUNT_BASEURI_PROPERTY;

    public static final String OWNCLOUD_URI_PROPERTY = DefaultOwncloudAccountURIFromEnv.OWNCLOUD_URI_PROPERTY;

    public static final List<String> OWNCLOUD_ACCOUNT_PROPERTIES = Arrays
            .asList(OWNCLOUD_ACCOUNT_USER_PROPERTY,
                    OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY,
                    OWNCLOUD_ACCOUNT_BASEURI_PROPERTY, OWNCLOUD_URI_PROPERTY);

    public static final String owncloudAccountUser = DefaultOwncloudAccountURIFromEnv.owncloudAccountUser;

    public static final String owncloudAccountPassword = DefaultOwncloudAccountURIFromEnv.owncloudAccountPassword;

    public static final String owncloudBaseUri = DefaultOwncloudAccountURIFromEnv.owncloudBaseUri;

    public static final String owncloudUri = DefaultOwncloudAccountURIFromEnv.owncloudUri;

}
