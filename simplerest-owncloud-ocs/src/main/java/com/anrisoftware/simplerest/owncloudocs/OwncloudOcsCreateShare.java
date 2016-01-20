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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.ocs.ShareResultMessage;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudCreateShare;
import com.anrisoftware.simplerest.owncloud.ShareType;
import com.anrisoftware.simplerest.owncloudocs.SimplePostWorker.SimplePostWorkerFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Creates a new share.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class OwncloudOcsCreateShare implements OwncloudCreateShare {

    public interface OwncloudOcsCreateShareFactory {

        OwncloudOcsCreateShare create(OwncloudAccount account,
                @Assisted("path") String path, ShareType type,
                @Nullable @Assisted("shareWith") String shareWith,
                @Nullable Boolean publicUpload,
                @Nullable @Assisted("password") String password,
                @Nullable Integer permissions);

    }

    private final OwncloudAccount account;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    @Inject
    private SimplePostWorkerFactory simpleWorkerFactory;

    @Inject
    private ShareResultParseJsonResponse parseResponse;

    @Inject
    private NopParseResponse nopParseResponse;

    private final String path;

    private final ShareType type;

    private final String shareWith;

    private final Boolean publicUpload;

    private final String password;

    private final Integer permissions;

    @Inject
    OwncloudOcsCreateShare(@Assisted OwncloudAccount account,
            @Assisted("path") String path, @Assisted ShareType type,
            @Assisted("shareWith") @Nullable String shareWith,
            @Assisted @Nullable Boolean publicUpload,
            @Assisted("password") @Nullable String password,
            @Assisted @Nullable Integer permissions) {
        this.account = account;
        this.path = path;
        this.type = type;
        this.shareWith = shareWith;
        this.publicUpload = publicUpload;
        this.password = password;
        this.permissions = permissions;
    }

    @Override
    public OwncloudAccount getAccount() {
        return account;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ShareType getType() {
        return type;
    }

    @Override
    public String getShareWith() {
        return shareWith;
    }

    @Override
    public Boolean isPublicUpload() {
        return publicUpload;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Integer getPermissions() {
        return permissions;
    }

    @Override
    public ShareResultMessage call() throws SimpleRestException {
        return sendRequest();
    }

    private ShareResultMessage sendRequest() throws SimpleRestException {
        URI requestUri = getRequestURI();
        SimplePostWorker requestWorker = simpleWorkerFactory.create(this,
                requestUri, getAccount(), parseResponse, nopParseResponse);
        requestWorker.addHeader("OCS-APIREQUEST", "true");
        List<NameValuePair> postParameters = createPostParameters();
        @SuppressWarnings("unchecked")
        HttpEntity postParametersEntry = requestWorker
                .createPostParametersEntity(postParameters);
        ShareResultMessage data = (ShareResultMessage) requestWorker
                .sendData(postParametersEntry);
        return data;
    }

    private List<NameValuePair> createPostParameters() {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("path", path));
        list.add(new BasicNameValuePair("shareType", getTypeInt()));
        if (permissions != null) {
            list.add(new BasicNameValuePair("permissions", Integer
                    .toString(permissions)));
        }
        switch (type) {
        case user:
        case group:
            list.add(new BasicNameValuePair("shareWith", shareWith));
            break;
        case link:
            if (publicUpload != null) {
                list.add(new BasicNameValuePair("publicUpload", Boolean
                        .toString(publicUpload)));
            }
            if (password != null) {
                list.add(new BasicNameValuePair("password", password));
            }
            break;
        default:
            break;
        }
        return list;
    }

    private String getTypeInt() {
        switch (type) {
        case user:
            return "0";
        case group:
            return "1";
        case link:
            return "3";
        case cloud:
            return "6";
        }
        return null;
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
    }

}
