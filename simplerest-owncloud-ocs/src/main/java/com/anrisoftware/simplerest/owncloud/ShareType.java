package com.anrisoftware.simplerest.owncloud;

/**
 * Share type.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public enum ShareType {

    /**
     * User share.
     */
    user,

    /**
     * Group share.
     */
    group,

    /**
     * Public link share.
     */
    link,

    /**
     * Federated cloud share.
     */
    cloud
}
