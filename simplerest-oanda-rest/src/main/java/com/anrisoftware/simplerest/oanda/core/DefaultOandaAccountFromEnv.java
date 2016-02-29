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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.builder.Builder;

import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.core.DefaultAccount.DefaultAccountFactory;

/**
 * Constructs the default Oanda account from environment variables.
 * <ul>
 * <li>{@code "user.oanda.account.number"}
 * <li>{@code "user.oanda.account.token"}
 * </ul>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Singleton
public class DefaultOandaAccountFromEnv implements Builder<OandaAccount> {

    /**
     * Checks if the Oanda account environment variables are available.
     *
     * @return {@code true} if available.
     */
    public static boolean haveOandaAccountEnv() {
        return DefaultOandaAccountURIFromEnv.haveOandaAccountEnv();
    }

    @Inject
    private DefaultAccountFactory accountFactory;

    /**
     * Creates the Oanda account from the environment variables.
     *
     * @return the {@link OandaAccount} account.
     */
    @Override
    public OandaAccount build() {
        DefaultAccount account = accountFactory.create();
        account.setAccount(accountNumber);
        account.setAccessToken(accessToken);
        return account;
    }

    public static final String OANDA_ACCOUNT_NUMBER_PROPERTY = DefaultOandaAccountURIFromEnv.OANDA_ACCOUNT_NUMBER_PROPERTY;

    public static final String OANDA_ACCOUNT_TOKEN_PROPERTY = DefaultOandaAccountURIFromEnv.OANDA_ACCOUNT_TOKEN_PROPERTY;

    public static final List<String> OANDA_ACCOUNT_PROPERTIES = Arrays.asList(
            OANDA_ACCOUNT_NUMBER_PROPERTY, OANDA_ACCOUNT_TOKEN_PROPERTY);

    private static final String accountNumber = System
            .getProperty(OANDA_ACCOUNT_NUMBER_PROPERTY);

    private static final String accessToken = System
            .getProperty(OANDA_ACCOUNT_TOKEN_PROPERTY);

}
