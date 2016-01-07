/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.core;

import java.io.IOException;

import org.apache.http.HttpResponse;

/**
 * Parses the response.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public interface ParseResponse<T> {

    /**
     * Parses the response.
     *
     * @param response
     *            the {@link HttpResponse} response.
     *
     * @return the parses value.
     *
     * @throws IOException
     *             if there was an error parsing the response.
     */
    T parse(HttpResponse response) throws IOException;

}
