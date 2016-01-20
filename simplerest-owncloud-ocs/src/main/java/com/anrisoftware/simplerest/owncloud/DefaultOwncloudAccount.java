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
package com.anrisoftware.simplerest.owncloud;

import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.Validate.notNull;

import java.net.URI;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class DefaultOwncloudAccount implements OwncloudAccount {

    public interface DefaultOwncloudAccountFactory {

        DefaultOwncloudAccount create();

        DefaultOwncloudAccount create(@Assisted("user") String user,
                @Assisted("password") String password, URI baseUri);

        DefaultOwncloudAccount create(@Assisted URI uri);

    }

    private String user;

    private String password;

    private URI baseUri;

    @AssistedInject
    DefaultOwncloudAccount() {
    }

    @AssistedInject
    DefaultOwncloudAccount(@Assisted("user") String user,
            @Assisted("password") String password, @Assisted URI baseUri) {
        this.user = user;
        this.password = password;
        this.baseUri = baseUri;
    }

    @AssistedInject
    DefaultOwncloudAccount(@Assisted URI uri) {
        String[] split = split(uri.getUserInfo(), ":");
        setUser(split[0]);
        setPassword(split[1]);
        setBaseUri(uri);
    }

    public void setUser(String user) {
        notNull(user, "user");
        this.user = user;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        notNull(password, "password");
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setBaseUri(URI baseUri) {
        notNull(baseUri, "baseUri");
        this.baseUri = baseUri;
    }

    @Override
    public URI getBaseUri() {
        return baseUri;
    }

}
