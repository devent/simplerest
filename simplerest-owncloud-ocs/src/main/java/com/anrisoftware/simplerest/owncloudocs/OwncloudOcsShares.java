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
import com.anrisoftware.simplerest.ocs.DefaultSharesMessage;
import com.anrisoftware.simplerest.ocs.SharesMessage;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudShares;
import com.anrisoftware.simplerest.owncloudocs.SimpleGetWorker.SimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * <p>
 * Retrieves information about shares.
 * </p>
 *
 * <p>
 * Usage example
 * </p>
 *
 * <pre>
 * DefaultOwncloudAccountFactory accountFactory;
 * account = accountFactory.create(new URI(account))
 * OwncloudOcsSharesFactory sharesFactory;
 * OwncloudOcsShares shares = sharesFactory.create(account);
 * SharesMessage message = shares.call();
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public class OwncloudOcsShares implements OwncloudShares {

    public interface OwncloudOcsSharesFactory {

        OwncloudOcsShares create(OwncloudAccount account);

    }

    private final OwncloudAccount account;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    @Inject
    private SimpleGetWorkerFactory simpleGetWorkerFactory;

    @Inject
    private SharesParseJsonResponse parseResponse;

    @Inject
    private NopParseResponse nopParseResponse;

    private String path;

    private Boolean reshares;

    private Boolean subfiles;

    @Inject
    OwncloudOcsShares(@Assisted OwncloudAccount account) {
        this.account = account;
        this.path = null;
        this.reshares = null;
        this.subfiles = null;
    }

    @Override
    public OwncloudAccount getAccount() {
        return account;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void setReshares(boolean reshares) {
        this.reshares = reshares;
    }

    @Override
    public void setSubfiles(boolean subfiles) {
        this.subfiles = subfiles;
    }

    @Override
    public SharesMessage call() throws SimpleRestException {
        return sendRequest();
    }

    private DefaultSharesMessage sendRequest() throws SimpleRestException {
        URI requestUri = getRequestURI();
        SimpleGetWorker requestWorker = simpleGetWorkerFactory.create(this,
                requestUri, getAccount(), parseResponse, nopParseResponse);
        DefaultSharesMessage data = (DefaultSharesMessage) requestWorker
                .retrieveData();
        return data;
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
        addParameters(builder);
        String statusPath = String.format("%s%s", extraPath,
                propertiesProvider.getOwncloudSharesPath());
        builder.setPath(statusPath);
        return builder.build();
    }

    private void addParameters(URIBuilder builder) {
        builder.addParameter("format", "json");
        if (path != null) {
            addPathParameters(builder);
        }
    }

    private void addPathParameters(URIBuilder builder) {
        builder.addParameter("path", path);
        if (reshares != null) {
            builder.addParameter("reshares", Boolean.toString(reshares));
        }
        if (subfiles != null) {
            builder.addParameter("subfiles", Boolean.toString(subfiles));
        }
    }

}
