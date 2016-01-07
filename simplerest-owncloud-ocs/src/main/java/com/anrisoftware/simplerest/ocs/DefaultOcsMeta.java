package com.anrisoftware.simplerest.ocs;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultOcsMeta implements OcsMeta {

    private String status;

    private int statuscode;

    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    @Override
    public int getStatuscode() {
        return statuscode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
