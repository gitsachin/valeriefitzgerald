package com.auto.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.auto.configProperties.ConfigProperties;


public class TestCore extends Page {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
	static Date date = new Date();
	public static Properties object = new Properties();
	public static Properties config = new Properties();
	public static WebDriver driver;
	public static String SCREENSHOT_FOLDER = "target/screenshots/";
	public static final String SCREENSHOT_FORMAT = ".png";
	private String testUrl;
	private String targetBrowser;
	private String environment;
	private String os;

	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) {

		try {

		} catch (Exception e) {
		}

		/**
		 *Web browser properties  
		 */
		
		testUrl = ConfigProperties.site_url;
		System.out.println("----------" + testUrl + "----------");
		targetBrowser = ConfigProperties.browser_name;
		environment = ConfigProperties.environment;
		os = ConfigProperties.OS;

	}

	/**
	 * WebDriver initialization
	 * 
	 * @return WebDriver object
	 * @throws MalformedURLException 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setup(Method method) throws MalformedURLException{
		if (environment.toLowerCase().equals("local")) {

			if (os.toLowerCase().equals("windows")) {
			 if (targetBrowser.toLowerCase().contains("chrome")) {
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
							+ "//src//main//resources//driver_windows//chromedriver.exe");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
				}
			}

			else if (os.toLowerCase().equals("linux")) {
				 if (targetBrowser.toLowerCase().contains("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "//src//main//resources//driver_linux//chromedriver");
					driver = new ChromeDriver();
					driver.manage().window().maximize();
				}
			}

		}


		// Open test url
		driver.get(testUrl);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String os = cap.getPlatform().toString();
		String browserName = cap.getBrowserName().toLowerCase();
		String browserVersion = cap.getVersion().toString();
		log("Operating System Name: "+os, ILogLevel.TEST);
		log("Browser Name: "+browserName, ILogLevel.TEST);
		log("Browser Version: "+browserVersion, ILogLevel.TEST);

		log("--------------------------------------------------------", ILogLevel.TESTCASE);
		log("Test [" + method.getName() + "] Started", ILogLevel.TESTCASE);
		log("--------------------------------------------------------", ILogLevel.TESTCASE);

	}

	/**
	 * capture screenshot on test(pass/fail)
	 * @throws InterruptedException 
	 * 
	 * 
	 */

	@AfterMethod(alwaysRun=true)
	public void setScreenshot(ITestResult result) throws InterruptedException {

		if (!result.isSuccess()) {
			try {
				if (driver != null) {
					File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					try {
						FileUtils.copyFile(f,
								new File(SCREENSHOT_FOLDER + result.getName() + SCREENSHOT_FORMAT).getAbsoluteFile());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (ScreenshotException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (driver != null) {

			driver.quit();
			
		}

	}
	

}
