package com.auto.tests;

import org.testng.annotations.Test;

import com.auto.base.BasePage;
import com.auto.base.TestCore;
import com.auto.pageobjects.HomePageObject;
import com.auto.pages.HomePage;

public class HomePageTest extends TestCore{
	
	BasePage basePage;
	HomePage homePage;
	
	@Test
	public void homePageTest(){
		
		basePage = new BasePage(driver);
		homePage = new HomePage(driver);
		
		homePage.waitForPage();
		homePage.getSearchButtonSize();
		homePage.getSearchButtonColor();
		assertTrue(homePage.verifyColor(HomePageObject.blueColor), "["+HomePageObject.blueColor+"] Color is not matched before mouse hover");
		homePage.mouseHoverOnSearchButton();
		assertTrue(homePage.verifyColor(HomePageObject.blackColor), "["+HomePageObject.blackColor+"] Color is not matched after mouse hover");
		
	}

}
