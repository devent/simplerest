package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultSharesMessage implements SharesMessage {

    private final DefaultOwncloudMessage<ShareData> message;

    public DefaultSharesMessage() {
        this.message = new DefaultOwncloudMessage<ShareData>();
    }

    public void setMeta(DefaultOwncloudMeta meta) {
        message.setMeta(meta);
    }

    @Override
    public OwncloudMeta getMeta() {
        return message.getMeta();
    }

    public void setData(List<DefaultShareData> data) {
        message.setData(data);
    }

    @Override
    public List<ShareData> getData() {
        return message.getData();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(message).toString();
    }
}
