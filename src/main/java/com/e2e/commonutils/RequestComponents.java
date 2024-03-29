package com.e2e.commonutils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import com.e2e.constants.Constants;
import com.e2e.constants.HTTPMethod;

/**
 * This abstarct class provides some default facilities to construct request to the services which extends this.
 * Also this can be overrided by the consumer service in case of any customized implementation requires.
 * @author Karthick Arunachalam
 *
 */
public abstract class RequestComponents {
	final static Logger log = Logger.getLogger(RequestComponents.class);

	protected String readXMLConfig(String xmlPath) throws Exception {
		DocumentBuilderFactory documentumentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentumentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentumentBuilder = documentumentBuilderFactory.newDocumentBuilder();
		Document document = documentumentBuilder.parse("./src/main/resources/configs/routes.xml");
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr = xpath.compile(xmlPath);
		return (String) expr.evaluate(document, XPathConstants.STRING);
	}

	protected String getServiceURL(String xmlPath) throws Exception {
		String protocol = readXMLConfig(Constants.PROTOCOL);
		String host = readXMLConfig(Constants.HOST);
		String route = readXMLConfig(xmlPath);
		return protocol + "://" + host + route;
	}

	protected HTTPMethod getHttpMethod(String xmlPath) throws Exception {
		String methodName = readXMLConfig(xmlPath + "/@method");
		if (methodName.equalsIgnoreCase("get"))
			return HTTPMethod.GET;
		else if (methodName.equalsIgnoreCase("put"))
			return HTTPMethod.PUT;
		else if (methodName.equalsIgnoreCase("post"))
			return HTTPMethod.POST;
		else if (methodName.equalsIgnoreCase("delete"))
			return HTTPMethod.DELETE;
		else if (methodName.equalsIgnoreCase("patch"))
			return HTTPMethod.PATCH;
		else
			throw new OperationNotSupportedException("Method not supported : " + methodName);
	}

	protected String StringSubstitutor(String templateString, Map<String, String> textToBeReplaced) throws Exception {
		StrSubstitutor sub = new StrSubstitutor(textToBeReplaced);
		String text = sub.replace(templateString);
		return text;
	}

	protected String getRequestBody(String payloadPath) throws Exception {
		return readFileAsString(payloadPath);
	}

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + fileName)));
		return data;
	}
}
