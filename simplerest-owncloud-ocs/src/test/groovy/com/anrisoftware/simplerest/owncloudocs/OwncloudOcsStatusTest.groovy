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

import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccountFromEnv
import com.anrisoftware.simplerest.owncloud.RestOwncloudModule
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsStatus.OwncloudOcsStatusFactory
import com.google.inject.Guice

/**
 * @see OwncloudOcsStatus
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
@Slf4j
@CompileStatic
class OwncloudOcsStatusTest {

    @Inject
    DefaultOwncloudAccountFromEnv account

    @Inject
    OwncloudOcsStatusFactory statusFactory

    @Test
    void "retrieve status"() {
        def status = statusFactory.create(account.build())
        status.call()
        assert status.installed == true
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
