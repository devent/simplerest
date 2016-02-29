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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.anrisoftware.simplerest.oanda.api.Granularity;
import com.google.inject.assistedinject.Assisted;

/**
 * Parses the granularities names and returns a sorted list of the parsed
 * granularities.
 *
 * @author Erwin Müller, erwin.mueller@deventm.de
 * @since 1.0
 */
public class Granularities implements List<Granularity> {

    /**
     * Factory to create the sorted granularities list.
     *
     * @author Erwin Müller, erwin.mueller@deventm.de
     * @since 1.0
     */
    public interface GranularitiesFactory {

        Granularities create(Set<String> granularites);
    }

    private final List<Granularity> list;

    @Inject
    Granularities(@Assisted Set<String> granularites) {
        this.list = getGranularities(granularites);
    }

    private List<Granularity> getGranularities(Set<String> granularites) {
        Set<Granularity> sorted = new TreeSet<Granularity>(
                new Comparator<Granularity>() {

                    @Override
                    public int compare(Granularity o1, Granularity o2) {
                        return o1.getDuration().compareTo(o2.getDuration());
                    }
                });
        for (String name : granularites) {
            sorted.add(Granularity.valueOf(name));
        }
        List<Granularity> result = new ArrayList<Granularity>(sorted.size());
        for (Granularity granularity : sorted) {
            result.add(granularity);
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Granularity> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Granularity e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Granularity> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Granularity> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public Granularity get(int index) {
        return list.get(index);
    }

    @Override
    public Granularity set(int index, Granularity element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Granularity element) {
        list.add(index, element);
    }

    @Override
    public Granularity remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Granularity> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Granularity> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Granularity> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        for (Granularity granularity : list) {
            builder.append(granularity);
        }
        return builder.toString();
    }

}
