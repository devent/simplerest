package com.anrisoftware.simplerest.owncloudocs;

import java.io.IOException;

import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.Message;
import com.anrisoftware.simplerest.core.ParseResponse;

class NopParseResponse implements ParseResponse<Message> {

    @Override
    public Message parse(HttpResponse response) throws IOException {
        return null;
    }

}
