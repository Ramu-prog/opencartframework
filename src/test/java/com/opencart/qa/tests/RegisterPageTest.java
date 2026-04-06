package com.opencart.qa.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.opencart.qa.base.BaseTest;
import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.CsvUtil;

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

	@DataProvider
	public Object[][]getUserRegCSVTestData(){
		return CsvUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getUserRegCSVTestData")
	public void userRegisterTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(registerPage.userRegisteration(firstName, lastName, telephone, password, subscribe));
	}

}
