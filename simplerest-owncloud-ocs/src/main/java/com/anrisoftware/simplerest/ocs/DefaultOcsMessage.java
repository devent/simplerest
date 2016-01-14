package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultOcsMessage<T extends OcsData> implements OcsMessage<T> {

    private OcsMeta meta;

    private List<T> data;

    public void setMeta(DefaultOcsMeta meta) {
        this.meta = meta;
    }

    @Override
    public OcsMeta getMeta() {
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
