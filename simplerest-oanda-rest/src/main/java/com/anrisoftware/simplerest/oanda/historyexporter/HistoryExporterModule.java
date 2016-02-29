/*
 * Copyright 2016 Erwin Müller <erwin.mueller@deventm.org>
 *
 * This file is part of simplerest-oanda-rest.
 *
 * simplerest-oanda-rest is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * simplerest-oanda-rest is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with simplerest-oanda-rest. If not, see <http://www.gnu.org/licenses/>.
 */
package com.anrisoftware.simplerest.oanda.historyexporter;

import javax.swing.table.TableModel;

import com.anrisoftware.simplerest.oanda.historyexporter.OpenDocumentHistoryExporter.OpenDocumentHistoryExporterFactory;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Installs the spreadsheet history exporter.
 *
 * @see OpenDocumentHistoryExporterFactory
 * @see BidaskCandlesTableModelFactory
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 0.3
 */
public class HistoryExporterModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().implement(
                OpenDocumentHistoryExporter.class,
                OpenDocumentHistoryExporter.class).build(
                OpenDocumentHistoryExporterFactory.class));
        install(new FactoryModuleBuilder().implement(TableModel.class,
                BidaskCandlesTableModel.class).build(
                BidaskCandlesTableModelFactory.class));
    }

}
