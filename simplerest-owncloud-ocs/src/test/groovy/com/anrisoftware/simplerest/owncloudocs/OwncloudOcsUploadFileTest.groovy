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
import static com.anrisoftware.simplerest.utils.Dependencies.*
import static com.google.inject.Guice.createInjector
import groovy.util.logging.Slf4j

import org.apache.commons.io.FileUtils
import org.apache.http.entity.ContentType
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.simplerest.utils.Dependencies

/**
 * @see OwncloudOcsUploadFile
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
class OwncloudOcsUploadFileTest {

    @Test
    void "send file"() {
        def account = dep.createAccount()
        if (!account) {
            return
        }
        def file = folder.newFile "test.txt"
        def remotePath = 'test/test.txt'
        FileUtils.write file, 'Start>>\nTest\n<<End\n'
        def status = dep.uploadFileFactory.create(account, file, remotePath, ContentType.create('text/plain', 'UTF-8'))
        status.call()
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    static Dependencies dep

    @BeforeClass
    static void createFactory() {
        toStringStyle
        this.dep = injector.getInstance Dependencies
    }
}
