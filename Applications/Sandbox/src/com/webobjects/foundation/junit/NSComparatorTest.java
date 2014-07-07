package com.webobjects.foundation.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSComparator.ComparisonException;
import com.webobjects.foundation.NSTimestamp;

public class NSComparatorTest {

	@Before
	public void setUp()
		throws Exception {
	}

	@After
	public void tearDown()
		throws Exception {
	}

	@Test
	public void testCompareNumbers()
		throws ComparisonException {
		Assert.assertEquals(-1, NSComparator.AscendingNumberComparator.compare(1, 2));
		Assert.assertEquals(0, NSComparator.AscendingNumberComparator.compare(1, 1));
		Assert.assertEquals(1, NSComparator.AscendingNumberComparator.compare(2, 1));
	}

	@Test
	public void testCompareStrings()
		throws ComparisonException {
		Assert.assertEquals(-1, NSComparator.AscendingStringComparator.compare("Alpha", "Bravo"));
		Assert.assertEquals(0, NSComparator.AscendingStringComparator.compare("Alpha", "Alpha"));
		Assert.assertEquals(1, NSComparator.AscendingStringComparator.compare("Bravo", "Alpha"));
	}

	@Test
	public void testCompareCaseInsensitiveStrings()
		throws ComparisonException {
		Assert.assertEquals(-1, NSComparator.AscendingCaseInsensitiveStringComparator.compare("alpha", "Bravo"));
		Assert.assertEquals(0, NSComparator.AscendingCaseInsensitiveStringComparator.compare("alpha", "Alpha"));
		Assert.assertEquals(1, NSComparator.AscendingCaseInsensitiveStringComparator.compare("Bravo", "alpha"));
	}

	@Test
	public void testCompareTimestamps()
		throws ComparisonException {
		NSTimestamp t1 = new NSTimestamp(System.currentTimeMillis() - 10000);
		NSTimestamp t2 = new NSTimestamp(System.currentTimeMillis() + 10000);
		Assert.assertEquals(-1, NSComparator.AscendingTimestampComparator.compare(t1, t2));
		Assert.assertEquals(0, NSComparator.AscendingTimestampComparator.compare(t1, t1));
		Assert.assertEquals(1, NSComparator.AscendingTimestampComparator.compare(t2, t1));
	}

}
