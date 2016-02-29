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
package com.anrisoftware.simplerest.oanda.api;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;

/**
 * Retrieves the price history of the tradeable instrument.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface InstrumentHistory extends Callable<InstrumentHistory> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OandaAccount getAccount();

    /**
     * Returns the instrument for which the history is returned.
     *
     * @return the {@link Instrument} instrument.
     */
    Instrument getInstrument();

    /**
     * Returns the time range represented by each candlestick. The value
     * specified will determine the alignment of the first candlestick.
     *
     * @return the {@link Granularity}.
     */
    Granularity getGranularity();

    /**
     * Sets how many candlesticks should be retrieved.
     *
     * @param count
     *            the {@link Integer} count.
     */
    void setCount(int count);

    /**
     * Sets the start date of the history.
     *
     * @param startDate
     *            the {@link DateTime} start date.
     */
    void setStartDate(DateTime startDate);

    /**
     * Sets the end date of the history.
     *
     * @param endDate
     *            the {@link DateTime} end date.
     */
    void setEndDate(DateTime endDate);

    /**
     * Returns the retrieved candlesticks.
     *
     * @return the {@link Collection} of the {@link Candle} candlesticks, sorted
     *         by time.
     */
    Collection<? extends Candle> getCandles();

    /**
     * Retrieves the price history of the tradeable instruments.
     *
     * @return this {@link InstrumentHistory}.
     *
     * @throws OandaErrorException
     *             if there was an error retrieving the prices.
     */
    @Override
    InstrumentHistory call() throws OandaErrorException;

}
