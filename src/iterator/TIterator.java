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

package iterator;

import static collection.c.a2i;
import collection.OneTimeIterable;
import collection.P;
import static collection.c.i2l;
import function.Holder;
import function.IntHolder;
import function.NoReach;
import function.TSupplier;
import static iterator.Iterators.toStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.*;
import java.util.stream.Stream;

/**
 *
 * @author masao
 */
public class TIterator<T> implements Iterator<T> {
    Iterator<T> body;
    Consumer<T> tee;
    
    static public <T> TIterator<T> set(Iterator<T> body) {
        return new TIterator<>(body);
    }
    
    public TIterator(Iterator<T> body) {
        this.body = body;
        this.tee = e->{};
    }
    
    public Iterator<?> base() {
        return (body instanceof TIterator) ? ((TIterator)body).base() : body;
    }
    
    @Override
    public boolean hasNext() {
        return body.hasNext();
    }
    
    @Override
    public T next() {
        return monitor(body.next());
    }
    
    private T monitor(T e) {
        tee.accept(e);
        return e;
    }
    
    public TIterator<T> tee(Consumer<T> tee) {
        this.tee = tee;
        return this;
    }
    
    static public <T> TIterator<T> of(T... t) {
        return set(a2i(t));
    }
    
    static public <T> TIterator<T> generate(Supplier<T> supplier) {
        return set(new SupplierWrapper<>(supplier));
    }
    
    static public <T> TIterator<T> iterate(T init, UnaryOperator<T> op) {
        return set(new OperatorWrapper<>(init, op));
    }
    
    static public TIterator<Integer> range(int start, int end) {
        assert end > start;
        IntHolder h = new IntHolder(start);
        return generate(()->h.push(h.get() + 1)).limit(end - start);
    }
    
    public T last() {
        assert hasNext();
        T retval = next();
        for (T t : this.i())
            retval = t;
        return retval;
    }
    
    public <S> TIterator<S> map(Function<T, S> map) {
        return set(new MapIterator<>(this, map));
    }
    
    public TIterator<T> concat(Iterator<T> iter) {
        return set(new IteratorIterator<>(a2i(this, iter)));
    }
    
    public TIterator<T> filter(Predicate<T> pred) {
        return set(new SelectIterator<>(this, pred));
    }
    
    public <S> TIterator<S> flatMap(Function<T, Iterator<S>> map) {
        return set(new IteratorIterator<>(new MapIterator<>(this, map)));
    }
    
    public <S> TIterator<S> weave(Function<T, Iterator<S>> map) {
        return set(new WeaveIterator<>(i2l(this.map(map))));
    }
    
    public TIterator<T> twist(Iterator<T> iter) {
        return set(new WeaveIterator<>(this, iter));
    }
    
    public TIterator<T> delimit(Supplier<T> delimiter) {
        return set(new Delimiter<>(this, delimiter));
    }
    
    public TIterator<T> before(Predicate<T> term) {
        return set(new BeforeIterator<>(this, term));
    }
    
    public TIterator<T> until(Predicate<T> term) {
        return set(new UntilIterator<>(this, term));
    }
    
    public TIterator<T> seek(Predicate<T> term) {
        return of(set(new UntilIterator<>(this, term)).last()).concat(this);
    }
    
    public TIterator<T> seek(int count) {
        for (int i = 0; i < count && hasNext(); i++)
            next();
        return this;
    }
       
    public TIterator<P<T, T>> diff() {
        if (!hasNext())
            return of();
        Holder<T> h = new Holder<>(next());
        return map(e->new P(h.push(e), e));
    }
        
    public <S> TIterator<P<T, S>> pair(Iterator<S> add) {
        assert (!(add instanceof TIterator) || base() != ((TIterator)add).base());
        return TIterator.set(new ZI<>(this, add));
    }
    
    public <S> TIterator<P<T, S>> diff(Iterator<S> add) {
        return diff(add, 1);
    }
    
    public <S> TIterator<P<T, S>> diff(Iterator<S> add, int seek) {
        return pair(set(add).seek(seek));
    }
    
    public <S> TIterator<P<T, S>> pmap(Function<T, S> map) {
        return map(e->P.p(e, map.apply(e)));
    }
    
    public TIterator<T> limit(long limit) {
        return set(new LimitIterator<>(this, limit));
    }
    
    public <S> TIterator<S> accum(S start, BiFunction<S, T, S> map) {
        Holder<S> h = new Holder<>(start);
        return of(start).concat(map(e->h.set(map.apply(h.get(), e))));
    }
    
    @Deprecated
    public <S> TIterator<S> heap(S start, BiFunction<T, S, S> map) {
        return accum(start, (a,b)->map.apply(b, a));
    }
    
    public TIterator<T> accum(BinaryOperator<T> map) {
        if (!hasNext())
            return set(Collections.emptyIterator());
        return accum(next(), map);
    }
    
    @Deprecated
    public TIterator<T> heap(BinaryOperator<T> map) {
        return accum((a,b)->map.apply(b,a));
    }
    
    public Stream<T> stream() {
        return toStream(this);
    }
    
    public Iterable<T> i() {
        return new OneTimeIterable<>(this);
    }
    
    public TSupplier<T> supplier(Supplier<T> shortage) {
        return TSupplier.f(()->this.hasNext() ? this.next() : shortage.get());
    }
    
    public TSupplier<T> supplier() {
        return TIterator.this.supplier(new NoReach<>());
    }
        
    public int count() {
        return Iterators.count(this);
    }
}
