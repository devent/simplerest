package com.anrisoftware.simplerest.owncloud;

import com.anrisoftware.simplerest.core.Message;

/**
 * Status message of the Owncloud installation.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudStatusMessage extends Message {

    boolean getInstalled();

    String getVersion();

    String getVersionstring();

    String getEdition();
}
