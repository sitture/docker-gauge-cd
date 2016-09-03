package com.sitture.core;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserUtils {
	
	private static final String CHROME = "chrome";
	private static final String FIREFOX = "firefox";
	private static final String PHANTOMJS = "phantomjs";
	private static final String DEFAULT_BROWSER = PHANTOMJS;
	
	private String getProcessedDriverLocation(String browser, String webDriverProperty) {
		String defaultBrowserLocation = "/resources/drivers";
		// Get 'browser.location' property from pom.xml
		String browserLocation = System.getProperty(webDriverProperty);
		// check if the property was found
		if (null == browserLocation) {
			// Try to get 'browser.location' property from the env/browser.properties
			browserLocation = System.getenv("browser.location");
			// if browser.location is null, then just set to default
			if (null == browserLocation) {
				browserLocation = defaultBrowserLocation;
			}
		}
		// check if the browserLocation contains the chromedriver
		if (!browserLocation.contains(browser) || browserLocation.endsWith("/")) {
			// now the chromedriver isn't part of the given path
			// build path with given directory
			browserLocation = getBrowserLocation(browser, browserLocation);
		} else {
			// process the given chromedriver path dependant on running OS.
			if (!browserLocation.startsWith("/")) {
				browserLocation = "/".concat(browserLocation);
			}
			// Replace backslashes in the path with forward slashes
			if (browserLocation.contains("\\")) {
				browserLocation = browserLocation.replace("\\", "/");
			}

                        // If running on mac or linux, remove the windows exe extension
                        Boolean isMacOrLinux = isMac() || isLinux();
                        if (isMacOrLinux && browserLocation.endsWith(".exe")) {
                            browserLocation = browserLocation.replace(".exe", "");
                        }

		}
		// append the processed path to project root dir
		String userDir = System.getProperty("user.dir");
		if (!browserLocation.contains(userDir)) {
			browserLocation = userDir + browserLocation;
		}
		return browserLocation;
	}
	
	private String getBrowserLocation(String browser, String browserDirectory) {
		String browserPath = "";
		String operatingSystem = "windows";
		String executable = "chromedriver.exe";
		String browserFolder = "googlechrome";
		if (null != browser && browser.equalsIgnoreCase(PHANTOMJS)) {
			executable = "phantomjs.exe";
			browserFolder = "phantomjs";
		}
		if (isMac()) {
			operatingSystem = "osx";
			executable = executable.replace(".exe", "");
		}

                if (isLinux()) {
                    operatingSystem = "linux";
                    executable = executable.replace(".exe", "");
                }

		String machineBits = "32bit";
		if (is64BitMachine()) {
			machineBits = "64bit";
		}
		// if given browserDirectory endsWith / or else add it.
		if (!browserDirectory.endsWith("/")) {
			browserDirectory = browserDirectory.concat("/");
		}
		// if given browserDirectory doesn't start with /
		if (!browserDirectory.startsWith("/")) {
			browserDirectory = "/".concat(browserDirectory);
		}
		// Now build the path
		browserPath = browserDirectory + operatingSystem + "/" 
				+ browserFolder + "/"
				+ machineBits + "/"
				+ executable;
		return browserPath;
	}
	
	/**
	 * Checks if running machine is 64 bit otherwise it's 32bit.
	 * @return true if 64 bit.
	 */
	private boolean is64BitMachine() {
		String bit = System.getProperty("os.arch");
		return bit.contains("64");
	}
	
	/**
	 * Checks if running on Mac OS X
	 * @return true if user is on a Mac
	 */
	private boolean isMac() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.startsWith("mac") || os.contains("mac");
	}
	
      /**
       * Checks if running on Linux
       * @return true if user is on Linux
       */
      private boolean isLinux() {
          String os = System.getProperty("os.name").toLowerCase();
          return os.contains("linux");
      }

       /**
	 * Checks if running on Windows
	 * @return true if user is on a Windows
	 */
	public boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.indexOf("win") > 0;
	}
	
	/**
	 * Sets WebDriver property
	 * @throws FileNotFoundException 
	 */
	private void setWebDriverProperty(String browser) throws FileNotFoundException {
		String property = "webdriver.chrome.driver";
		if (browser.equalsIgnoreCase(PHANTOMJS)) {
			property = "phantomjs.binary.path";
		}
		String webDriverLocation = getProcessedDriverLocation(browser, property);
		File executable = new File(webDriverLocation);
		if (!executable.exists()) {
			throw new FileNotFoundException("WebDriver not found!");
		}
		System.setProperty(property, webDriverLocation);
	}
	
	private DesiredCapabilities getChromeCapabilities() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		return capabilities;
	}

	private DesiredCapabilities getChromeMobileCapabilities() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", "Samsung Galaxy S4");
		Map<String, Object> mobileOptions = new HashMap<String, Object>();
		mobileOptions.put("mobileEmulation", mobileEmulation);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(ChromeOptions.CAPABILITY, mobileOptions);
		return capabilities;
	}
	
	private DesiredCapabilities getPhantomJsCapabilities() {
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        final List<String> cliArguments = new ArrayList<String>();
        cliArguments.add("--web-security=false");
        cliArguments.add("--ssl-protocol=any");
        cliArguments.add("--ignore-ssl-errors=true");
        cliArguments.add("--proxy-type=none");
        capabilities.setCapability("phantomjs.cli.args", cliArguments);
        capabilities.setCapability("takesScreenshot", true);
        return capabilities;
	}
	
	public void closeBrowser(WebDriver driver) {
		// quit the browser if the browser isn't null
		if (null != driver) {
			driver.quit();
		}
	}
	
	private WebDriver initBrowser(String browser) {
		// check if there is an existing driver running
		WebDriver driver = SpecHooks.getDriver();
		// close if there is a browser instance running already
		closeBrowser(driver);
		// launch the browser
		// By default, launch phantomjs
		if (browser.equalsIgnoreCase(CHROME)) {
			// initialize chrome browser
			driver = initChromeBrowser(false);
		} else if (browser.equalsIgnoreCase(FIREFOX)) {
			// If browser property is anything but chrome or firefox
			// instantiate a firefox driver by default
			driver = new FirefoxDriver();
			// resize the window to max
			driver.manage().window().maximize();
		} else {
			driver = initPhantomJsBrowser();
			// resize the window to max
			driver.manage().window().maximize();
		}
		SpecHooks.setDriver(driver);
		waitFor(500);
		return driver;
	}

	private WebDriver initChromeBrowser(boolean mobileEmulation) {
		// Set the chrome driver property
		try {
			setWebDriverProperty("chromedriver");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Get desired capabilities for chrome
		DesiredCapabilities desiredCapabilities = getChromeCapabilities();
		if (mobileEmulation) {
			desiredCapabilities = getChromeMobileCapabilities();
		}
		// instantiate a new chrome driver with the given capabilities
		return new ChromeDriver(desiredCapabilities);
	}
	
	private WebDriver initPhantomJsBrowser() {
		DesiredCapabilities desiredCapabilities = getPhantomJsCapabilities();
		// Set the phantomjs driver property
		try {
			setWebDriverProperty("phantomjs");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// instantiate a new phantomjs driver with the given capabilities
		return new PhantomJSDriver(desiredCapabilities);
	}
	
	private String getBrowserName() {
		String browser = System.getenv("browser");
		// when browser property isn't found
		// then get browser property from env properties file
		if (null == browser || browser.length() < 1) {
			browser = System.getenv("browser.name");
		}
		// when browser property from env properties is still null
		// and not chrome or firefox
		// then fallback to phantomjs
		if (null == browser || 
				(!browser.equalsIgnoreCase(CHROME) 
						&& !browser.equalsIgnoreCase(FIREFOX)
						&& !browser.equals(PHANTOMJS))) {
			browser = DEFAULT_BROWSER;
		}
		return browser;
	}

	public WebDriver initDesktopBrowser() {
		WebDriver driver = SpecHooks.getDriver();
		// get browser env variable
		String browser = getBrowserName();
		// when browser name is firefox
		// and driver isn't null
		// then just resize the window to max
		if ((browser.equalsIgnoreCase(FIREFOX) 
				|| browser.equalsIgnoreCase(PHANTOMJS))
				&& null != driver) {
			driver.manage().window().maximize();
			waitFor(500);
			return driver;
		}
		// if browser property isn't firefox
		// then close existing and instantiate a new browser
		driver = initBrowser(browser);
		// there is an outstanding issue with maximising on mac
		// Hence, has to be done manually
		if (isMac() && browser.equalsIgnoreCase(CHROME)) {
			maximizeBrowserWindow(driver);
		}
		return driver;
	}
	
	/**
	 * Initialise a browser, emulated if chrome or resized window if firefox.
	 * @return driver
	 */
	public WebDriver initMobileBrowser() {
		WebDriver driver = SpecHooks.getDriver();
		String browser = getBrowserName();
		// Check if browser name isn't chrome 
		// then just resize the window to mobile size
		if (!browser.equalsIgnoreCase(CHROME)
				&& null != driver) {
			driver.manage().window().setSize(new Dimension(360, 640));
			waitFor(500);
			return driver;
		}
		// if browser property is chrome
		// then close existing and instantiate a new browser
		closeBrowser(driver);
		driver = initChromeBrowser(true);
		SpecHooks.setDriver(driver);
		waitFor(500);
		return driver;
	}
	
	public void waitFor(long waitFor) {
		try {
			Thread.sleep(waitFor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void maximizeBrowserWindow(WebDriver driver) {
		if (driver instanceof PhantomJSDriver) {
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point position = new Point(0, 0);
		driver.manage().window().setPosition(position);
		Dimension maximizedScreenSize =
		    new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(maximizedScreenSize);
		} else {
			driver.manage().window().maximize();
		}
		waitFor(500);
	}

}
