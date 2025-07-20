package com.demo.qa.trivago;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
//import java.util.logging.ConsoleHandler;
//import java.util.logging.FileHandler;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseClass{
	
	protected WebDriver driver;

	
	protected String driverType ="";
	
	public static Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME); //Logger.getLogger(BaseClass.class.getName());
	
	
	public String getDriverTypeFromCMD() {
        // Access command-line arguments
        String[] args = System.getProperty("driver.args") != null 
                ? System.getProperty("driver.args").split(",") 
                : new String[]{};

        String browser = args.length > 0 ? args[0] : "firefox"; // Default to firefox
        
        return browser;

	}
	
	
	@DisplayName("Setting up Logger")
	public void setupLogger() {

		LogInfoMessage("Setting up Logger...");

		try {

			// Create a dynamic timestamp for the log file name
	        String timestamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
	        String logFileName = "target/logs/test-log-" + timestamp + ".txt";  // Example: test-log-2025-03-10-14-25-30.txt

	        // Get the existing FileAppender
	        FileAppender fileAppender = (FileAppender) logger.getAppender("FILE");
	        if (fileAppender != null) {
	            // Set the file name dynamically
	            fileAppender.setFile(logFileName);

	            // Initialize the encoder (Pattern Layout)
	            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
	            encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} [%highlight(%level)] - %msg%n");
	            encoder.setContext(logger.getLoggerContext());
	            encoder.start();

	            // Set the encoder to the appender
	            fileAppender.setEncoder(encoder);
	            fileAppender.start();

	        } else {
	            System.err.println("FileAppender not found. Make sure it's defined in logback.xml.");
	        }
		} catch (Exception e) {
			System.err.println("Exception setting up logger: %s \n".formatted(e.getMessage()));
		} 
    
	}
	
	@DisplayName("Setting up Driver")
	public void setupDriver() throws Exception {
		
		String browser = this.getDriverTypeFromCMD();
		driverType = browser.toLowerCase();
		
		LogInfoMessage("Setting up %s driver".formatted(driverType));
		
		
        switch (driverType) {
            case "safari":
                driver = new SafariDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
            	driver = new FirefoxDriver();
            	break;
            default:
            	LogTestCaseFail("Driver argument must be empty or one of 'chrome', 'firefox', 'safari'. Instead"
            			+ "got %s ".formatted(browser));
            	throw new Exception("No driver argument specified correctly");
        }
	}
	
	
	
	protected String getCurrentTestName() {
		return new Throwable().getStackTrace()[1].getMethodName();
	}
	
	// Helper method to get the current test class name
	protected String getTestClassName() {
        // You can use reflection to dynamically get the class name
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }
	
	public static void LogTestCasePass(String passMessage) {
		LogInfoMessage("TEST CASE PASSED: %s ".formatted(passMessage));
		assertTrue(true,passMessage);
	}
	
	public static void LogTestCaseFail(String failMessage) {
		//LogInfoMessage(failMessage);
		LogError("TEST CASE FAILED: %s ".formatted(failMessage));
		assertTrue(false,failMessage);
	}
	
	public static void LogInfoMessage(String infoMessage) {
		//logger.info("\u001B[30m" + infoMessage+ "\u001B[0m");
		logger.info(infoMessage);
	}
	
	public static void LogDebugMessage(String debugMessage) {
		logger.debug(debugMessage);
	}
	
	public static void LogWarningMessage(String wMessage) {
		logger.warn(wMessage );
	}
	
	public static void LogError(String criticalError) {
		logger.error("ERROR: %s ".formatted(criticalError));
	}
	
	
    public static String generateRandomString(int length) {
    	final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
    
    
	
}
