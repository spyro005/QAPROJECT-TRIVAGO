package com.demo.qa.trivago.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.platform.commons.logging.Logger;

import com.demo.qa.trivago.BaseClass;
import com.demo.qa.trivago.pages.BasePage;
import com.demo.qa.trivago.pages.MainPage;

public class LoginPage {
	
	private BasePage bp = new BasePage();
	
	static String email = "qauser13@protonmail.com";
	
	static private String password = "trivagoTEST1#";
	
	static String emailLoginURL = "https://auth.trivago.com/en-US";
	
	static String passwordLoginURL = "https://auth.trivago.com/en-US/login";
	
	static String emailInputXpath = "//input[@name='email']";
	
	static String passwordInputXpath = "//input[@name='password']";
	
	static String continueButtonXpath = "//button[@type='submit' and contains(text(),'Continue')]";
	
	static String loginButtonXpath = "//button[@type='submit' and contains(text(),'Log in')]";
	
	public static boolean login(WebDriver driver){
		
		try {
			boolean clickedBox = BasePage.clickElement(driver, By.xpath(emailInputXpath), 2000);
			BaseClass.LogDebugMessage("Clicked email box: %b \n with xpath %s".formatted(clickedBox,emailInputXpath));
			WebElement emailBox = driver.findElement(By.xpath(emailInputXpath));
			
			emailBox.sendKeys(email);
			
			boolean clickedContinue = BasePage.clickElement(driver, By.xpath(continueButtonXpath), 2000);
			BaseClass.LogDebugMessage("Clicked continue box: %b \n with xpath %s".formatted(clickedContinue,continueButtonXpath));
			
			boolean wpp = waitPPageLoad(driver,5);
			BaseClass.LogDebugMessage("Waited password page: %b".formatted(wpp));

			
			boolean clickedPwdBox = BasePage.clickElement(driver, By.xpath(passwordInputXpath), 2000);
			BaseClass.LogDebugMessage("Clicked password box: %b \n with xpath %s".formatted(clickedPwdBox, clickedPwdBox));
			WebElement pwdBox = driver.findElement(By.xpath(passwordInputXpath));
			
			pwdBox.sendKeys(password);
			
			boolean clickedLogin = BasePage.clickElement(driver, By.xpath(loginButtonXpath), 2000);
			BaseClass.LogDebugMessage("Clicked password box: %b \n with xpath %s".formatted(clickedLogin, loginButtonXpath));
			
			return clickedLogin;
		}catch(Exception e) {
			BaseClass.LogError("Exception while attempting to login:\n %s".formatted(e.getMessage()));
			return false;
			
		}
		
		
	}
	
	public static boolean waitEPageLoad(WebDriver driver,int s) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(s));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailInputXpath)));
			return true;
		}catch(Exception e) {
			//System.out.println("Exception in waiting email page");
			return false;
		}
	}
	
	public static boolean waitPPageLoad(WebDriver driver,int s) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(s));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(passwordInputXpath)));
			return true;
		}catch(Exception e) {
			
			//System.out.println("Exception in waiting Password page");
			return false;
		}
	}
	
	public static boolean verifyEmailLoginPage(WebDriver driver) {
		return driver.getCurrentUrl().contentEquals(emailLoginURL);
	}
	
	public static boolean verifyPasswordLoginPage(WebDriver driver) {
		return driver.getCurrentUrl().contentEquals(passwordLoginURL);
	}
	
	
	

}
