package com.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class K2 {

	public static WebDriver driver;
	public static WebDriver chromeDriver;
	public static WebDriver launchChrome(String driverPath,String appURL)
	{
		System.out.println("driverPath"+driverPath);
		chromeDriver = FlowMethods.driver;
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/lib/chromedriver");
		System.setProperty("webdriver.chrome.driver", driverPath);
		Map<String, Object> prefs = new HashMap<String, Object>();

		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions co = new ChromeOptions();
		co.setExperimentalOption("prefs", prefs);
		co.addArguments("disable-extensions");
		co.addArguments("--disable-popup-blocking");
		//co.setPageLoadStrategy(PageLoadStrategy.NONE);
		co.addArguments("javascript.enabled");
		co.setExperimentalOption("useAutomationExtension", false);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, co);
		// To set download path
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		co.setExperimentalOption("prefs", chromePrefs);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, co);
		chromeDriver = new ChromeDriver(co);
		chromeDriver.manage().deleteAllCookies();

		chromeDriver.get(appURL);
		driver = chromeDriver;
		return driver;
	}
	
}
