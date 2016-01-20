package com.anrisoftware.simplerest.owncloud

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.anrisoftware.simplerest.utils.Dependencies.injector
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.utils.Dependencies

/**
 * @see DefaultOwncloudAccount
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@CompileStatic
@Slf4j
class DefaultOwncloudAccountTest {

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
            def account = dep.accountFactory.create test.uri as URI
            assert account.user == test.expectedUser
            assert account.password == test.expectedPassword
            assert account.baseUri.toString() == test.expectedBaseURI
        }
    }

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.dep = injector.getInstance Dependencies
    }
}
