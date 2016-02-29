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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.anrisoftware.simplerest.oanda.api.BidaskCandle;
import com.anrisoftware.simplerest.oanda.api.Candle;
import com.google.inject.assistedinject.Assisted;

/**
 * BID/ASK candle historic data table model.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 0.3
 */
public class BidaskCandlesTableModel implements TableModel {

    private static final String[] COLUMN_NAMES = new String[] { "time",
            "open-bid", "open-ask", "high-bid", "high-ask", "low-bit",
            "low-ask", "close-bid", "close-ask", "volume", "complete" };

    private static final Class<?>[] COLUMN_CLASSES = new Class<?>[] {
            Date.class, Double.class, Double.class, Double.class, Double.class,
            Double.class, Double.class, Double.class, Double.class,
            Integer.class, Boolean.class };

    private final List<Candle> history;

    /**
     * @see BidaskCandlesTableModelFactory#create(Collection)
     */
    @Inject
    BidaskCandlesTableModel(@Assisted Collection<? extends Candle> history) {
        this.history = new ArrayList<Candle>(history);
    }

    @Override
    public int getRowCount() {
        return history.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_NAMES[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BidaskCandle candle = (BidaskCandle) history.get(rowIndex);
        switch (columnIndex) {
        case 0:
            return candle.getTime().toDate();
        case 1:
            return candle.getOpenBid();
        case 2:
            return candle.getOpenAsk();
        case 3:
            return candle.getHighBid();
        case 4:
            return candle.getHighAsk();
        case 5:
            return candle.getLowBid();
        case 6:
            return candle.getLowAsk();
        case 7:
            return candle.getCloseBid();
        case 8:
            return candle.getCloseAsk();
        case 9:
            return candle.getVolume();
        case 10:
            return candle.isComplete();
        default:
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

}
