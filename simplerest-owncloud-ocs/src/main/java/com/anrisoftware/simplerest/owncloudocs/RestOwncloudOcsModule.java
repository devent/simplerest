package com.anrisoftware.simplerest.owncloudocs;

import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudUploadFile;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsPoolingStatus.OwncloudOcsPoolingStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsStatus.OwncloudOcsStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsUploadFile.OwncloudOcsUploadFileFactory;
import com.anrisoftware.simplerest.owncloudocs.PoolingSimpleGetWorker.PoolingSimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.PoolingSimplePutWorker.PoolingSimplePutWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.SimpleGetWorker.SimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.SimplePutWorker.SimplePutWorkerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @see OwncloudOcsStatusFactory
 * @see OwncloudOcsPoolingStatusFactory
 * @see OwncloudOcsUploadFileFactory
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
        install(new FactoryModuleBuilder().implement(OwncloudUploadFile.class,
                OwncloudOcsUploadFile.class).build(
                OwncloudOcsUploadFileFactory.class));
        install(new FactoryModuleBuilder().implement(SimpleGetWorker.class,
                SimpleGetWorker.class).build(SimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                PoolingSimpleGetWorker.class, PoolingSimpleGetWorker.class)
                .build(PoolingSimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(SimplePutWorker.class,
                SimplePutWorker.class).build(SimplePutWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                PoolingSimplePutWorker.class, PoolingSimplePutWorker.class)
                .build(PoolingSimplePutWorkerFactory.class));
    }

}
