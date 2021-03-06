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

package collection;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 *
 * @author masao
 */
public class IterableSupplier<T> implements Supplier<T> {
    Iterable<T> body;
    Iterator<T> iter;
    
    public IterableSupplier(Iterable<T> body) {
        this.body = body;
        this.iter = body.iterator();
        assert iter.hasNext();
    }
    
    @Override
    public T get() {
        if (!iter.hasNext())
            iter = body.iterator();
        return iter.next();
    }
    
}
