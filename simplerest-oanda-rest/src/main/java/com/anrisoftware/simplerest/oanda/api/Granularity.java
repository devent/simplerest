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
package com.anrisoftware.simplerest.oanda.api;

import static org.joda.time.Duration.standardDays;
import static org.joda.time.Duration.standardHours;
import static org.joda.time.Duration.standardMinutes;
import static org.joda.time.Duration.standardSeconds;

import org.joda.time.Duration;

/**
 * The time range represented by each candlestick.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public enum Granularity {

    /*
     * Top of the minute alignment.
     */

    /**
     * 5 seconds.
     */
    S5(standardSeconds(5)),

    /**
     * 10 seconds.
     */
    S10(standardSeconds(10)),

    /**
     * 15 seconds.
     */
    S15(standardSeconds(15)),

    /**
     * 30 seconds.
     */
    S30(standardSeconds(30)),

    /**
     * 1 minute.
     */
    M1(standardMinutes(1)),

    /*
     * Top of the hour alignment.
     */

    /**
     * 2 minutes.
     */
    M2(standardMinutes(2)),

    /**
     * 3 minutes.
     */
    M3(standardMinutes(3)),

    /**
     * 4 minutes.
     */
    M4(standardMinutes(4)),

    /**
     * 5 minutes.
     */
    M5(standardMinutes(5)),

    /**
     * 15 minutes.
     */
    M10(standardMinutes(10)),

    /**
     * 15 minutes.
     */
    M15(standardMinutes(15)),

    /**
     * 30 minutes.
     */
    M30(standardMinutes(30)),

    /**
     * 1 hour.
     */
    H1(standardHours(1)),

    /*
     * Start of day alignment (default 17:00, Timezone/New York).
     */

    /**
     * 2 hours.
     */
    H2(standardHours(2)),

    /**
     * 3 hours.
     */
    H3(standardHours(3)),

    /**
     * 4 hours.
     */
    H4(standardHours(4)),

    /**
     * 6 hours.
     */
    H6(standardHours(6)),

    /**
     * 8 hours.
     */
    H8(standardHours(8)),

    /**
     * 12 hours.
     */
    H12(standardHours(12)),

    /**
     * 1 day.
     */
    D(standardDays(1)),

    /*
     * Start of week alignment (default Friday).
     */

    /**
     * 1 week.
     */
    W(standardDays(7)),

    /*
     * Start of month alignment (First day of the month).
     */

    /**
     * 1 month.
     */
    M(standardDays(30));

    private Duration duration;

    private Granularity(Duration duration) {
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }
}
