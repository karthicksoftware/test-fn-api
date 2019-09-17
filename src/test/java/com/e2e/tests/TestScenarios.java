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
	public void checkIfRespectiveCommentsRetrievesAsPerPostId() throws Exception {
		Response posts = helper.getPostsByUser("Samantha");
		helper.validateRespectiveCommentsByPostId(posts);
	}
	
	@Test
	public void checkIfUserAbleToCommentToPost() throws Exception {
		Response allPosts = helper.getAllPosts();
		helper.addCommentToAnyPost(allPosts);
	}
	
	@Test
	public void checkIfUserIsAbleToDeleteThePost() throws Exception {
		helper.deletePostById(5);
	}
	
	@Test
	public void checkIfUserIsAbleToModifyPostTitle() throws Exception {
		Response post = helper.getPostsById(5);
		helper.validateTitleModification(post, 5, "The Downtown hero");
	}
	
	@Test
	public void checkIfUserCanUpdateCommentThatHasInvalidEmailId() throws Exception {
		Response post = helper.getCommentsById(15);
		helper.validateEmailUpdate(post, 15, "validemail@hotmail.com");
	}
}
 