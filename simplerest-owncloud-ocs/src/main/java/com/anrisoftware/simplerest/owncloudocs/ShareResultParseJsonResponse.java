package com.anrisoftware.simplerest.owncloudocs;

import java.util.Map;

import com.anrisoftware.simplerest.json.AbstractParseJsonResponseFromMap;
import com.anrisoftware.simplerest.ocs.DefaultShareResultMessage;
import com.anrisoftware.simplerest.ocs.ShareResultMessage;
import com.fasterxml.jackson.core.type.TypeReference;

class ShareResultParseJsonResponse extends
        AbstractParseJsonResponseFromMap<ShareResultMessage> {

    protected ShareResultParseJsonResponse() {
        super("ocs",
                new TypeReference<Map<String, DefaultShareResultMessage>>() {
        });
    }

}
