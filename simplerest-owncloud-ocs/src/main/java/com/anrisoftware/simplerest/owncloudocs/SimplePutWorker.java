package com.anrisoftware.simplerest.owncloudocs;

import java.net.URI;

import javax.inject.Inject;

import com.anrisoftware.simplerest.core.AbstractSimplePutWorker;
import com.anrisoftware.simplerest.core.Message;
import com.anrisoftware.simplerest.core.ParseResponse;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes a PUT request.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
class SimplePutWorker extends AbstractSimplePutWorker {

    interface SimplePutWorkerFactory {

        SimplePutWorker create(
                Object parent,
                URI requestUri,
                OwncloudAccount account,
                @Assisted("parseResponse") ParseResponse<?> parseResponse,
                @Assisted("parseErrorResponse") ParseResponse<? extends Message> parseErrorResponse);

    }

    @SuppressWarnings("unchecked")
    @Inject
    SimplePutWorker(
            @Assisted Object parent,
            @Assisted URI requestUri,
            @Assisted OwncloudAccount account,
            @Assisted("parseResponse") ParseResponse<?> parseResponse,
            @Assisted("parseErrorResponse") ParseResponse<? extends Message> parseErrorResponse) {
        super(parent, requestUri, parseResponse, parseErrorResponse);
        setAccount(account);
        setCompressed(true);
        addHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=utf-8");
    }

}
