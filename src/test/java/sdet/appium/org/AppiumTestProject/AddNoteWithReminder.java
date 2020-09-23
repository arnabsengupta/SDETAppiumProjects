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

public class AddNoteWithReminder {

	// Application: Google Keep
	//Goal: Use the Google Keep app to add a note with a reminder

	WebDriverWait wait;
	AppiumDriver<MobileElement> driver = null;

	@BeforeClass
	public void beforeClass() throws MalformedURLException {
		// Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("deviceName", "Xiaomi Redmi Y3");
		caps.setCapability("platformName", "android");
		caps.setCapability("appPackage", "com.google.android.keep");
		caps.setCapability("appActivity", ".activities.BrowseActivity");
		caps.setCapability("noReset", true);

		// Instantiate Appium Driver
		URL appServer = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(appServer, caps);
		wait = new WebDriverWait(driver, 10);
	}

	@Test
	public void CreateNewNoteAndVerify() {
		//Adding new note
		driver.findElementById("new_note_button").click();
		driver.findElementById("editable_title").sendKeys("Adding a note with description");
		driver.findElementById("edit_note_text").sendKeys("Adding a note with description");
	//	driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]").click();

		//Add reminder
		driver.findElementById("menu_switch_to_grid_view").click();
		driver.findElementById("time_spinner").click();
		wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("reminder_time_afternoon"))).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElementById("save").click();
		driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]").click();  //click Save

		//an assertion to ensure that the note was added with a reminder
		assertTrue(driver.findElementByXPath("//androidx.cardview.widget.CardView[@content-desc=\"Adding a note with description. Adding a note with description. \"]/android.widget.LinearLayout/android.widget.LinearLayout").isDisplayed());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
