package com.sitture.core;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SpecHooks {

	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		SpecHooks.driver = driver;
	}
	
	@BeforeSuite
	public void setupSuite() {
		driver = new HtmlUnitDriver();
		setDriver(driver);
	}

	@AfterSuite
	public void tearDownSuite() {
		if (null != getDriver()) {
			getDriver().quit();
		}
	}
	
}
