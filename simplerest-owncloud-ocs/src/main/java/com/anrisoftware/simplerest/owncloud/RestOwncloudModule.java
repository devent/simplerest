package com.anrisoftware.simplerest.owncloud;

import com.anrisoftware.simplerest.owncloud.DefaultOwncloudAccount.DefaultOwncloudAccountFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @see DefaultOwncloudAccountFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class RestOwncloudModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(
                DefaultOwncloudAccount.class, DefaultOwncloudAccount.class)
                .build(DefaultOwncloudAccountFactory.class));
    }

}
