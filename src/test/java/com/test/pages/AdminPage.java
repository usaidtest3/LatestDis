package com.test.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.AdminTestData;
import com.test.utils.Constant;

public class AdminPage extends FlowMethods{

	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();

	
	public void validateMyProfileDetails()
	{
		String locator = "", msg = null;
		try
		{
			actionMethods.waitFor();
			if(actionMethods.isElementPresentOptional(locator))
				System.out.println("Page loading completed");
				
			locator = objectRepo.getProperty("Admin.FirstName");
			if(actionMethods.getText(locator).trim().contains(AdminTestData.FIRSTNMAE))
			{
				locator = objectRepo.getProperty("Admin.Lastname");
				if(actionMethods.getText(locator).trim().contains(AdminTestData.LASTNAME)) 
				{
					locator = objectRepo.getProperty("Admin.Email");
					if(actionMethods.getText(locator).trim().contains(AdminTestData.EMAIL))
					{
						locator = objectRepo.getProperty("Admin.Status");
						if(actionMethods.getText(locator).trim().contains(AdminTestData.STATUS))
						{
							locator = objectRepo.getProperty("Admin.AuthenticatedBy");
							if(actionMethods.getText(locator).trim().contains(AdminTestData.AUTHENTICATE_BY))
							{
								System.out.println("All validation done successfully!");
							} else
								msg = "Authentication field failed";
						} else
							msg = "STatus Field failed";
					} else
						msg = "Email filed failed";
				} else
					msg = "Kast name failed";
			} else
				msg = "First name failed : "+actionMethods.getText(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"My profile details validated successfully ", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
				
		}
	}
	
	public void navigatToAdminPage()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Admin.Usename");
			actionMethods.click(locator);
			
			locator = objectRepo.getProperty("Admin.MyProfile");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Navigated to Amdmin screen", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void validateAdminRoles(String adminRoles)
	{
		String locator = "", msg = "";
		try
		{
			List<String> roles = Arrays.asList(adminRoles.split("/"));
			locator = objectRepo.getProperty("Admin.Roles");
			List<WebElement> ele = actionMethods.getListOfdata(locator);
			
			for(int i= 0;i< ele.size()-1;i++)
			{
				if(ele.get(i).getText().contains(roles.get(i)))
				{
					msg = "Expected message : "+ele.get(i).getText()+" <> Actual message : "+roles.get(i);
				} else
				{
					Constant.statusFlag = "Failed";
					msg = "Expected message : "+ele.get(i).getText()+" <> Actual message : "+roles.get(i);
				}
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Admin roles matched successfully", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
				
		}
	}
	
	public void selectdminRolesAndAddRoles(String adminRoles)
	{
		String locator = "", msg = "";
		try
		{
			List<String> roles = Arrays.asList(adminRoles.split("/"));
			locator = objectRepo.getProperty("Admin.Roles");
			List<WebElement> ele = actionMethods.getListOfdata(locator);
			
			for(int i= 0;i< ele.size()-1;i++)
			{
				if(ele.get(i).getText().contains(roles.get(i)))
				{
					msg = "Expected message : "+ele.get(i).getText()+" <> Actual message : "+roles.get(i);
					locator = objectRepo.getProperty("Admin.Header");
					if(actionMethods.isElementPresent(locator))
					{
						locator = objectRepo.getProperty("Admin.CancelBtn");
						actionMethods.click(locator);
					} else
					{
						msg = "Add Role header not presented";
						return;
					}
				} else
				{
					Constant.statusFlag = "Failed";
					msg = "Expected message : "+ele.get(i).getText()+" <> Actual message : "+roles.get(i);
				}
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Admin roles matched successfully", driver);
		} catch (Exception e) 
		{
			locator = objectRepo.getProperty("Admin.CacelBtn");
			actionMethods.click(locator);
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);

		}
	}
	
	public void navigatToNonUsaIdEmployee()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Admin.NonUsaIdEmployee");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Navigated to NonUsaidEmployee screen", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void navigatToManageUsers()
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Admin.ManageUsers");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Navigated to NonUsaidEmployee screen", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void navigatToaddAnImplementingPartner()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Admin.AddImplementingPartner");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Navigated to NonUsaidEmployee screen", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void enterNonUsaIdEmployeeDetails1(String firstName, String lastName, String email )
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("NonUsaid.FirstName");
			actionMethods.enterInputMandatoryFiled(locator, firstName+actionMethods.getTimeStamp(""));
			
			locator = objectRepo.getProperty("NonUsaid.LastName");
			actionMethods.enterInputMandatoryFiled(locator, lastName+actionMethods.getTimeStamp(""));
			locator = objectRepo.getProperty("NonUsaid.email");
			actionMethods.enterInputMandatoryFiled(locator, email);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Added FirstNmae/LastName/Email: "+firstName+","+lastName+","+email, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void enterNonUsaIdEmployeeDetails2(String positionTiltle, String organization)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("NonUsaId.PositionTitle");
			actionMethods.enterInputMandatoryFiled(locator, positionTiltle);
			
			locator = objectRepo.getProperty("NonUsaid.Organization");
			actionMethods.enterInputMandatoryFiled(locator, organization);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Added Position/Organization : "+positionTiltle+","+organization, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void addAccountExpiryDate(String expDate)
	{
		ProjectsPage project = new ProjectsPage();
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("NonUsaid.DatePicker");
			actionMethods.click(locator);
			project.selectCalanderDate(expDate);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Account expi date added : "+expDate, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void saveImplementingPartnerDetails()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("NonUsaid.SaveBtn");
			actionMethods.click(locator);
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"getScreenShot", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 locator = objectRepo.getProperty("NonUsais.CancelBtn");
			 actionMethods.click(locator);
		}
	}
	
	public void unsavedChanges()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("NonUsais.CancelBtn");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("NonUsaid.UnsavedChanges");
			if(actionMethods.isElementPresent(locator))
			{
				locator = objectRepo.getProperty("NonUsaid.YesBtn");
				actionMethods.click(locator);
			}
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User added details and slected Cancel button", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 locator = objectRepo.getProperty("NonUsaid.NoBtn");
			 actionMethods.click(locator);
		}
	}

}
