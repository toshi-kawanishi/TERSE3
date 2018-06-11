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

package solver;

import collection.P;
import collection.TList;
import static function.ComparePolicy.inc;
import java.util.function.ToIntFunction;

/**
 * Knapsack problem solver.
 * 
 * @author masao
 */
public class Knapsack {
    public class Result {
        final TList<Integer> content;
        final int value;
        public Result() {
            this(TList.c(), 0);
        }

        public Result(TList<Integer> content, int value) {
            this.content = content;
            this.value = value;
        }

        public Result add(int addedIndex, int addedValue) {
            return new Result(content.append(TList.wrap(addedIndex)), value+addedValue);
        }
    }
    public Knapsack() {
    }
    
    /**
     * 
     * @param i from here to the end of the list are candidate elements to be used
     * @param rest remaining space
     * @param r seed of result
     * @param t target list of elements.
     * @return 
     */
    Result value(int i, int rest, Result r, TList<P<Integer, Integer>> t) {
        if (i==t.size())
            return r;
        if (rest<t.get(i).l())
            return value(i+1,rest,r,t);
        return TList.ofStatic(value(i+1,rest,r,t), value(i+1,rest-t.get(i).l(),r,t).add(i,t.get(i).r())).max(inc(x->x.value)).get();
    }
    
    public Result solve(int capacity, TList<P<Integer, Integer>> c) {
        return value(0, capacity, new Result(), c);
    }
    
    static public <T> Result solve(Knapsack k, int capacity, TList<T> target, ToIntFunction<T> volume, ToIntFunction<T> value) {
        return k.solve(capacity, target.map(x->P.p(volume.applyAsInt(x),value.applyAsInt(x))));
    }
    
    static public <T> TList<T> solveElements(Knapsack k, int capacity, TList<T> target, ToIntFunction<T> volume, ToIntFunction<T> value) {
        Result result = solve(k,capacity,target,volume,value);
        return target.pickUp(result.content);
    }
    
}