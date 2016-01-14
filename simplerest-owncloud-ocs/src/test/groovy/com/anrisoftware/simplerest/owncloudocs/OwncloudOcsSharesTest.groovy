/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.owncloudocs

import static com.anrisoftware.globalpom.utils.TestUtils.*
import static com.anrisoftware.simplerest.utils.Dependencies.*
import static com.google.inject.Guice.createInjector
import groovy.util.logging.Slf4j

import org.junit.BeforeClass
import org.junit.Test

import com.anrisoftware.simplerest.utils.Dependencies

/**
 * @see OwncloudOcsShares
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class OwncloudOcsSharesTest {

    @Test
    void "retrieve shares"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def shares = dep.sharesFactory.create(account)
        def message = shares.call()
        log.info "Received shares: {}", message
        assert message.meta.statuscode == 100
    }

    @Test
    void "retrieve shares for path"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def shares = dep.sharesFactory.create(account)
        shares.path = '/test/shared'
        def message = shares.call()
        log.info "Received shares: {}", message
        assert message.meta.statuscode == 100
        assert message.data.size() == 1
    }

    @Test
    void "not shared path"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def shares = dep.sharesFactory.create(account)
        shares.path = '/test/not_shared'
        def message = shares.call()
        log.info "Received shares: {}", message
        assert message.meta.statuscode == 404
        assert message.data.size() == 0
    }

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.dep = injector.getInstance Dependencies
    }
}
