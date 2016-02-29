/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-oanda-rest.
 *
 * simplerest-oanda-rest is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-oanda-rest is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-oanda-rest. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.oanda.rest;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.ParseResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.inject.assistedinject.Assisted;

/**
 * Parses a named list from the JSON response.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
class NamedListParseResponse implements ParseResponse<Collection<?>> {

    /**
     * Factory to create the named list parser..
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 0.3
     */
    interface NamedListParseResponseFactory {

        /**
         * Creates the named list parser.
         *
         * @param objectsName
         *            the objects {@link String} name.
         *
         * @param typeRef
         *            the {@link TypeReference} to parse the JSON reply.
         *
         * @return the {@link NamedListParseResponse}.
         */
        NamedListParseResponse create(String objectsName,
                TypeReference<?> typeRef);
    }

    private final String objectsName;

    private final TypeReference<?> typeRef;

    /**
     * @see NamedListParseResponseFactory#create(String, TypeReference)
     */
    @Inject
    NamedListParseResponse(@Assisted String objectsName,
            @Assisted TypeReference<?> typeRef) {
        this.objectsName = objectsName;
        this.typeRef = typeRef;
    }

    @Override
    public Collection<?> parse(HttpResponse response) throws IOException {
        ObjectMapper mapper = createParserMapper();
        JsonParser parser = createParser(mapper, response.getEntity());
        return parseResponseList(mapper, parser);
    }

    private ObjectMapper createParserMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        return mapper;
    }

    private <T> Collection<T> parseResponseList(ObjectMapper mapper,
            JsonParser parser) throws IOException, JsonParseException,
            JsonMappingException {
        Map<String, Collection<T>> results = mapper.readValue(parser, typeRef);
        Collection<T> objs = results.get(objectsName);
        return objs;
    }

    private <T> JsonParser createParser(ObjectMapper mapper, HttpEntity entity)
            throws IOException, JsonParseException, JsonMappingException {
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(entity.getContent());
        return parser;
    }

}
