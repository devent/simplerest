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

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;

public abstract class AbstractOwncloudOcsStatus implements OwncloudStatus {

    private final OwncloudAccount account;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    private OwncloudStatusMessage statusMessage;

    protected AbstractOwncloudOcsStatus(OwncloudAccount account) {
        this.account = account;
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
        this.statusMessage = callStatusFromWorker(requestUri);
    }

    protected abstract OwncloudStatusMessage callStatusFromWorker(URI requestUri)
            throws SimpleRestException;

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
