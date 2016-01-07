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
 * @see OwncloudOcsStatus
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class OwncloudOcsStatusTest {

    @Test
    void "retrieve status"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def status = dep.statusFactory.create(account)
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
