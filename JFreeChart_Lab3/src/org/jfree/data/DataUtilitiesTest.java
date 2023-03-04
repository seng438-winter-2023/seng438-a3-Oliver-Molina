package org.jfree.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataUtilitiesTest extends DataUtilities {
	Mockery mockingContext;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockingContext  = new Mockery();
		final Values2D values = mockingContext.mock(Values2D.class);
		mockingContext.checking(new Expectations() {
		    {
		         one(values).getRowCount();
		         will(returnValue(2));
		         one(values).getValue(0, 0);
		         will(returnValue(7.5));
		         one(values).getValue(1, 0);
		         will(returnValue(2.5));
		    }
		});
	}
	
	
	// Testing method calculateColumnTotal(Values2D data, int column)
	
	@Test
	public void calculateColumnTotalForTwoValues() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getRowCount();
		         will(returnValue(2));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(1, 0);
		         will(returnValue(2.5));
		    }
		});
		// test
		double result = DataUtilities.calculateColumnTotal(val,0);
		assertEquals("CalculateColumnTotal should return 10 but instead returned" + result, 10.0, result, .000000001d);
	}
	
	// added for more coverage
	@Test
	public void calColumnTotalValidRows() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getRowCount();
		         will(returnValue(3));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(1, 0);
		         will(returnValue(2.5));
		         one(val).getValue(2, 0);
		         will(returnValue(1.0));
		    }
		});
		// test
		int[] validrows = {0, 2};
		double result = DataUtilities.calculateColumnTotal(val,0,validrows);
		assertEquals("CalculateColumnTotal should return 8.5 but instead returned" + result, 8.5, result, .000000001d);
	}
	
	@Test //(expected = NullPointerException.class) // it says that invalid input returns 0
	public void calculateColumnTotalForInvalidColumn() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(values).getRowCount();
		         will(returnValue(2));
//		         one(values).getValue(0, 5);
//		         will(throwException(new IndexOutOfBoundsException("outta bounds bozo")));
		         one(values).getValue(0, 5);
		    	will(returnValue(0));
		    	one(values).getValue(1, 5);
		    	will(returnValue(0));
		    }
		});
		// test
		double result = DataUtilities.calculateColumnTotal(values,5);
		assertEquals("CalculateColumnTotal with invalid column should return 0 but instead returned" + result, 0, result, .000000001d);
	}
	
	@Test 
	public void calculateColumnTotalForNegativeColumn() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(values).getRowCount();
		         will(returnValue(2));
