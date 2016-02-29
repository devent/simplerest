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
package com.anrisoftware.simplerest.oanda.core;

import com.anrisoftware.simplerest.oanda.api.InstrumentsFilter;
import com.anrisoftware.simplerest.oanda.core.DefaultAccount.DefaultAccountFactory;
import com.anrisoftware.simplerest.oanda.core.DefaultInstrument.DefaultInstrumentFactory;
import com.anrisoftware.simplerest.oanda.core.DefaultInstrumentsFilter.DefaultInstrumentsFilterFactory;
import com.anrisoftware.simplerest.oanda.core.Granularities.GranularitiesFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the OANDA core.
 *
 * @see DefaultAccountFactory
 * @see DefaultInstrumentFactory
 * @see DefaultInstrumentsFilterFactory
 * @see GranularitiesFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class OandaCoreModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(DefaultAccount.class,
                DefaultAccount.class).build(DefaultAccountFactory.class));
        install(new FactoryModuleBuilder().implement(DefaultInstrument.class,
                DefaultInstrument.class).build(DefaultInstrumentFactory.class));
        install(new FactoryModuleBuilder().implement(InstrumentsFilter.class,
                DefaultInstrumentsFilter.class).build(
                DefaultInstrumentsFilterFactory.class));
        install(new FactoryModuleBuilder().implement(Granularities.class,
                Granularities.class).build(GranularitiesFactory.class));
    }

}
