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

import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

import java.net.URI;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Account information required for authentication.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public final class DefaultAccount implements OandaAccount {

    /**
     * Factory to create the account information required for authentication.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    public interface DefaultAccountFactory {

        /**
         * Creates the account information required for authentication.
         *
         * @return the {@link DefaultAccount}.
         */
        DefaultAccount create();

        /**
         * Creates the account information required for authentication.
         *
         * <pre>
         * oanda:ACCOUNT:TOKEN
         * </pre>
         *
         * where {@code ACCOUNT} is the account number and {@code TOKEN} is the
         * access token.
         *
         * @param uri
         *            the {@link URI} containing the account information.
         *
         * @return the {@link DefaultAccount}.
         */
        DefaultAccount create(URI uri);
    }

    private static final String URI_MESSAGE = "uri";

    private static final String URI_SCHEME = "oanda";

    private static final String ACCOUNT_MESSAGE = "Account";

    private static final String ACCESS_TOKEN_MESSAGE = "Access-Token";

    private static final String ACCOUNT_FIELD = "account";

    private String accessToken;

    private String account;

    /**
     * @see DefaultAccountFactory#create()
     */
    @AssistedInject
    DefaultAccount() {
    }

    /**
     * @see DefaultAccountFactory#create(URI)
     */
    @AssistedInject
    DefaultAccount(@Assisted URI uri) {
        isTrue(uri.getScheme().equals(URI_SCHEME), URI_MESSAGE);
        String[] split = split(uri.getSchemeSpecificPart(), ":");
        setAccount(split[0]);
        setAccessToken(split[1]);
    }

    public void setAccessToken(String accessToken) {
        notBlank(accessToken, ACCESS_TOKEN_MESSAGE);
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccount(String account) {
        notBlank(account, ACCOUNT_MESSAGE);
        this.account = account;
    }

    @Override
    public String getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ACCOUNT_FIELD, account)
                .toString();
    }
}
