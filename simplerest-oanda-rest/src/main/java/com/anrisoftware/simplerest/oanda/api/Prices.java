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

import java.util.concurrent.Callable;

/**
 * Retrieves the instruments prices.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface Prices extends Callable<Prices> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OandaAccount getAccount();

    /**
     * Returns the instruments for which the prices are returned.
     *
     * @return the {@link Iterable} of {@link Instrument} instruments.
     */
    Iterable<Instrument> getInstruments();

    /**
     * Returns the instruments prices.
     *
     * @return the {@link Iterable} of the {@link Price} prices.
     */
    Iterable<? extends Price> getPrices();

    /**
     * Retrieves the instruments prices.
     *
     * @return this {@link Prices}.
     *
     * @throws OandaErrorException
     *             if there was an error retrieving the prices.
     */
    @Override
    Prices call() throws OandaErrorException;

}
