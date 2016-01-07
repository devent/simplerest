package com.anrisoftware.simplerest.owncloud;

import static org.apache.commons.lang3.Validate.notNull;

import java.net.URI;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class DefaultOwncloudAccount implements OwncloudAccount {

    public interface DefaultOwncloudAccountFactory {

        DefaultOwncloudAccount create();

        DefaultOwncloudAccount create(@Assisted("user") String user,
                @Assisted("password") String password, URI baseUri);

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
