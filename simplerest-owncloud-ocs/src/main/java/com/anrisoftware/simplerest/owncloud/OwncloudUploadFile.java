package com.anrisoftware.simplerest.owncloud;

import java.util.concurrent.Callable;

import com.anrisoftware.simplerest.core.SimpleRestException;

/**
 * Uploads a file to the Owncloud server.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudUploadFile extends Callable<OwncloudUploadFile> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OwncloudAccount getAccount();

    /**
     * Uploads the file.
     *
     * @return this {@link OwncloudUploadFile}.
     */
    @Override
    OwncloudUploadFile call() throws SimpleRestException;
}
