package com.e2e.tests;

import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class TestScenarios extends TestSetup {

	@Test
	public void exerciseScenario1() throws Exception {
		Response posts = helper.getPostsByUser("Samantha");
		helper.validateEmailFormatInCommentsSection(posts);
	}
	
}
 