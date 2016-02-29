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

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.oanda.core.DefaultOandaAccountFromEnv
import com.anrisoftware.simplerest.oanda.core.OandaCoreModule
import com.anrisoftware.simplerest.oanda.rest.OandaRestPrices.OandaRestPricesFactory
import com.anrisoftware.simplerest.oanda.utils.InstrumentsUtil
import com.google.inject.Guice

/**
 * @see OandaRestPrices
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class RestPricesTest {

    @Inject
    InstrumentsUtil instrumentsUtil

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOandaAccountFromEnv account

    @Inject
    OandaRestPricesFactory pricesFactory

    @Test
    void "retrieve price for one instrument"() {
        def prices = pricesFactory.create(account.build(), [instrumentsUtil.eurCad] as Set)
        prices.call()
        assert prices.prices.size() == 1
    }

    @Test
    void "retrieve price for multiple instruments"() {
        def prices = pricesFactory.create(account.build(), [
            instrumentsUtil.eurCad,
            instrumentsUtil.audChf] as Set)
        prices.call()
        assert prices.prices.size() == 2
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
