package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;

import javax.inject.Inject;

import com.anrisoftware.simplerest.core.AbstractPoolingSimpleGetWorker;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.google.inject.assistedinject.Assisted;

class PoolingSimpleGetWorker extends
        AbstractPoolingSimpleGetWorker<OwncloudStatusMessage> {

    interface PoolingSimpleGetWorkerFactory {

        PoolingSimpleGetWorker create(Object parent, URI requestUri,
                OwncloudAccount account);

    }

    @Inject
    PoolingSimpleGetWorker(@Assisted Object parent, @Assisted URI requestUri,
            @Assisted OwncloudAccount account,
            ParseStatusResponse parseResponse,
            NopParseResponse parseErrorResponse) {
        super(parent, requestUri, parseResponse, parseErrorResponse);
        setAccount(account);
        setCompressed(true);
        addHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=utf-8");
    }

}
