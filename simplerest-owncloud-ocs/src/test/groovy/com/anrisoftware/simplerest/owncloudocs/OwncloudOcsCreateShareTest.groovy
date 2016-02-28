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
import com.anrisoftware.simplerest.owncloud.ShareType
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsCreateShare.OwncloudOcsCreateShareFactory
import com.google.inject.Guice

/**
 * @see OwncloudOcsCreateShare
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class OwncloudOcsCreateShareTest {

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOwncloudAccountFromEnv account

    @Inject
    OwncloudOcsCreateShareFactory createShareFactory

    static List<LinkedHashMap> testCases = [
        [
            path: '/test/test.txt',
            type: ShareType.link,
            shareWith: null,
            publicUpload: null,
            password: null,
            permissions: null,
        ],
        [
            path: '/test/test.txt',
            type: ShareType.link,
            shareWith: null,
            publicUpload: null,
            password: 'abc1234',
            permissions: null,
        ],
    ]

    @Test
    void "create new share"() {
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            "do create new share"(test)
        }
    }

    @Test
    void "create new share, pooling"() {
        List<Thread> threads = []
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            threads << Thread.start { "do create new share"(test) }
        }
        threads.each { it.join() }
    }

    void "do create new share"(Map test) {
        String path = test.path
        ShareType type = test.type as ShareType
        String shareWith = test.shareWith
        Boolean publicUpload = test.publicUpload
        String password = test.password
        Integer permissions = test.permissions as Integer
        def shares = createShareFactory.create(account.build(), path, type, shareWith, publicUpload, password, permissions, clientProvider.get())
        def message = shares.call()
        log.info "Received result: {}", message
        assert message.meta.statuscode == 100
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
