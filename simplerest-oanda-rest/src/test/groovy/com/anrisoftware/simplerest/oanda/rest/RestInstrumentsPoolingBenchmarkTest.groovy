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
import static com.google.inject.Guice.createInjector
import static org.junit.Assume.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.apache.http.impl.client.CloseableHttpClient
import org.junit.BeforeClass
import org.junit.Test
import org.perfidix.Benchmark
import org.perfidix.annotation.BeforeFirstRun
import org.perfidix.annotation.Bench
import org.perfidix.ouput.TabularSummaryOutput

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.oanda.api.OandaAccount
import com.anrisoftware.simplerest.oanda.core.DefaultOandaAccountFromEnv
import com.anrisoftware.simplerest.oanda.core.OandaCoreModule
import com.anrisoftware.simplerest.oanda.core.DefaultInstrumentsFilter.DefaultInstrumentsFilterFactory
import com.anrisoftware.simplerest.oanda.rest.OandaRestInstruments.OandaRestInstrumentsFactory
import com.google.inject.Injector

/**
 * Microbenchmark {@link RestInstruments}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class RestInstrumentsPoolingBenchmarkTest {

    OandaAccount account

    CloseableHttpClient client

    @Bench(runs=5)
    void "retrieve all instruments"() {
        def instruments = dep.instrumentsFactory.create(account, dep.instrumentsFilterFactory.create(), client)
        instruments.call()
    }

    @BeforeFirstRun
    void beforeFirstRun() {
        this.account = dep.account.build()
        this.client = dep.clientProvider.get()
    }

    @Test
    void "benchmark calculate analysis of fractals"() {
        Benchmark benchmark = new Benchmark()
        benchmark.add RestInstrumentsPoolingBenchmarkTest
        def result = benchmark.run()
        new TabularSummaryOutput().visitBenchmark(result)
    }

    static class Dependencies {

        @Inject
        DefaultHttpClientProvider clientProvider

        @Inject
        OandaRestInstrumentsFactory instrumentsFactory

        @Inject
        DefaultInstrumentsFilterFactory instrumentsFilterFactory

        @Inject
        DefaultOandaAccountFromEnv account
    }

    static Injector injector

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.injector = createInjector(new OandaCoreModule(), new OandaRestModule())
        this.dep = injector.getInstance Dependencies
    }

    @BeforeClass
    static void checkOanda() {
        assumeTrue 'Could not reach oanda.com', InetAddress.getByName('oanda.com') != null
        assumeTrue "No OANDA account variables set: ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_NUMBER_PROPERTY} and ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_TOKEN_PROPERTY}",
                DefaultOandaAccountFromEnv.haveOandaAccountEnv()
    }
}
