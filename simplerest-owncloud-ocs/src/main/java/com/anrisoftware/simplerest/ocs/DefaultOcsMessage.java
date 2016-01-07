package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultOcsMessage implements OcsMessage {

    private OcsMeta meta;

    private List<? extends OcsData> data;

    public void setMeta(DefaultOcsMeta meta) {
        this.meta = meta;
    }

    @Override
    public OcsMeta getMeta() {
        return meta;
    }

    public void setData(List<? extends OcsData> data) {
        this.data = data;
    }

    @Override
    public List<? extends OcsData> getData() {
        return data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
