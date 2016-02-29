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

import static java.lang.String.format;

import java.net.URI;
import java.util.Collection;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.AbstractSimpleGetWorker;
import com.anrisoftware.simplerest.core.BadResponseException;
import com.anrisoftware.simplerest.core.ErrorCloseResponseException;
import com.anrisoftware.simplerest.core.ErrorExecuteRequestException;
import com.anrisoftware.simplerest.core.ErrorParseResponseException;
import com.anrisoftware.simplerest.core.ErrorResponseDataException;
import com.anrisoftware.simplerest.core.ParseResponse;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes a request to the OANDA REST API that returns a list of data.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
@SuppressWarnings("rawtypes")
class SimpleRequestWorker extends AbstractSimpleGetWorker<Collection<?>> {

    /**
     * Factory to create the simple request worker.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    interface SimpleRequestWorkerFactory {

        /**
         * Create the simple request worker.
         *
         * @param parent
         *            the {@link Object} parent.
         *
         * @param account
         *            the {@link OandaAccount}.
         *
         * @param requestUri
         *            the request {@link URI}.
         *
         * @param parseResponse
         *            the {@link ParseResponse} that parses the response.
         *
         * @return the {@link SimpleRequestWorker}.
         */
        SimpleRequestWorker create(@Assisted Object parent,
                @Assisted OandaAccount acbcount, @Assisted URI requestUri,
                @Assisted ParseResponse<Collection<?>> parseResponse,
                @Assisted @Nullable CloseableHttpClient httpClient);
    }

    private static final String BEARER_TEMPLATE = "Bearer %s";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Inject
    private SimpleRequestWorkerLogger log;

    /**
     * @see SimpleRequestWorkerFactory#create(Object, OandaAccount, URI,
     *      TypeReference, ParseResponse)
     */
    @Inject
    SimpleRequestWorker(@Assisted Object parent,
            @Assisted OandaAccount account, @Assisted URI requestUri,
            @Assisted ParseResponse<Collection<?>> parseResponse,
            @Assisted @Nullable CloseableHttpClient httpClient,
            ParseOandaErrorResponse parseErrorResponse) {
        super(parent, requestUri, httpClient, parseResponse, parseErrorResponse);
        setAccount(account);
        addHeader(AUTHORIZATION_HEADER,
                format(BEARER_TEMPLATE, account.getAccessToken()));
        setCompressed(true);
    }

    @Override
    public Collection retrieveData() throws BadResponseException,
            ErrorParseResponseException, ErrorExecuteRequestException,
            ErrorCloseResponseException, ErrorResponseDataException {
        Collection data = super.retrieveData();
        log.parsedObjects(getParent(), data != null ? data.size() : 0);
        return data;
    }
}
