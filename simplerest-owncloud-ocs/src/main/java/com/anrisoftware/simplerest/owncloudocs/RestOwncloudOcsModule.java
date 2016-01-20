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

import com.anrisoftware.simplerest.owncloud.OwncloudStatus;
import com.anrisoftware.simplerest.owncloud.OwncloudUploadFile;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsCreateShare.OwncloudOcsCreateShareFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsPoolingStatus.OwncloudOcsPoolingStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsShares.OwncloudOcsSharesFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsStatus.OwncloudOcsStatusFactory;
import com.anrisoftware.simplerest.owncloudocs.OwncloudOcsUploadFile.OwncloudOcsUploadFileFactory;
import com.anrisoftware.simplerest.owncloudocs.PoolingSimpleGetWorker.PoolingSimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.PoolingSimplePutWorker.PoolingSimplePutWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.SimpleGetWorker.SimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.SimplePostWorker.SimplePostWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.SimplePutWorker.SimplePutWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.StatusPoolingSimpleGetWorker.StatusPoolingSimpleGetWorkerFactory;
import com.anrisoftware.simplerest.owncloudocs.StatusSimpleGetWorker.StatusSimpleGetWorkerFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @see OwncloudOcsStatusFactory
 * @see OwncloudOcsPoolingStatusFactory
 * @see OwncloudOcsUploadFileFactory
 * @see OwncloudOcsSharesFactory
 * @see OwncloudOcsCreateShareFactory
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
        install(new FactoryModuleBuilder().implement(OwncloudOcsShares.class,
                OwncloudOcsShares.class).build(OwncloudOcsSharesFactory.class));
        install(new FactoryModuleBuilder().implement(
                OwncloudOcsCreateShare.class, OwncloudOcsCreateShare.class)
                .build(OwncloudOcsCreateShareFactory.class));
        installStatusWorkers();
        installPoolingWorkers();
        installSimpleWorkers();
    }

    private void installStatusWorkers() {
        install(new FactoryModuleBuilder().implement(
                StatusSimpleGetWorker.class, StatusSimpleGetWorker.class)
                .build(StatusSimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                StatusPoolingSimpleGetWorker.class,
                StatusPoolingSimpleGetWorker.class).build(
                StatusPoolingSimpleGetWorkerFactory.class));
    }

    private void installSimpleWorkers() {
        install(new FactoryModuleBuilder().implement(SimpleGetWorker.class,
                SimpleGetWorker.class).build(SimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(SimplePutWorker.class,
                SimplePutWorker.class).build(SimplePutWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(SimplePostWorker.class,
                SimplePostWorker.class).build(SimplePostWorkerFactory.class));
    }

    private void installPoolingWorkers() {
        install(new FactoryModuleBuilder().implement(
                PoolingSimpleGetWorker.class, PoolingSimpleGetWorker.class)
                .build(PoolingSimpleGetWorkerFactory.class));
        install(new FactoryModuleBuilder().implement(
                PoolingSimplePutWorker.class, PoolingSimplePutWorker.class)
                .build(PoolingSimplePutWorkerFactory.class));
    }

}
