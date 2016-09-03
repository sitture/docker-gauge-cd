package com.sitture.core;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;

public class SpecHooks {

	private static WebDriver driver;
	private Utilities utilities = new Utilities();
	
	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		SpecHooks.driver = driver;
	}
	
	@BeforeSuite
	public void setupSuite() {
		utilities.initDesktopBrowser();
	}

	@AfterSuite
	public void tearDownSuite() {
		utilities.closeBrowser(getDriver());
	}
	
}
