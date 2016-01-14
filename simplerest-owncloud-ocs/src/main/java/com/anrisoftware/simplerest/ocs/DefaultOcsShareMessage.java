package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultOcsShareMessage implements OcsShareMessage {

    private final DefaultOcsMessage<OcsShareData> message;

    public DefaultOcsShareMessage() {
        this.message = new DefaultOcsMessage<OcsShareData>();
    }

    public void setMeta(DefaultOcsMeta meta) {
        message.setMeta(meta);
    }

    @Override
    public OcsMeta getMeta() {
        return message.getMeta();
    }

    public void setData(List<DefaultOcsShareData> data) {
        message.setData(data);
    }

    @Override
    public List<OcsShareData> getData() {
        return message.getData();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(message).toString();
    }
}
