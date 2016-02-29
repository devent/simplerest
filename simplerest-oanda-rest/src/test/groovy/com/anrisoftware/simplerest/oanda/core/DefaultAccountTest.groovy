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
package com.anrisoftware.simplerest.oanda.core

import static com.anrisoftware.globalpom.utils.TestUtils.*
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.junit.Before
import org.junit.Test

import com.anrisoftware.simplerest.oanda.core.DefaultAccount.DefaultAccountFactory
import com.google.inject.Guice

/**
 * @see DefaultAccount
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class DefaultAccountTest {

    @Inject
    DefaultAccountFactory factory

    @Test
    void "parse account uri"() {
        def testCases = [
            [
                uri: new URI("oanda:1234567:1234-1234"),
                accountNumberExcepted: "1234567",
                accessTokenExcepted: "1234-1234"
            ]
        ]
        testCases.eachWithIndex { testCase, int k ->
            log.info "{} test case: {}", k, testCase
            def account = factory.create(testCase.uri as URI)
            assert account.getAccount() == testCase.accountNumberExcepted
            assert account.getAccessToken() == testCase.accessTokenExcepted
        }
    }

    @Before
    void setupTest() {
        toStringStyle
        Guice.createInjector(new OandaCoreModule()).injectMembers this
    }
}
