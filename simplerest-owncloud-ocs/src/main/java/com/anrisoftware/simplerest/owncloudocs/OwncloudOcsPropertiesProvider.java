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

    public String getOwncloudSharesPath() {
        return get().getProperty("shares_path");
    }
}
