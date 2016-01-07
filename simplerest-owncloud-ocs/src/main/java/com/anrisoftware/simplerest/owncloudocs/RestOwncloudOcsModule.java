package com.anrisoftware.simplerest.owncloudocs;

import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsPoolingStatus.OwncloudOcsPoolingStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsStatus.OwncloudOcsStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.StatusPoolingSimpleGetWorker.StatusPoolingSimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.StatusSimpleGetWorker.StatusSimpleGetWorkerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @see OwncloudOcsStatusFactory
 * @see OwncloudOcsPoolingStatusFactory
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class RestOwncloudOcsModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(OwncloudStatus.class,
                OwncloudOcsStatus.class).build(OwncloudOcsStatusFactory.class));
        install(new FactoryModuleBuilder().implement(OwncloudStatus.class,
                OwncloudOcsPoolingStatus.class).build(
                OwncloudOcsPoolingStatusFactory.class));
        install(new FactoryModuleBuilder().implement(
                StatusSimpleGetWorker.class, StatusSimpleGetWorker.class)
                .build(StatusSimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                StatusPoolingSimpleGetWorker.class,
                StatusPoolingSimpleGetWorker.class).build(
                StatusPoolingSimpleGetWorkerFactory.class));
    }

}
