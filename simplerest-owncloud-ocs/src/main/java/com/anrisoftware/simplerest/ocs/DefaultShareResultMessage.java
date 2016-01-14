package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DefaultShareResultMessage implements ShareResultMessage {

    private final DefaultOwncloudMessage<ShareResult> message;

    public DefaultShareResultMessage() {
        this.message = new DefaultOwncloudMessage<ShareResult>();
    }

    public void setMeta(DefaultOwncloudMeta meta) {
        message.setMeta(meta);
    }

    @Override
    public OwncloudMeta getMeta() {
        return message.getMeta();
    }

    public void setData(DefaultShareResult data) {
        message.addData(data);
    }

    @Override
    public List<ShareResult> getData() {
        return message.getData();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(message).toString();
    }
}
