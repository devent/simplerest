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

import com.anrisoftware.simplerest.oanda.api.MidpointCandle;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Midpoint-based candlesticks with tick volume.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class DefaultMidpointCandle extends AbstractDefaultCandle implements
        MidpointCandle {

    private BigDecimal openMid;

    private BigDecimal highMid;

    private BigDecimal lowMid;

    private BigDecimal closeMid;

    @JsonProperty("openMid")
    public void setOpenMid(BigDecimal openMid) {
        this.openMid = openMid;
    }

    @Override
    public BigDecimal getOpenMid() {
        return openMid;
    }

    @JsonProperty("highMid")
    public void setHighMid(BigDecimal highMid) {
        this.highMid = highMid;
    }

    @Override
    public BigDecimal getHighMid() {
        return highMid;
    }

    @JsonProperty("lowMid")
    public void setLowMid(BigDecimal lowMid) {
        this.lowMid = lowMid;
    }

    @Override
    public BigDecimal getLowMid() {
        return lowMid;
    }

    @JsonProperty("closeMid")
    public void setCloseMid(BigDecimal closeMid) {
        this.closeMid = closeMid;
    }

    @Override
    public BigDecimal getCloseMid() {
        return closeMid;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
