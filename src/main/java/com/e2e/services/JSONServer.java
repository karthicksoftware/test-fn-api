package com.e2e.services;

import java.util.HashMap;
import java.util.Map;

import com.e2e.commonutils.HTTPRequestFacade;
import com.e2e.commonutils.HTTPRequestImpl;
import com.e2e.commonutils.RequestComponents;
import com.e2e.constants.HTTPMethod;
import com.e2e.constants.Headers;
import com.e2e.constants.PayloadList;
import com.e2e.constants.RouteList;
import com.jayway.restassured.response.Response;

public class JSONServer extends RequestComponents {
	
	HTTPRequestFacade request;
	
	public JSONServer() {
		request = new HTTPRequestImpl();
	}

	public Response getPostsByUser(String userName) throws Exception {
		Map<String, String> substitutes = new HashMap<String, String>();
		substitutes.put("userName", userName);
		String serviceUrl = StringSubstitutor(getServiceURL(RouteList.GETUSERS), substitutes);
		HTTPMethod method = getHttpMethod(RouteList.GETUSERS);
		return request.sendRequest(method, serviceUrl, Headers.CONTENT_TYPE_JSON, null, null);
	}

	public Response addNewComment(String body, int postId, String email) throws Exception {
		Map<String, String> substitutes = new HashMap<String, String>();
		substitutes.put("body", body);
		substitutes.put("postId", String.valueOf(postId));
		substitutes.put("email", email);
		HashMap<String, Object> headers = new HashMap<String, Object>();
		headers.put("Accept", "application/json");
		String serviceUrl = StringSubstitutor(getServiceURL(RouteList.ADD_COMMENT), substitutes);
		String payload = StringSubstitutor(getRequestBody(PayloadList.ADD_COMMENT), substitutes);
		HTTPMethod method = getHttpMethod(RouteList.ADD_COMMENT);
		return request.sendRequest(method, serviceUrl, Headers.CONTENT_TYPE_JSON, headers, payload);
	}
	
	public Response getCommentsByPost(int postId) throws Exception {
		Map<String, String> substitutes = new HashMap<String, String>();
		substitutes.put("postId", String.valueOf(postId));
		String serviceUrl = StringSubstitutor(getServiceURL(RouteList.GET_COMMENTS_BY_POSTID), substitutes);
		HTTPMethod method = getHttpMethod(RouteList.GET_COMMENTS_BY_POSTID);
		return request.sendRequest(method, serviceUrl, Headers.CONTENT_TYPE_JSON, null, null);
	}

	public Response getAllPosts() throws Exception {
		String serviceUrl = getServiceURL(RouteList.GET_ALL_POSTS);
		HTTPMethod method = getHttpMethod(RouteList.GET_ALL_POSTS);
		return request.sendRequest(method, serviceUrl, Headers.CONTENT_TYPE_JSON, null, null);
	}
}
