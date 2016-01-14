package com.anrisoftware.simplerest.owncloudocs;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.Message;
import com.anrisoftware.simplerest.core.ParseResponse;

class NopParseResponse implements ParseResponse<Message> {

    @Override
    public Message parse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String content = IOUtils.toString(entity.getContent());
        System.out.println(content);// TODO
                                    // println
        return null;
    }

}
