package com.e2e.helpers;

import java.util.Random;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.e2e.services.JSONServer;
import com.jayway.restassured.response.Response;

public class TestHelper {

	final static Logger log = Logger.getLogger(TestHelper.class);
	
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
	
	public void checkUnavailableUserReturnsEmptyPosts(String userName) throws Exception {
		Response response = getPostsByUser(userName);
		assertionHelper.isFalse(((JSONArray) new JSONParser().parse(response.asString())).size() != 0, "No User found.");
	}

	public void validateEmailFormatInCommentsSection(Response response) throws Exception {
		JSONArray listOfPosts = getListOfPosts(response);
		for (Object eachPost : listOfPosts) {
			int postId = getPostId(eachPost);
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
		JSONArray allComments = getListOfComments(response);
		if (allComments.size() == 0)
			return;
		for (Object eachComments : allComments) {
			String emailId = getEmailId(eachComments, "email");
			boolean isValidEmail = Pattern.compile("^[\\w\\d-]{6,30}@[\\w\\d]+\\.[\\w]{3}$").matcher(emailId).matches();
			assertionHelper.isTrue(isValidEmail,
					"The Post Id " + String.valueOf(((JSONObject) eachComments).get("postId"))
							+ " has invalid email in comment section. " + " Email Found -> " + emailId);
		}
	}

	private int getPostId(Object eachPost) throws Exception {
		JSONObject post = (JSONObject) eachPost;
		return Integer.parseInt(post.get("id").toString());
	}

	private String getCommentsKey(Object eachComments, String key) throws Exception {
		JSONObject post = (JSONObject) eachComments;
		return String.valueOf(post.get(key).toString());
	}
	
	private String getEmailId(Object eachComments, String key) throws Exception {
		return getCommentsKey(eachComments, key);
	}
	
	private int getCommentsPostId(Object eachComments, String key) throws Exception {
		return Integer.parseInt(getCommentsKey(eachComments, key));
	}

	private JSONArray getListOfItems(Response response) throws Exception {
		return (JSONArray) new JSONParser().parse(response.asString());
	}

	private JSONArray getListOfPosts(Response response) throws Exception {
		return getListOfItems(response);
	}

	private JSONArray getListOfComments(Response response) throws Exception {
		return getListOfItems(response);
	}

	public void validateRespectiveCommentsByPostId(Response response) throws Exception {
		JSONArray listOfPosts = getListOfPosts(response);
		for (Object eachPost : listOfPosts) {
			int postId = getPostId(eachPost);
			Response allComments = getCommentsByPost(postId);
			checkValidCommentsOfPost(postId, allComments);
		}
	}

	private void checkValidCommentsOfPost(int postId, Response response) throws Exception {
		JSONArray allComments = getListOfComments(response);
		if (allComments.size() == 0)
			return;
		for (Object eachComments : allComments) {
			int commentPostId = getCommentsPostId(eachComments, "postId");
			assertionHelper.equals(postId, commentPostId);
		}
	}
	
	public Response getAllPosts() throws Exception {
		Response response = jsonService.getAllPosts();
		assertionHelper.assertStatusCode(response.statusCode(), 200);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Response addCommentToAnyPost(Response response) throws Exception {
		JSONArray listOfPosts = getListOfPosts(response);
		int randomPost = new Random().nextInt(listOfPosts.size());
		String body = "Good Post.";
		String email = "hacker@gmail.com";
		JSONObject expectedCommentResponse = new JSONObject();
		JSONArray expectedResults = new JSONArray();
		expectedCommentResponse.put("body", body);
		expectedCommentResponse.put("postId", randomPost);
		expectedCommentResponse.put("email", email);
		expectedResults.add(expectedCommentResponse);
		Response commentResponse = jsonService.addNewComment(body, randomPost, email);
		assertionHelper.assertStatusCode(commentResponse.statusCode(), 201);
		assertionHelper.assertJSON(((JSONArray) new JSONParser().parse(commentResponse.asString())).toJSONString(), expectedResults.toJSONString());
		return response;
	}
}
