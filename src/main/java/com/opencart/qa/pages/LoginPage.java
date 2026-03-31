package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;

import io.qameta.allure.Step;
import seleniumSession.ElementsUtil;

public class LoginPage {
	// initial driver and ele util
	private WebDriver driver;
	private ElementsUtil eleUtil;

	// 2.page class constructor..
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);

	}

	// 3. private By locator:POM
	private final By eamilId = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.cssSelector("input[type='submit']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By registerLink = By.linkText("Register");

	// 4.public page action/methods
	@Step("getting is log page title")
	public String getLoginpageTitle() {
		String actTitle = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page Title:" + actTitle);
		return actTitle;
	}

	@Step("getting is log page url..")
	public String getLoginpageURL() {
		String actURL = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL, AppConstants.SHORT_TIME_OUT);
		System.out.println("Login Page Title:" + actURL);
		return actURL;
	}

	@Step("checking forgot pwd links exist on the login page.. ")
	public boolean isForgotPWdLinkExist() {
		return eleUtil.waitForElementVisible(forgotPwdLink, AppConstants.MEDIUM_TIME_OUT).isDisplayed();
	}

	@Step("user is logged-in with username:{0} and password:{1}")
	public HomePage doLogin(String username, String pwd) {
		System.out.println("App Credentials: " + username + " : " + pwd);
		eleUtil.doSendkey(eamilId, username, AppConstants.MEDIUM_TIME_OUT);
		eleUtil.doSendkey(password, pwd);
		eleUtil.doClick(loginBtn);
		return new HomePage(driver);// page chaining concept two pages are connected to each other
	}

	@Step("navigating to the register page...")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.waitForElementReadyAndClick(registerLink, AppConstants.SHORT_TIME_OUT);
		return new RegisterPage(driver);
	}

}
