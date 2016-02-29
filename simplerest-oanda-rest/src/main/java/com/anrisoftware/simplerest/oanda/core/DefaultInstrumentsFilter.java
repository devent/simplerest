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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.simplerest.oanda.api.InstrumentsFilter;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Filters the instruments on the specified names.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class DefaultInstrumentsFilter implements InstrumentsFilter {

    /**
     * Factory to create the filter that filters the instruments on the
     * specified names.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    public interface DefaultInstrumentsFilterFactory {

        /**
         * Creates an empty filter.
         *
         * @return the {@link InstrumentsFilter}.
         */
        InstrumentsFilter create();

        /**
         * Creates the filter that filters the instruments on the specified
         * name.
         *
         * @param name
         *            the {@link String} name.
         *
         * @return the {@link InstrumentsFilter}.
         */
        InstrumentsFilter create(String name);

        /**
         * Creates the filter that filters the instruments on the specified
         * names.
         *
         * @param names
         *            the {@link Set} of names.
         *
         * @return the {@link InstrumentsFilter}.
         */
        InstrumentsFilter create(Set<String> names);
    }

    private final Set<String> names;

    @AssistedInject
    DefaultInstrumentsFilter() {
        this(new HashSet<String>());
    }

    @AssistedInject
    DefaultInstrumentsFilter(@Assisted String name) {
        this();
        names.add(name);
    }

    @AssistedInject
    DefaultInstrumentsFilter(@Assisted Set<String> names) {
        this.names = new HashSet<String>(names);
    }

    @Override
    public Set<String> getNames() {
        return Collections.unmodifiableSet(names);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(names).toString();
    }
}
