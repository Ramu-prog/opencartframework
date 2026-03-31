package com.opencart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.opencart.qa.utils.AppConstants;

import seleniumSession.ElementsUtil;

public class ResultsPage {

	// initial driver and element Utils
	private WebDriver driver;
	private ElementsUtil eleUtil;

	// 2.page class constructor..
	public ResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);
	}

	// 3.private by locator:PO
	private final By searchResults = By.cssSelector("div.product-thumb");

	// 4.public page actions/methods:

	public int getSearchResultsCount() {
		int resultsCount = eleUtil.waitForAllElementsPresence(searchResults, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Total number of results agter search:" + resultsCount);
		return resultsCount;
	}

	public ProductInfoPage selectProduct(String productName) {
		System.out.println("select product name :" + productName);
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}

}
