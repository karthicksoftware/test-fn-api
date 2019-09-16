package com.e2e.helpers;

import java.util.Iterator;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.e2e.services.JSONServer;
import com.jayway.restassured.response.Response;

public class TestHelper {
	
	JSONServer jsonService;
	AssertionHelper assertionHelper;
	
	private TestHelper() {
		jsonService = new JSONServer();
		assertionHelper = new AssertionHelper();
	}
	
	public static TestHelper createInstance() {
		return new TestHelper();
	}
	
	public Response getPostsByUser(String userName) throws Exception {
		Response response = jsonService.getPostsByUser(userName.toLowerCase());
		assertionHelper.assertStatusCode(response.statusCode(), 200);
		return response;
	}
	
	public void validateEmailFormatInCommentsSection(Response posts) throws Exception {
		JSONArray listOfPosts = (JSONArray) new JSONParser().parse(posts.asString());
		for(Object eachPost : listOfPosts) {
			JSONObject post = (JSONObject) eachPost;
			int postId = Integer.parseInt(post.get("id").toString());
			Response allComments = getCommentsByPost(postId);
			validateEmailFormat(allComments);
		}
		
	}
	
	private Response getCommentsByPost(int postId) throws Exception {
		Response response = jsonService.getCommentsByPost(postId);
		assertionHelper.assertStatusCode(200, response.statusCode());
		return response;
	}
	
	private void validateEmailFormat(Response response) throws Exception {
		JSONArray allComments = (JSONArray) new JSONParser().parse(response.asString());
		if(allComments.size() == 0) return; 
		for(Object eachComments : allComments) {
			JSONObject comment = (JSONObject) eachComments;
			String emailId = (String) comment.get("email");
			boolean isValidEmail = Pattern.compile("^[\\w\\d-]{6,30}@[\\w\\d]+\\.[\\w]{3}$").matcher(emailId).matches();
			assertionHelper.isTrue(isValidEmail, "The Post Id "+String.valueOf(comment.get("postId")) + " has invalid email in comment section. " + " Email Found -> " + emailId);
		}
	}
}
