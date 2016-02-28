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
package com.anrisoftware.simplerest.ocs;

/**
 * Share information.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
public interface ShareData extends OwncloudData {

    int getId();

    String getItemType();

    int getItemSource();

    String getParent();

    int getShareType();

    String getShareWith();

    int getFileSource();

    String getFileTarget();

    String getPath();

    int getPermissions();

    long getStime();

    String getExpiration();

    String getToken();

    int getStorage();

    int getMailSend();

    String getUidOwner();

    String getShareWithDisplayname();

    String getDisplaynameOwner();

    String getMimetype();

    boolean getIsPreviewAvailable();
}
