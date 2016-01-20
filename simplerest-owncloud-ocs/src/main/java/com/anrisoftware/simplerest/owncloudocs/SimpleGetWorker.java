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

import javax.inject.Inject;

import com.anrisoftware.simplerest.core.AbstractSimpleGetWorker;
import com.anrisoftware.simplerest.core.Message;
import com.anrisoftware.simplerest.core.ParseResponse;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.google.inject.assistedinject.Assisted;

/**
 * Makes a GET request.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("rawtypes")
class SimpleGetWorker extends AbstractSimpleGetWorker {

    interface SimpleGetWorkerFactory {

        SimpleGetWorker create(
                Object parent,
                URI requestUri,
                OwncloudAccount account,
                @Assisted("parseResponse") ParseResponse<?> parseResponse,
                @Assisted("parseErrorResponse") ParseResponse<? extends Message> parseErrorResponse);

    }

    @SuppressWarnings("unchecked")
    @Inject
    SimpleGetWorker(
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
