package com.restapiexample.dummy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class Commonfunction {
	
	
	public static  ExtentReports setupResult()
	{	
		DateFormat format=new SimpleDateFormat("yyyyMMddhhmmss");
		Date date=new Date();
		String datestr=format.format(date);
		String extentReport=System.getProperty("user.dir")+"\\src\\test\\java\\TestReport\\API_"+datestr+".html";
		System.out.println(extentReport);
		ExtentReports report=new ExtentReports(extentReport);
		return report;
	}

}
