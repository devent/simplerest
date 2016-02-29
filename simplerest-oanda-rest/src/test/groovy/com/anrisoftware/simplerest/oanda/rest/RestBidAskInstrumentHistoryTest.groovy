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
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.joda.time.DateTime
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.oanda.api.Granularity
import com.anrisoftware.simplerest.oanda.core.DefaultBidaskCandle
import com.anrisoftware.simplerest.oanda.core.DefaultOandaAccountFromEnv
import com.anrisoftware.simplerest.oanda.core.OandaCoreModule
import com.anrisoftware.simplerest.oanda.rest.OandaRestBidAskInstrumentHistory.OandaRestBidAskInstrumentHistoryFactory
import com.anrisoftware.simplerest.oanda.utils.InstrumentsUtil
import com.google.inject.Guice

/**
 * @see OandaRestBidAskInstrumentHistory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class RestBidAskInstrumentHistoryTest {

    @Inject
    InstrumentsUtil instrumentsUtil

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOandaAccountFromEnv account

    @Inject
    OandaRestBidAskInstrumentHistoryFactory historyFactory

    @Test
    void "retrieve history for one instrument"() {
        def history = historyFactory.create(account.build(), instrumentsUtil.eurCad)
        history.setGranularity(Granularity.S15)
        history.call()
        assert history.candles.size() == 500
        assert history.candles[0].getClass() == DefaultBidaskCandle.class
    }

    @Test
    void "retrieve history for one instrument, with start and end date"() {
        def startDate = new DateTime(2015, 4, 5, 7, 00)
        def endDate = new DateTime(2015, 4, 6, 7, 00)
        def container = []
        def history = historyFactory.create(account.build(), instrumentsUtil.eurCad, container)
        history.setGranularity(Granularity.S5)
        history.setStartDate startDate
        history.setEndDate endDate
        history.call()
        assert history.candles.size() == 2955
    }

    @Test
    void "retrieve history for EUR_USD instrument"() {
        def history = historyFactory.create(account.build(), instrumentsUtil.eurCad)
        history.setCount 5000
        history.setGranularity(Granularity.S5)
        history.call()
        assert history.candles.size() == 5000
        assert history.candles[0].getClass() == DefaultBidaskCandle.class
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
