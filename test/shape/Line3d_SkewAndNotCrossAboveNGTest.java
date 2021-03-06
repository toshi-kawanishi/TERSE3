/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import static java.lang.Math.sqrt;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static shape.ShapeUtil.*;

/**
 * skew and no cross as a ray.
 * 
 * y
 * 
 * 3---x----------------
 * 
 * 2               y-from
 *               /
 * 1           /
 *           /
 * 0       y-to
 *     1   2   3   4  x
 * @author masao
 */
public class Line3d_SkewAndNotCrossAboveNGTest {
    Line3d tested = line3(1, 3, 1, 2, 0, 0);
    Line3d line = line3(4, 2, 2, -1, -1, 0);
    Ray3d ray = ray3(4, 2, 2, -1, -1, 0);
    Segment3d segment = segment3(4, 2, 2, 2, 0, 2);
    TPoint3d point = point3(4, 2, 2);
    
    public Line3d_SkewAndNotCrossAboveNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @Test
    public void testEquals_Object() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.equals(line);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testEquals_Line3d_double() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.equals(line, 0);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testEquivalent_Line3d_double() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.equivalent(line, 0);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testEquivalent_Line3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.equivalent(line);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testAsRay() {
        System.out.println(test.TestUtils.methodName(0));
        Ray3d result = tested.asRay();
        Ray3d expected = ray3(1, 3, 1, 2, 0, 0);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testReverse() {
        System.out.println(test.TestUtils.methodName(0));
        Line3d result = tested.reverse();
        Line3d expected = line3(1, 3, 1, -2, 0, 0);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testMove() {
        System.out.println(test.TestUtils.methodName(0));
        Line3d result = tested.move(vector3(1, 2, 3));
        Line3d expected = line3(2, 5, 4, 2, 0, 0);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testPerpendicular() {
        System.out.println(test.TestUtils.methodName(0));
        TVector3d result = tested.perpendicular(point);
        TVector3d expected = vector3(0, 1, -1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testClosestSegment() {
        System.out.println(test.TestUtils.methodName(0));
        Segment3d result = tested.closestSegment(line);
        Segment3d expected = segment3(5, 3, 1, 5, 3, 2);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testIntersect_Line3d() {
        // N/A
    }

    @Test
    public void testIntersect_Line3d_TVector3d() {
        testIntersect_Line3d();
    }

    @Test
    public void testClosestPoint_Line3d() {
        System.out.println(test.TestUtils.methodName(0));
        TPoint3d result = tested.closestPoint(line);
        TPoint3d expected = point3(5, 3, 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testClosestPoint_Ray3d() {
        System.out.println(test.TestUtils.methodName(0));
        TPoint3d result = tested.closestPoint(ray);
        TPoint3d expected = point3(4, 3, 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testClosestPoint_Segment3d() {
        System.out.println(test.TestUtils.methodName(0));
        TPoint3d result = tested.closestPoint(segment);
        TPoint3d expected = point3(4, 3, 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testClosestPoint_TPoint3d() {
        System.out.println(test.TestUtils.methodName(0));
        TPoint3d result = tested.closestPoint(point);
        TPoint3d expected = point3(4, 3, 1);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testDistance_Line3d() {
        System.out.println(test.TestUtils.methodName(0));
        double result = tested.distance(line);
        double expected = 1;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testDistance_Ray3d() {
        System.out.println(test.TestUtils.methodName(0));
        double result = tested.distance(ray);
        double expected = sqrt(2);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testDistance_Segment3d() {
        System.out.println(test.TestUtils.methodName(0));
        double result = tested.distance(segment);
        double expected = sqrt(2);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testDistance_TPoint3d() {
        System.out.println(test.TestUtils.methodName(0));
        double result = tested.distance(point);
        double expected = sqrt(2);
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testHit_TPoint3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.hit(point);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testHit_Segment3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.hit(segment);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testHit_Ray3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.hit(ray);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testHit_Line3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.hit(line);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testParallel_Segment3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.parallel(segment);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testParallel_Ray3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.parallel(ray);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

    @Test
    public void testParallel_Line3d() {
        System.out.println(test.TestUtils.methodName(0));
        boolean result = tested.parallel(line);
        boolean expected = false;
        System.out.println("result  : " + result);
        System.out.println("expected: " + expected);
        assertEquals(result, expected);
    }

}
