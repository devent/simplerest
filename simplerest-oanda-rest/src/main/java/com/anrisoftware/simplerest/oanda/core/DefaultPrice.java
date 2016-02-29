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
package com.anrisoftware.simplerest.oanda.core;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.anrisoftware.simplerest.oanda.api.Price;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Returns the price for the instrument.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class DefaultPrice implements Price {

    private Instrument instrument;

    private String instrumentName;

    private DateTime time;

    private BigDecimal bid;

    private BigDecimal ask;

    @JsonIgnore
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    @JsonIgnore
    public Instrument getInstrument() {
        return instrument;
    }

    @JsonProperty("instrument")
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    @Override
    public BigDecimal getBid() {
        return bid;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    @Override
    public BigDecimal getAsk() {
        return ask;
    }

}
