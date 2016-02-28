/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-core.
 *
 * simplerest-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-core. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.core;

import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * Provides the {@link CloseableHttpClient}.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Singleton
public class DefaultHttpClientProvider implements Provider<CloseableHttpClient> {

    private final CloseableHttpClient httpclient;

    DefaultHttpClientProvider() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        this.httpclient = HttpClients.custom().setConnectionManager(cm).build();
    }

    @Override
    public CloseableHttpClient get() {
        return httpclient;
    }

}
