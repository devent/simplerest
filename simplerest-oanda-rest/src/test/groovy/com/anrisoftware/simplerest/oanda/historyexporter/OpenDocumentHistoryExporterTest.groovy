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
package com.anrisoftware.simplerest.oanda.historyexporter

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static org.junit.Assume.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.oanda.api.Granularity
import com.anrisoftware.simplerest.oanda.api.Instrument
import com.anrisoftware.simplerest.oanda.core.DefaultBidaskCandle
import com.anrisoftware.simplerest.oanda.core.DefaultOandaAccountFromEnv
import com.anrisoftware.simplerest.oanda.core.OandaCoreModule
import com.anrisoftware.simplerest.oanda.historyexporter.OpenDocumentHistoryExporter.OpenDocumentHistoryExporterFactory
import com.anrisoftware.simplerest.oanda.rest.OandaRestModule
import com.anrisoftware.simplerest.oanda.rest.OandaRestBidAskInstrumentHistory.OandaRestBidAskInstrumentHistoryFactory
import com.anrisoftware.simplerest.oanda.utils.InstrumentsUtil
import com.google.inject.Guice

/**
 * @see OpenDocumentHistoryExporter
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class OpenDocumentHistoryExporterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    @Inject
    InstrumentsUtil instrumentsUtil

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOandaAccountFromEnv account

    @Inject
    OandaRestBidAskInstrumentHistoryFactory restBidAskInstrumentHistoryFactory

    @Inject
    BidaskCandlesTableModelFactory bidaskCandlesTableModelFactory

    @Inject
    OpenDocumentHistoryExporterFactory openDocumentHistoryExporterFactory

    @Test
    void "save in spreadsheet document for EUR_USD, 64 samples, M5, BidAsk"() {
        def args = [
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-10-24T11:50:30.000+02:00",
                count: 65,
                granularity: Granularity.S10
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-10-24T11:50:25.000+02:00",
                count: 44,
                granularity: Granularity.S15
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-10-24T11:50:10.000+02:00",
                count: 23,
                granularity: Granularity.S30
            ],
        ]
        args.each { Map it ->
            Instrument instrument = it.instrument as Instrument
            DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(it.date as String)
            int count = it.count as int
            Granularity granularity = it.granularity as Granularity
            def container = new ArrayList()
            def history = restBidAskInstrumentHistoryFactory.create(account.build(), instrument, container)
            history.setStartDate date
            history.setCount count
            history.setGranularity granularity
            history.call()
            assert history.candles.size() == it.count
            assert history.candles[0].getClass() == DefaultBidaskCandle.class
            def file = folder.newFile("history-${granularity}".toLowerCase())
            def candlesTableModelFactory = bidaskCandlesTableModelFactory
            def exporter = openDocumentHistoryExporterFactory.create instrument, container, candlesTableModelFactory
            exporter.saveData(file)
        }
        //Thread.sleep 60*1000; assert false : "Thread.sleep"
    }

    @Test
    void "save in spreadsheet document for EUR_USD, 128 samples, M5, BidAsk"() {
        def args = [
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-12-11T04:20:00.000+01:00",
                count: 129,
                granularity: Granularity.M5
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-12-11T04:20:00.000+01:00",
                count: 65,
                granularity: Granularity.M10
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-12-11T04:15:00.000+01:00",
                count: 44,
                granularity: Granularity.M15
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-12-11T04:00:00.000+01:00",
                count: 23,
                granularity: Granularity.M30
            ],
        ]
        args.each { Map it ->
            Instrument instrument = it.instrument as Instrument
            DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(it.date as String)
            int count = it.count as int
            Granularity granularity = it.granularity as Granularity
            def container = new ArrayList()
            def history = restBidAskInstrumentHistoryFactory.create account.build(), instrument, container
            history.setStartDate date
            history.setCount count
            history.setGranularity granularity
            history.call()
            assert history.candles.size() == it.count
            assert history.candles[0].getClass() == DefaultBidaskCandle.class
            def file = folder.newFile("history-${granularity}".toLowerCase())
            def candlesTableModelFactory = bidaskCandlesTableModelFactory
            def exporter = openDocumentHistoryExporterFactory.create instrument, container, candlesTableModelFactory
            exporter.saveData(file)
        }
        //Thread.sleep 60*1000; assert false : "Thread.sleep"
    }

    @Test
    void "save in spreadsheet document for EUR_USD, 256 samples, M5, BidAsk"() {
        def args = [
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-11-15T17:40:00.000+01:00",
                count: 257,
                granularity: Granularity.M5
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-11-15T17:40:00.000+01:00",
                count: 129,
                granularity: Granularity.M10
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-11-15T17:45:00.000+01:00",
                count: 86,
                granularity: Granularity.M15
            ],
            [
                instrument: instrumentsUtil.eurUsd,
                date: "2015-11-15T17:30:00.000+01:00",
                count: 44,
                granularity: Granularity.M30
            ],
        ]
        args.each { Map it ->
            Instrument instrument = it.instrument as Instrument
            DateTime date = ISODateTimeFormat.dateTimeParser().parseDateTime(it.date as String)
            int count = it.count as int
            Granularity granularity = it.granularity as Granularity
            def container = new ArrayList()
            def history = restBidAskInstrumentHistoryFactory.create account.build(), instrument, container
            history.setStartDate date
            history.setCount count
            history.setGranularity granularity
            history.call()
            assert history.candles.size() == it.count
            assert history.candles[0].getClass() == DefaultBidaskCandle.class
            def file = folder.newFile("history-${granularity}".toLowerCase())
            def candlesTableModelFactory = bidaskCandlesTableModelFactory
            def exporter = openDocumentHistoryExporterFactory.create instrument, container, candlesTableModelFactory
            exporter.saveData(file)
        }
        //Thread.sleep 60*1000; assert false : "Thread.sleep"
    }

    @Before
    void setupTest() {
        toStringStyle
        Guice.createInjector(new OandaCoreModule(), new OandaRestModule(), new HistoryExporterModule()).injectMembers(this)
    }

    @BeforeClass
    static void checkOanda() {
        assumeTrue 'Could not reach oanda.com', InetAddress.getByName('oanda.com') != null
        assumeTrue "No OANDA account variables set: ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_NUMBER_PROPERTY} and ${DefaultOandaAccountFromEnv.OANDA_ACCOUNT_TOKEN_PROPERTY}",
                DefaultOandaAccountFromEnv.haveOandaAccountEnv()
    }
}
