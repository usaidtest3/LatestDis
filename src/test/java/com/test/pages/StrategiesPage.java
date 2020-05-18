package com.test.pages;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.utils.Constant;

public class StrategiesPage extends FlowMethods{
	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	
	
	public void navigteToStrategisScreen() {
		try
		{
			actionMethods.waitFor();
			String locator = objectRepo.getProperty("Strategies.Link");
			actionMethods.click(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User navigate to Strategic screen : ", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void selectGoalStatement() {
		try
		{
			String locator = objectRepo.getProperty("Strategies.EditGoal");
			actionMethods.click(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void validateTheGoalStatementFields() {
		String locator= "", msg="";
		try
		{
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			if(actionMethods.isElementPresent(locator))
			{
				locator = objectRepo.getProperty("Strategis.StartDate");
				if(actionMethods.isAlertPresent(locator))
				{
					locator = objectRepo.getProperty("Strategis.EndDate");
					if(actionMethods.isAlertPresent(locator))
					{
					   msg = "Goal settings, start date & end date displayed successfully!"	;
					}
				}
			}
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void editGoalStatement(String goalStmt) {
		String locator= "";
		try
		{
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			actionMethods.enterInputMandatoryFiled(locator, goalStmt);
			locator = objectRepo.getProperty("Strategis.UpdateButton");
			actionMethods.click(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Edited Goal stmt : "+goalStmt, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void discardGoalStatementChanges(String goalStmt, String action) throws Exception {
		String locator= "";
		try
		{
			locator = objectRepo.getProperty("Strategies.EditGoal");
			actionMethods.waitFor();
			if(actionMethods.isElementPresentOptional(locator))
				actionMethods.click(locator);
			locator = objectRepo.getProperty("Strategis.GoalStatement");
			actionMethods.enterInputMandatoryFiled(locator, goalStmt);
			locator = objectRepo.getProperty("Strategis.Cancel");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Strategis.WarningMsg");
			if(actionMethods.isElementPresent(locator))
			{
			if(action.contains("Yes"))
			{
				locator = objectRepo.getProperty("Strategis.YesBtn");
				actionMethods.click(locator);
				afterCancel_YES_NavigateToStrategisScreen();
			}else
			{
				System.out.println("User has selected NO option");
				locator = objectRepo.getProperty("Strategis.NoBtn");
				actionMethods.click(locator);
				validateTheGoalStatementFields();
				locator = objectRepo.getProperty("Strategis.UpdateButton");
				actionMethods.click(locator);
			} 
			} else
			{
				System.out.println("Warning message not displayed");
			}
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Edited Goal stmt : "+goalStmt+"User selected : "+action, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User selected : "+action, driver);
			 throw e;
		}
	}
	
	public void afterCancel_YES_NavigateToStrategisScreen() throws Exception {
		String locator= "";
		try
		{
			Thread.sleep(3000);
			locator = objectRepo.getProperty("Strategies.Header");
			actionMethods.isElementPresent(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"After select 'Yes' user navigated to 'Strategis' screen", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void uploadStrategiesDocument()
	{
		String locator = "";
		try
		{
			
			locator = objectRepo.getProperty("Strategies.UploadDocuments");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Strategies.ChooseFile");
			locator = "//span[contains(@class,'ui-button ui-fileupload-choose')]~Xpath";
			actionMethods.click(locator);
		
		//	String path = System.getProperty(("user.dir"));
			String userName = System.getProperty("user.name");
			actionMethods.uploadFile("C:\\Users\\"+userName+"\\Desktop\\24-06-2019-cab.pdf");
			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"FileUploaded : ", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	
	public void autoIt(String fileName) throws IOException
	{
		String fileLocation = System.getProperty("user.dir")+"\\src\\test\\java\\uploadDocuments\\"+fileName;
		fileLocation = fileLocation.replace("\\", "\\\\");
		String fileToUpload;
		
		fileToUpload = fileLocation.replaceAll("//", Matcher.quoteReplacement("\\"));
		StringSelection filePath = new StringSelection(fileToUpload);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePath, null);
		if(Constant.browser.equalsIgnoreCase("Chrome"))
		{
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\test\\java\\uploadDocuments\\fileUpload.exe "+fileToUpload);
		}
		
	}

}
