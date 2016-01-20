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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;

public class DefaultOwncloudStatusMessage implements OwncloudStatusMessage {

    private boolean installed;

    private String version;

    private String versionString;

    private String edition;

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    @Override
    public boolean getInstalled() {
        return installed;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersionstring(String versionString) {
        this.versionString = versionString;
    }

    @Override
    public String getVersionstring() {
        return versionString;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    @Override
    public String getEdition() {
        return edition;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
