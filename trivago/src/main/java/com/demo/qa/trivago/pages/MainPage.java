package com.demo.qa.trivago.pages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import java.time.YearMonth;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.demo.qa.trivago.BaseClass;
import com.demo.qa.trivago.pages.LoginPage;
import com.demo.qa.trivago.pages.SearchPage;

public class MainPage extends BasePage{
	
	protected LoginPage loginPage;
	
	public static String searchInputXpath = "//input[@role='searchbox']";
	public static String checkInBoxXpath = "//button[@data-testid='search-form-calendar-checkin']";
	public static String checkOutBoxXpath = "//button[@data-testid='search-form-calendar-checkout']";
	public static String calendarPopUpXpath = "//div[@data-testid='calendar-popover']";
	
	public static String destination = "London";
	
	public static String firstSuggestionXpath = "(//div[@data-testid='search-suggestions']//ul/li)[1]";
	
	public static String guestBoxXpath = "//button[@data-testid='search-form-guest-selector']";
	public static String guestPopUpXpath = "//section[@data-testid='guest-selector-popover']";
	public static String guestPopUpApplyXpath = guestPopUpXpath + "//button[contains(text(),'Apply')]";
	
	public static String searchButtonXpath = "//button//span[contains(text(),'Search')]"; 
	
	@Override
	public void setupWebpage() {
		this.pageURL = BasePage.basePageURL;
		super.setupWebpage();
		//skip cookies
		//Button 'Allow All'

		clickShadowCookiesAllow(driver);
		//clickShadowCookiesOK(driver);
		
		
		// close notif popup
		boolean ret = closePopup(driver,3000);
		ret = closePopup(driver,3000);
		
	}
	
	/*
	
	public boolean clickCookiesAllowAll() {
		boolean ret = false;
		String allowXpath = "//button[contains(text(),'Allow')]";
		try {
			ret = BasePage.clickElement(this.driver, By.xpath(allowXpath), 3500);
			LogInfoMessage("Attempted to Click close popup button with xpath %s".formatted(allowXpath));	
		}catch(Exception e) {
			LogWarningMessage("Did not Click close popup button with xpath %s and got exception %s".formatted(allowXpath,e.getMessage()));
			return false;
		}
		
		return ret;		
	}
	
	public boolean clickClosePopup() {
		boolean ret = false;
		String closePXpath = "(//button/span[contains(text(),'Close')]/preceding-sibling::span)[1]";
		try {
			ret = BasePage.clickElement(this.driver, By.xpath(closePXpath), 3500);
			LogInfoMessage("Attempted to Click close popup button with xpath %s".formatted(closePXpath));	
		}catch(Exception e) {
			LogWarningMessage("Did not Click close popup button with xpath %s and got exception %s".formatted(closePXpath,e.getMessage()));
			return false;
		}
		
		return ret;	
	}
	*/
	
	public static boolean closePopup(WebDriver driver,int mseconds) {
		boolean ret = false;
		String closePXpath = "(//button/span[contains(text(),'Close')]/preceding-sibling::span)[1]";
		try {
			ret = BasePage.clickElement(driver, By.xpath(closePXpath), mseconds);
			LogInfoMessage("Click result: %b of popup button with xpath %s".formatted(ret,closePXpath));	
		}catch(Exception e) {
			LogWarningMessage("Did not Click close popup button with xpath %s and got exception %s".formatted(closePXpath,e.getMessage()));
			return false;
		}
		
		return ret;
	}
	
	public static boolean closeNotifWindow(WebDriver driver) {
		boolean ret = false;
		String closeXpath = "//button[contains(@data-testid,'close-button')]";
		try {
			
			ret = BasePage.clickElement(driver, By.xpath(closeXpath), 3000);
			LogInfoMessage("Attempted to Click close popup button with xpath %s. \n Result %b".formatted(closeXpath, ret));
			//Thread.sleep(200000000);
		}catch(Exception e) {
			LogWarningMessage("Did not click close popup button with xpath %s. \n Exception %s".formatted(closeXpath, e.getMessage()));
			return false;
		}
		
		return ret;
	}
	
