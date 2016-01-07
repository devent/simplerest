package com.anrisoftware.simplerest.owncloudocs;

import com.anrisoftware.simplerest.json.AbstractParseJsonResponse;
import com.anrisoftware.simplerest.owncloud.OwncloudStatusMessage;

class ParseStatusResponse extends
        AbstractParseJsonResponse<OwncloudStatusMessage> {

    protected ParseStatusResponse() {
        super(DefaultOwncloudStatusMessage.class);
    }

}
