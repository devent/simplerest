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

import com.anrisoftware.simplerest.oanda.api.BidaskCandle;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BID/ASK-based candlesticks with tick volume.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class DefaultBidaskCandle extends AbstractDefaultCandle implements
        BidaskCandle {

    private BigDecimal openBid;

    private BigDecimal openAsk;

    private BigDecimal highBid;

    private BigDecimal highAsk;

    private BigDecimal lowBid;

    private BigDecimal lowAsk;

    private BigDecimal closeBid;

    private BigDecimal closeAsk;

    @JsonProperty("openBid")
    public void setOpenBid(BigDecimal openBid) {
        this.openBid = openBid;
    }

    @Override
    public BigDecimal getOpenBid() {
        return openBid;
    }

    @JsonProperty("openAsk")
    public void setOpenAsk(BigDecimal openAsk) {
        this.openAsk = openAsk;
    }

    @Override
    public BigDecimal getOpenAsk() {
        return openAsk;
    }

    @JsonProperty("highBid")
    public void setHighBid(BigDecimal highBid) {
        this.highBid = highBid;
    }

    @Override
    public BigDecimal getHighBid() {
        return highBid;
    }

    @JsonProperty("highAsk")
    public void setHighAsk(BigDecimal highAsk) {
        this.highAsk = highAsk;
    }

    @Override
    public BigDecimal getHighAsk() {
        return highAsk;
    }

    @JsonProperty("lowBid")
    public void setLowBid(BigDecimal lowBid) {
        this.lowBid = lowBid;
    }

    @Override
    public BigDecimal getLowBid() {
        return lowBid;
    }

    @JsonProperty("lowAsk")
    public void setLowAsk(BigDecimal lowAsk) {
        this.lowAsk = lowAsk;
    }

    @Override
    public BigDecimal getLowAsk() {
        return lowAsk;
    }

    @JsonProperty("closeBid")
    public void setCloseBid(BigDecimal closeBid) {
        this.closeBid = closeBid;
    }

    @Override
    public BigDecimal getCloseBid() {
        return closeBid;
    }

    @JsonProperty("closeAsk")
    public void setCloseAsk(BigDecimal closeAsk) {
        this.closeAsk = closeAsk;
    }

    @Override
    public BigDecimal getCloseAsk() {
        return closeAsk;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
