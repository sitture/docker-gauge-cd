package com.sitture.core;

public class Utilities extends BrowserUtils {
	
	/**
	 * 
	 * @return Returns base url of site without the trailing forward slash.<br>
	 *         E.g. http://web:8080
	 */
	public String getBaseUrl() {
		String baseUrl = System.getenv("BASE_URL");
		if (null != baseUrl) {
			return baseUrl;
		}
		baseUrl = "http://web:8080";
		return baseUrl;
	}

}
