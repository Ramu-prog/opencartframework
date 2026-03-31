package com.opencart.qa.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

	private WebDriver driver;
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver= driver;
	}
	
	// Flash element //where is driver for that purpose we can use 
    public void flash(WebElement element) {
        String bgColor = element.getCssValue("backgroundColor");//purple
        for (int i = 0; i < 7; i++) {
            changeColor("rgb(0,200,0)", element); // green
            changeColor(bgColor, element);        // original color
        }
    }

    // Change color using JS
    private void changeColor(String color, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.backgroundColor = '"+ color + "'" ,element );
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Get title using JS
    public String getTitleByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.title;").toString();
    }
	
    public String getURLByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript("return document.URL;").toString();
    }
 // Refresh Page
    public void refreshBrowserByJS() {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    // Navigate Back
    public void navigateToBackPage() {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(-1)");
    }

    // Navigate Forward
    public void navigateToForwardPage() {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(1)");
    }

    public void navigateToSpecificPage(String pageNumber) {
  	  JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("history.go('"+pageNumber+"')");
  }
    
    
    // Generate Alert
    public void generateAlert(String message) throws InterruptedException {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("alert(arguments[0]);", message);
        Thread.sleep(3000);
        driver.switchTo().alert().accept();
    }
	
    public String getPageInnerText() {
    	  JavascriptExecutor js = (JavascriptExecutor) driver;
     return	  js.executeScript("return document.documentElement.innerText;").toString();
    }
	
    public void clickElementByJS(WebElement element) {
  	  JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("arguments[0].click();", element);
  }
    
    
    
	public void drawBorder(WebElement element) {
	JavascriptExecutor js = (JavascriptExecutor) driver;	
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void scrollPageDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void scrollPageDown(String height) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0,'"+ height +"')");
	}
	
	public void scrollPageUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}
	
	public void scrollIntoView(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("argument[0].scrollIntoView(true);", element);
}}
