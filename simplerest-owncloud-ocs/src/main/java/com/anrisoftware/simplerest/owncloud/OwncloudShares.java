package com.anrisoftware.simplerest.owncloud;

import java.util.concurrent.Callable;

import com.anrisoftware.simplerest.core.SimpleRestException;
import com.anrisoftware.simplerest.ocs.SharesMessage;

/**
 * Retrieves information about shares.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudShares extends Callable<SharesMessage> {

    /**
     * Returns the account required for authentication.
     *
     * @return the {@link OandaAccount}.
     */
    OwncloudAccount getAccount();

    /**
     * Sets the path to the file or folder.
     *
     * @param path
     *            the {@link String} path or {@code null}.
     */
    void setPath(String path);

    /**
     * Sets to return not only the shares from the current user but all shares
     * from the given file.
     *
     * @param reshares
     *            set to {@code true} to return all shares.
     */
    void setReshares(boolean reshares);

    /**
     * Sets to return all shares within a folder, given that path defines a
     * folder.
     *
     * @param subfiles
     *            set to {@code true} to return all shares within a folder.
     */
    void setSubfiles(boolean subfiles);

    /**
     * Retrieves information about shares.
     *
     * @return this {@link SharesMessage}.
     */
    @Override
    SharesMessage call() throws SimpleRestException;

}
