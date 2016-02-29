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
import static org.apache.commons.lang3.Validate.isTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.api.OandaErrorException;
import com.anrisoftware.simplerest.oanda.api.Price;
import com.anrisoftware.simplerest.oanda.api.Prices;
import com.anrisoftware.simplerest.oanda.core.DefaultPrice;
import com.anrisoftware.simplerest.oanda.rest.NamedListParseResponse.NamedListParseResponseFactory;
import com.anrisoftware.simplerest.oanda.rest.SimpleRequestWorker.SimpleRequestWorkerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Retrieves the instruments prices via the REST API.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public class OandaRestPrices implements Prices {

    /**
     * Factory to create the retrieving of prices for tradeable instruments via
     * the REST API.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.3
     */
    public interface OandaRestPricesFactory {

        /**
         * Creates the retrieving of prices for tradeable instruments via the
         * REST API.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instruments
         *            the {@link Set} of the {@link Instrument} instruments.
         *
         * @return the {@link OandaRestPrices}.
         */
        OandaRestPrices create(@Assisted OandaAccount account,
                @Assisted Set<Instrument> instruments);

        /**
         * Creates the retrieving of prices for tradeable instruments via the
         * REST API.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instruments
         *            the {@link Set} of the {@link Instrument} instruments.
         *
         * @return the {@link OandaRestPrices}.
         */
        OandaRestPrices create(@Assisted OandaAccount account,
                @Assisted Set<Instrument> instruments,
                @Assisted CloseableHttpClient httpClient);
    }

    private static final String INSTRUMENTS_MESSAGE = "Instruments";

    private static final String COMMA = ",";

    private static final String INSTRUMENTS_PARAM = "instruments";

    private static final String ACCOUNT_ID_PARAM = "accountId";

    private static final String ACCOUNT_FIELD = "account";

    private static final String PRICES_NAME = "prices";

    private final OandaAccount account;

    private final TypeReference<Map<String, List<DefaultPrice>>> pricesTypeRef;

    private final Map<String, Instrument> instruments;

    @Inject
    private OandaPropertiesProvider propertiesProvider;

    @Inject
    private SimpleRequestWorkerFactory simpleRequestWorkerFactory;

    @Inject
    private NamedListParseResponseFactory namedListParseResponseFactory;

    private Collection<DefaultPrice> prices;

    private CloseableHttpClient httpClient;

    @AssistedInject
    OandaRestPrices(@Assisted OandaAccount account,
            @Assisted Set<Instrument> instruments) {
        this(account, instruments, null);
    }

    @AssistedInject
    OandaRestPrices(@Assisted OandaAccount account,
            @Assisted Set<Instrument> instruments,
            @Assisted CloseableHttpClient httpClient) {
        isTrue(instruments.size() > 0, INSTRUMENTS_MESSAGE);
        this.account = account;
        this.instruments = createInstruments(instruments);
        this.httpClient = httpClient;
        this.pricesTypeRef = new TypeReference<Map<String, List<DefaultPrice>>>() {
        };
    }

    private Map<String, Instrument> createInstruments(
            Set<Instrument> instruments) {
        Map<String, Instrument> result = new HashMap<String, Instrument>();
        for (Instrument instrument : instruments) {
            result.put(instrument.getName(), instrument);
        }
        return result;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public OandaAccount getAccount() {
        return account;
    }

    @Override
    public Iterable<Instrument> getInstruments() {
        return instruments.values();
    }

    @Override
    public Iterable<? extends Price> getPrices() {
        return prices;
    }

    @Override
    public Prices call() throws OandaErrorException {
        retrievePrices();
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(ACCOUNT_FIELD,
                account.toString()).toString();
    }

    private void retrievePrices() throws OandaErrorException {
        URI requestUri = getRequestURI();
        SimpleRequestWorker requestWorker = simpleRequestWorkerFactory.create(
                this, account, requestUri, namedListParseResponseFactory
                        .create(PRICES_NAME, pricesTypeRef), httpClient);
        try {
            @SuppressWarnings("unchecked")
            Collection<DefaultPrice> prices = requestWorker.retrieveData();
            for (DefaultPrice price : prices) {
                price.setInstrument(instruments.get(price.getInstrumentName()));
            }
            this.prices = prices;
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
                propertiesProvider.getOandaPricesURI());
        builder.setParameter(ACCOUNT_ID_PARAM, account.getAccount());
        String names = join(instruments.keySet(), COMMA);
        builder.setParameter(INSTRUMENTS_PARAM, names);
        return builder.build();
    }

}
