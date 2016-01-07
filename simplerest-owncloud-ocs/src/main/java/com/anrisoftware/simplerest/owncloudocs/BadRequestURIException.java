/*
 * Copyright 2015 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of forecast-forex-oanda. All rights reserved.
 */
package com.anrisoftware.simplerest.owncloudocs;

import java.net.URISyntaxException;

import com.anrisoftware.simplerest.core.SimpleRestException;

@SuppressWarnings("serial")
public class BadRequestURIException extends SimpleRestException {

    public BadRequestURIException(URISyntaxException e) {
        super("Bad request URI", e);
    }

}
