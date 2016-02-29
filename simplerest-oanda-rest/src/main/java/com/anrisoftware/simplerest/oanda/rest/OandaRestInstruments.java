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

import static org.apache.commons.lang3.StringUtils.join;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.Instruments;
import com.anrisoftware.simplerest.oanda.api.InstrumentsFilter;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.api.OandaErrorException;
import com.anrisoftware.simplerest.oanda.core.DefaultInstrument;
import com.anrisoftware.simplerest.oanda.rest.NamedListParseResponse.NamedListParseResponseFactory;
import com.anrisoftware.simplerest.oanda.rest.SimpleRequestWorker.SimpleRequestWorkerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Retrieves the tradeable instruments via the REST API.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public class OandaRestInstruments implements Instruments {

    /**
     * Factory to create the retrieving of the tradeable instruments via the
     * REST API.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.3
     */
    public interface OandaRestInstrumentsFactory {

        /**
         * Create the retrieving of the tradeable instruments via the REST API.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param filter
         *            the {@link InstrumentsFilter}.
         *
         * @return the {@link OandaRestInstruments}.
         */
        OandaRestInstruments create(@Assisted OandaAccount account,
                @Assisted InstrumentsFilter filter);

        /**
         * Create the retrieving of the tradeable instruments via the REST API.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param filter
         *            the {@link InstrumentsFilter}.
         *
         * @return the {@link OandaRestInstruments}.
         */
        OandaRestInstruments create(@Assisted OandaAccount account,
                @Assisted InstrumentsFilter filter,
                @Assisted CloseableHttpClient httpClient);
    }

    private static final String COMMA = ",";

    private static final String INSTRUMENTS_PARAM = "instruments";

    private static final String ACCOUNT_ID_PARAM = "accountId";

    private static final String ACCOUNT_FIELD = "account";

    private static final String INSTRUMENTS_NAME = "instruments";

    private final OandaAccount account;

    private final InstrumentsFilter filter;

    private final TypeReference<Map<String, Collection<DefaultInstrument>>> instrumentsTypeRef;

    @Inject
    private OandaPropertiesProvider propertiesProvider;

    @Inject
    private SimpleRequestWorkerFactory simpleRequestWorkerFactory;

    @Inject
    private NamedListParseResponseFactory namedListParseResponseFactory;

    private Collection<DefaultInstrument> instruments;

    private CloseableHttpClient httpClient;

    @AssistedInject
    OandaRestInstruments(@Assisted OandaAccount account,
            @Assisted InstrumentsFilter filter) {
        this(account, filter, null);
    }

    @AssistedInject
    OandaRestInstruments(@Assisted OandaAccount account,
            @Assisted InstrumentsFilter filter,
            @Assisted CloseableHttpClient httpClient) {
        this.account = account;
        this.filter = filter;
        this.httpClient = httpClient;
        this.instrumentsTypeRef = new TypeReference<Map<String, Collection<DefaultInstrument>>>() {
        };
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public InstrumentsFilter getInstrumentsFilter() {
        return filter;
    }

    @Override
    public OandaAccount getAccount() {
        return account;
    }

    @Override
    public Instruments call() throws OandaErrorException {
        retrieveInstruments();
        return this;
    }

    @Override
    public Iterable<? extends Instrument> getInstruments() {
        return instruments;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ACCOUNT_FIELD,
                account.toString()).toString();
    }

    @SuppressWarnings("unchecked")
    private void retrieveInstruments() throws OandaErrorException {
        URI requestUri = getRequestURI();
        SimpleRequestWorker requestWorker = simpleRequestWorkerFactory.create(
                this, account, requestUri, namedListParseResponseFactory
                        .create(INSTRUMENTS_NAME, instrumentsTypeRef),
                httpClient);
        try {
            Collection<DefaultInstrument> instruments;
            instruments = requestWorker.retrieveData();
            this.instruments = instruments;
        } catch (SimpleRestException e) {
            throw new ErrorRetrieveOandaRestDataException(e);
        }
    }

    private URI getRequestURI() throws BadRequestURI {
        try {
            return getRequestURI0();
        } catch (URISyntaxException e) {
            throw new BadRequestURI(e);
        }
    }

    private URI getRequestURI0() throws URISyntaxException {
        URIBuilder builder = new URIBuilder(
                propertiesProvider.getOandaInstrumentsURI());
        builder.setParameter(ACCOUNT_ID_PARAM, account.getAccount());
        if (filter.getNames().size() > 0) {
            String instruments = join(filter.getNames(), COMMA);
            builder.setParameter(INSTRUMENTS_PARAM, instruments);
        }
        return builder.build();
    }

}
