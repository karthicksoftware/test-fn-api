package com.e2e.helpers;

import org.apache.log4j.Logger;
import org.testng.Assert;

public class AssertionHelper {

	final static Logger log = Logger.getLogger(AssertionHelper.class);
	
	public void assertStatusCode(int actual, int expected) throws Exception {
		Assert.assertEquals(actual, expected);
	}
	
	public void isTrue(boolean result, String message) throws Exception {
		Assert.assertTrue(result, message);
	}
	
	public void isFalse(boolean result, String message) throws Exception {
		Assert.assertFalse(result, message);
	}
	
	public void equals(int actual, int expected) throws Exception {
		Assert.assertEquals(actual, expected);
	}
	
	public void assertJSON(String actual, String expected) throws Exception {
		Assert.assertEquals(actual.trim(), expected.trim());
	}
}
