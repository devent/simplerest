package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;

import javax.inject.Inject;

import com.anrisoftware.simplerest.core.AbstractSimpleGetWorker;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes a GET request to retrieve the status.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
class SimpleGetWorker extends
        AbstractSimpleGetWorker<OwncloudStatusMessage> {

    interface SimpleGetWorkerFactory {

        SimpleGetWorker create(Object parent, URI requestUri,
                OwncloudAccount account);

    }

    @Inject
    SimpleGetWorker(@Assisted Object parent, @Assisted URI requestUri,
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
