package com.demo.qa.trivago.pages;

import java.time.Duration;
import java.util.ArrayList;
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
import com.demo.qa.trivago.pages.MainPage;

public class SearchPage extends BasePage {
	
	//Filters xpath
	
	public static String filtersRowXpath = "//div[@data-testid='refinement-row']";
	//Price filter
	public static String priceFilterXpath = filtersRowXpath + "//div[@data-testid='refinement_row_element']" +"//button[@name='budget']";
	public static String priceFilterPopUpXpath = "//div[@data-testid='refinement-row-active-popover']";
	public static String sliderDivXpath = "//div[@data-testid='price-slider']";
	public static String priceLeftSliderXpath = "//div[@role='slider' and @data-testid='slider-handle-min']";
	public static String priceRightSliderXpath = "//div[@role='slider' and @data-testid='slider-handle-max']";
	
	public static String minPriceXpath = "//div//input[@type='number' and @data-testid='price-filter-value-min']/following-sibling::span";
	public static String maxPriceXpath = "//div//input[@type='number' and @data-testid='price-filter-value-max']/following-sibling::span";
	public static String priceResultMainXpath = "//div[@data-testid='clickout-area']//div[@data-testid='recommended-price' and @itemprop='price']";
	
	public static String resetButtonXpath = "//button[text()='Reset' and @data-testid='filters-popover-reset-button']";
	public static String priceFilterApplyButtonXpath = "//button[text()='Apply' and @data-testid='filters-popover-apply-button']";
	
	@Override
	public void setupWebpage() {
		// setup Main page
		this.pageURL = BasePage.basePageURL;
		super.setupWebpage();

		MainPage.clickShadowCookiesAllow(driver);
		MainPage.clickShadowCookiesOK(driver);

		// close notif popup
		boolean ret = MainPage.closePopup(driver,3000);
		ret = MainPage.closePopup(driver,3000);
		
		// login
		MainPage.login(driver);
		
		//search
		MainPage.search(driver);
		
	}
	
	@Override
	public void testTearDown() {
		super.testTearDown();
		resetPriceFilter(this.driver);
	
	}
	
	public static void moveLeftSlider(WebDriver driver,int percent) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 2000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		WebElement priceSlider = driver.findElement(By.xpath(sliderDivXpath));
		int sw = BasePage.getElementWidth(priceSlider);
		int result = (percent * sw) / 100;
		
		WebElement leftSlider = driver.findElement(By.xpath(priceLeftSliderXpath));
		BasePage.dragAndDropElementByXY(driver, leftSlider, result, 0);	
		
	}
	
	public static void moveRightSlider(WebDriver driver,int percent) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 2000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		WebElement priceSlider = driver.findElement(By.xpath(sliderDivXpath));
		int sw = BasePage.getElementWidth(priceSlider);
		int result = (percent * sw) / 100;
		
		WebElement rightSlider = driver.findElement(By.xpath(priceRightSliderXpath));
		BasePage.dragAndDropElementByXY(driver, rightSlider, result, 0);	
		
	}
	
	public static int getMinPrice(WebDriver driver) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 2000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		WebElement minPriceInput = driver.findElement(By.xpath(minPriceXpath));
		String price = minPriceInput.getText();

        // Regular expression to match digits
        String numberPart = price.replaceAll("[^0-9]", "");
        while(numberPart.equals("")) {
    		minPriceInput = driver.findElement(By.xpath(minPriceXpath));
    		price = minPriceInput.getText();

            // Regular expression to match digits
            numberPart = price.replaceAll("[^0-9]", ""); 	
        }

        // Convert to integer
        int number = Integer.parseInt(numberPart);
		
		return number;
	}
	
	public static int getMaxPrice(WebDriver driver) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 2000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		WebElement maxPriceInput = driver.findElement(By.xpath(maxPriceXpath));
		String price = maxPriceInput.getText();

        // Regular expression to match digits
        String numberPart = price.replaceAll("[^0-9]", "");
        while(numberPart.equals("")) {
    		maxPriceInput = driver.findElement(By.xpath(maxPriceXpath));
    		price = maxPriceInput.getText();

            // Regular expression to match digits
            numberPart = price.replaceAll("[^0-9]", ""); 	
        }

        // Convert to integer
        int number = Integer.parseInt(numberPart);
		
		return number;
	}
	
	public static void resetPriceFilter(WebDriver driver) {
		
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 2000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		boolean clickedReset = BasePage.clickElement(driver, By.xpath(resetButtonXpath), 1000);
		LogDebugMessage("Clicked price reset Box %b".formatted(clickedReset));
		
		if (BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 1000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		SearchPage.waitPageLoad(driver, 5);

	}
	
	public static void hitApplyPriceFilter(WebDriver driver) {
		if (!BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 1000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		boolean clickedApply = BasePage.clickElement(driver, By.xpath(priceFilterApplyButtonXpath), 1000);
		LogDebugMessage("Clicked price reset Box %b".formatted(clickedApply));
		
		if (BasePage.isElementVisible(driver,By.xpath(priceFilterPopUpXpath), 1000)){
			boolean clickedPrice = BasePage.clickElement(driver, By.xpath(priceFilterXpath), 1000);
			LogDebugMessage("Clicked Price Box %b".formatted(clickedPrice));
		}
		
		SearchPage.waitPageLoad(driver, 5);
		
	}
	
	public static ArrayList<Integer> getMainPrices(WebDriver driver) {
		
		 ArrayList<Integer> ints = new ArrayList<Integer>();
		 List<WebElement> els =  driver.findElements(By.xpath(priceResultMainXpath));
		 
		 for(WebElement el:els) {
			 String p = el.getText();
	        // Regular expression to match digits
	        String numberPart = p.replaceAll("[^0-9]", "");
	        // Convert to integer
	        int number = Integer.parseInt(numberPart);
	        ints.add(number);
		 }
		 return ints;
	}
	
	
	public static boolean waitPageLoad(WebDriver driver,int s) {
		
		try {
			String searchButtonXpath = "//button//span[contains(text(),'Search')]";
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(s));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(searchButtonXpath)));
			
			if(element == null) {
				LogError("Search button did not show %d seconds after search".formatted(s));
				return false;
			}
			
			String imagesResultListXpath = "//div[@data-testid='result-list-ready']//ol//li//div//button/img";
			
			WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(s));
			List<WebElement> elements = wait2.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(imagesResultListXpath)));
			
			LogDebugMessage("Found %d number of elements with xpath %s on search Page".formatted(elements.size(),imagesResultListXpath));
			if(elements.size() == 0) {
				LogError("no images showed %s seconds after search".formatted(s));
				return false;
			}
			
			String mapBoxXpath = "//div[contains(@data-testid,'map-container')]";
			
			WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(s));
			WebElement element3 = wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mapBoxXpath)));
			
			if(element3 == null) { 
				LogError("no map showed %s seconds after search".formatted(s));
				return false;
			}
			
			 
			return true;
		
		}catch(Exception e) {
			LogWarningMessage("Exception while waiting for Search Page to Load: %s".formatted(e.getMessage()));
			return false;
		}
		 
	}
	

}
