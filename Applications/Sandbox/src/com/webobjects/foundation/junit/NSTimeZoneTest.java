package com.webobjects.foundation.junit;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSTimeZone;

public class NSTimeZoneTest {

	@Before
	public void setUp()
		throws Exception {
	}

	@After
	public void tearDown()
		throws Exception {
	}

	@Test
	public void testAbbreviationDictionary() {
		try {
			NSDictionary<String, String> abbreviations = NSTimeZone.abbreviationDictionary();
			for (String key : abbreviations.keySet()) {
				System.out.println(key + " = " + abbreviations.get(key));
			}
			Assert.assertTrue(true);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testTimeZoneWithName() {
		try {
			NSTimeZone timeZone = NSTimeZone.timeZoneWithName("PST", true);
			System.err.println(timeZone);
			if (timeZone != null) {
				Assert.assertTrue(true);
			} else {
				fail("Didn't return timezone");
			}
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

}
