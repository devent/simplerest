/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.utils

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.apache.commons.lang3.StringUtils

import com.anrisoftware.simplerest.owncloud.OwncloudAccount
import com.anrisoftware.simplerest.owncloud.RestOwncloudModule
import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccount.DefaultOwncloudAccountFactory
import com.anrisoftware.simplerest.owncloudocs.RestOwncloudOcsModule
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsPoolingStatus.OwncloudOcsPoolingStatusFactory
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsStatus.OwncloudOcsStatusFactory
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsUploadFile.OwncloudOcsUploadFileFactory
import com.google.inject.Guice
import com.google.inject.Injector


/**
 * Dependencies for the Owncloud tests.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@CompileStatic
@Slf4j
class Dependencies {

    static final String OWNCLOUD_ACCOUNT_USERNAME_PROPERTY = 'user.owncloud.account.username'

    static final String OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY = 'user.owncloud.account.password'

    static final String OWNCLOUD_BASE_URI_PROPERTY = 'user.owncloud.base_uri'

    static String accountUsername = System.getProperty(OWNCLOUD_ACCOUNT_USERNAME_PROPERTY)

    static String accountPassword = System.getProperty(OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY)

    static String baseUri = System.getProperty(OWNCLOUD_BASE_URI_PROPERTY)

    static Injector injector = Guice.createInjector(
    new RestOwncloudModule(),
    new RestOwncloudOcsModule())

    @Inject
    DefaultOwncloudAccountFactory accountFactory

    @Inject
    OwncloudOcsStatusFactory statusFactory

    @Inject
    OwncloudOcsPoolingStatusFactory poolingStatusFactory

    @Inject
    OwncloudOcsUploadFileFactory uploadFileFactory

    OwncloudAccount createAccount() {
        if (StringUtils.isBlank(accountUsername)) {
            log.info "No Owncloud account set in {} and {} and {}.", OWNCLOUD_ACCOUNT_USERNAME_PROPERTY, OWNCLOUD_ACCOUNT_PASSWORD_PROPERTY, OWNCLOUD_BASE_URI_PROPERTY
            return null
        } else {
            def account = accountFactory.create()
            account.setUser accountUsername
            account.setPassword accountPassword
            account.setBaseUri new URI(baseUri)
            return account
        }
    }
}
