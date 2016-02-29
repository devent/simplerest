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
 * Returns the tradeable instruments.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface Instruments extends Callable<Instruments> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OandaAccount getAccount();

    /**
     * Returns the instruments filter that determines which instruments are
     * retrieved.
     *
     * @return the {@link InstrumentsFilter}.
     */
    InstrumentsFilter getInstrumentsFilter();

    /**
     * Retrieves the tradeable instruments.
     *
     * @return this {@link Instruments}.
     *
     * @throws OandaErrorException
     *             if there was an error retrieving the instruments.
     */
    @Override
    Instruments call() throws OandaErrorException;

    /**
     * Returns the tradeable instruments.
     *
     * @return the {@link Iterable} of the {@link Instrument} instruments.
     */
    Iterable<? extends Instrument> getInstruments();

}
