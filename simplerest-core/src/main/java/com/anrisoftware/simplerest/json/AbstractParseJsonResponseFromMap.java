package com.anrisoftware.simplerest.json;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.ParseResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Parses the HTTP response JSON output. The response is expected to have a map
 * and it retrieves the data from the specified map key.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class AbstractParseJsonResponseFromMap<T> implements ParseResponse<T> {

    private final String keyName;

    private final TypeReference<? extends Map<String, ? extends T>> responseType;

    /**
     * Sets the response type.
     *
     * @param responseType
     *            the {@link Class} response type.
     */
    protected AbstractParseJsonResponseFromMap(String keyName,
            TypeReference<? extends Map<String, ? extends T>> responseType) {
        this.keyName = keyName;
        this.responseType = responseType;
    }

    @Override
    public T parse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(entity.getContent());
        Map<String, T> results = mapper.readValue(parser, responseType);
        return results.get(keyName);
    }

}
