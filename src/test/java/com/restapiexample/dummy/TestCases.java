package com.restapiexample.dummy;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import org.json.simple.JSONObject;


public class TestCases {
	
	public  ExtentReports report;
	public  ExtentTest logger; 
	public  String extentReport;
	
	@BeforeClass(alwaysRun = true)
	public void createReport()
	{
		report = Commonfunction.setupResult();
	}
	
	
	@Test(priority=1)
	public void TestCases1_GetAllEmpDetails()
	{
		
		Response resp = RestAssured.get("http://dummy.restapiexample.com/api/v1/employees");
		System.out.println(resp.asString());
		String resptext = resp.getBody().jsonPath().get("status");
		System.out.println("Response Text = "+resptext);
		Assert.assertEquals(resptext, "success");
	}
	
	
	@Test(priority=2)
	public void TestCases2_GetAllEmpDetails()
	{
		ValidatableResponse resp = RestAssured.get("http://dummy.restapiexample.com/api/v1/employees").then().assertThat().body("status",equalTo("success"));
		
	}
	
	
	@Test(priority=3)
	public void TestCases3_GetAllEmpDetails()
	{
		when().get("http://dummy.restapiexample.com/api/v1/employees").then().assertThat().body("status", equalTo("success"));
		
	}
	
	@Test(priority=4)
	public void TestCases4_GetAllEmpDetails()
	{
	   when().
		 get("http://dummy.restapiexample.com/api/v1/employees").
	   then().
	     assertThat().body("status", equalTo("success"));
		
	}
	
	//@Test(priority=5)
	public void E2EFlow()
	{
		/*
		RestAssured.baseURI = "http://dummy.restapiexample.com";
        given().urlEncodingEnabled(true)
            .param("name", "user@site.com")
            .param("salary", "123")
            .param("age", "23")
            .header("Accept", ContentType.JSON.getAcceptHeader())
            .post("/create")
            .then().statusCode(200);
        */
        RestAssured.baseURI ="http://dummy.restapiexample.com";
		RequestSpecification request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "user@site.com"); // Cast
		requestParams.put("salary", "123");
		requestParams.put("age", "23");
		request.body(requestParams.toJSONString());
		System.out.println(requestParams.toJSONString());
		Response response = request.post("/create");
        System.out.println(response.getBody().asString());
	}
	
	@Test(dataProvider = "dp")
	public void RegistrationSuccessful(String fname, String lname, String usernam, String pwd, String email, int code , String Node, String NodeVal)
	{		
		SoftAssert sa = new SoftAssert();
		logger = report.startTest("RegistrationSuccessful");
		RestAssured.baseURI ="http://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();
		logger.log(LogStatus.INFO, "URI = http://restapi.demoqa.com/customer/register");
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", fname); // Cast
		requestParams.put("LastName", lname);
		requestParams.put("UserName", usernam);
		requestParams.put("Password", pwd);
		requestParams.put("Email",  email);
		request.body(requestParams.toJSONString());
		System.out.println(requestParams.toJSONString());
		logger.log(LogStatus.INFO, "REquest = "+requestParams.toJSONString());
		
		//File file = new File("E:\\Selenium\\Batches\\Pune\\KarateAPITesting\\RestAssuredTestNGFramework\\src\\test\\resources\\request.json");
		//request.body(file);
		Response response = request.post("/register");
        System.out.println(response.getBody().asString());
        logger.log(LogStatus.INFO, "Response = "+response.getBody().asString());
		int statusCode = response.getStatusCode();
		if(statusCode==code)
		{
			 logger.log(LogStatus.PASS, "Status code matched successfully and status code was "+statusCode);
		}
		else
		{
			logger.log(LogStatus.FAIL, "Status code does not match and status code was "+statusCode);
		}
		sa.assertEquals(statusCode, code);
		
		
		String successCode = response.jsonPath().get(Node);
		sa.assertEquals(successCode, NodeVal);
		
		
		
		if(successCode.equals(NodeVal))
		{
			 logger.log(LogStatus.PASS, "SuccessCode matched successfully and SuccessCodee was "+successCode);
		}
		else
		{
			logger.log(LogStatus.FAIL, "SuccessCode did not match  and SuccessCodee was "+successCode);
		}
		report.endTest(logger);
		report.flush();
		sa.assertAll();
	}
	
	
	@DataProvider
	  public Object[][] dp() {
	    return new Object[][] {
	      new Object[] {"aaaaaaa", "aaaaaa","aaaaauserid","aaaaapasword","aaaaaauserid@gmail.com", 201,"SuccessCode","OPERATION_SUCCESS"  },
	      new Object[] {"bbbbb", "bbbbb","bbbbuserid","bbbbpasword","bbbbuserid@gmail.com",200, "FaultId", "User already exists" },
	   
	    };
	  }
	
	
	
	
	

}
