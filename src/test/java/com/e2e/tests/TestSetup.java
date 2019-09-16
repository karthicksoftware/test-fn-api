package com.e2e.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.e2e.helpers.TestHelper;

public class TestSetup {
	final static Logger log = Logger.getLogger(TestSetup.class);
	
	TestHelper helper = null;
	
	@BeforeMethod
	public void initializeTestSetup() throws Exception {
		helper = TestHelper.createInstance();
		log.info("Test Started...");
	}
	
	@AfterMethod
	public void endOfTest() throws Exception {
		log.info("Test Ended...");
	}

}
