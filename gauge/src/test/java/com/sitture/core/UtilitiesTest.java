package com.sitture.core;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilitiesTest {
	
	private static Utilities utilities;
	
	@BeforeClass
	public static void setup() {
		utilities = new Utilities();
	}
	
	@Test
	public void canGetBaseUrlTest() {
		String baseUrl = System.getenv("base.url");
		String expectedBaseUrl = "http://web:8080";
		if (null != baseUrl) {
			expectedBaseUrl = baseUrl;
		}
		Assert.assertEquals(expectedBaseUrl, utilities.getBaseUrl());
	}

}
