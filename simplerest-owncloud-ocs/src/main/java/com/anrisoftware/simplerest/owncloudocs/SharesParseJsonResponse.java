package com.anrisoftware.simplerest.owncloudocs;

import java.util.Map;

import com.anrisoftware.simplerest.json.AbstractParseJsonResponseFromMap;
import com.anrisoftware.simplerest.ocs.DefaultSharesMessage;
import com.anrisoftware.simplerest.ocs.SharesMessage;
import com.fasterxml.jackson.core.type.TypeReference;

class SharesParseJsonResponse extends
        AbstractParseJsonResponseFromMap<SharesMessage> {

    protected SharesParseJsonResponse() {
        super("ocs", new TypeReference<Map<String, DefaultSharesMessage>>() {
        });
    }

}
