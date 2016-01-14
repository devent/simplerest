package com.anrisoftware.simplerest.ocs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultOwncloudMessage<T extends OwncloudData> implements
        OwncloudMessage<T> {

    private OwncloudMeta meta;

    private List<T> data;

    public DefaultOwncloudMessage() {
        this.data = new ArrayList<T>();
    }

    public void setMeta(DefaultOwncloudMeta meta) {
        this.meta = meta;
    }

    @Override
    public OwncloudMeta getMeta() {
        return meta;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setData(List data) {
        this.data = data;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    public void addData(T data) {
        this.data.add(data);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
