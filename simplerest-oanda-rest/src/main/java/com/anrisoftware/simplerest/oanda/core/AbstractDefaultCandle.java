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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.anrisoftware.simplerest.oanda.api.Candle;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tradeable instrument market values.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class AbstractDefaultCandle implements Candle {

    private DateTime time;

    private int volume;

    private boolean complete;

    @JsonProperty("time")
    public void setTime(DateTime time) {
        this.time = time;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    @JsonProperty("volume")
    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @JsonProperty("complete")
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
