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

import java.util.Map;

import com.anrisoftware.simplerest.json.AbstractParseJsonResponseFromMap;
import com.anrisoftware.simplerest.ocs.DefaultShareResultMessage;
import com.anrisoftware.simplerest.ocs.ShareResultMessage;
import com.fasterxml.jackson.core.type.TypeReference;

class ShareResultParseJsonResponse extends
        AbstractParseJsonResponseFromMap<ShareResultMessage> {

    protected ShareResultParseJsonResponse() {
        super("ocs",
                new TypeReference<Map<String, DefaultShareResultMessage>>() {
        });
    }

}
