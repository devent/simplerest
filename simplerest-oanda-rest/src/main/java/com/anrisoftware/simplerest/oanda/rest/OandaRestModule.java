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
package com.anrisoftware.simplerest.oanda.rest;

import com.anrisoftware.simplerest.oanda.rest.NamedListParseResponse.NamedListParseResponseFactory;
import com.anrisoftware.simplerest.oanda.rest.OandaRestBidAskInstrumentHistory.OandaRestBidAskInstrumentHistoryFactory;
import com.anrisoftware.simplerest.oanda.rest.OandaRestInstruments.OandaRestInstrumentsFactory;
import com.anrisoftware.simplerest.oanda.rest.OandaRestMidpointInstrumentHistory.OandaRestMidpointInstrumentHistoryFactory;
import com.anrisoftware.simplerest.oanda.rest.OandaRestPrices.OandaRestPricesFactory;
import com.anrisoftware.simplerest.oanda.rest.SimpleRequestWorker.SimpleRequestWorkerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the OANDA REST API.
 *
 * @see OandaRestInstrumentsFactory
 * @see OandaRestPricesFactory
 * @see OandaRestBidAskInstrumentHistoryFactory
 * @see OandaRestMidpointInstrumentHistoryFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public final class OandaRestModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(
                OandaRestInstruments.class, OandaRestInstruments.class).build(
                OandaRestInstrumentsFactory.class));
        install(new FactoryModuleBuilder().implement(OandaRestPrices.class,
                OandaRestPrices.class).build(OandaRestPricesFactory.class));
        install(new FactoryModuleBuilder().implement(
                OandaRestBidAskInstrumentHistory.class,
                OandaRestBidAskInstrumentHistory.class).build(
                OandaRestBidAskInstrumentHistoryFactory.class));
        install(new FactoryModuleBuilder().implement(
                OandaRestMidpointInstrumentHistory.class,
                OandaRestMidpointInstrumentHistory.class).build(
                OandaRestMidpointInstrumentHistoryFactory.class));
        install(new FactoryModuleBuilder().implement(SimpleRequestWorker.class,
                SimpleRequestWorker.class).build(
                SimpleRequestWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                NamedListParseResponse.class, NamedListParseResponse.class)
                .build(NamedListParseResponseFactory.class));
    }

}
