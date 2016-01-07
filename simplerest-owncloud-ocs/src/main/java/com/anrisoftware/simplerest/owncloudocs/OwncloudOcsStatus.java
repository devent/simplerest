package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.http.client.utils.URIBuilder;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.anrisoftware.simplerest.owncloudocs.StatusSimpleGetWorker.StatusSimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

public class OwncloudOcsStatus implements OwncloudStatus {

    public interface OwncloudOcsStatusFactory {

        OwncloudStatus create(OwncloudAccount account);

    }

    private final OwncloudAccount account;

    @Inject
    private StatusSimpleGetWorkerFactory statusSimpleGetWorkerFactory;

    @Inject
    private OwncloudOcsPropertiesProvider propertiesProvider;

    private OwncloudStatusMessage statusMessage;

    @Inject
    OwncloudOcsStatus(@Assisted OwncloudAccount account) {
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
        StatusSimpleGetWorker requestWorker = statusSimpleGetWorkerFactory
                .create(this, requestUri, getAccount());
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
