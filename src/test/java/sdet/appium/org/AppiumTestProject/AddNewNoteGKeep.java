package sdet.appium.org.AppiumTestProject;

import static org.testng.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;


// Application: Google Keep
//2.Goal: Use the Google Keep app to add a note.

public class AddNewNoteGKeep {
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
	}


	@Test
	public void CreateNewNoteAndVerify() {
		//Adding new note
		driver.findElementById("new_note_button").click();
		driver.findElementById("editable_title").sendKeys("My First note in google keep!");
		driver.findElementById("edit_note_text").sendKeys("My First note in google keep!");
		driver.findElementByXPath("//android.widget.ImageButton[@content-desc=\"Open navigation drawer\"]").click();

		//assertion to ensure all three tasks have been added to the list.
		assertTrue(driver.findElementByXPath("//androidx.cardview.widget.CardView[@content-desc=\"My First note in google keep!. My First note in google keep!. \"]/android.widget.LinearLayout").isDisplayed());


	}
	
	  @AfterClass
	    public void afterClass() {
	        driver.quit();
	    }
}
