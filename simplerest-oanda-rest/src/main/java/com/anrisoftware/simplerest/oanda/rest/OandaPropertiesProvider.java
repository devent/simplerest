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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.inject.Singleton;

import com.anrisoftware.propertiesutils.AbstractContextPropertiesProvider;

/**
 * Provides OANDA properties from {@code "/oanda.properties".}
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
@SuppressWarnings("serial")
@Singleton
public final class OandaPropertiesProvider extends
        AbstractContextPropertiesProvider {

    private static final URL resource = OandaPropertiesProvider.class
            .getResource("/oanda.properties");

    OandaPropertiesProvider() {
        super(OandaPropertiesProvider.class, resource);
    }

    public URI getOandaInstrumentsURI() throws URISyntaxException {
        return get().getURIProperty("instruments_uri");
    }

    public URI getOandaPricesURI() throws URISyntaxException {
        return get().getURIProperty("prices_uri");
    }

    public URI getOandaCandlesURI() throws URISyntaxException {
        return get().getURIProperty("candles_uri");
    }
}
