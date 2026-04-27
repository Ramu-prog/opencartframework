package com.opencart.qa.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.opencart.qa.exceptions.BrowserException;
import com.opencart.qa.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	public static String highlight;
	public OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {
		this.prop = prop;
		String browserName = prop.getProperty("browser");
		System.out.println("browser name:" + browserName);
		highlight = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				intiRemoteDriver(browserName);
			} else {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;

		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				intiRemoteDriver(browserName);
			} else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
			}
			break;

		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				intiRemoteDriver(browserName);
			} else {
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
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

	private void intiRemoteDriver(String browserName) {
		System.out.println("Runing the testcase on the seleniumgrid...with browser:" + browserName);
		try {
			switch (browserName.trim().toLowerCase()) {
			case "chrome":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFireFoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties intProp() {
		FileInputStream ip = null;
		prop = new Properties();

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
			e.printStackTrace();
		}
		return prop;
	}

	public static File getScreenshotFile() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
	}
}
