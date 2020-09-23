package sdet.appium.org.AppiumTestProject;

import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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

// Application: Google Tasks
//1.Goal: Use the Google Tasks app to create a list of activities that need to be completed.
public class CreateAddNewTask {
	WebDriverWait wait;
	AppiumDriver<MobileElement> driver = null;

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		// Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "Xiaomi Redmi Y3");
		caps.setCapability("platformName", "android");
		caps.setCapability("appPackage", "com.google.android.apps.tasks");
		caps.setCapability("appActivity", ".ui.TaskListsActivity");
		caps.setCapability("noReset", true);

		// Instantiate Appium Driver
		URL appServer = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(appServer, caps);
		wait = new WebDriverWait(driver, 10);
	}


	@Test
	public void CreateNewNoteAndVerify() {
		//Adding new task
		addTasks("Complete Activity with Google Tasks");
		addTasks("Complete Activity with Google Keep");
		addTasks("Complete the second Activity Google Keep");
		
		//add assertion all tasks are added
		
		assertTrue(driver.findElementByXPath("//android.widget.FrameLayout[@content-desc=\"Complete the second Activity Google Keep\"]/android.widget.LinearLayout/android.widget.TextView").isDisplayed());
		assertTrue(driver.findElementByXPath("//android.widget.FrameLayout[@content-desc=\"Complete Activity with Google Keep\"]/android.widget.LinearLayout/android.widget.TextView").isDisplayed());
		assertTrue(driver.findElementByXPath("//android.widget.FrameLayout[@content-desc=\"Complete Activity with Google Tasks\"]/android.widget.LinearLayout/android.widget.TextView").isDisplayed());
		
	
	}

	//Adding new task
	public void addTasks(String task) {
		driver.findElementById("tasks_fab").click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("add_task_title"))).sendKeys(task);
		driver.findElementById("add_task_done").click();  //click Save 

	}
	  @AfterClass
	    public void afterClass() {
	        driver.quit();
	    }
}
