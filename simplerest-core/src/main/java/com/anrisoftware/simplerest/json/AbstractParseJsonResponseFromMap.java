/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-core.
 *
 * simplerest-core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-core. If not, see <http://www.gnu.org/licenses/>.
 */
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
