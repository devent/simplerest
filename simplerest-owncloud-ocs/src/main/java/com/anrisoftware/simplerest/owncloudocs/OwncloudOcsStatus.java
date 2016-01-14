package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;

import javax.inject.Inject;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.anrisoftware.simplerest.owncloudocs.SimpleGetWorker.SimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

public class OwncloudOcsStatus extends AbstractOwncloudOcsStatus {

    public interface OwncloudOcsStatusFactory {

        OwncloudStatus create(OwncloudAccount account);

    }

    @Inject
    private SimpleGetWorkerFactory statusSimpleGetWorkerFactory;

    @Inject
    OwncloudOcsStatus(@Assisted OwncloudAccount account) {
        super(account);
    }

    @Override
    protected OwncloudStatusMessage callStatusFromWorker(URI requestUri)
            throws SimpleRestException {
        SimpleGetWorker requestWorker = statusSimpleGetWorkerFactory
                .create(this, requestUri, getAccount());
        OwncloudStatusMessage message = requestWorker.retrieveData();
        return message;
    }

}
