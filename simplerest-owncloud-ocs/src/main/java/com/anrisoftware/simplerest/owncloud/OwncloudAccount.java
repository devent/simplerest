package com.anrisoftware.simplerest.owncloud;

import java.net.URI;

import com.anrisoftware.simplerest.core.RestAccount;

/**
 * Owncloud REST account.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface OwncloudAccount extends RestAccount {

    String getUser();

    String getPassword();

    URI getBaseUri();
}
