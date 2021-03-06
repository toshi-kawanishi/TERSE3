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

import static java.lang.Integer.min;
import java.util.AbstractList;
import java.util.List;
import java.util.function.BiFunction;

/**
 *
 * @author masao
 */
public class PairRandomList<S, T, U> extends AbstractList<U> {
    List<S> left;
    List<T> right;
    BiFunction<S, T, U> map;
    
    public PairRandomList(List<S> left, List<T> right, BiFunction<S, T, U> map) {
        this.left = left;
        this.right = right;
        this.map = map;
    }
    
    @Override
    public U get(int index) {
        return map.apply(left.get(index), right.get(index));
    }

    @Override
    public int size() {
        return min(left.size(), right.size());
    }
    
}
