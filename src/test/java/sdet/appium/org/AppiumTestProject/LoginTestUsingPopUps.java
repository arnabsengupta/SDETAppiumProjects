package sdet.appium.org.AppiumTestProject;

import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

//Application: Google Chrome

//3.Goal: Opening a page on the browser and testing a simple login page with correct and incorrect credentials
public class LoginTestUsingPopUps {


	WebDriverWait wait;
	AppiumDriver<MobileElement> driver = null;

	//Valid credentials
	String validUserName = "admin";
	String validPassword = "password";

	//Invalid Credentials
	String invalidUserName = "lkkkk";
	String invalidPassword = "lklklk";


	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		// Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "Xiaomi Redmi Y3");
		caps.setCapability("platformName", "Android");
		caps.setCapability("appPackage", "com.android.chrome");
		caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		caps.setCapability("noReset", true);

		// Instantiate Appium Driver
		URL appServer = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(appServer, caps);
		wait = new WebDriverWait(driver, 10);

		// Open page
		driver.get("https://www.training-support.net/selenium");
	}

	/*
	 * Test for invalid credentials
	 */
	@Test
	public void verifyInvalidCredentialsTest() {
		// Wait for the page to load
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.View")));

		// Scroll element into view and click it
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).flingForward()"));
		driver.findElement(MobileBy.AndroidUIAutomator("UiScrollable(UiSelector()).scrollIntoView(text(\"Popups\"))")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//click Sign in button
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Sign In\")")).click();
		//Enter valid credentials

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@index='1']"))).sendKeys(invalidUserName);
		driver.findElementByXPath("//android.widget.EditText[@index='3']").sendKeys(invalidPassword);

		driver.findElementByXPath("//android.widget.Button[@text='Log in']").click();

		assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.view.View[@index='3']"), "Invalid Credentials")));

	}

	/*
	 * Test for valid credentials
	 */
	@Test
	public void verifyValidCredentialsTest() {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//click Sign in button
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Sign In\")")).click();

		//Enter valid credentials
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.EditText[@index='1']"))).sendKeys(validUserName);
		driver.findElementByXPath("//android.widget.EditText[@index='3']").sendKeys(validPassword);

		driver.findElementByXPath("//android.widget.Button[@text='Log in']").click();

		assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//android.view.View[@index='3']"), "Welcome Back, admin")));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
