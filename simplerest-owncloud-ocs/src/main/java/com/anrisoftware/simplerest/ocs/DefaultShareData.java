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

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultShareData implements ShareData {

    private int id;

    private String itemType;

    private int itemSource;

    private String parent;

    private int shareType;

    private String shareWith;

    private int fileSource;

    private String fileTarget;

    private String path;

    private int permissions;

    private long stime;

    private String expiration;

    private String token;

    private int storage;

    private int mailSend;

    private String uidOwner;

    private String shareWithDisplayname;

    private String displaynameOwner;

    private String mimetype;

    private boolean isPreviewAvailable;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setItem_type(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String getItemType() {
        return itemType;
    }

    public void setItem_source(int itemSource) {
        this.itemSource = itemSource;
    }

    @Override
    public int getItemSource() {
        return itemSource;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getParent() {
        return parent;
    }

    public void setShare_type(int shareType) {
        this.shareType = shareType;
    }

    @Override
    public int getShareType() {
        return shareType;
    }

    public void setShare_with(String shareWith) {
        this.shareWith = shareWith;
    }

    @Override
    public String getShareWith() {
        return shareWith;
    }

    public void setFile_source(int fileSource) {
        this.fileSource = fileSource;
    }

    @Override
    public int getFileSource() {
        return fileSource;
    }

    public void setFile_target(String fileTarget) {
        this.fileTarget = fileTarget;
    }

    @Override
    public String getFileTarget() {
        return fileTarget;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    @Override
    public int getPermissions() {
        return permissions;
    }

    public void setStime(long stime) {
        this.stime = stime;
    }

    @Override
    public long getStime() {
        return stime;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    @Override
    public String getExpiration() {
        return expiration;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    @Override
    public int getStorage() {
        return storage;
    }

    public void setMail_send(int mailSend) {
        this.mailSend = mailSend;
    }

    @Override
    public int getMailSend() {
        return mailSend;
    }

    public void setUid_owner(String uidOwner) {
        this.uidOwner = uidOwner;
    }

    @Override
    public String getUidOwner() {
        return uidOwner;
    }

    public void setShare_with_displayname(String shareWithDisplayname) {
        this.shareWithDisplayname = shareWithDisplayname;
    }

    @Override
    public String getShareWithDisplayname() {
        return shareWithDisplayname;
    }

    public void setDisplayname_owner(String displaynameOwner) {
        this.displaynameOwner = displaynameOwner;
    }

    @Override
    public String getDisplaynameOwner() {
        return displaynameOwner;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @Override
    public String getMimetype() {
        return mimetype;
    }

    public void setIsPreviewAvailable(boolean isPreviewAvailable) {
        this.isPreviewAvailable = isPreviewAvailable;
    }

    @Override
    public boolean getIsPreviewAvailable() {
        return isPreviewAvailable;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
