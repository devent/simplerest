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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * OCS response message.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.1
 */
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
