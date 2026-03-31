package com.opencart.qa.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencart.qa.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementsUtil {
	// In the Utils class we can't make static variable/method because it stop the
	// parallel execution.
	// In the utils u can't create the driver related method as a static becase it
	// prevent the parallel execution
	// Ns calling >Ns

	private WebDriver driver;
	private Actions act;
	private JavaScriptUtil jsUtil;

	public ElementsUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}

	@Step("clicking on element using By Locator:{0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}
	@Step("clicking on element using By Locator:{0}")
	public void doClick(By locator, long timeout) {
		getElement(locator, timeout).click();
	}
    
	public void doClear(By locator) {
		getElement(locator).clear();
	}
	@Step("entering value:{1}using By locator:{0}")
	public void doSendkey(By locator, String value) {
		getElement(locator).sendKeys(value);
	}
    @Step("entering value:{1}using By locator:{0}")
	public void doSendkey(By locator, String value, long timeout) {
    	doClear(locator);
		getElement(locator, timeout).sendKeys(value);
	}
    @Step("get text of the element using By Locator:{0}")
	public String doElementgetText(By Locator) {
		return getElement(Locator).getText();
	}

	public String getElementAttribute(By Locator, String attrname) {
		return getElement(Locator).getAttribute(attrname);
	}
    @Step("element is dispalyed using locator:{0}")
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Elemnt not found useing this locator:" + locator);
			e.printStackTrace();
			return false;
		}
	}
    @Step("find element using locator:{0}")
	public WebElement getElement(By locator) {
		WebElement element = driver.findElement(locator);
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	public WebElement getElement(By locator, long timeout) {
		try {
			return driver.findElement(locator);
		} catch (NoSuchElementException e) {
			System.out.println("Element is not found using:" + locator);
			e.printStackTrace();
			return waitForElementVisible(locator, timeout);
		}

	}
	@Step("find element using locator:{0}")
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}
    
	public List<String> getElementList(By locator) {
		List<WebElement> elelist = getElements(locator);
		List<String> eleTextlist = new ArrayList<String>();
		for (WebElement e : elelist) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextlist.add(text);
			}
		}
		return eleTextlist;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	// ***********SelectTag DropDown utils*************

	private Select getSelect(By locator) {
		return new Select(getElement(locator));
	}

	public void doDropdwonSelectByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}

	public void doDropdwonSelectByVisibleText(By locator, String visibleText) {
		getSelect(locator).selectByVisibleText(visibleText);
	}

	public void doDropdwonSelectByValue(By locator, String optionValue) {
		getSelect(locator).selectByValue(optionValue);
	}

	public int getDropDownOptions(By locator) {
		return getSelect(locator).getOptions().size();
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		List<WebElement> optionList = getSelect(locator).getOptions();
		List<String> optiontextList = new ArrayList<String>();// pc=0 vc=10;[]
		for (WebElement e : optionList) {
			String text = e.getText();
			optiontextList.add(text);
		}
		return optiontextList;
	}

//*********** Actions Class utils*************

	public void doActionClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doActionSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	public void handleMenuItemLevel2(By parentlocator, By childlocator) {
		act.moveToElement(getElement(parentlocator)).perform();
		doClick(childlocator);
	}

	public void handleMenuItemLevel3(By menu1, By menu2, By menu3) throws InterruptedException {
		doClick(menu1);
		act.moveToElement(getElement(menu2)).perform();
		doClick(menu3);
	}

	public void handleMenuItemLevel4(By menu1, By menu2, By menu3, By menu4) throws InterruptedException {
		doClick(menu1);
		act.moveToElement(getElement(menu2)).perform();
		act.moveToElement(getElement(menu3)).perform();
		doClick(menu4);
	}

	public void sendKeyWithPause(By locator, String value, long pauseTime) {
		char val[] = value.toCharArray();// [A,u,t,o,m,a,t,i,o,n]
		for (char e : val) {
			act.sendKeys(getElement(locator), String.valueOf(e)).pause(500).perform();
		}
	}

	// ****************WaitUtils*************************

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public List<WebElement> waitForAllElementsPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */

	public List<WebElement> waitForAllElementsVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeOut
	 */

	public void waitForElementReadyAndClick(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible. Parameters:
	 * locator used to find the element and timeout Returns: the WebElement once it
	 * is located
	 */
	@Step("wating for the element :{0}with timeout:{1}")
	public WebElement waitElementPresence(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0. Parameters: locator used to
	 * find the element and timeout Returns: the WebElement once it is located and
	 * visible
	 */
	@Step("wating for the element :{0}with timeout:{1}")
	public WebElement waitForElementVisible(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitElementVisible(By locator, long timeOut, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class)
				.withMessage("__Eleement Not Found__Im custome message___" + locator);

		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForVisibleWithFluentWait(By locator, long timeOut, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(ElementNotInteractableException.class)
				.withMessage("__Eleement Not Found__Im custome message___" + locator);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Pass Partial URL
	public String waitForURLContains(String urlValue, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlContains(urlValue));
			return driver.getCurrentUrl();
		} catch (TimeoutException e) {
			System.out.println(urlValue + "is not found");
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}

	// Pass full URL
	public String waitForURLToBe(String urlValue, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlToBe(urlValue));
			return driver.getCurrentUrl();
		} catch (TimeoutException e) {
			System.out.println(urlValue + "is not found");
			e.printStackTrace();
		}
		return driver.getCurrentUrl();
	}

	// Partial title need to pass
	public String waitForTitleContains(String titleValue, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleContains(titleValue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println(titleValue + "is not found");
			e.printStackTrace();
			return null;
		}
	}

	// Full title need to pass in below message
	public String waitForTitleIs(String titleValue, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleIs(titleValue));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println(titleValue + "is not found");
			e.printStackTrace();
			return null;
		}
	}

	private Alert waitForAlert(long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String waitForJSAlertAndAccept(long timeOut) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public String waitForJSAlertAndDismiss(long timeOut) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.dismiss();
		return text;
	}

	public String waitForJSPromptAlertAndEnterValue(String value, long timeOut) {
		Alert alert = waitForAlert(timeOut);
		String text = alert.getText();
		alert.sendKeys(value);
		alert.accept();
		return text;
	}

	public void waitForFrameAndSwitchToIt(By frameLocator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameAndSwitchToIt(int frameIndex, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameAndSwitchToIt(String frameIdOrname, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrname));
	}

	public void waitForFrameAndSwitchToIt(WebElement frameElement, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
}
