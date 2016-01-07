package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;

import javax.inject.Inject;

import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.anrisoftware.simplerest.owncloudocs.StatusPoolingSimpleGetWorker.StatusPoolingSimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

public class OwncloudOcsPoolingStatus extends AbstractOwncloudOcsStatus {

    public interface OwncloudOcsPoolingStatusFactory {

        OwncloudStatus create(OwncloudAccount account,
                CloseableHttpClient httpClient);

    }

    private final CloseableHttpClient httpClient;

    @Inject
    private StatusPoolingSimpleGetWorkerFactory getWorkerFactory;

    @Inject
    OwncloudOcsPoolingStatus(@Assisted OwncloudAccount account,
            @Assisted CloseableHttpClient httpClient) {
        super(account);
        this.httpClient = httpClient;
    }

    @Override
    protected OwncloudStatusMessage callStatusFromWorker(URI requestUri)
            throws SimpleRestException {
        StatusPoolingSimpleGetWorker requestWorker = getWorkerFactory.create(
                this, requestUri, getAccount());
        requestWorker.setHttpClient(httpClient);
        OwncloudStatusMessage message = requestWorker.retrieveData();
        return message;
    }

}
