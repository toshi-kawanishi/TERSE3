/*
 Copyright 2017, 2018 Masao Tomono

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and limitations under the License.
 */

package orderedSet;

import java.util.*;
import string.Message;

/**
 * 
 * @author mtomono
 * @param <T>
 */
public class Range<T extends Comparable<T>> {
    T start;
    T end;
    Order<T> order;
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Range))
            return false;
        return order.eq(this.start, ((Range<T>)o).start) && order.eq(this.end, ((Range<T>)o).end);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.start);
        hash = 29 * hash + Objects.hashCode(this.end);
        hash = 29 * hash + Objects.hashCode(this.order);
        return hash;
    }

    protected Range() {
        
    }
    
    @Override
    public Range<T> clone() {
        return new Range<>(start, end, order);
    }
    
    public Range(T start, T end, Order<T> order) {
        assert order.le(start, end) : "start=" + start + ":end = " + end;
        this.start = start;
        this.end = end;
        this.order = order;
    }
    
    public Range(T start, T end) {
        this(start, end, Default.order);
    }
    
    static public <T extends Comparable<T>> List<Range<T>> c(Order<T> order, T... range) {
        if (range.length == 0) {
            return Collections.<Range<T>>emptyList();
        } else if (range.length % 2 != 0) {
            throw new RuntimeException("Range(T...) illegal number of parameters");
        } else {
            List<Range<T>> retval = new ArrayList<>(range.length / 2);
            for (int i = 0; i < range.length; i += 2) {
                retval.add(new Range<>(range[i], range[i+1], order));
            }
            return retval;
        }
    }

    public boolean isEmpty() {
        return order.eq(start, end);
    }
    
    public boolean startsAt(T value) {
        return order.eq(start, value);
    }
    
    public boolean endsAt(T value) {
        return order.eq(end, value);
    }
    
    /**
     * contains a value
     * @param value
     * @return 
     */
    public boolean contains(T value) {
        return order.le(this.start, value) && order.lt(value, this.end);
    }
    
    /**
     * contains every value in values
     * @param values
     * @return 
     */
    public boolean contains(List<T> values) {
        return values.stream().allMatch(this::contains);
    }
    
    public boolean contains(Range<T> another) {
        return !another.hasLowerThan(start) && !another.hasUpperThan(end);
    }
    
    public boolean overlaps(List<T> values) {
        return values.stream().anyMatch(this::contains);
    }
    
    public boolean overlaps(Range<T> another) {
        return another.hasUpperThan(start) && another.hasLowerThan(end);
    }

    /**
     * the width of a range start to value is not zero
     * @param value
     * @return 
     */
    public boolean hasLowerThan(T value) {
        return order.lt(this.start, value);
    }
    
    /**
     * the width of a range value to end is not zero
     * @param value
     * @return 
     */
    public boolean hasUpperThan(T value) {
        return order.lt(value, this.end);
    }
    
    public boolean startsUpperThan(Range<T> another) {
        return another.hasLowerThan(start);
    }
    
    public boolean endsLowerThan(Range<T> another) {
        return another.hasUpperThan(end);
    }
    
    public boolean isLowerThan(Range<T> another) {
        return !another.hasLowerThan(end);
    }
    
    public boolean isUpperThan(Range<T> another) {
        return another.isLowerThan(this);
    }
    
    public boolean isBelow(T value) {
        return order.le(end, value);
    }
    
    public boolean isAbove(T value) {
        return order.lt(value, start);
    }
    
    public boolean adjoins(Range<T> another) {
        return adjoinsAtStartWith(another) || adjoinsAtEndWith(another);
    }
    
    public boolean adjoinsAtStartWith(Range<T> another) {
        return another.endsAt(start);
    }

    public boolean adjoinsAtEndWith(Range<T> another) {
        return another.startsAt(end);
    }
    
    public Optional<Range<T>> intersect(Range<T> another) {
        return another.getUpper(start).flatMap(r->r.getLower(end));
    }
    
    /**
     * extend the range according to the added point.
     * added point is not necessarily included. specifically this range is below
     * the added point, the point is not included in this range.
     * @param value
     * @return 
     */
    public Range<T> cover(T value) {
        if (!hasUpperThan(value))
            return new Range<>(start, value, order);
        if (!hasLowerThan(value))
            return new Range<>(value, end, order);
        return this;
    }
    
    public Range<T> cover(Range<T> another) {
        return another.cover(start).cover(end);
    }
    
    public Range<T> cover(Optional<Range<T>> another) {
        return another.map(p->cover(p)).orElse(this);
    }
    
    
    public Optional<Range<T>> getLower(T value) {
        if (!hasLowerThan(value))
            return Optional.empty();
        return Optional.of(new Range<>(this.start, hasUpperThan(value) ? value : this.end, order));
    }
    
    public Optional<Range<T>> getUpper(T value) {
        if (!hasUpperThan(value))
            return Optional.empty();
        return Optional.of(new Range<>(hasLowerThan(value) ? value : this.start, this.end, order));
    }
    
    public Optional<Range<T>> getLowerRoomIn(Range<T> another) {
        return another.getLower(start);
    }
    
    public Optional<Range<T>> getUpperRoomIn(Range<T> another) {
        return another.getUpper(end);
    }
    @Override
    public String toString() {
        return Message.nl(start).c("<->").c(end).toString();
    }
    
    public T clip(T x) {
        if (order.lt(x, start))
            return start;
        if (order.ge(x, end))
            return end;
        return x;
    }
    
    public T start() {
        return start;
    }
    
    public T end() {
        return end;
    }
    
}
