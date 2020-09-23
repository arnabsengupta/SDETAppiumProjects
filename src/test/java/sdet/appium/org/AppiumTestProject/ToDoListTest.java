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


// Application: Google Chrome
//1. Goal: Opening a page on the browser and testing a to-do list page
public class ToDoListTest {


	WebDriverWait wait;
	AppiumDriver<MobileElement> driver = null;

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

	@Test
	public void AddStrickOutClearTask() {
		// Wait for the page to load
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.View")));

		// Scroll element into view and click it
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(3)"));
		driver.findElement(MobileBy.AndroidUIAutomator("UiScrollable(UiSelector()).scrollIntoView(text(\"To-Do List\"))")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Adding tasks
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//android.widget.EditText[@index='0']"))).sendKeys("Add tasks to list");
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add Task\")")).click();

		driver.findElementByXPath("//android.widget.EditText[@index='0']").sendKeys("Get number of tasks");
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add Task\")")).click();

		driver.findElementByXPath("//android.widget.EditText[@index='0']").sendKeys("Clear the list");
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add Task\")")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		//Assert tasks are added
		assertTrue(driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add tasks to list\")")).isDisplayed());
		assertTrue(driver.findElement(MobileBy.AndroidUIAutomator("text(\"Clear the list\")")).isDisplayed());
		assertTrue(driver.findElement(MobileBy.AndroidUIAutomator("text(\"Get number of tasks\")")).isDisplayed());

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Strikeout tasks

		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add tasks to list\")")).click();
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Clear the list\")")).click();
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Get number of tasks\")")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		assertTrue(driver.findElementByXPath("//android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[2]").isDisplayed());
		//Clear list
		//driver.findElement(MobileBy.AndroidUIAutomator("text(\"Clear List\")")).click();
		driver.findElementByXPath("//android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[3]").click();

		//Assert list is cleared

		//assertFalse(driver.findElementById("tasksList").isDisplayed());
		assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[2]"))));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
