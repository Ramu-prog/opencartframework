package com.opencart.qa.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opencart.qa.exceptions.BrowserException;
import com.opencart.qa.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This is used to init the driver on the basis of given browserName
	 * 
	 * @param browserName
	 * @return it will return the driver value
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("browser name:" + browserName);
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;

		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			// driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
			break;

		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			// driver = new SafariDriver();
			break;
		default:
			System.out.println("=====Invalid browser=====" + browserName);
			throw new BrowserException("====Invalid Browser =====");
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	/**
	 * this will return one local copy of driver for a specific thread
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is used to init the properties file
	 * 
	 * @return it return the properties class object which is having all the
	 *         properties(key-value pair)
	 */
	// mvn test -Denv="qa"
	
	public Properties intProp() {
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		System.out.println("Env name is:" + envName);

		try {
			if (envName == null) {
				System.out.println("env name is null, hence runing test cases on QA environemnt...");
				ip = new FileInputStream("./src/test/Resources/config/config.qa.properties");
			} else {

				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/Resources/config/config.qa.properties");
					break;

				case "dev":
					ip = new FileInputStream("./src/test/Resources/config/config.dev.properties");
					break;

				case "stage":
					ip = new FileInputStream("./src/test/Resources/config/config.stage.properties");
					break;

				case "uat":
					ip = new FileInputStream("./src/test/Resources/config/config.uat.properties");
					break;

				case "prod":
					ip = new FileInputStream("./src/test/Resources/config/config.properties");
					break;

				default:
					System.out.println("=========invalid env name:" + envName);
					throw new FrameworkException("Invalid ENV Name :" + envName);

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;
		}
		
		
//		try {
//			FileInputStream ip = new FileInputStream("./src/test/Resources/config/config.properties");
//			prop = new Properties();
//			try {
//				prop.load(ip);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}

	

	/**
	 * takescreenshot
	 */
	public static File getScreenshotFile() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir

	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir
	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir
	}

}
