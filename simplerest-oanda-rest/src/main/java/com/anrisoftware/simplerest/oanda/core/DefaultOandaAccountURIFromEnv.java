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
package com.anrisoftware.simplerest.oanda.core;

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;

import org.apache.commons.lang3.builder.Builder;

/**
 * Constructs the Oanda account URI from environment variables.
 * <ul>
 * <li>{@code "user.oanda.account.number"}
 * <li>{@code "user.oanda.account.token"}
 * </ul>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Singleton
public class DefaultOandaAccountURIFromEnv implements Builder<URI> {

    /**
     * Checks if the Oanda account environment variables are available.
     *
     * @return {@code true} if available.
     */
    public static boolean haveOandaAccountEnv() {
        return accountNumber != null && accessToken != null;
    }

    /**
     * Creates the Oanda account URI from the environment variables.
     *
     * @return the {@link URI} URI.
     *
     * @throws BuildAccountURIException
     *             if the URI is invalid.
     */
    @Override
    public URI build() {
        String string = format("oanda:%s:%s", accountNumber, accessToken);
        try {
            return new URI(string);
        } catch (URISyntaxException e) {
            throw new BuildAccountURIException(e, string);
        }
    }

    public static final String OANDA_ACCOUNT_NUMBER_PROPERTY = "user.oanda.account.number";

    public static final String OANDA_ACCOUNT_TOKEN_PROPERTY = "user.oanda.account.token";

    private static final String accountNumber = System
            .getProperty(OANDA_ACCOUNT_NUMBER_PROPERTY);

    private static final String accessToken = System
            .getProperty(OANDA_ACCOUNT_TOKEN_PROPERTY);

}
