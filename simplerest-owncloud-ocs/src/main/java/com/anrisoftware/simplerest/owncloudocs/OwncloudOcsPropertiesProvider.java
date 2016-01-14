/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.owncloudocs;

import java.net.URL;

import javax.inject.Singleton;

import com.anrisoftware.propertiesutils.AbstractContextPropertiesProvider;

/**
 * Provides Owncloud OCS properties from {@code "/owncloud_ocs.properties".}
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
@SuppressWarnings("serial")
@Singleton
public final class OwncloudOcsPropertiesProvider extends
        AbstractContextPropertiesProvider {

    private static final URL resource = OwncloudOcsPropertiesProvider.class
            .getResource("/owncloud_ocs.properties");

    OwncloudOcsPropertiesProvider() {
        super(OwncloudOcsPropertiesProvider.class, resource);
    }

    public String getOwncloudStatusPath() {
        return get().getProperty("status_path");
    }

    public String getOwncloudWebdavPath() {
        return get().getProperty("webdav_path");
    }
}
