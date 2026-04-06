package com.opencart.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementsUtil;


public class HomePage {

	//initial driver and ele util
	private WebDriver driver;
	private ElementsUtil eleUtil;

	//2.page class constructor..
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementsUtil(driver);
	}
	
	//3.private By locator:PO
	private final By logoutLink= By.linkText("Logout");
	private final By header= By.cssSelector("div#content h2");
	private final By searchTextField = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button"); 
	
	//4.public page actions/methods
	
	public String getHomePageTitle() {
		String actTitle= eleUtil.waitForTitleIs(AppConstants.Home_PAGE_TITLE, AppConstants.SHORT_TIME_OUT);
		System.out.println("Home page title:"+actTitle);
		return actTitle;
	}
	public boolean isLogoutLinkExist() {
	return	eleUtil.isElementDisplayed(logoutLink);
	}

	public List<String> getHomePageHeaders() {
		List<WebElement>headerList = 
				eleUtil.waitForAllElementsPresence(header, AppConstants.SHORT_TIME_OUT);
       List<String> headersValueList = new ArrayList<String>();
		
       for(WebElement e : headerList) {
        	String text = e.getText(); 
        	headersValueList.add(text);
        }
       return headersValueList;
	}
	
	public ResultsPage doSearch(String searchKey) {
		System.out.println("Search Key:"+ searchKey);
		eleUtil.doSendkey(searchTextField, searchKey, AppConstants.SHORT_TIME_OUT);
		eleUtil.doClick(searchIcon);
		return new ResultsPage(driver);
	}
	
	
	
	
	
}
