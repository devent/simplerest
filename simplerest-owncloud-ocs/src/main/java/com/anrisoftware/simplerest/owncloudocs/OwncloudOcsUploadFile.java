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
package com.anrisoftware.simplerest.owncloudocs;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudUploadFile;
import com.anrisoftware.simplerest.owncloudocs.SimplePutWorker.SimplePutWorkerFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * <p>
 * Uploads a file to the Owncloud server.
 * </p>
 *
 * <p>
 * Usage example
 * </p>
 *
 * <pre>
 * DefaultOwncloudAccountFactory accountFactory;
 * account = accountFactory.create(new URI(account))
 * OwncloudOcsUploadFileFactory uploadFileFactory;
 * OwncloudUploadFile upload;
 * file = new File("test.txt");
 * remotePath = "Public/test.txt";
 * upload = uploadFileFactory.create(account, file, remotePath, ContentType.create('text/plain', 'UTF-8'))
 * upload.call();
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public class OwncloudOcsUploadFile implements OwncloudUploadFile {

    public interface OwncloudOcsUploadFileFactory {

        OwncloudUploadFile create(OwncloudAccount account, File file,
                String remotePath, ContentType contentType);

    }

    private final OwncloudAccount account;

    private final File file;

    private final ContentType contentType;

    private final String remotePath;

    @Inject
    private SimplePutWorkerFactory workerFactory;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    private String requiredEtag;

    private final NopParseResponse nopParseResponse;

    @Inject
    OwncloudOcsUploadFile(@Assisted OwncloudAccount account,
            @Assisted File file, @Assisted String remotePath,
            @Assisted ContentType contentType, NopParseResponse nopParseResponse) {
        this.account = account;
        this.file = file;
        this.remotePath = remotePath;
        this.contentType = contentType;
        this.requiredEtag = null;
        this.nopParseResponse = nopParseResponse;
    }

    public void setRequiredEtag(String etag) {
        this.requiredEtag = etag;
    }

    @Override
    public OwncloudAccount getAccount() {
        return account;
    }

    @Override
    public OwncloudUploadFile call() throws SimpleRestException {
        sendRequest();
        return this;
    }

    private void sendRequest() throws SimpleRestException {
        URI requestUri = getRequestURI();
        SimplePutWorker requestWorker = workerFactory.create(this,
                requestUri, getAccount(), nopParseResponse, nopParseResponse);
        requestWorker.addHeader("OC-Total-Length",
                Long.toString(FileUtils.sizeOf(file)));
        if (requiredEtag != null) {
            requestWorker.addHeader("If-Match", requiredEtag);
        }
        HttpEntity entity = new FileEntity(file, contentType);
        requestWorker.sendData(entity);
    }

    private URI getRequestURI() throws BadRequestURIException {
        try {
            return getRequestURI0();
        } catch (URISyntaxException e) {
            throw new BadRequestURIException(e);
        }
    }

    private URI getRequestURI0() throws URISyntaxException {
        URI baseUri = account.getBaseUri();
        String extraPath = baseUri.getPath();
        URIBuilder builder = new URIBuilder(baseUri);
        builder.setUserInfo(account.getUser(), account.getPassword());
        String statusPath = String.format("%s%s%s", extraPath,
                propertiesProvider.getOwncloudWebdavPath(), remotePath);
        builder.setPath(statusPath);
        return builder.build();
    }

}
