package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.http.client.utils.URIBuilder;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.ocs.DefaultOcsShareMessage;
import com.anrisoftware.simplerest.ocs.OcsShareMessage;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudShares;
import com.anrisoftware.simplerest.owncloudocs.SimpleGetWorker.SimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * Retrieves information about shares.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
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
    public OcsShareMessage call() throws SimpleRestException {
        return sendRequest();
    }

    private DefaultOcsShareMessage sendRequest() throws SimpleRestException {
        URI requestUri = getRequestURI();
        SimpleGetWorker requestWorker = simpleGetWorkerFactory.create(this,
                requestUri, getAccount(), parseResponse, nopParseResponse);
        DefaultOcsShareMessage data = (DefaultOcsShareMessage) requestWorker
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
