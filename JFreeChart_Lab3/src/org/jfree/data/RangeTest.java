package org.jfree.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RangeTest {
	private Range range1;
	private Range range2;
	private Range range3;
	private Range range4;
	private Range NaNrange;
	
	@BeforeClass public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	public void setup() throws Exception {
		range1 = new Range(-1,1);
		range2 = new Range(-10,0);
		range3 = new Range(1,10);
		range4 = new Range(-10,10);
		NaNrange = new Range(Double.NaN,Double.NaN);
	}	

	//Testing method Range(double lower, double upper)
	
	
	/*
	 * Testing to confirm that upper bound < lower bound throws exception
	 */
	@Test (expected = IllegalArgumentException.class)
	public void RangeConstructorForInvalidRange() {
		Range r1 = new Range(10,0);
	}
	
	//Testing method getCentralValue()
	
	/*
	 * getCentralValue should return the correct value for a given range
	 */
	@Test 
	public void CentralValueIsCorrect() {
		assertEquals("The central value should be 0",0,range1.getCentralValue(),0.000000001d);
	}

	// Testing method combine(Range range1, Range range2)
	@Test
    public void CombineOverlappingRanges() throws Exception{
		Range r1 = new Range(0, 5);
		Range r2 = new Range(3, 10);
		Range result = Range.combine(r1, r2);
		Range expected = new Range(0, 10);
    	assertEquals("The combined range should be 0, 10", result, expected);
    }
	
	// Testing method combine(Range range1, Range range2)
	@Test
    public void CombineSubsetRanges() throws Exception{
		Range r1 = new Range(0, 5);
		Range r2 = new Range(1, 2);
		Range result = Range.combine(r1, r2);
		Range expected = new Range(0, 5);
    	assertEquals("The combined range should be 0, 5", result, expected);
    }
	
	// Testing method combine(Range range1, Range range2)
	
	/*
	 *  Testing to confirm that two overlapping ranges are merged correctly
	 */
	@Test
    public void CombineSeparatedRanges() throws Exception{
		Range r1 = new Range(0, 1);
		Range r2 = new Range(3, 4);
		Range result = Range.combine(r1, r2);
		Range expected = new Range(0, 4);
    	assertEquals("The combined range should be 0, 4", result, expected);
    }
	
	/*
	 *  Testing to confirm that ranges whos boundaries touch are merged correctly
	 */
	@Test
    public void CombineAdjacentRanges() throws Exception{
		Range r1 = new Range(0, 1);
		Range r2 = new Range(1, 2);
		Range result = Range.combine(r1, r2);
		Range expected = new Range(0, 2);
    	assertEquals("The combined range should be 0, 2", result, expected);
    }
	
	/*
	 * Testing to confirm that range1 is returned when a null range 2 is used
	 */
    @Test
    public void CombinedRangeShouldBeRange1() throws Exception{
    	assertEquals("The combined range should be -1,1",range1,Range.combine(range1,null));
    }
    
	/*
	 * Testing to confirm that range2 is returned when a null range 1 is used
	 */
    @Test
    public void CombinedRangeShouldBeRange2() throws Exception{
    	assertEquals("The combined range should be 1,2",range2,Range.combine(null,range2));
    }
    
    /*
     * Testing to confirm that null is returned for two null arguments
     */
    @Test
    public void CombinedRangeShouldBeNull() throws Exception{
    	assertNull("The combined range should be null", Range.combine(null, null));
    }
    
    // Testing method combineIgnoringNaN(Range range1, Range range2)
    
	/*
	 * Testing to confirm that range2 is returned when a null range 1 is used 
	 */
    @Test
    public void CombinedNaNRangeShouldBeRange2() {
    	assertEquals(range2,Range.combineIgnoringNaN(null,range2));
    }
    
    /*
     * Testing to confirm that null is returned for two null arguments
     */
    @Test
    public void CombinedNaNRangeShouldBeNull2() {
    	assertNull(Range.combineIgnoringNaN(null,null));
    }
    
	/*
	 * Testing to confirm that range1 is returned when a null range1 and NaN range1 is used
	 */
    @Test
    public void CombinedNaNRangeShouldBeRange1() {
    	assertEquals(range2,Range.combineIgnoringNaN(range1,null));
    }
    
    /*
     * Testing to confirm null is returned when null and NaN range are used
     */
    @Test
    public void CombinedNaNRangeShouldBeNull1() {
    	assertNull(Range.combineIgnoringNaN(NaNrange,null));
    }
   
    /*
     * Testing to confirm that null is returned for two NaN range objects
     */
    @Test
    public void CombinedRangeShouldBeNullTwoNaN() {
    	assertNull(Range.combineIgnoringNaN(NaNrange, NaNrange));
    }
    
    
    /*
     *  Testing to confirm that combineIgnoringNaN returns the correct range for two valid inputs
     */
    @Test
    public void CombinedRangeIsValid() {
    	assertEquals(range4,Range.combineIgnoringNaN(range2,range3));
    }
    
    // Testing method expandToInclude(Range range, double value)
    
    /*
     *  Checking expandToInclude returns the correct result for standard range and double inputs
     */
    @Test
    public void ExpandToIncludeUpperBoundShouldBe2() {
    	Range newRange = Range.expandToInclude(range1, 2);
    	assertEquals(1,newRange.getLowerBound(),0.000000001d);
    	assertEquals(2,newRange.getUpperBound(),0.000000001d);
    }
    
    @Test
    public void ExpandToIncludeLowerBoundShouldBeMinus2() {
    	Range newRange = Range.expandToInclude(range1, -2);
    	assertEquals(-2,newRange.getLowerBound(),0.000000001d);
    	assertEquals(1,newRange.getUpperBound(),0.000000001d);
    }
    
    
    /*
     * Checking that new range is properly constructed when expanding a null range object
     */
    @Test
    public void ExpandToIncludeNullShouldBeValue() {
    	Range testRange = new Range(1,1);
    	Range newRange = Range.expandToInclude(null, 1);
    	assertEquals(testRange,newRange);
    }
    
    /*
     *  Checking that range expansion of 0 returns a new range equivalent to the original
     */
    @Test
    public void ExpandToIncludeMiddleOfRangeShouldBeTheSame() {
    	assertEquals(range1,Range.expandToInclude(range1, 0));
    }
    
    // Testing method constrain(double value)
    
    /*
     * 
     */
    @Test
    public void ConstrainReturnValueShouldBeLowerBound() throws Exception {
    	double constrainValue = range1.constrain(-5);
    	assertEquals("The expected value was -1.0 but the returned value was " + constrainValue, -1.0, constrainValue, .000000001d);
    }
    
    @Test
    public void ConstrainReturnValueShouldBeUpperBound() throws Exception {
    	double constrainValue = range1.constrain(10);
    	assertEquals("The expected value was 1.0 but the returned value was " + constrainValue, 1.0, constrainValue, .000000001d);
    }
    
    @Test
    public void ConstrainReturnValueShouldBe5() throws Exception {
    	double constrainValue = range3.constrain(5);
    	assertEquals("The expected value was 5.0 but the returned value was " + constrainValue, 5.0, constrainValue, .000000001d);
    }
    
    @Test
    public void ConstrainReturnValueShouldBe0() throws Exception {
    	Range testRange = new Range(0, 0);
    	double constrainValue = testRange.constrain(-5);
    	assertEquals("The expected value was 0.0 but the returned value was " + constrainValue, 0.0, constrainValue, .000000001d);
    }
    
    //Testing method intersects(double b0, double b1)
    
    @Test
    public void IntersectReturnsTrueForOverlappingDouble() {
    	assertTrue("intersects should return true for overlapping range",range3.intersects(-1,20));
    }
    
    @Test 
    public void IntersectReturnsTrueForIntersectingDouble() {
    	assertTrue("Intersects should return true for overlapping range", range3.intersects(-5,5));
    }
    
    @Test
    public void IntersectReturnsFalseForNonIntersectingDouble() {
    	assertFalse("Intersect should return false for non intersecting range",range3.intersects(20,40));
    }
    
    //Testing method intersects(range)

    @Test 
    public void IntersectReturnsTrueForIntersectingRange() {
    	assertTrue("Intersects should return true for overlapping range", range3.intersects(range1));
    }
    
    @Test
    public void IntersectReturnsFalseForNonIntersectingRange() {
    	assertFalse("Intersect should return false for non intersecting range",range3.intersects(range2));
    }
    
    // Testing method contains(double value)
    @Test
    public void ContainsReturnsTrue() throws Exception {
    	assertTrue("Contains should return true for argument 2 on range3",range3.contains(2));
    }
    
    @Test
    public void ContainsReturnsFalse() throws Exception {
    	assertFalse("Contains should return false for argument -5 on range3",range3.contains(-5));
    }
    
    @Test 
    public void ContainsLowerBound() throws Exception {
    	assertTrue("Contains should return false for argument 1 on range3", range3.contains(1));
    }
    
    @Test
    public void ContainsUpperBound() throws Exception {
    	assertTrue("Contains should return false for argument 10 on range3", range3.contains(10));
    }
    
    // Testing method equals(Object obj)
    @Test
    public void EqualsSameRange() throws Exception {
    	Range testRange = new Range(-1, 1);
    	assertTrue("The equals method should be returning true on equivalent ranges.", testRange.equals(testRange));
    }
    
    @Test
    public void NotEqualsRange() throws Exception {
    	Range range = new Range(-1, 2);
    	assertFalse("The equals method should be returning false on nonequivalent ranges.", range.equals(range1));
    }
    
    @Test
    public void EqualsSimilarRangeObject() throws Exception {
    	Range range1Similar = new Range(-1, 1);
    	assertTrue("The equals method should be returning false on equivalent ranges.", range1.equals(range1Similar));
    }
    
    @Test
    public void EqualsReturnsFalseForNonRange() {
    	assertFalse(range1.equals(10));
    }
    
    // Testing method expand(Range range, double lowerMargin, double upperMargin)
    @Test
    public void ExpandsPercentanges() throws Exception {
    	Range testRange = new Range(2, 6);
    	Range newRange = Range.expand(testRange, 0.25, 0.5);
    	Range expectedRange = new Range(1,8);
    	assertTrue("The range did not properly expand bounds.", newRange.equals(expectedRange));
    }
    
    @Test
    public void ExpandsPercentangesBottom() throws Exception {
    	Range testRange = new Range(2, 6);
    	Range newRange = Range.expand(testRange, 0.25, 0.0);
    	Range expectedRange = new Range(1,6);
    	assertTrue("The range did not properly expand the lower bound.", newRange.equals(expectedRange));
    }
    
    @Test
    public void ExpandsPercentangesTop() throws Exception {
    	Range testRange = new Range(2, 6);
    	Range newRange = Range.expand(testRange, 0.0, 0.5);
    	Range expectedRange = new Range(2, 8);
    	assertTrue("The range did not properly expand the upper bound.", newRange.equals(expectedRange));
    }
    
    @Test (expected = InvalidParameterException.class)
    public void ExpandThrowsException() {
    	Range.expand(null, 2, 6);
    }
    
    @Test
    public void ExpandWithNegative() {
    	Range testRange = Range.expand(range1, -2, 0);
    	assertEquals(1,testRange.getLowerBound(),0.000000001d);
    	assertEquals(3,testRange.getUpperBound(),0.000000001d);
    	
    }
    // Testing method scale(Range base, double factor)
    
    @Test
    public void ScaledRangeShouldBeDouble() {
    	Range testRange = new Range(-2,2);
    	Range newRange = Range.scale(range1, 2);
    	assertEquals(testRange,newRange);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void ScaledRangeShouldThrowExceptionForNegative() {
    	Range newRange = Range.scale(range1, -1);
    }
    
    
    // Testing method shift(Range base, double delta)
    @Test
    public void ShiftShouldNotPassZeroDefault() {
    	Range newRange = Range.shift(range2, 11);
    	assertEquals(range2,newRange);
    	
    }
    
    // Testing method shift(Range base, double delta, boolean allowZeroCrossing)
    @Test
    public void ShiftShouldPassZero() {
    	Range testRange = new Range(1,11);
    	Range newRange = Range.shift(range2, 11,true);
    	assertEquals(testRange,newRange);
    }
    
    // Testing method shiftWithNoZeroCrossing(double value, double delta)
    @Test
    public void ShiftShouldNotPassZero() {
    	Range newRange = Range.shift(range4, -20);
    	assertEquals(range4,newRange);
    }
    
    // Testing method hashCode()
    @Test
    public void HashCodeShouldBeConsistent() {
    	assertEquals(range1.hashCode(),range1.hashCode());
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}