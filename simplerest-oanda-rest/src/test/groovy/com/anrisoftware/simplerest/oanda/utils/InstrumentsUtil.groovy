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
package com.anrisoftware.simplerest.oanda.utils

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import com.anrisoftware.simplerest.oanda.api.Instrument
import com.anrisoftware.simplerest.oanda.core.DefaultInstrument.DefaultInstrumentFactory


/**
 * Dependencies for the Forex OANDA tests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class InstrumentsUtil {

    @Inject
    DefaultInstrumentFactory instrumentFactory

    Instrument getEurUsd() {
        def instrument = instrumentFactory.create()
        instrument.setName "EUR_USD"
        instrument.setDisplayName "EUR/USD"
        return instrument
    }

    Instrument getEurCad() {
        def instrument = instrumentFactory.create()
        instrument.setName "EUR_CAD"
        instrument.setDisplayName "EUR/CAD"
        return instrument
    }

    Instrument getAudChf() {
        def instrument = instrumentFactory.create()
        instrument.setName "AUD_CHF"
        instrument.setDisplayName "AUD/CHF"
        return instrument
    }
}
