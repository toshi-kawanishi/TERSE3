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

import iterator.MergeListIterator;
import java.util.AbstractSequentialList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import static collection.ListOperations.checkOrdered;

/**
 *
 * @author masao
 */
public class MergeList<T> extends AbstractSequentialList<T> {
    List<T> left;
    List<T> right;
    Comparator<T> c;
    
    public MergeList(List<T> left, List<T> right, Comparator<T> c) {
        assert checkOrdered(left, c);
        assert checkOrdered(right, c);
        this.left = left;
        this.right = right;
        this.c = c;
    }
    
    @Override
    public ListIterator<T> listIterator(int index) {
        return MergeListIterator.c(left.listIterator(), right.listIterator(), c, index);
    }

    @Override
    public int size() {
        return left.size() + right.size();
    }
}
