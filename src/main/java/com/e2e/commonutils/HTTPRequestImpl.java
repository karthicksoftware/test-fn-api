package com.e2e.commonutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.e2e.constants.HTTPMethod;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class HTTPRequestImpl implements HTTPRequestFacade{
	
	final static Logger log = Logger.getLogger(HTTPRequestImpl.class);

	private HashMap<String, Object> cookies = new HashMap<String, Object>();
	
	@Override
	public HashMap<String, Object> getCookies() {
		return cookies;
	}
	
	private void setCookies(Map<String, String> cookies) {
		this.cookies.putAll(cookies);
	}
	
	@Override
	public Response sendRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody) throws Exception {
		printRequest(method, requestUrl, contentType, header, jsonBody);
		Response response = sendRequest(method, requestUrl, contentType, header, jsonBody, cookies);
		printResponse(response);
		return response;
	}
	
	public Response sendRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		Response response = null;
		switch(method) {
		case GET:
			response = sendGetRequest(method, requestUrl, contentType, header, jsonBody, cookies);
			break;
		case PUT:
			response = sendPutRequest(method, requestUrl, contentType, header, jsonBody, cookies);
			break;
		case POST:
			response = sendPostRequest(method, requestUrl, contentType, header, jsonBody, cookies);
			break;
		case DELETE:
			response = sendDeleteRequest(method, requestUrl, contentType, header, jsonBody, cookies);
			break;
		case PATCH:
			response = sendPatchRequest(method, requestUrl, contentType, header, jsonBody, cookies);
			break;
		default :
			throw new InvalidHTTPMethodException("The given method => "+method.name()+ " is not supported.");
		}
		setCookies(response.cookies());
		return response;
	}
	
	private Response sendGetRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		RequestSpecification specs = constructRequestSpecification(method, requestUrl, contentType, header, jsonBody, cookies);
		if(requestUrl.startsWith("https"))
			return specs.relaxedHTTPSValidation().get(requestUrl);
		else
			return specs.get(requestUrl);
	}
	
	private Response sendPutRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		RequestSpecification specs = constructRequestSpecification(method, requestUrl, contentType, header, jsonBody, cookies);
		if(requestUrl.startsWith("https"))
			return specs.relaxedHTTPSValidation().put(requestUrl);
		else
			return specs.put(requestUrl);
	}
	
	private Response sendPostRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		RequestSpecification specs = constructRequestSpecification(method, requestUrl, contentType, header, jsonBody, cookies);
		if(requestUrl.startsWith("https"))
			return specs.relaxedHTTPSValidation().post(requestUrl);
		else
			return specs.post(requestUrl);
	}
	
	private Response sendDeleteRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		RequestSpecification specs = constructRequestSpecification(method, requestUrl, contentType, header, jsonBody, cookies);
		if(requestUrl.startsWith("https"))
			return specs.relaxedHTTPSValidation().delete(requestUrl);
		else
			return specs.delete(requestUrl);
	}
	
	private Response sendPatchRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		RequestSpecification specs = constructRequestSpecification(method, requestUrl, contentType, header, jsonBody, cookies);
		if(requestUrl.startsWith("https"))
			return specs.relaxedHTTPSValidation().patch(requestUrl);
		else
			return specs.patch(requestUrl);
	}
	
	private RequestSpecification constructRequestSpecification(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody, HashMap<String, Object> cookies) throws Exception {
		EncoderConfig encoderconfig = new EncoderConfig();
		RequestSpecification request = RestAssured.given().config(RestAssured.config()
                .encoderConfig(encoderconfig.appendDefaultContentCharsetToContentTypeIfUndefined(false)));
		if(contentType != null)
			request = request.contentType(contentType);
		if(cookies != null && !cookies.isEmpty())
			request = request.cookies(cookies);
		if(header != null && !header.isEmpty())
			request = request.headers(header);
		if(jsonBody != null)
			request = request.body(jsonBody);
		return request;
	}
	
	private void printRequest(HTTPMethod method, String requestUrl, String contentType, HashMap<String, Object> header, String jsonBody) throws Exception {
		log.info("-------------------------------------- Begins ------------------------------------------------------------------------------------");
		log.info("HTTP Method : "+method.name());
		log.info("Request URL : "+requestUrl);
		log.info("Headers : ");
		log.info("content-type : "+contentType);
		if(header != null)
		header.forEach((k, v) -> {
			log.info(k + " : "+v);
		});
		if(jsonBody != null) 
			log.info("Payload : " + jsonBody);
	}
	
	private void printResponse(Response response) throws Exception {
		log.info("Response : "+response.asString());
	}
	
}

@SuppressWarnings("serial")
class InvalidHTTPMethodException extends Exception {
	InvalidHTTPMethodException(String message) {
		super(message);
	}
}