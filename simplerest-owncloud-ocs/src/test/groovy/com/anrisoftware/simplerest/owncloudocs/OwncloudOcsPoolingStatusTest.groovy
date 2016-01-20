/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-owncloud-ocs.
 *
 * simplerest-owncloud-ocs is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-owncloud-ocs is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-owncloud-ocs. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.owncloudocs

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.anrisoftware.simplerest.utils.Dependencies.*
import static com.google.inject.Guice.createInjector
import groovy.util.logging.Slf4j

import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.utils.Dependencies

/**
 * @see OwncloudOcsPoolingStatus
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class OwncloudOcsPoolingStatusTest {

    @Test
    void "retrieve status"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def cm = new PoolingHttpClientConnectionManager()
        def httpclient = HttpClients.custom().setConnectionManager(cm).build();
        def status = dep.poolingStatusFactory.create(account, httpclient)
        status.call()
        assert status.installed == true
    }

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.dep = injector.getInstance Dependencies
    }
}
