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

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.http.impl.client.CloseableHttpClient;

import com.anrisoftware.simplerest.core.AbstractSimpleGetWorker;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes a GET request to retrieve the status.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
class StatusSimpleGetWorker extends
        AbstractSimpleGetWorker<OwncloudStatusMessage> {

    interface StatusSimpleGetWorkerFactory {

        StatusSimpleGetWorker create(@Assisted Object parent,
                @Assisted URI requestUri, @Assisted OwncloudAccount account,
                @Assisted @Nullable CloseableHttpClient httpClient);

    }

    @Inject
    StatusSimpleGetWorker(@Assisted Object parent, @Assisted URI requestUri,
            @Assisted OwncloudAccount account,
            @Assisted @Nullable CloseableHttpClient httpClient,
            ParseStatusResponse parseResponse,
            NopParseResponse parseErrorResponse) {
        super(parent, requestUri, httpClient, parseResponse, parseErrorResponse);
        setAccount(account);
        setCompressed(true);
        addHeader("Content-Type",
                "application/x-www-form-urlencoded; charset=utf-8");
    }

}
