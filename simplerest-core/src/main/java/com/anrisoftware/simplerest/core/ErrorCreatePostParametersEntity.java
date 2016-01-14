package com.anrisoftware.simplerest.core;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("serial")
public class ErrorCreatePostParametersEntity extends SimpleRestException {

    public ErrorCreatePostParametersEntity(UnsupportedEncodingException e) {
        super("Error create post parameters entity", e);
    }

}
