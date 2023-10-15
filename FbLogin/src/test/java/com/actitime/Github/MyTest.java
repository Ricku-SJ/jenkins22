package com.actitime.Github;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

public class MyTest extends MyMethods{

	@Test
	public void facebook()
	{
		Reporter.log("Login",true);
		extent.createTest("This test case is for Login")
		.assignAuthor("Ricku")
		.assignCategory("Regression Testing")
		.assignDevice("Windows11")
		.pass("This test case execute smoothly")
		.log(Status.PASS, "This test case is pass ")
		.info("No ifo msg")
		.warning("No warnig msg");
	}
}
