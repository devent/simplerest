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

import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.MidpointCandle;
import com.anrisoftware.simplerest.oanda.api.OandaAccount;
import com.anrisoftware.simplerest.oanda.core.DefaultMidpointCandle;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Retrieves the price history of the tradeable instrument in midpoint-based
 * based candlesticks.
 *
 * @see MidpointCandle
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public class OandaRestMidpointInstrumentHistory extends
        AbstractInstrumentHistory<MidpointCandle> {

    /**
     * Factory to create the price history of the tradeable instrument in
     * midpoint-based based candlesticks.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.3
     */
    public interface OandaRestMidpointInstrumentHistoryFactory {

        /**
         * Creates the price history of the tradeable instrument in
         * midpoint-based based candlesticks.
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
         * @return the {@link OandaRestMidpointInstrumentHistory}.
         */
        OandaRestMidpointInstrumentHistory create(
                @Assisted OandaAccount account,
                @Assisted Instrument instrument,
                @Assisted Collection<MidpointCandle> container);

        /**
         * Creates the price history of the tradeable instrument in
         * midpoint-based based candlesticks.
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
         * @return the {@link OandaRestMidpointInstrumentHistory}.
         */
        OandaRestMidpointInstrumentHistory create(
                @Assisted OandaAccount account,
                @Assisted Instrument instrument,
                @Assisted Collection<MidpointCandle> container,
                @Assisted CloseableHttpClient httpClient);

        /**
         * Creates the price history of the tradeable instrument in
         * midpoint-based based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @return the {@link OandaRestMidpointInstrumentHistory}.
         */
        OandaRestMidpointInstrumentHistory create(
                @Assisted OandaAccount account, @Assisted Instrument instrument);

        /**
         * Creates the price history of the tradeable instrument in
         * midpoint-based based candlesticks.
         *
         * @param account
         *            the OANDA {@link OandaAccount} account.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @return the {@link OandaRestMidpointInstrumentHistory}.
         */
        OandaRestMidpointInstrumentHistory create(
                @Assisted OandaAccount account,
                @Assisted Instrument instrumentr,
                @Assisted CloseableHttpClient httpClient);
    }

    @AssistedInject
    OandaRestMidpointInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted Collection<MidpointCandle> container) {
        super(account, instrument, MIDPOINT_FORMAT, container,
                DefaultMidpointCandle.class, null);
    }

    @AssistedInject
    OandaRestMidpointInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted Collection<MidpointCandle> container,
            @Assisted CloseableHttpClient httpClient) {
        super(account, instrument, MIDPOINT_FORMAT, container,
                DefaultMidpointCandle.class, httpClient);
    }

    @AssistedInject
    OandaRestMidpointInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument) {
        super(account, instrument, MIDPOINT_FORMAT,
                new ArrayList<MidpointCandle>(1024),
                DefaultMidpointCandle.class, null);
    }

    @AssistedInject
    OandaRestMidpointInstrumentHistory(@Assisted OandaAccount account,
            @Assisted Instrument instrument,
            @Assisted CloseableHttpClient httpClient) {
        super(account, instrument, MIDPOINT_FORMAT,
                new ArrayList<MidpointCandle>(1024),
                DefaultMidpointCandle.class, httpClient);
    }

    private static final String MIDPOINT_FORMAT = "midpoint";

}
