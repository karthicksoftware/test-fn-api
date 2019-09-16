package com.e2e.helpers;

import org.testng.Assert;

public class AssertionHelper {

	public void assertStatusCode(int actual, int expected) throws Exception {
		Assert.assertEquals(actual, expected);
	}
	
	public void isTrue(boolean result, String message) throws Exception {
		Assert.assertTrue(result, message);
	}
}
