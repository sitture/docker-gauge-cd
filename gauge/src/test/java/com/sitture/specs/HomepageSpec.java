package com.sitture.specs;

import com.sitture.core.BaseSpec;
import com.thoughtworks.gauge.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;

public class HomepageSpec extends BaseSpec {

	private final static Logger log = Logger.getLogger(HomepageSpec.class);

	@Step("Goto Homepage")
	public void gotoHomepage() {
		log.info("Navigating to homepage");
		driver.get(utilities.getBaseUrl());
	}

	@Step("Verify page title is <pageTitle>")
	public void verifyPageTitle(String pageTitle) {
		Assert.assertEquals("Page titles do not match", pageTitle, driver.getTitle());
	}
}
