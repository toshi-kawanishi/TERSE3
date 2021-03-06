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

package debug;

import static test.TestUtils.className;
import static test.TestUtils.methodName;

/**
 *
 * @author masao
 */
public class StackTraceCollectDump extends CollectDump {

    public StackTraceCollectDump(String name) {
        super(name);
    }
    
    @Override
    public <T> T set(String name, T object) {
        super.set(name, new StackTraceDump(object, 1));
        return object;
    }
    
    @Override
    public <T> T add(String name, T object) {
        super.add(name, new StackTraceDump(object, 1));
        return object;
    }
    
    public void countOnePoint(String name) {
        count(className(1)+"#"+methodName(1)+":"+name);
    }
}
