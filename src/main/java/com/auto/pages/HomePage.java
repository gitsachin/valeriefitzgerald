package com.auto.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.auto.base.BasePage;
import com.auto.base.ILogLevel;
import com.auto.pageobjects.HomePageObject;

public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	String hex = "";
	
	public void getSearchButtonSize(){
		Dimension buttonSize = driver.findElement(By.cssSelector(HomePageObject.searchButton_css)).getSize();
		log("Search button size: "+buttonSize,ILogLevel.TEST);
	}
	
	public void getSearchButtonColor(){
		WebElement ele = driver.findElement(By.cssSelector(HomePageObject.searchButton_css));
		String colorRGB = ((JavascriptExecutor)driver)
                .executeScript("return window.getComputedStyle(arguments[0], ':after').getPropertyValue('background-color');",ele).toString();
		 convertInHexa("before",colorRGB);
	}
	
	
	public boolean waitForPage() {
		waitForElementDisplayed(By.cssSelector(HomePageObject.searchButton_css));
		boolean element = driver.findElement(By.cssSelector(HomePageObject.searchButton_css)).isEnabled() && isElementPresent(By.cssSelector(HomePageObject.searchButton_css));
		if(element){
			pause(8); // wait for page load
			return true;
		}
		else{
			return false;
		}
        
    }
	
	public void convertInHexa(String _str,String _color){
		
		String[] numbers = _color.replace("rgb(", "").replace(")", "").split(",");
		int r = Integer.parseInt(numbers[0].trim());
		int g = Integer.parseInt(numbers[1].trim());
		int b = Integer.parseInt(numbers[2].trim());
		hex = "#" + Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
		log("Search button color "+ _str +" mousehover: "+hex,ILogLevel.TEST);
	}
	
	public void mouseHoverOnSearchButton(){
		
		Actions action = new Actions(driver);
		WebElement ele = driver.findElement(By.cssSelector(HomePageObject.searchButton_css));
		action.moveToElement(ele).build().perform();
		pause(7);// wait for color changed
        log("mousehover on search button: ",ILogLevel.TEST);
        String colorRGB = ((JavascriptExecutor)driver)
                .executeScript("return window.getComputedStyle(arguments[0], ':before').getPropertyValue('background-color');",ele).toString();
        convertInHexa("after",colorRGB);
       
	}
	
	public boolean verifyColor(String _hex){
		
		if(hex.equals(_hex)){
			return true;
		}
		else{
			return false;
		}
	}

}
