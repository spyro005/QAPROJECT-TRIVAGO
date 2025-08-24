package com.demo.qa.trivago.pagesTests;

import com.demo.qa.trivago.pages.SearchPage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import com.demo.qa.trivago.BaseClass;
import com.demo.qa.trivago.pages.LoginPage;
import com.demo.qa.trivago.pages.MainPage;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SearchPageTests extends SearchPage {
	
	@Test
	@Order(1)
	public void priceMinTest(){

		SearchPage.moveLeftSlider(this.driver,10);
		SearchPage.hitApplyPriceFilter(this.driver);
		int min = SearchPage.getMinPrice(this.driver);
		LogDebugMessage("Found min price: %d".formatted(min));
		
		ArrayList<Integer> ps = SearchPage.getMainPrices(driver);
		for(int p: ps) {
			//LogDebugMessage("MIN Found price: %d".formatted(p));
			if (p < min) {
				LogTestCaseFail("Price %d is below minimum allowed %d".formatted(p,min));
				
			}
		}
    }
	
	@Test
	@Order(2)
	public void priceMaxTest(){

		SearchPage.moveRightSlider(this.driver,-10);
		SearchPage.hitApplyPriceFilter(this.driver);
		int max = SearchPage.getMaxPrice(this.driver);
		LogDebugMessage("Found max price %d".formatted(max));
		
		ArrayList<Integer> ps = SearchPage.getMainPrices(driver);
		for(int p: ps) {
			//LogDebugMessage("MAX Found price: %d".formatted(p));
			if (p > max) {
				LogTestCaseFail("Price %d is above max allowed %d".formatted(p,max));
				
			}
		}
    }
	
	/*
	@Test
	@Order(3)
	public void priceMinMaxTest(){

		SearchPage.moveLeftSlider(this.driver,10);
		SearchPage.hitApplyPriceFilter(this.driver);
		SearchPage.moveRightSlider(this.driver,-10);
		SearchPage.hitApplyPriceFilter(this.driver);
		
		int min = SearchPage.getMinPrice(this.driver);
		int max = SearchPage.getMaxPrice(this.driver);
		

		LogDebugMessage("Found min, max prices: %d,%d".formatted(min,max));
		

		ArrayList<Integer> ps = SearchPage.getMainPrices(driver);
		for(int p: ps) {
			LogDebugMessage("mIN MAX Found price: %d".formatted(p));
			if (p > max || p < min) {
				LogTestCaseFail("Price %d is not between min and max allowed %d, %d".formatted(p,min,max));
				
			}
		}
    }
    */

}
