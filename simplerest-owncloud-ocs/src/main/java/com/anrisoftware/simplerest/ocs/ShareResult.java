package com.anrisoftware.simplerest.ocs;

/**
 * Share result.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface ShareResult extends OwncloudData {

    /**
     * Returns the share identifier.
     *
     * @return the {@link Integer} identifier.
     */
    int getId();

    /**
     * Returns the shared link if the share was a public link.
     *
     * @return the {@link String} URL of the link or {@code null}.
     */
    String getUrl();

    /**
     * Returns the share token if the share was a public link.
     *
     * @return the {@link String} token or {@code null}.
     */
    String getToken();
}
