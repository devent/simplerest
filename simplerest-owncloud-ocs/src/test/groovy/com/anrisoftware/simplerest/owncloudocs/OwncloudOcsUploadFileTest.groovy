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

import org.apache.commons.io.FileUtils
import org.apache.http.entity.ContentType
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import com.anrisoftware.simplerest.core.DefaultHttpClientProvider
import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccountFromEnv
import com.anrisoftware.simplerest.owncloud.RestOwncloudModule
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsUploadFile.OwncloudOcsUploadFileFactory
import com.google.inject.Guice

/**
 * @see OwncloudOcsUploadFile
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@Slf4j
@CompileStatic
class OwncloudOcsUploadFileTest {

    @Inject
    DefaultHttpClientProvider clientProvider

    @Inject
    DefaultOwncloudAccountFromEnv account

    @Inject
    OwncloudOcsUploadFileFactory uploadFileFactory

    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

    static List<LinkedHashMap> testCases = [
        [
            file: 'test_1.txt',
            fileContent: 'Start>>\nTest\n<<End\n',
            remotePath: 'test/test_1.txt',
            contentType: ContentType.create('text/plain', 'UTF-8'),
        ],
        [
            file: 'test_2.txt',
            fileContent: 'Start>>\nTest\n<<End\n',
            remotePath: 'test/test_2.txt',
            contentType: ContentType.create('text/plain', 'UTF-8'),
        ],
    ]

    @Test
    void "send file"() {
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            "do send file"(test)
        }
    }

    @Test
    void "send file, pooling"() {
        List<Thread> threads = []
        testCases.eachWithIndex { Map test, int k ->
            log.info '{}.Test case: {}', k, test
            threads << Thread.start { "do send file"(test) }
        }
        threads.each { it.join() }
    }

    def "do send file"(Map test) {
        File file = folder.newFile test.file as String
        String remotePath = test.remotePath
        ContentType contentType = test.contentType as ContentType
        FileUtils.write file, test.fileContent as String
        def upload = uploadFileFactory.create(account.build(), file, remotePath, contentType)
        upload.call()
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
