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

import java.util.HashMap;
import java.util.Map;
import static shape.ShapeUtil.p2i;
import shape.TPoint2i;

/**
 * Memo recursive version of Knapsack.
 * @author masao
 */
public class KnapsackMemo extends Knapsack {
    Map<TPoint2i, Result>memo;

    public KnapsackMemo(Map<TPoint2i, Result> memo) {
        super();
        this.memo = memo;
    }
    
    public KnapsackMemo() {
        this(new HashMap<>());
    }
    
    @Override
    public Result value(int i, int rest) {
        return memo.computeIfAbsent(p2i(i,rest),p->super.value(i,rest));
    }
}
