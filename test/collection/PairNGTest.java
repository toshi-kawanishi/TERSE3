/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import java.util.Comparator;
import orderedSet.Comparators;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author masao
 */
public class PairNGTest {
    
    public PairNGTest() {
    }

    @Test
    public void testEquals() {
        System.out.println("testEquals");
        P<String, Integer> result = new P<>("wow", 0);
        P<String, Integer> expected = new P<>("wow", 0);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }
    
    @Test
    public void testFirstIsDifferent() {
        System.out.println("testFirstIsDifferent");
        P<String, Integer> result = new P<>("wow", 0);
        P<String, Integer> expected = new P<>("now", 0);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertNotEquals(result, expected);
    }
    
    @Test
    public void testSecondIsDifferent() {
        System.out.println("testSecondIsDifferent");
        P<String, Integer> result = new P<>("wow", 0);
        P<String, Integer> expected = new P<>("wow", 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertNotEquals(result, expected);
    }
    
    @Test
    public void testTotallyDifferent() {
        System.out.println("testTotallyDifferent");
        P<String, Integer> result = new P<>("wow", 0);
        P<String, Integer> expected = new P<>("now", 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertNotEquals(result, expected);
    }
    
    @DataProvider(name="toList")
    public Object[][] toList() {
        return new Object[][] {
                {P.p(0,1), TList.of(0,1)},
                {P.p(P.p(0, 1),2), TList.of(0,1,2)},
                {P.p(0,P.p(1,2)), TList.of(0,1,2)},
        };
    }
    @Test(dataProvider="toList")
    public void testToList(P p, TList expected) {
        System.out.println(test.TestUtils.methodName(0));
        TList result = p.toList();
        System.out.println("result  : "+result);
        System.out.println("expected: "+expected);
        assertEquals(result, expected);
    }
    
    @Test
    public void testLcomp_Comparator() {
        System.out.println(test.TestUtils.methodName(0));
        Comparator<P<Integer, P<Integer, Integer>>> tested = Comparators.<P<Integer,P<Integer,Integer>>>sof(p->p.l(),p->p.r().l(),p->p.r().r()).compile();
        int result = tested.compare(P.p(0,P.p(1, 2)), P.p(0, P.p(2, 1)));
        int expected = -1;
        System.out.println("result  : "+result);
        System.out.println("expected: "+expected);
        assertEquals(result, expected);
    }

    @Test
    public void testRLcomp_Comparator() {
        System.out.println(test.TestUtils.methodName(0));
        Comparator<P<Integer, P<Integer, Integer>>> tested = Comparators.<P<Integer,P<Integer,Integer>>>sof(p->p.r().r(),p->p.r().l(),p->p.l()).compile();
        int result = tested.compare(P.p(0,P.p(1, 2)), P.p(0, P.p(2, 1)));
        int expected = 1;
        System.out.println("result  : "+result);
        System.out.println("expected: "+expected);
        assertEquals(result, expected);
    }

    @Test
    public void testRcomp_Comparator() {
        System.out.println(test.TestUtils.methodName(0));
        Comparator<P<P<Integer, Integer>, Integer>> tested = Comparators.<P<P<Integer,Integer>,Integer>>sof(p->p.r(),p->p.l().r(),p->p.l().l()).compile();
        int result = tested.compare(P.p(P.p(1, 2),0), P.p(P.p(2, 1),0));
        int expected = 1;
        System.out.println("result  : "+result);
        System.out.println("expected: "+expected);
        assertEquals(result, expected);
    }
}
