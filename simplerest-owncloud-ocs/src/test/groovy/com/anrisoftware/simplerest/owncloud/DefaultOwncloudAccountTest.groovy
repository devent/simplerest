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
package com.anrisoftware.simplerest.owncloud

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.junit.Before
import org.junit.Test

import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccount.DefaultOwncloudAccountFactory
import com.anrisoftware.simplerest.owncloudocs.RestOwncloudOcsModule
import com.google.inject.Guice

/**
 * @see DefaultOwncloudAccount
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
@Slf4j
@CompileStatic
class DefaultOwncloudAccountTest {

    @Inject
    DefaultOwncloudAccountFactory accountFactory

    @Test
    void "account from URI"() {
        def testCases = [
            [
                uri: new URI('https://user:password@anrisoftware.com/owncloud'),
                expectedUser: 'user',
                expectedPassword: 'password',
                expectedBaseURI: 'https://user:password@anrisoftware.com/owncloud'
            ],
        ]
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}. case: {}', k, test
            def account = accountFactory.create test.uri as URI
            assert account.user == test.expectedUser
            assert account.password == test.expectedPassword
            assert account.baseUri.toString() == test.expectedBaseURI
        }
    }

    @Before
    void setupTest() {
        toStringStyle
        Guice.createInjector(new RestOwncloudModule(), new RestOwncloudOcsModule()).injectMembers(this)
    }
}
