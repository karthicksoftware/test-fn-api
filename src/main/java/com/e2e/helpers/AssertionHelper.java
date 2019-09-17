package com.e2e.helpers;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import com.jayway.restassured.response.Response;

/**
 * This contains the assertion helpers. Every assert statements should be accessed by this object.
 * @author Karthick Arunachalam
 *
 */
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
	
	public void assertJSON(JSONObject actual, JSONObject expected) throws Exception {
		Assert.assertEquals(actual.toJSONString(), expected.toJSONString().trim());
	}
	
	public void assertJSON(JSONArray actual, JSONArray expected) throws Exception {
		Assert.assertEquals(actual.toJSONString(), expected.toJSONString().trim());
	}
	
	public void assertEmptyJson(Response response) throws Exception {
		Assert.assertEquals(response.asString(), "{}");
	}
}
