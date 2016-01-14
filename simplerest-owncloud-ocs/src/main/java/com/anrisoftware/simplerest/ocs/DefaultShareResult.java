package com.anrisoftware.simplerest.ocs;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultShareResult implements ShareResult {

    private int id;

    private String url;

    private String token;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
