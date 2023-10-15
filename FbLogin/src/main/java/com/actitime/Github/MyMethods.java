package com.actitime.Github;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.ReporterConfigurable;
import com.beust.jcommander.Parameter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MyMethods {
static WebDriver driver;
ExtentReports extent=new ExtentReports();
ExtentSparkReporter spark=new ExtentSparkReporter("MyExtentRepo.html");
@Parameters({"browser"})
@BeforeClass  
public void open(String browser)
{
	Reporter.log("Open",true);
	extent.attachReporter(spark);
	if(browser.equalsIgnoreCase("chrome"))
	{
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
	}
	else if(browser.equalsIgnoreCase("firefox"))
	{
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
	}
}
@Parameters({"url"})
	@BeforeMethod
	public void login(String url) throws IOException
	{
		Reporter.log("Login",true);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.name("pwd")).sendKeys("manager");
		driver.findElement(By.xpath("//div[.='Login ']")).click();
		String title = driver.getTitle();
		String base64Code=captureScreenshot();
		String path=captureScreenshot(title);
		extent.createTest("This is test 1","LogLevel Screenshot")
		.info("This is info msg")
		.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build());
		
		extent.createTest("This is test 2","This screenshot for test level")
		.info("This a info msg")
		.addScreenCaptureFromPath(path);
	}
@AfterMethod
public void logout()
{
	Reporter.log("Logout",true);
	driver.findElement(By.id("logoutLink")).click();
}
@AfterClass
public void close() throws IOException
{
	Reporter.log("Close",true);
	extent.flush();
	Desktop.getDesktop().browse(new File("MyExtentRepo.html").toURI());
}
public static String captureScreenshot()
{
	TakesScreenshot t=(TakesScreenshot)driver;
	String base64Code = t.getScreenshotAs(OutputType.BASE64);
	return base64Code;
}
public static String captureScreenshot(String filename) throws IOException
{
	TakesScreenshot t=(TakesScreenshot)driver;
	File src = t.getScreenshotAs(OutputType.FILE);
	File f=new File("./screenshot/"+filename+".png");
	FileUtils.copyFile(src, f);
	return f.getAbsolutePath();
}
	

}


