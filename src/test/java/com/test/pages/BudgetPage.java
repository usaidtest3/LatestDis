package com.test.pages;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.BudgetTestData;
import com.test.utils.Constant;

public class BudgetPage extends FlowMethods {
	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	public static String saveReportName = " ";

	public void saveBudgetReport(String reportName)
	{
		String locator;

		try
		{
			locator = objectRepo.getProperty("Budget.Link");
			actionMethods.click(locator);
			actionMethods.isAlertPresent("accept");
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.Office");
			actionMethods.click(locator);
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.RunReportBtn");
			actionMethods.click(locator);
			actionMethods.waitFor();
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.PrimarySaveBtn");
			actionMethods.click(locator);
			enterReportName();

			locator = objectRepo.getProperty("Budget.SaveREportBtn");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag+"ReportName : "+saveReportName, driver);
		} catch(Exception e)
		{
			System.out.println("Exception occured : "+e.getLocalizedMessage());
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	public void enterReportName()
	{
		try
		{
			saveReportName = BudgetTestData.REPORT_NAME+actionMethods.genarateRandomNumber(4);
			actionMethods.waitFor();
			String locator = objectRepo.getProperty("Budget.EnterCustomReport");
			actionMethods.enterInputMandatoryFiled(locator, saveReportName);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ReportName : "+saveReportName, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}

	}

	public void navigateToBudgetScreen()
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.Link");
			actionMethods.isAlertPresent("accept");
			actionMethods.click(locator);
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.Office");
			actionMethods.click(locator);
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User navigate to Budget Screen", driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}

	public void navigateToStandardBudgetReport()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Budget.Office");
			actionMethods.click(locator);
			actionMethods.waitFor();
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Selected Standard Budget Report", driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void SelectAddOrRemoveColumn()
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.AddOrREmoveBtn");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Selected Add or Remove Btn", driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void SelectAddOrRemoveCheckBoxColumns(String chkBoxNames)
	{
		String locator = "";
		List<String> columns = Arrays.asList(chkBoxNames.split(","));
		try
		{
			
			//locator = "//div[contains(@class,'mat-menu-item')]/descendant::mat-checkbox//div[@class='mat-checkbox-inner-container']~Xpath";
			for(int i=0;i<columns.size();i++)
			{
				locator = objectRepo.getProperty("Budget.AddOrREmoveChkBox");
				List<WebElement> list = actionMethods.getListOfdata(locator);
				for(int j=0;j<list.size();j++)
				{
					if(list.get(j).getText().trim().equalsIgnoreCase(columns.get(i).trim().split("~")[0]))
					{
						locator = "//div[contains(@class,'mat-menu-item')]/descendant::mat-checkbox//div[@class='mat-checkbox-inner-container']/input~Xpath";
						System.out.println(actionMethods.waitForElementClicable(By.xpath(locator.split("~")[0]), 1).isSelected());
						if(actionMethods.waitForElementClicable(By.xpath(locator.split("~")[0]), 1).isSelected() && columns.get(i).split("~")[1].equalsIgnoreCase("Remove"))
						{
							list.get(j).click();
							break;
						} else
						if((actionMethods.waitForElementClicable(By.xpath(locator.split("~")[0]), 1).isSelected()) && columns.get(i).split("~")[1].equalsIgnoreCase("Add"))
						{
							list.get(j).click();
							break;
						}
					} else
						System.out.println(list.get(j).getText());
				}

			}
			locator = objectRepo.getProperty("Budget.ApplyBtn");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Selected Add or Remove columns List : "+columns, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateTheNewleyAddedColumnsList(String chkBoxNames)
	{
		String locator = ""; int count = 0;
		List<String> columns = Arrays.asList(chkBoxNames.split(","));
		try
		{
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.ColumnsHearList");
			List<WebElement> list = actionMethods.getListOfdata(locator);
			
			for(int j=0;j<columns.size();j++)
			{
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getText().trim().contains(columns.get(i).split("~")[0]))
					{
						count++;
					} else
					{
						System.out.println(list.get(i).getText());
						locator = objectRepo.getProperty("Budget.LeftColumnsHeaderList");
						List<WebElement> list1 = actionMethods.getListOfdata(locator);
						for(int k=0;k<list1.size();k++)
						if(list1.get(k).getText().trim().contains(columns.get(k).split("~")[0]))
						{
							count++;

						}
					}
				}
			}
			
			if(columns.size()==count)
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Validate the newley added columns list : "+chkBoxNames, driver);
			else
				Constant.statusFlag = "Failed"; 
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateTheRemovedColumnsList(String chkBoxNames)
	{
		String locator = ""; int count = 0;
		List<String> columns = Arrays.asList(chkBoxNames.split(","));
		try
		{
			actionMethods.waitFor();
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Budget.ColumnsHearList");
			List<WebElement> list = actionMethods.getListOfdata(locator);
			
			for(int j=0;j<columns.size();j++)
			{
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).getText().trim().contains(columns.get(i).split("~")[0]))
					{
						count++;
					} else
					{
						System.out.println(list.get(i).getText());
						locator = objectRepo.getProperty("Budget.LeftColumnsHeaderList");
						List<WebElement> list1 = actionMethods.getListOfdata(locator);
						for(int k=0;k<list1.size();k++)
						if(list1.get(k).getText().trim().contains(columns.get(k).split("~")[0]))
						{
							count++;

						}
					}
				}
			}
			
			if(columns.size()==count)
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Validate the Removed columns list : "+chkBoxNames, driver);
			else
				Constant.statusFlag = "Failed"; 
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}

}
