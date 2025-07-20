package com.demo.qa.trivago.pagesTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import com.demo.qa.trivago.BaseClass;
import com.demo.qa.trivago.pages.SearchPage;
import com.demo.qa.trivago.pages.MainPage;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTests extends MainPage{
	
	
	//@Test
	//public void simpleTest() {
   // 	assertTrue(true,"11");
    //    System.out.println("Simple test is running!");
    //}
	
	//close refresh popup  //button data-testid="idle-notification-close-button"
	
	@Test
	@Order(1)
	public void loginTest(){

		boolean loggedIn = MainPage.login(this.driver);
		LogDebugMessage("Did login: %s".formatted(loggedIn));
		
    }
	
	@Test
	@Order(2)
	public void searchTest(){
		
    	boolean searched = MainPage.search(this.driver);
    	LogDebugMessage("Did search after login: %s".formatted(searched));
    	
    	boolean WS = SearchPage.waitPageLoad(this.driver, 10);
    	LogDebugMessage("Waited after search: %s".formatted(WS));
    	
    }
	

}
