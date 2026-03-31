package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.opencart.qa.base.BaseTest;

public class RegisterPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registerPage = loginPage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object[][] { 
			    { "Narula", "Test", "7896541237", "pass#2", "Yes" },
				{ "Aurag", "Test", "7896541231", "pass#3", "NO" },
				{ "Saurav", "Test", "7896541232", "pass#4", "Yes" }, 
				};
	}

	@Test(dataProvider = "getUserRegTestData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(registerPage.userRegisteration(firstName, lastName, telephone, password, subscribe));
	}

}
