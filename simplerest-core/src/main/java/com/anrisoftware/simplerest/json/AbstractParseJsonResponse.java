package com.anrisoftware.simplerest.json;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.ParseResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses the HTTP response JSON output.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class AbstractParseJsonResponse<T> implements ParseResponse<T> {

    private final Class<? extends T> responseType;

    /**
     * Sets the response type.
     *
     * @param responseType
     *            the {@link Class} response type.
     */
    protected AbstractParseJsonResponse(Class<? extends T> responseType) {
        this.responseType = responseType;
    }

    @Override
    public T parse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(entity.getContent());
        T result = mapper.readValue(parser, responseType);
        return result;
    }

}
