package samplePackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;

public class habitatUITest {
	
	public static String countryCode = "GB";
	public static WebDriver driver;
	static By country = By.cssSelector("polygon[data-code='" + countryCode +"']");

	// This method is to launch Chrome browser, URL and accept cookies button on the cookie pop up 
	public static void launchURL() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "./Selenium/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.epexspot.com/en/market-data");
		driver.manage().window().maximize();
		Thread.sleep(2000);
		WebElement clickButton = driver.findElement(By.id("edit-acceptationbutton"));
		clickButton.click();
		Thread.sleep(2000);
		WebElement acceptCookie = driver.findElement(By.cssSelector("button[class='btn agree-button eu-cookie-compliance-default-button']"));
		acceptCookie.click();
		Thread.sleep(5000);
	}
	
		
	// This method is to check if the country has price taged to it and the country color in the map
	public static void scenario1_getCountryandPrice() throws InterruptedException {	
			Thread.sleep(3000);
		String countryValue = driver.findElement(country).getAttribute("class");
		if(countryValue.contains("has-value")) {
		System.out.println("Country has price values : " + countryValue);
		} else {System.out.println("ERROR: Country does not price values : " + countryValue);}
		
		String colorValue = driver.findElement(country).getCssValue("fill");
		String colorformat = Color.fromString(colorValue).asHex();
		System.out.println("Color format is : " + colorformat);
		}
	
	/* This method is to apply filter by selecting continuous button - We will call scenario1 again 
	  to check if the country color and price is changed on the map after applying filter */
	public static void scenario2_changeFilterOption() throws InterruptedException {	
		WebElement continuousButton = driver.findElement(By.xpath("//*[contains(text(), 'Continuous')]"));
		continuousButton.click();
		System.out.println("Filter is applied by selecting Continuous button option");
		
		}
	// This method is to click on the country link on the map and check if its navigating to detailed page
	public static void scenario3_clickCountryImageLink() throws InterruptedException {	
		driver.findElement(By.cssSelector("polygon[data-code='FR']")).click();
		System.out.println("Country image is clicked");
		Thread.sleep(3000);
		WebElement label = driver.findElement(By.xpath("//*[@class=\"table-container\"]/h2"));
		String labelValue = label.getText();
		
		if(labelValue.contains("FR")) {
			System.out.println("Country Link on the map is clicked and navigated to detailed page");	
		}else {System.out.println("ERROR: Country Link on the map is not clicked. Kindly check");}
		}
	
	// Closing the driver
	public static void closeBrowser() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();	
		}
		

	public static void main (String[] args) throws InterruptedException{
		launchURL();
		scenario1_getCountryandPrice();
		scenario2_changeFilterOption();
		scenario1_getCountryandPrice();
		scenario3_clickCountryImageLink();
		closeBrowser();

		
	}
}
