package com.demo.qa.trivago.pages;

import java.time.Duration;
import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




import com.demo.qa.trivago.BaseClass;

public class BasePage extends BaseClass{

	protected static String basePageURL = "https://www.trivago.com";
	
	protected String pageURL;
	

	@DisplayName("Setting up Web page")
	public void setupWebpage() {
		System.out.println("Setting up Start Web page...");
		LogInfoMessage("Setting up Start Web page...");
		//Navigate to web page
		driver.get(pageURL);
		
		//Maximizing window
		driver.manage().window().maximize();
		
	}
	
	
	@BeforeAll
	public  void setupAll() {		
        String childClassName = this.getClass().getSimpleName();
        LogInfoMessage("The child class name is: " + childClassName);
        LogInfoMessage("Running setup for tests.");
        try {
            setupLogger();
    		setupDriver();
    		setupWebpage();
        }catch(Exception e) {
        	LogTestCaseFail("BEFORE ALL FAILS WITH: " + e.getMessage());
        }


	}
	
	
    public static boolean scrollToElement(WebDriver driver,WebElement element) {
    	try {
    		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        	//LogTestCasePass("Scrolled to Element %s".formatted(element.toString()));
    		return true;
    	}catch(Exception e) {
    		//LogTestCaseFail(e.getMessage());
    		return false;
    	}
    	
    }
    
    public static boolean scrollToElementMiddle(WebDriver driver,WebElement element) {
    	try {
	        // Use JavaScript to scroll the element into view
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	
	        // Calculate the position to scroll to (middle of the viewport)
	        int elementPosition = element.getLocation().getY();
	        int windowHeight = driver.manage().window().getSize().getHeight();
	        int scrollPosition = elementPosition - (windowHeight / 2);
	
	        // Execute the scroll
	        js.executeScript("window.scrollTo(0, arguments[0]);", scrollPosition);
	        
	        return true;
    	}catch(Exception e) {
    		//LogTestCaseFail(e.getMessage());
    		return false;
    	}
    	
    	
    }
    
    public static int getElementWidth(WebElement element) {
    	
    	Dimension size = element.getSize();
    	int width = size.getWidth();
    	
    	return width;

    }
    
    public static int getElementHeight(WebElement element) {
    	
    	Dimension size = element.getSize();
    	int height = size.getHeight();
    	
    	return height;

    }
    
    public static int getX(WebElement element) {
    	
    	int currentX = element.getLocation().getX();
    	
    	return currentX;

    }
    
    public static int getY(WebElement element) {
    	
    	int currentY = element.getLocation().getY();
    	
    	return currentY;

    }
    
    public static void moveElementByXY(WebDriver driver,WebElement element,int px,int py) {
    	int cx = getX(element);
    	int cy = getY(element);
    	
    	int newX = cx + px;
    	int newY = cy + py;
    	
    	LogDebugMessage("Will move element from (%d,%d) to (%d,%d)".formatted(cx,cy,newX,newY));
 
        // JavaScript code to move the element horizontally
        String script = "arguments[0].style.position = 'absolute';" +
                        "arguments[0].style.left = '" + newX + "px';" +
                        "arguments[0].style.top = '" + newY + "px';";
        
        // Execute
        ((JavascriptExecutor) driver).executeScript(script, element);
        
        LogDebugMessage("Moved Element to %d,%d".formatted(newX,newY));
    	
    	
    }
    
    public static void dragAndDropElementByXY(WebDriver driver,WebElement element,int px,int py) {
    	int cx = getX(element);
    	int cy = getY(element);
    	
    	int newX = cx + px;
    	int newY = cy + py;
    	
    	LogDebugMessage("Will move element from (%d,%d) to (%d,%d)".formatted(cx,cy,newX,newY));
 
        // Create an Actions object to perform drag and drop
        Actions actions = new Actions(driver);
        
        // Drag the element by 100 pixels to the right and 50 pixels down
        actions.clickAndHold(element)
               .moveByOffset(px, py)  // Move the element by px to the right and py down
               .release()              // Release the element
               .perform();
        
        LogDebugMessage("Moved Element to %d,%d".formatted(newX,newY));
    	
    }
    
    
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            // Try to find the element
            WebElement element = driver.findElement(locator);
            return element != null; // If found, return true
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false; // If not found, return false
        }
    }
    
    public static boolean clickElement(WebDriver driver,By locator,int mseconds){
    	Actions actions = new Actions(driver);
    	try {
    		//if(!isElementPresent(driver,locator)) {
    		//	return false;
    		//}

    		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(mseconds/1000));
    		 WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    		 
             // Perform the click action
             actions.moveToElement(element).perform();
             //System.out.println("Moved to element");
    		
    		
    	}catch(Exception e) {
			LogWarningMessage("Exception on waiting elemnent to be visible before clicking: ");
			LogWarningMessage(e.getMessage());
    		return false;
    	}
    	
    	try {
   		 	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(mseconds/1000));
   		 	WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
    		
   		 	element.click();
    		//actions.click().perform();
 			
    		//System.out.println("Clicked element");
    		
    	}catch(Exception e) {
    		LogWarningMessage("Exception on clicking element after being present");
    		LogWarningMessage(e.getMessage());
    		return false;
    	}
    	
    	return true;
    }
    
    public static boolean isElementVisible(WebDriver driver,By locator,int mseconds){
    	Actions actions = new Actions(driver);
    	try {

    		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(mseconds/1000));
    		 WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    		
    	}catch(Exception e) {
    		LogWarningMessage("Exception on waiting element to be visible: ");
    		LogWarningMessage(e.getMessage());
    		return false;
    	}
    	
    	return true;
    }
    
    
    
    
	@AfterAll
	@DisplayName("Quitting Driver")
	public void closeApp() {
		LogInfoMessage("Quitting %s driver".formatted(driverType));
		driver.quit();
	}
	
	@BeforeEach
	void logTestStart(TestInfo testInfo) {
		String methodName = testInfo.getTestMethod().orElseThrow().getName();
		LogInfoMessage("\n\nSTARTING test method: " + methodName);
	}
	
	@AfterEach
	void logTestEnd(TestInfo testInfo) {
		String methodName = testInfo.getTestMethod().orElseThrow().getName();
		LogInfoMessage("END test method: " + methodName);
	}
    
    
}
