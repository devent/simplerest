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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SheetTableModel.MutableTableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import com.anrisoftware.simplerest.oanda.api.Candle;
import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Exports history data to Open Document Spreadsheet.
 *
 * @author Erwin Mueller, erwin.mueller@deventm.org
 * @since 0.3
 */
public class OpenDocumentHistoryExporter {

    /**
     * Factory to create the history data to Open Document Spreadsheet exporter.
     *
     * @author Erwin Mueller, erwin.mueller@deventm.org
     * @since 0.3
     */
    public interface OpenDocumentHistoryExporterFactory {

        /**
         * Create the history data exporter.
         *
         * @param instrument
         *            the {@link Instrument}.
         *
         * @param history
         *            the {@link List} history data.
         *
         * @param modelFactory
         *            the {@link CandlesTableModelFactory}.
         *
         * @return the {@link OpenDocumentHistoryExporter}.
         */
        OpenDocumentHistoryExporter create(Instrument instrument,
                Collection<? extends Candle> history,
                CandlesTableModelFactory modelFactory);
    }

    private final Instrument instrument;

    private final TableModel model;

    /**
     * @see OpenDocumentHistoryExporterFactory#create(Instrument, Collection,
     *      CandlesTableModelFactory)
     */
    @AssistedInject
    OpenDocumentHistoryExporter(@Assisted Instrument instrument,
            @Assisted Collection<? extends Candle> history,
            @Assisted CandlesTableModelFactory modelFactory) {
        this.instrument = instrument;
        this.model = modelFactory.create(history);
    }

    /**
     * Saves the history to the document.
     *
     * @param file
     *            the {@link File} file to save to.
     *
     * @throws IOException
     *             if there was an error saving the data.
     */
    public void saveData(File file) throws IOException {
        try {
            writeData(file);
        } catch (IOException e) {
            throw new SaveOpenDocumentHistoryException(e, file);
        }
    }

    /**
     * Creates the spread sheet with the historical data.
     *
     * @return the {@link SpreadSheet}.
     */
    public SpreadSheet createSheet() {
        int colCount = model.getColumnCount();
        int rowCount = model.getRowCount() + 1;
        SpreadSheet ss = SpreadSheet.create(1, colCount, rowCount);
        return saveSheet(ss, 0);
    }

    private void writeData(File file) throws IOException {
        SpreadSheet ss = createSheet();
        ss.saveAs(file);
    }

    private SpreadSheet saveSheet(SpreadSheet spreadSheet, int sheetNumber) {
        int colCount = model.getColumnCount();
        int rowCount = model.getRowCount();
        MutableTableModel<SpreadSheet> mmodel;
        Sheet sheet = spreadSheet.getSheet(sheetNumber);
        sheet.setName(instrument.getDisplayName());
        mmodel = sheet.getMutableTableModel(0, 0);
        Object next;
        for (int col = 0; col < colCount; col++) {
            next = model.getColumnName(col);
            mmodel.setValueAt(next, 0, col);
            for (int row = 0; row < rowCount; row++) {
                next = model.getValueAt(row, col);
                mmodel.setValueAt(next, row + 1, col);
            }
        }
        return spreadSheet;
    }

}
