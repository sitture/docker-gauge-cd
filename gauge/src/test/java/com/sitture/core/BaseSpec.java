package com.sitture.core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public abstract class BaseSpec {
	
	protected final WebDriver driver;
	protected final Utilities utilities;

    public BaseSpec() {
        this.driver = SpecHooks.getDriver();
        this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        this.utilities = new Utilities();
    }

}