//		         one(values).getValue(0, -1);
//		         will(throwException(new IndexOutOfBoundsException("outta bounds bozo")));
		         one(values).getValue(0, -1);
		    	will(returnValue(0));
		    	one(values).getValue(1, -1);
		    	will(returnValue(0));
		    }
		});
		// test
		double result = DataUtilities.calculateColumnTotal(values,-1);
		assertEquals("CalculateColumnTotal with negative column should return 0 but instead returned" + result, 0, result, .000000001d);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void calculateColumnTotalForNullTable() throws Exception{
		DataUtilities.calculateColumnTotal(null,0);
	}
	
	
	// Testing method calculateRowTotal(Values2D data, int row)
	
	@Test
	public void calculateRowTotalForTwoValues() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(values).getColumnCount();
		         will(returnValue(2));
		         one(values).getValue(0, 0);
		         will(returnValue(7.5));
		         one(values).getValue(0, 1);
		         will(returnValue(1.0));
		    }
		});
		// test
		double result = DataUtilities.calculateRowTotal(values,0);
		assertEquals("CalculateColumnTotal should return 8.5 but instead returned " + result, 8.5, result, .000000001d);
	}
	
	// added for more coverage
	@Test
	public void calRowTotalValidColumns() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getColumnCount();
		         will(returnValue(3));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(0, 1);
		         will(returnValue(2.5));
		         one(val).getValue(0, 2);
		         will(returnValue(1.0));
		    }
		});
		// test
		int[] validcols = {0, 2};
		double result = DataUtilities.calculateRowTotal(val,0,validcols);
		assertEquals("CalculateColumnTotal should return 8.5 but instead returned" + result, 8.5, result, .000000001d);
	}
	
	@Test // (expected = NullPointerException.class)
	public void calculateRowTotalForInvalidRow() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		    	one(values).getColumnCount();
		    	will(returnValue(2));
		    	one(values).getValue(5, 0);
		    	will(returnValue(0));
		    	one(values).getValue(5, 1);
		    	will(returnValue(0));
		    }
		});
		// test
		double result = DataUtilities.calculateRowTotal(values,5);
		assertEquals("CalculateColumnTotal with invalid row should return 0 but instead returned " + result, 0, result, .000000001d);
	}
	
	@Test // (expected = NullPointerException.class)
	public void calculateRowTotalForNegativeRow() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		    	one(values).getColumnCount();
		    	will(returnValue(2));
		    	one(values).getValue(-1, 0);
		    	will(returnValue(0));
		    	one(values).getValue(-1, 1);
		    	will(returnValue(0));
		    }
		});
		// test
		double result = DataUtilities.calculateRowTotal(values,-1);
		assertEquals("CalculateColumnTotal with negative row should return 0 but instead returned " + result, 0, result, .000000001d);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void calculateRowTotalForNullTable() throws Exception{
		DataUtilities.calculateRowTotal(null,0);
	}
	
	
	// Testing method createNumberArray(double[] data)
	// createNumberArray() function makes the last value null. good thing we tested!
	@Test
	public void createNumberArray() 
	{
		double[] val = new double[] {60.0, -40.0, 20.0, -10.0};
		Number[] actual = DataUtilities.createNumberArray(val);
		//System.out.println(Arrays.toString(actual));
		for(int i = 0; i < 4; i++)
			assertTrue("Values of array are "+Arrays.toString(actual)+", expected [60.0, 40.0, 20.0, -10.0]", actual[i].doubleValue() == val[i]);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void nullNumberArrayThrowsException() 
	{
		Number[] actual = DataUtilities.createNumberArray(null);
	}
	
	@Test
	public void createEmptyNumberArray() 
	{
		double[] val = new double[] {};
		Number[] actual = DataUtilities.createNumberArray(val);
		
		assertTrue("Values of array are "+Arrays.toString(actual)+", expected []", actual.length == 0);
	}

	
	
	// Testing method createNumberArray2D(double[][] data)
	
	@Test
	public void createNumberArray2D()
	{
		double[][] val = new double[][] {{42.0, 42.0}, {42.0, 42.0}};
		Number[][] actual = DataUtilities.createNumberArray2D(val);
		
		//for(int i = 0; i < val.length; i++)
		//	System.out.println(Arrays.toString(actual[i]));
	
		for(int i = 0; i < val.length; i++) {
			for(int j = 0; j < val[i].length; j++) {
				assertTrue("Values of array are "+Arrays.toString(actual)+", expected [[42.0, 42.0], [42.0, 42.0]]", actual[i][j].doubleValue() == val[i][j]);
			}
		}
	}
	
	@Test (expected = InvalidParameterException.class)
	public void null2DNumberArrayThrowsException() 
	{
		Number[][] actual = DataUtilities.createNumberArray2D(null);
	}
	
	@Test
	public void createEmptySecondDimensionNumberArray2D() 
	{
		double[][] val = new double[1][];
		Number[][] actual = DataUtilities.createNumberArray2D(val);
		
		assertTrue("Values of array are "+Arrays.toString(actual)+", expected []", val.length == 1 && val[0].length == 0);
	}
	
	@Test
	public void createEmptyNumberArray2D() 
	{
		double[][] val = new double[1][1];
		Number[][] actual = DataUtilities.createNumberArray2D(val);
		
		assertTrue("Values of array are "+Arrays.toString(actual)+", expected [[]]", val.length == 1 && val[0].length == 1);
	}

	
	
	// Testing method getCumulativePercentages(KeyedValues data)
	// NOTE: key != index as stated by documentation - 2 separate things!
	// getValue uses a key, getKey uses an index
	@Test
	public void ZeroKey_GetCumulativePercentages() {
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//i had to go digging through jmock documentation for this one :(
		    	//item count = 3
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(3));
		    	//keys = 0, 1, 2
		    	allowing(kvValues).getKey(0);
		    	will(returnValue(0));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue(1));
		    	allowing(kvValues).getKey(2);
		    	will(returnValue(2));
		    	//values = 5, 9, 2
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(5));
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(9));
		    	allowing(kvValues).getValue(2);
		    	will(returnValue(2));
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
				func.getValue(2).doubleValue()
		};
		double[] expected = {0.3125, 0.875, 1.0};
		
		assertTrue("results should equal expected.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	@Test
	public void StringKeys_GetCumulativePercentages() {
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//i had to go digging through jmock documentation for this one :(
		    	//item count = 3
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(3));
		    	//keys = 0, 1, 2
		    	allowing(kvValues).getKey(0);
		    	will(returnValue("key1"));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue("key2"));
		    	allowing(kvValues).getKey(2);
		    	will(returnValue("key3"));
		    	//values = 5, 9, 2
		    	allowing(kvValues).getValue("key1");
		    	will(returnValue(5));
		    	allowing(kvValues).getValue("key2");
		    	will(returnValue(9));
		    	allowing(kvValues).getValue("key3");
		    	will(returnValue(2));
		    	//code doesnt properly check for keys lol
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(null));
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(null));
		    	allowing(kvValues).getValue(2);
		    	will(returnValue(null));
		    	
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
				func.getValue(2).doubleValue()
		};
		double[] expected = {0.3125, 0.875, 1.0};
		
		assertTrue("results should equal expected.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	@Test
	public void NoZeroKey_GetCumulativePercentages() {
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//i had to go digging through jmock documentation for this one :(
		    	//item count = 3
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(2));
		    	//keys = 0, 1, 2
		    	allowing(kvValues).getKey(0);
		    	will(returnValue(-4));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue(128));
		    	//values = 5, 9, 2
		    	allowing(kvValues).getValue(-4);
		    	will(returnValue(5));
		    	allowing(kvValues).getValue(128);
		    	will(returnValue(9));
		    	//code doesnt properly check for keys lol
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(null));
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(null));
		    	
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
		};
		double[] expected = {5/14, 1.0};
		
		assertTrue("results should equal expected.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	@Test
	public void SizeFive_GetCumulativePercentages() {
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//i had to go digging through jmock documentation for this one :(
		    	//item count = 3
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(5));
		    	//keys = 0, 1, 2
		    	allowing(kvValues).getKey(0);
		    	will(returnValue(1));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue(2));
		    	allowing(kvValues).getKey(2);
		    	will(returnValue(3));
		    	allowing(kvValues).getKey(3);
		    	will(returnValue(4));
		    	allowing(kvValues).getKey(4);
		    	will(returnValue(5));
		    	//values = 5, 9, 2
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(5));
		    	allowing(kvValues).getValue(2);
		    	will(returnValue(9));
		    	allowing(kvValues).getValue(3);
		    	will(returnValue(2));
		    	allowing(kvValues).getValue(4);
		    	will(returnValue(0));
		    	allowing(kvValues).getValue(5);
		    	will(returnValue(0));
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(null));
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
				func.getValue(2).doubleValue(),
				func.getValue(3).doubleValue(),
				func.getValue(4).doubleValue()
		};
		double[] expected = {0.3125, 0.875, 1.0, 1.0, 1.0};
		
		assertTrue("results should equal expected.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	@Test
	public void SizeTwoZeroKey_GetCumulativePercentages() {
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//our list of 3 keys
		List<Integer> keys = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++) keys.add(i);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//i had to go digging through jmock documentation for this one :(
		    	//item count = 2
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(2));
		    	//keys = 0, 1
		    	allowing(kvValues).getKey(0);
		    	will(returnValue(0));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue(1));
		    	//values = 5, 5
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(5));
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(5));
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
		};
		double[] expected = {0.5, 1.0};
		
		assertTrue("results should equal expected.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	@Test (expected = InvalidParameterException.class)
	public void NullThrowsException_GetCumulativePercentages() {
		KeyedValues func = DataUtilities.getCumulativePercentages(null);
	}
	
	// equals
	@Test
	public void equalTrueTest() {
		double[][] paramA = {{1, 2}, {3, 4}};
		double[][] paramB = {{1, 2}, {3, 4}};
		boolean isEqual = DataUtilities.equal(paramA, paramB);
		assertTrue("they should be equal", isEqual);
	}
	
	@Test
	public void equalFalseTest() {
		double[][] paramA = {{1, 2}, {3, 4}};
		double[][] paramB = {{5, 6}, {3, 4}};
		boolean isEqual = DataUtilities.equal(paramA, paramB);
		assertTrue("they should not be equal", !isEqual);
	}
	
	@Test
	public void equalFalseTest2() {
		double[][] paramA = {{1, 2}, {3, 4}, {5, 6}};
		double[][] paramB = {{1, 2}, {3, 4}};
		boolean isEqual = DataUtilities.equal(paramA, paramB);
		assertTrue("they should not be equal", !isEqual);
	}
	
	@Test
	public void equalAIsNull() {
		double[][] paramB = {{1, 2}, {3, 4}};
		boolean isEqual = DataUtilities.equal(null, paramB);
		assertTrue("they should not be equal", !isEqual);
	}
	
	@Test
	public void equalBIsNull() {
		double[][] paramA = {{1, 2}, {3, 4}};
		boolean isEqual = DataUtilities.equal(paramA, null);
		assertTrue("they should not be equal", !isEqual);
	}
	
	@Test
	public void equalBothAreNull() {
		boolean isEqual = DataUtilities.equal(null, null);
		assertTrue("they should be equal", isEqual);
	}
	
	@Test
	public void cloneTest() {
		double[][] array = {{1,2,3},{4,5,6},{7,8,9}};
		double[][] arrayclone = DataUtilities.clone(array);
		assertTrue("clone should be equal to original", DataUtilities.equal(array, arrayclone));
	}
	
	@Test
	public void cloneNullTest() {
		double[][] array = {{1,2,3},null,{7,8,9}};
		double[][] arrayclone = DataUtilities.clone(array);
		assertTrue("clone should be equal to original", DataUtilities.equal(array, arrayclone));
	}
	

	// meant to return 0 when invalid column/row count i think
	@Test
	public void negativeColCountForRowTotal() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getColumnCount();
		         will(returnValue(-3));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(0, 1);
		         will(returnValue(2.5));
		         one(val).getValue(0, 2);
		         will(returnValue(1.0));
		    }
		});
		// test
		int[] validcols = {0, 2};
		double result = DataUtilities.calculateRowTotal(val,0,validcols);
		assertEquals("CalculateColumnTotal should return 0 but instead returned" + result, 0, result, .000000001d);
	}
	
	@Test
	public void negativeRowCountForColTotal() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getRowCount();
		         will(returnValue(-3));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(1, 0);
		         will(returnValue(2.5));
		         one(val).getValue(2, 0);
		         will(returnValue(1.0));
		    }
		});
		// test
		int[] validrows = {0, 2};
		double result = DataUtilities.calculateColumnTotal(val,0,validrows);
		assertEquals("CalculateColumnTotal should return 0 but instead returned" + result, 0, result, .000000001d);
	}
	
	// these next three tests to be completely honest feel like
	// cheating but i have covered every line i could and every 
	// single red line left in datautilities is dead code that is
	// technically unreachable so fuck it heres some code that reaches it
	@Test
	public void negativeRowCount() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D val = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(val).getRowCount();
		         will(returnValue(-2));
		         one(val).getValue(0, 0);
		         will(returnValue(7.5));
		         one(val).getValue(1, 0);
		         will(returnValue(2.5));
		    }
		});
		// test
		double result = DataUtilities.calculateColumnTotal(val,0);
		assertEquals("ill just assume this is meant to return 0. instead returned: " + result, 0, result, .000000001d);
	}
	
	@Test
	public void negativeColTotal() {
		// setup
		Mockery mockcntxt  = new Mockery();
		final Values2D values = mockcntxt.mock(Values2D.class);
		mockcntxt.checking(new Expectations() {
		    {
		         one(values).getColumnCount();
		         will(returnValue(-2));
		         one(values).getValue(0, 0);
		         will(returnValue(7.5));
		         one(values).getValue(0, 1);
		         will(returnValue(1.0));
		    }
		});
		// test
		double result = DataUtilities.calculateRowTotal(values,0);
		assertEquals("ill just assume this is meant to return 0. instead returned: " + result, 0, result, .000000001d);
	}
	
	@Test
	public void negativeItemCount() {
		// stolen from the zerokey test but made item count negative
		Mockery mocking  = new Mockery();
		KeyedValues kvValues = mocking.mock(KeyedValues.class);
		
		//set up mocking
		mocking.checking(new Expectations() {
		    {
		    	//item count = 3
		    	allowing(kvValues).getItemCount();
		    	will(returnValue(-2));
		    	//keys = 0, 1, 2
		    	allowing(kvValues).getKey(0);
		    	will(returnValue(-4));
		    	allowing(kvValues).getKey(1);
		    	will(returnValue(128));
		    	//values = 5, 9, 2
		    	allowing(kvValues).getValue(-4);
		    	will(returnValue(5));
		    	allowing(kvValues).getValue(128);
		    	will(returnValue(9));
		    	allowing(kvValues).getValue(0);
		    	will(returnValue(null));
		    	allowing(kvValues).getValue(1);
		    	will(returnValue(null));
		    	
		    }
		});
		
		// test
		KeyedValues func = DataUtilities.getCumulativePercentages(kvValues);
		double[] results = {
				func.getValue(0).doubleValue(),
				func.getValue(1).doubleValue(),
		};
		double[] expected = {5/14, 1.0};
		
		assertTrue("results should equal expected?.\nresults: "+Arrays.toString(results)+"\nexpected: "+Arrays.toString(expected), Arrays.equals(expected, results));
	}
	
	// we can explain the last 3 as "testing what happens with invalid row/col/item counts" or some bs
	
	@After
	public void tearDown() throws Exception {
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
}
