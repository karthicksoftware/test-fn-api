package com.e2e.commonutils;

import java.util.HashMap;

import com.e2e.constants.HTTPMethod;
import com.jayway.restassured.response.Response;

/**
 * The high level available abstart methods to be accessed by the consumers. 
 * This can be overrided in case of any other customized requests that are be to performed in future.
 * @author Karthick Arunachalam
 *
 */
public interface HTTPRequestFacade {

	public Response sendRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody) throws Exception;
	public HashMap<String, Object> getCookies();
	
}
