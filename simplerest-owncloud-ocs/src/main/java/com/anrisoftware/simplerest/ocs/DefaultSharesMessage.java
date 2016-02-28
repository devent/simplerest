/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-owncloud-ocs.
 *
 * simplerest-owncloud-ocs is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-owncloud-ocs is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-owncloud-ocs. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.ocs;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Shares information message.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
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
