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
