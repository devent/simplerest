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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.simplerest.oanda.api.Instrument;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tradeable instrument, i.e. currency pairs, CFDs, and commodities.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class DefaultInstrument implements Instrument {

    /**
     * Factory to create the tradeable instrument, i.e. currency pairs, CFDs,
     * and commodities.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    public interface DefaultInstrumentFactory {

        /**
         * Creates the tradeable instrument, i.e. currency pairs, CFDs, and
         * commodities.
         *
         * @return the {@link DefaultInstrument}.
         */
        DefaultInstrument create();
    }

    private String name;

    private String displayName;

    private BigDecimal pip;

    private long maxTradeUnits;

    @JsonProperty("instrument")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @JsonProperty("displayName")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("pip")
    public void setPip(BigDecimal pip) {
        this.pip = pip;
    }

    @Override
    public BigDecimal getPip() {
        return pip;
    }

    @JsonProperty("maxTradeUnits")
    public void setMaxTradeUnits(long maxTradeUnits) {
        this.maxTradeUnits = maxTradeUnits;
    }

    @Override
    public long getMaxTradeUnits() {
        return maxTradeUnits;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultInstrument)) {
            return false;
        }
        DefaultInstrument rhs = (DefaultInstrument) obj;
        return name.equals(rhs.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
