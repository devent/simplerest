/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.owncloudocs

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.anrisoftware.simplerest.utils.Dependencies.injector
import static com.google.inject.Guice.createInjector
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.owncloud.ShareType
import com.anrisoftware.simplerest.utils.Dependencies

/**
 * @see OwncloudOcsCreateShare
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@CompileStatic
@Slf4j
class OwncloudOcsCreateShareTest {

    @Test
    void "create new share public"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def testCases = [
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
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            String path = test.path
            ShareType type = test.type
            String shareWith = test.shareWith
            Boolean publicUpload = test.publicUpload
            String password = test.password
            Integer permissions = test.permissions
            def shares = dep.createShareFactory.create(account, path, type, shareWith, publicUpload, password, permissions)
            def message = shares.call()
            log.info "Received result: {}", message
            assert message.meta.statuscode == 100
        }
    }

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.dep = injector.getInstance Dependencies
    }
}
