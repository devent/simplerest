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
package com.anrisoftware.simplerest.utils

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.apache.commons.lang3.StringUtils

import com.anrisoftware.simplerest.owncloud.OwncloudAccount
import com.anrisoftware.simplerest.owncloud.RestOwncloudModule
import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccount.DefaultOwncloudAccountFactory
import com.anrisoftware.simplerest.owncloudocs.RestOwncloudOcsModule
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsCreateShare.OwncloudOcsCreateShareFactory
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsPoolingStatus.OwncloudOcsPoolingStatusFactory
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsShares.OwncloudOcsSharesFactory
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

    static final String OWNCLOUD_ACCOUNT_PROPERTY = 'user.owncloud.account'

    static String account = System.getProperty(OWNCLOUD_ACCOUNT_PROPERTY)

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

    @Inject
    OwncloudOcsSharesFactory sharesFactory

    @Inject
    OwncloudOcsCreateShareFactory createShareFactory

    OwncloudAccount createAccount() {
        if (StringUtils.isBlank(account)) {
            log.info "No Owncloud account set in '{}'", OWNCLOUD_ACCOUNT_PROPERTY
            return null
        } else {
            def account = accountFactory.create(new URI(account))
            return account
        }
    }
}
