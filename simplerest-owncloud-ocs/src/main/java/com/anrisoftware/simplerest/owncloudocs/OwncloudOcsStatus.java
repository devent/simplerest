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

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.anrisoftware.simplerest.owncloudocs.StatusSimpleGetWorker.StatusSimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * <p>
 * Requests the status of the Owncloud server.
 * </p>
 *
 * <p>
 * Usage example
 * </p>
 *
 * <pre>
 * DefaultOwncloudAccountFactory accountFactory;
 * account = accountFactory.create(new URI(account))
 * OwncloudOcsStatusFactory statusFactory;
 * status = statusFactory.create(account);
 * status.call();
 * status.getInstalled()
 * </pre>
 *
 * <p>
 * Usage example using pooling HTTP client.
 * </p>
 *
 * <pre>
 * DefaultOwncloudAccountFactory accountFactory;
 * account = accountFactory.create(new URI(account))
 * PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
 * CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
 * 
 * public void run() {
 *      OwncloudOcsStatusFactory statusFactory;
 *      status = statusFactory.create(account, httpclient);
 *      status.call();
 *      status.getInstalled()
 * }
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public class OwncloudOcsStatus implements OwncloudStatus {

    /**
     * Factory to create the status request.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.1
     */
    public interface OwncloudOcsStatusFactory {

        /**
         * Creates the status.
         */
        OwncloudStatus create(@Assisted OwncloudAccount account);

        /**
         * Creates the status using the specified pooling HTTP client.
         */
        OwncloudStatus create(@Assisted OwncloudAccount account,
                @Assisted CloseableHttpClient httpClient);

    }

    private final OwncloudAccount account;

    @Inject
    private StatusSimpleGetWorkerFactory statusSimpleGetWorkerFactory;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    private OwncloudStatusMessage statusMessage;

    private CloseableHttpClient httpClient;

    @AssistedInject
    OwncloudOcsStatus(@Assisted OwncloudAccount account) {
        this(account, null);
    }

    @AssistedInject
    OwncloudOcsStatus(@Assisted OwncloudAccount account,
            @Assisted CloseableHttpClient httpClient) {
        this.account = account;
        this.httpClient = httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public boolean getInstalled() {
        return statusMessage.getInstalled();
    }

    @Override
    public String getVersion() {
        return statusMessage.getVersion();
    }

    @Override
    public String getVersionString() {
        return statusMessage.getVersionstring();
    }

    @Override
    public String getEdition() {
        return statusMessage.getEdition();
    }

    @Override
    public OwncloudAccount getAccount() {
        return account;
    }

    @Override
    public OwncloudStatus call() throws SimpleRestException {
        retrieveStatus();
        return this;
    }

    private void retrieveStatus() throws SimpleRestException {
        URI requestUri = getRequestURI();
        StatusSimpleGetWorker requestWorker = statusSimpleGetWorkerFactory
                .create(this, requestUri, account, httpClient);
        OwncloudStatusMessage message = requestWorker.retrieveData();
        this.statusMessage = message;
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
        String statusPath = String.format("%s%s", extraPath,
                propertiesProvider.getOwncloudStatusPath());
        builder.setPath(statusPath);
        return builder.build();
    }

}
