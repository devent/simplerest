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
package com.anrisoftware.simplerest.owncloudocs;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import com.anrisoftware.simplerest.core.Message;
import com.anrisoftware.simplerest.core.ParseResponse;

class NopParseResponse implements ParseResponse<Message> {

    @Override
    public Message parse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        String content = IOUtils.toString(entity.getContent());
        System.out.println(content);// TODO
                                    // println
        return null;
    }

}
