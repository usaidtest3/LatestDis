package com.test.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.K2TestData;
import com.test.testData.TestDataObjectLogin;
import com.test.utils.Constant;

public class K2WindowsSTSPage extends FlowMethods{

	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	
	public void navigateToK2Application(String appName)
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			if(appName.equalsIgnoreCase("K2_Windows"))
			{
			 locator = objectRepo.getProperty("K2.WindowsSTS");
			 actionMethods.mouseHoverAction(locator);
			 actionMethods.click(locator);
			} else
			{
			 locator = objectRepo.getProperty("K2.USAIDAzure");
			 actionMethods.mouseHoverAction(locator);
			 actionMethods.click(locator);
			}
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User selected Tab : "+appName, driver);
		} catch(Exception e)
		{
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectTravelREquest()
	{
		String locator;
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("K2.EditOption");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User selected TravelREquest option : ", driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectTravelType(String travelTYpe)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.TravelType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("K2.TravelTYpeDropdown");
			actionMethods.selectDropDownData(locator, travelTYpe);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"TravelTYpe : "+travelTYpe, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectDEstCountry(String destCountry)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.DEstinationCountry");
			actionMethods.enterInputMandatoryFiled(locator, destCountry);
			locator = objectRepo.getProperty("K2.CountrySerach");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"destCountry : "+destCountry, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectStartDate(String startDate)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.StartDate");
			String date = actionMethods.getTheFutureDate(Integer.parseInt(startDate), "MM/dd/YYYY");
			actionMethods.enterInputMandatoryFiled(locator, date);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"StartDate : "+date, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectEndDate(String endDate)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.ReturnDate");
			String date = actionMethods.getTheFutureDate(Integer.parseInt(endDate), "MM/dd/YYYY");
			actionMethods.enterInputMandatoryFiled(locator, date);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"EndDate : "+date, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void enterJustification(String justification)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.Justification");
			actionMethods.enterInputMandatoryFiled(locator, justification);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Justification : "+justification, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void enterTotalCost(String cost)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.TotalCost");
			actionMethods.enterInputMandatoryFiled(locator, cost);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"TotalCost : "+cost, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void enterUniqName(String uniqName)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.UniqueName");
			actionMethods.enterInputMandatoryFiled(locator, uniqName);
			locator = objectRepo.getProperty("K2.UniqueNameSearch");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"uniqName : "+uniqName, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void enterComments(String commnets)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.Comment");
			actionMethods.enterInputMandatoryFiled(locator, commnets);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Comments : "+commnets, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectAction(String action)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("K2.TRavelSubmit");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void loginIntoUSAIdAzureId(String username, String password)
	{
		String locator;
		LoginPage login = new LoginPage();
		try
		{
			locator = objectRepo.getProperty("K2.EnterusaIdEmail");
			actionMethods.enterInputMandatoryFiled(locator, username);
			locator = objectRepo.getProperty("K2.NextBtn");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("K2.EnterUsaIdPwd");
			actionMethods.enterInputMandatoryFiled(locator, password);
			locator = objectRepo.getProperty("K2.SignInBtn");
			actionMethods.click(locator);
			
			actionMethods.launchChrome();
			//	actionMethods.launchURsL("");
				String gmailLoginDetails = K2TestData.GMAILLOGINDETAILS;
				login.loginGmailAccount(gmailLoginDetails.split("/")[0],gmailLoginDetails.split("/")[1].split("~")[0]);
				String  s = getOtP();
			  actionMethods.closeWindow();
			  locator = objectRepo.getProperty("K2.EnterCode");
			   actionMethods.enterInputMandatoryFiled(locator, s);
			  
			   locator = objectRepo.getProperty("K2.VerifyBtn");
			   actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch(Exception e)
		{
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	/*
	 * public String getOtp() { String locator, getOTP = null; try {
	 * actionMethods.waitFor(); locator =
	 * objectRepo.getProperty("Gmail.selectMail"); actionMethods.click(locator);
	 * actionMethods.waitFor(); locator = objectRepo.getProperty("K2.GetOTP");
	 * getOTP = actionMethods.getText(locator);
	 * System.out.println("Login OTP : "+getOTP.split(" ")[0]);
	 * Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1]
	 * .getMethodName(),Constant.statusFlag+getOTP, driver); } catch (Exception e) {
	 * Constant.statusFlag = "Failed";
	 * Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1]
	 * .getMethodName(), Constant.statusFlag, driver); } return getOTP.trim(); }
	 */
	
	public String getOtP()
	   {
		   String locator, getOTP = "";
		   try
		   {
			   actionMethods.waitFor();
			   locator = objectRepo.getProperty("Gmail.selectMail");
			   actionMethods.click(locator);
			   actionMethods.waitFor();
			   locator = objectRepo.getProperty("K2.GetOTP");
			   getOTP = actionMethods.getText(locator);
			   System.out.println("Login OTP : "+getOTP.substring(0, 6));
			   Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),Constant.statusFlag+getOTP, driver);
		   } catch (Exception e) {
			   log.error("Exception Occured at getOtP : "+e.getMessage());
			   Constant.statusFlag = "Failed";
			   Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(),Constant.statusFlag, driver);
			   throw e;
		}
		return getOTP.substring(0, 6);
	   }
}









