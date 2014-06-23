package com.webobjects.foundation.junit;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSPropertyListSerialization;

public class NSPropertyListSerializationTest {

	File oldstylePlist, xmlPlist, binaryPlist;

	@Before
	public void setUp()
		throws Exception {
		oldstylePlist = new File("Plist_OLDSTYLE.plist");
		xmlPlist = new File("Plist_XML1.plist");
		binaryPlist = new File("Plist_BINARY1.plist");
	}

	@After
	public void tearDown()
		throws Exception {
	}

	@Test
	public void testDictionaryWithPathURLURL() {
		try {
			NSDictionary<String, Object> plist = NSPropertyListSerialization.dictionaryWithPathURL(oldstylePlist.toURI().toURL());
			for (String key : plist.keySet()) {
				Object value = plist.get(key);
				System.out.println(key + ", " + value + " [" + value.getClass() + "]");
			}
			Assert.assertTrue(true);
		} catch (MalformedURLException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDictionaryWithPathURLURLBoolean() {
		try {
			NSDictionary<String, Object> plist = NSPropertyListSerialization.dictionaryWithPathURL(xmlPlist.toURI().toURL(), true);
			for (String key : plist.keySet()) {
				Object value = plist.get(key);
				System.out.println(key + ", " + value + " [" + value.getClass() + "]");
			}
			Assert.assertTrue(true);
		} catch (MalformedURLException e) {
			Assert.fail(e.getMessage());
		}
	}

}
