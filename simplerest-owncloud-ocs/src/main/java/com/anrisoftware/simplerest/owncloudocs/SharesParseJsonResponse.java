package com.anrisoftware.simplerest.owncloudocs;

import java.util.Map;

import com.anrisoftware.simplerest.json.AbstractParseJsonResponseFromMap;
import com.anrisoftware.simplerest.ocs.DefaultOcsShareMessage;
import com.anrisoftware.simplerest.ocs.OcsShareMessage;
import com.fasterxml.jackson.core.type.TypeReference;

class SharesParseJsonResponse extends
        AbstractParseJsonResponseFromMap<OcsShareMessage> {

    protected SharesParseJsonResponse() {
        super("ocs", new TypeReference<Map<String, DefaultOcsShareMessage>>() {
        });
    }

}