	public static boolean clickShadowCookiesOK(WebDriver driver) {
		try {
			//System.out.println("IN OK");
			String shadowDivXpath = "//aside[contains(@id,'usercentrics')]"; 
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement shadowHost = wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(shadowDivXpath)));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object shadowRoot = js.executeScript("return arguments[0].shadowRoot", shadowHost);
			//Thread.sleep(3000);
			
			// Wait for the button to be visible in the shadow DOM
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	        wait.until(driver_ -> {
	            // Find all buttons inside the shadow root
	            List<WebElement> buttons = (List<WebElement>) js.executeScript("return arguments[0].querySelectorAll('button');", shadowRoot);
	            // Return true if the button with text 'Allow all' is present
	            return buttons.stream().anyMatch(btn -> btn.getText().contains("Allow all"));
	        });			
	        
			List<WebElement> buttons = (List<WebElement>) js.executeScript("return arguments[0].querySelectorAll('button');", shadowRoot);
			//System.out.println("buttons");
			//System.out.println(buttons);
	        WebElement shadowButton = buttons.stream()
	                    .filter(btn -> btn.getText().contains("OK"))
	                    .findFirst()
	                    .orElse(null);
			LogInfoMessage("Shadow button: %s".formatted(shadowButton.toString()));
			shadowButton.click(); 
			LogInfoMessage("Clicked 'shadow' button OK (cookies accept)");
			
		}catch(Exception e) {
			LogWarningMessage("Exception while clicking 'shadow OK cookies': %s".formatted(e.getMessage()));
			return false;
		}
		return true;
	}
	
	public static boolean clickShadowCookiesAllow(WebDriver driver) {
		try {
			String shadowDivXpath = "//aside[contains(@id,'usercentrics')]"; 
			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement shadowHost = wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(shadowDivXpath)));
			//WebElement shadowHost = driver.findElement(By.xpath(shadowDivXpath));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Object shadowRoot = js.executeScript("return arguments[0].shadowRoot", shadowHost);
			
			
			// Wait for the button to be visible in the shadow DOM
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(driver_ -> {
	            // Find all buttons inside the shadow root
	            List<WebElement> buttons = (List<WebElement>) js.executeScript("return arguments[0].querySelectorAll('button');", shadowRoot);
	            // Return true if the button with text 'Allow all' is present
	            return buttons.stream().anyMatch(btn -> btn.getText().contains("Allow all"));
	        });
			
			//Thread.sleep(3000);
			List<WebElement> buttons = (List<WebElement>) js.executeScript("return arguments[0].querySelectorAll('button');", shadowRoot);
			System.out.println("buttons");
			System.out.println(buttons);
	        WebElement shadowButton = buttons.stream()
	                    .filter(btn -> btn.getText().contains("Allow all"))
	                    .findFirst()
	                    .orElse(null);
	        LogInfoMessage("Shadow button: %s".formatted(shadowButton.toString()));
			shadowButton.click(); 
			LogInfoMessage("Clicked 'shadow' button 'Allow all' (cookies accept)");
			
		}catch(Exception e) {
			LogWarningMessage("Exception while clicking 'shadow Allow cookies': %s".formatted(e.getMessage()));
			return false;
		}
		return true;
	}
	
	
	public static boolean clickLogin(WebDriver driver) {
		String loginButtonXpath = "//button//span[contains(text(),'Log in')]";
		String signinButtonXpath = "//button//span[contains(text(),'Sign in')]";
		if (BasePage.isElementVisible(driver, By.xpath(loginButtonXpath), 2000)) {
			return BasePage.clickElement(driver, By.xpath(loginButtonXpath), 5000);
		}else if (BasePage.isElementVisible(driver, By.xpath(signinButtonXpath), 2000)) {
			return BasePage.clickElement(driver, By.xpath(signinButtonXpath), 5000);	
		}
		return false;
	}
	
	public static boolean clickLoginOrCreatePopup(WebDriver driver) {
		String loginCreate = "//button/span[contains(text(),'Log in or create')]";
		String signinCreate = "//button/span[contains(text(),'Sign in or create')]";
		if (BasePage.isElementVisible(driver, By.xpath(loginCreate), 2000)) {
			return BasePage.clickElement(driver, By.xpath(loginCreate), 5000);
		}else if (BasePage.isElementVisible(driver, By.xpath(signinCreate), 2000)) {
			return BasePage.clickElement(driver, By.xpath(signinCreate), 5000);	
		}
		return false;}
	
	public static boolean clickMaybeLaterSurvey(WebDriver driver) {
		String maybeL = "//button/span[contains(text(),'Maybe later')]";
		return BasePage.clickElement(driver, By.xpath(maybeL), 2000);
	}
	
	public static boolean clickNotNowPref(WebDriver driver) {
		String notNowPref = "//button/span[contains(text(),'Not now')]";
		return BasePage.clickElement(driver, By.xpath(notNowPref), 5000);
	}
	
	public static boolean login(WebDriver driver) {
		try {
			
	    	boolean clickedloggedin = clickLogin(driver);
	    	LogDebugMessage("Clicked login: %s".formatted(clickedloggedin));
	    	boolean clickedLoginPopup = clickLoginOrCreatePopup(driver);
	    	LogDebugMessage("Clicked login Popup: %s".formatted(clickedLoginPopup));
	    	boolean ep = LoginPage.waitEPageLoad(driver, 5);
	    	LogDebugMessage("Waited for email page load for login: %b".formatted(ep));
	    	LogDebugMessage("Starting login ...");
	    	boolean loggedIn = loginFromMain(driver);
	    	LogDebugMessage("Ended login. \n Result: %b ".formatted(loggedIn));
	    	assertTrue(loggedIn);
			
	    	boolean np = clickNotNowPref(driver);
	    	LogDebugMessage("Clicked Not now: %b".formatted(np));
	    	
	    	boolean cp = closePopup(driver,4000);
	    	LogDebugMessage("Clicked popup: %b".formatted(cp));

	    	boolean wp = waitPageLoad(driver,5);
	    	LogDebugMessage("Waited for main page load: %b".formatted(wp));
	    	
	    	return wp;
			
		}catch(Exception e) {
			BaseClass.LogError("Exception while attempting to search from main page:\n %s".formatted(e.getMessage()));
			return false;	
		}
	}
	
	public static boolean search(WebDriver driver){
		
		try {
			boolean clickedSearchBox = BasePage.clickElement(driver, By.xpath(searchInputXpath), 2000);
			BaseClass.LogDebugMessage("Clicked search box: %b \n with xpath %s".formatted(clickedSearchBox,searchInputXpath));
			WebElement searchBox = driver.findElement(By.xpath(searchInputXpath));
			
			searchBox.sendKeys(destination);
			//searchBox.sendKeys(Keys.ENTER); 
			boolean clickedFirstSug = BasePage.clickElement(driver, By.xpath(firstSuggestionXpath), 5000);
			LogDebugMessage("Clicked first sugg %b".formatted(clickedFirstSug));
			
			selectDates(driver);

			if (!BasePage.isElementVisible(driver,By.xpath(guestPopUpXpath), 2000)){
				boolean clickedGuestBox = BasePage.clickElement(driver, By.xpath(guestBoxXpath), 3000);
				LogDebugMessage("Clicked Guest Box %b".formatted(clickedGuestBox));
			}
			
			boolean clickedApply = BasePage.clickElement(driver, By.xpath(guestPopUpApplyXpath), 2000);
			LogDebugMessage("Clicked Guest Box Apply %b".formatted(clickedApply));
			
			boolean clickedSearch = BasePage.clickElement(driver, By.xpath(searchButtonXpath), 2000);
			LogDebugMessage("Clicked Search %b".formatted(clickedSearch));
			
			return clickedSearch;
			
		}catch(Exception e) {
			BaseClass.LogError("Exception while attempting to search from main page:\n %s".formatted(e.getMessage()));
			return false;
			
		}
		
	}
	
	public static void selectDates(WebDriver driver) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(calendarPopUpXpath), 2000)){
			boolean clickedCheckedInBox = BasePage.clickElement(driver, By.xpath(checkInBoxXpath), 5000);
			LogDebugMessage("Clicked Checked In Box %b".formatted(clickedCheckedInBox));
		}
		
		String dayXXpath = "button[contains(@data-testid,'-%s')]";
		String firstDayXpath = dayXXpath.formatted("01");
		String firstDayM1Xpath = "(//%s)[1]".formatted(firstDayXpath);
		String firstDayM2Xpath = "(//%s)[2]".formatted(firstDayXpath);
		
		try {
			Thread.sleep(Duration.ofSeconds(1));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		WebElement D1 = driver.findElement(By.xpath("(//time[ancestor::%s])[1]".formatted(firstDayXpath)));
		String date1 = D1.getAttribute("datetime");
		
		WebElement D2 = driver.findElement(By.xpath("(//time[ancestor::%s])[2]".formatted(firstDayXpath)));
		String date2 = D2.getAttribute("datetime");
		
		LogDebugMessage("Found first day datetime '%s' from month 1 from popup and first day  datetime '%s' from month 2".formatted(date1,date2));
		
		String[] d1Parts = date1.split("-");
		String y1 = d1Parts[0];
		String m1 = d1Parts[1];
		String d1 = d1Parts[2];
		
		String[] d2Parts = date2.split("-");
		String y2 = d2Parts[0];
		String m2 = d2Parts[1];
		String d2 = d2Parts[2];
		
		LogDebugMessage("Found date parts %s/%s/%s for month 1 and %s/%s/%s for month2".formatted(y1,m1,d1,y2,m2,d2));
		int lastDay1 = YearMonth.of(Integer.parseInt(y1),Integer.parseInt(m1)).lengthOfMonth();
		int lastDay2 = YearMonth.of(Integer.parseInt(y2),Integer.parseInt(m2)).lengthOfMonth();
		
		String formattedLD1 = String.format("%02d",lastDay1);
		String formattedLD2 = String.format("%02d",lastDay2);
		String formattedFD2 = String.format("%02d",Integer.parseInt(d2));
		LogDebugMessage("Found last days for months %s, %s and first day for 2nd month %s".formatted(formattedLD1, formattedLD2,formattedFD2));
		
		String lastDay1Xpath = dayXXpath.formatted(formattedLD1);
		String lastDayM1Xpath = "(//%s)[1]".formatted(lastDay1Xpath);
		
		boolean clickedLDayM1 = BasePage.clickElement(driver, By.xpath(lastDayM1Xpath), 500);
		LogDebugMessage("Clicked Last Day %s in month1 result: %b".formatted(formattedLD1,clickedLDayM1));
		
		if (!BasePage.isElementVisible(driver,By.xpath(calendarPopUpXpath), 2000)){
			boolean clickedCheckedOutBox = BasePage.clickElement(driver, By.xpath(checkOutBoxXpath), 500);
			LogDebugMessage("Clicked Checked Out Box %b".formatted(clickedCheckedOutBox));
		}
		
		boolean clickedFDayM2 = BasePage.clickElement(driver, By.xpath(firstDayM2Xpath), 500);
		LogDebugMessage("Clicked First Day %s in month2 result: %b".formatted(formattedFD2,clickedFDayM2));
		
		
	}
	
	
	
	public static boolean waitPageLoad(WebDriver driver,int s) {
		
		try {
			String searchButtonXpath = "//button//span[contains(text(),'Search')]";
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(s));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(searchButtonXpath)));
			
			if(element == null) return false;
			
			String imagesResultListXpath = "//ul//li//div[contains(@data-testid,'deal-item')]//div/img";
			
			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(s));
			List<WebElement> elements = wait2.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(imagesResultListXpath)));
			
			LogDebugMessage("Found %d number of elements (accomondation results) with xpath %s on main Page".formatted(elements.size(),imagesResultListXpath));
			if(elements.size() > 0) return true;
			 
			return false;
		
		}catch(Exception e) {
			LogWarningMessage("Exception while waiting for Main Page to Load: %s".formatted(e.getMessage()));
			return false;
		}
		 
	}
	
	public static boolean loginFromMain(WebDriver driver) {
		return LoginPage.login(driver);
	}

}
