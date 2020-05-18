package com.test.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.OverviewTestData;
import com.test.utils.Constant;

public class OverviewPage extends FlowMethods{
	
	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	public static String officeTitle = "";
	public void createNewOffice(String offName,String offAcrynum, String date, String offDescr)
	{
		String locator = "";
		try
		{
			locator  = objectRepo.getProperty("Overview.Tital");
			if(actionMethods.isElementPresent(locator))
			{
				locator  = objectRepo.getProperty("Overview.OfficeBtn");
				actionMethods.click(locator);
				locator  = objectRepo.getProperty("Overview.CrateOffice");
				actionMethods.click(locator);
				actionMethods.isAlertPresent("accept");
				
				locator  = objectRepo.getProperty("Overview.CreateOfficeTital");
				if(actionMethods.isElementPresent(locator))
				{
					locator  = objectRepo.getProperty("Overview.OffoceName");
					enterOfficeName(locator,offName);
					actionMethods.enterInputMandatoryFiled(locator, officeTitle);
					actionMethods.waitFor();

					locator  = objectRepo.getProperty("Overview.OfficeAcronyam");
					actionMethods.enterInputMandatoryFiled(locator, offAcrynum+actionMethods.genarateRandomNumber(5));
					actionMethods.waitFor();
//TODO >> Add calendar logic

					locator  = objectRepo.getProperty("OverView.OffceDescription");
					actionMethods.enterInputMandatoryFiled(locator, offDescr);
					locator  = objectRepo.getProperty("OverView.OffceSaveBtn");
					actionMethods.click(locator);
					
				} else
					log.info("CreateOfficeTital : is not displayed");
				
			} else
				log.info("Tital : is not presnted");
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
            Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			
		}
	}
	
	public void enterOfficeName(String locator,String offName) {
		try
		{
			officeTitle = offName+actionMethods.genarateRandomNumber(5);
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"OfficeName : "+offName, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
            throw e;
		}
	}
	
	public void validateTheNewOffice()
	{
		String locator;
		try
		{
			locator  = objectRepo.getProperty("Overview.SearchOffice");
			actionMethods.enterInputMandatoryFiled(locator, officeTitle);
			locator  = objectRepo.getProperty("Overview.Text");
			if(actionMethods.isElementPresent(locator.replace("Dummy", officeTitle)))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
			} else
				log.info(officeTitle+" : not present");
		}catch (Exception e) {
            Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
		
	}
	
	public void navigateToHomeScreen() 
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Header.Home");
			if(actionMethods.isElementPresentOptional(locator))
			{
				//actionMethods.scrollToElement(locator);
				driver.findElement(By.xpath(locator.split("~")[0])).click();
			} else
				System.out.println("Home button not presented");
		}catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
		
	}

}
