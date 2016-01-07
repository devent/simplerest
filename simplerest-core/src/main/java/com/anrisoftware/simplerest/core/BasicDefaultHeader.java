package com.anrisoftware.simplerest.core;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public enum BasicDefaultHeader {

    GZIP_COMPRESSION(new BasicHeader("Accept-Encoding", "gzip, deflate"));

    private Header header;

    private BasicDefaultHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return header;
    }
}
