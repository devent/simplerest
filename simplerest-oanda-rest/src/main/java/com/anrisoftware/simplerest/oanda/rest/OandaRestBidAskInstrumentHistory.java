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

import java.util.ArrayList;
import java.util.Collection;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.oanda.api.BidaskCandle;
import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.core.DefaultBidaskCandle;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Retrieves the price history of the tradeable instrument in bid/ask based
 * candlesticks.
 *
 * @see BidaskCandle
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
@NotThreadSafe
public class OandaRestBidAskInstrumentHistory extends
        AbstractInstrumentHistory<BidaskCandle> {

    /**
     * Factory to create the price history of the tradeable instrument in
     * bid/ask based candlesticks.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.3
     */
    public interface OandaRestBidAskInstrumentHistoryFactory {

        /**
         * Creates the price history of the tradeable instrument in bid/ask
         * based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @param container
         *            the {@link Collection} container for the retrieved
         *            candles.
         *
         * @return the {@link OandaRestBidAskInstrumentHistory}.
         */
        OandaRestBidAskInstrumentHistory create(@Assisted OandaAccount account,
                @Assisted Instrument instrument,
                @Assisted Collection<BidaskCandle> container);

        /**
         * Creates the price history of the tradeable instrument in bid/ask
         * based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @param container
         *            the {@link Collection} container for the retrieved
         *            candles.
         *
         * @return the {@link OandaRestBidAskInstrumentHistory}.
         */
        OandaRestBidAskInstrumentHistory create(@Assisted OandaAccount account,
                @Assisted Instrument instrument,
                @Assisted Collection<BidaskCandle> container,
                @Assisted CloseableHttpClient httpClient);

        /**
         * Creates the price history of the tradeable instrument in bid/ask
         * based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @return the {@link OandaRestBidAskInstrumentHistory}.
         */
        OandaRestBidAskInstrumentHistory create(@Assisted OandaAccount account,
                @Assisted Instrument instrument);

        /**
         * Creates the price history of the tradeable instrument in bid/ask
         * based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @return the {@link OandaRestBidAskInstrumentHistory}.
         */
        OandaRestBidAskInstrumentHistory create(@Assisted OandaAccount account,
                @Assisted Instrument instrument,
                @Assisted CloseableHttpClient httpClient);
    }

    @AssistedInject
    OandaRestBidAskInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted Collection<BidaskCandle> container) {
        super(account, instrument, BIDASK_FORMAT, container,
                DefaultBidaskCandle.class, null);
    }

    @AssistedInject
    OandaRestBidAskInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted Collection<BidaskCandle> container,
            @Assisted CloseableHttpClient httpClient) {
        super(account, instrument, BIDASK_FORMAT, container,
                DefaultBidaskCandle.class, httpClient);
    }

    @AssistedInject
    OandaRestBidAskInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument) {
        super(account, instrument, BIDASK_FORMAT, new ArrayList<BidaskCandle>(
                1024), DefaultBidaskCandle.class, null);
    }

    @AssistedInject
    OandaRestBidAskInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted CloseableHttpClient httpClient) {
        super(account, instrument, BIDASK_FORMAT, new ArrayList<BidaskCandle>(
                1024), DefaultBidaskCandle.class, httpClient);
    }

    private static final String BIDASK_FORMAT = "bidask";

}
