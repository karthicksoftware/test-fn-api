package com.e2e.commonutils;

import java.util.HashMap;

import com.e2e.constants.HTTPMethod;
import com.jayway.restassured.response.Response;

public interface HTTPRequestFacade {

	public Response sendRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody) throws Exception;
	public HashMap<String, Object> getCookies();
	
}
