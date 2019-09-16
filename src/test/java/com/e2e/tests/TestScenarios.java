package com.e2e.tests;

import org.testng.annotations.Test;

import com.jayway.restassured.response.Response;

public class TestScenarios extends TestSetup {

	@Test
	public void exerciseScenario1() throws Exception {
		Response posts = helper.getPostsByUser("Samantha");
		helper.validateEmailFormatInCommentsSection(posts);
	}
	
	@Test
	public void checkUnavailableUserReturnsEmptyPosts() throws Exception {
		helper.checkUnavailableUserReturnsEmptyPosts("unavailabe-User");
	}
	
	@Test
	public void validateIfRespectiveCommentsRetrievesAsPerPostId() throws Exception {
		Response posts = helper.getPostsByUser("Samantha");
		helper.validateRespectiveCommentsByPostId(posts);
	}
	
	@Test
	public void checkIfUserAbleToCommentToPost() throws Exception {
		Response allPosts = helper.getAllPosts();
		helper.addCommentToAnyPost(allPosts);
	}
}
 