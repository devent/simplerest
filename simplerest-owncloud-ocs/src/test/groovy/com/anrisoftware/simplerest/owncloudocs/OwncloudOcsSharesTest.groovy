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
import static org.junit.Assume.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccountFromEnv
import com.anrisoftware.simplerest.owncloud.RestOwncloudModule
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsShares.OwncloudOcsSharesFactory
import com.google.inject.Guice

/**
 * @see OwncloudOcsShares
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
@Slf4j
@CompileStatic
class OwncloudOcsSharesTest {

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOwncloudAccountFromEnv account

    @Inject
    OwncloudOcsSharesFactory sharesFactory

    static List<LinkedHashMap> testCases = [
        [
            path: null,
            expectedStatusCode: 100,
            expectedDataSize: null,
        ],
        [
            path: '/test/shared',
            expectedStatusCode: 100,
            expectedDataSize: 1,
        ],
        [
            path: '/test/not_shared',
            expectedStatusCode: 404,
            expectedDataSize: 0,
        ],
    ]

    @Test
    void "retrieve shares"() {
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            "do retrieve shares"(test)
        }
    }

    @Test
    void "retrieve shares, pooling"() {
        List<Thread> threads = []
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            threads << Thread.start { "do retrieve shares"(test) }
        }
        threads.each { it.join() }
    }

    def "do retrieve shares"(Map test) {
        String path = test.path
        def shares = sharesFactory.create account.build(), clientProvider.get()
        if (path) {
            shares.setPath path
        }
        def message = shares.call()
        log.info "Received shares: {}", message
        assert message.meta.statuscode == test.expectedStatusCode
        if (test.expectedDataSize) {
            assert message.data.size() == test.expectedDataSize
        }
    }

    @Before
    void setupTest() {
        toStringStyle
        Guice.createInjector(new RestOwncloudModule(), new RestOwncloudOcsModule()).injectMembers(this)
    }

    @BeforeClass
    static void checkOwncloud() {
        assumeTrue 'Could not reach anrisoftware.com', InetAddress.getByName('anrisoftware.com') != null
        assumeTrue "No Owncloud account variables set: ${DefaultOwncloudAccountFromEnv.OWNCLOUD_URI_PROPERTY}",
                DefaultOwncloudAccountFromEnv.haveOwncloudAccountEnv()
    }
}
