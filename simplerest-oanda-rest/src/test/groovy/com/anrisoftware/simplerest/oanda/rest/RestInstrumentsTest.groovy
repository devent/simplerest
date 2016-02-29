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
package com.anrisoftware.simplerest.oanda.rest

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.junit.Assume.*
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.oanda.core.DefaultOandaAccountFromEnv
import com.anrisoftware.simplerest.oanda.core.OandaCoreModule
import com.anrisoftware.simplerest.oanda.core.DefaultInstrumentsFilter.DefaultInstrumentsFilterFactory
import com.anrisoftware.simplerest.oanda.rest.OandaRestInstruments.OandaRestInstrumentsFactory
import com.google.inject.Guice

/**
 * @see OandaRestInstruments
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class RestInstrumentsTest {

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOandaAccountFromEnv account

    @Inject
    DefaultInstrumentsFilterFactory instrumentsFilterFactory

    @Inject
    OandaRestInstrumentsFactory instrumentsFactory

    @Test
    void "retrieve all instruments"() {
        def instruments = instrumentsFactory.create(account.build(), instrumentsFilterFactory.create())
        instruments.call()
        assert instruments.instruments.size() == 122
    }

    @Test
    void "retrieve USD/EURO instruments"() {
        def instruments = instrumentsFactory.create(account.build(), instrumentsFilterFactory.create(["EUR_USD"] as Set))
        instruments.call()
        assert instruments.instruments.size() == 1
        assert instruments.instruments[0].name == "EUR_USD"
        assert instruments.instruments[0].displayName == "EUR/USD"
        assert instruments.instruments[0].pip == 0.0001
        assert instruments.instruments[0].maxTradeUnits == 10000000
    }

    @Test
    void "retrieve multiple instruments"() {
        def instruments = instrumentsFactory.create(account.build(), instrumentsFilterFactory.create([
            "EUR_USD",
            "USD_JPY",
            "EUR_CAD"] as Set))
        instruments.call()
        assert instruments.instruments.size() == 3
    }

    @Test
    void "retrieve bad instrument"() {
        def instruments = instrumentsFactory.create(account.build(), instrumentsFilterFactory.create(["xxx"] as Set))
        shouldFailWith ErrorRetrieveOandaRestDataException.class, { instruments.call() }
    }

    @Before
    void setupTest() {
        toStringStyle
        Guice.createInjector(new OandaCoreModule(), new OandaRestModule()).injectMembers(this)
    }

    @BeforeClass
    static void checkOanda() {
        assumeTrue 'Could not reach oanda.com', InetAddress.getByName('oanda.com') != null
        assumeTrue "No OANDA account variables set: ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_NUMBER_PROPERTY} and ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_TOKEN_PROPERTY}",
                DefaultOandaAccountFromEnv.haveOandaAccountEnv()
    }
}
