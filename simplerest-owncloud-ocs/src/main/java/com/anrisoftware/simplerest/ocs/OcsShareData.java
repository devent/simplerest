package com.anrisoftware.simplerest.ocs;

public interface OcsShareData extends OcsData {

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
