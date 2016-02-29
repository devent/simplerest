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
package com.anrisoftware.simplerest.oanda.historyexporter;

import java.util.Collection;

import javax.swing.table.TableModel;

import com.anrisoftware.simplerest.oanda.api.Candle;

/**
 * Factory to create a table model out of the historic candles.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public interface CandlesTableModelFactory {

    /**
     * Creates a table model out of the historic candles.
     *
     * @param history
     *            the {@link Collection} of the historic data.
     *
     * @return the {@link TableModel}.
     */
    TableModel create(Collection<? extends Candle> history);
}
