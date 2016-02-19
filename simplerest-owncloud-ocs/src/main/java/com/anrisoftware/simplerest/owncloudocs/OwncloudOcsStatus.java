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

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.owncloud.OwncloudAccount;
import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;
import com.anrisoftware.simplerest.owncloudocs.StatusSimpleGetWorker.StatusSimpleGetWorkerFactory;
import com.google.inject.assistedinject.Assisted;

/**
 * <p>
 * Requests the status of the Owncloud server.
 * </p>
 *
 * <p>
 * Usage example
 * </p>
 *
 * <pre>
 * DefaultOwncloudAccountFactory accountFactory;
 * account = accountFactory.create(new URI(account))
 * OwncloudOcsStatusFactory statusFactory;
 * status = statusFactory.create(account);
 * status.call();
 * status.getInstalled()
 * </pre>
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public class OwncloudOcsStatus extends AbstractOwncloudOcsStatus {

    /**
     * Factory to create the status request.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.1
     */
    public interface OwncloudOcsStatusFactory {

        OwncloudStatus create(OwncloudAccount account);

    }

    @Inject
    private StatusSimpleGetWorkerFactory statusSimpleGetWorkerFactory;

    @Inject
    OwncloudOcsStatus(@Assisted OwncloudAccount account) {
        super(account);
    }

    @Override
    protected OwncloudStatusMessage callStatusFromWorker(URI requestUri)
            throws SimpleRestException {
        StatusSimpleGetWorker requestWorker = statusSimpleGetWorkerFactory
                .create(this, requestUri, getAccount());
        OwncloudStatusMessage message = requestWorker.retrieveData();
        return message;
    }

}
