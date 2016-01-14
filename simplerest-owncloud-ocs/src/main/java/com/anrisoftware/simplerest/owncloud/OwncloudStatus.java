package com.anrisoftware.simplerest.owncloud;

import java.util.concurrent.Callable;

import com.anrisoftware.simplerest.core.SimpleRestException;

/**
 * Requests the status of the Owncloud server.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudStatus extends Callable<OwncloudStatus> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OwncloudAccount getAccount();

    boolean getInstalled();

    String getVersion();

    String getVersionString();

    String getEdition();

    /**
     * Retrieves the status.
     *
     * @return this {@link OwncloudStatus}.
     */
    @Override
    OwncloudStatus call() throws SimpleRestException;

}
