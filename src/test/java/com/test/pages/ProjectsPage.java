package com.test.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.IndicatorTestData;
import com.test.testData.ProjectTestData;
import com.test.utils.Constant;

public class ProjectsPage extends FlowMethods{

	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();

	public int getMonthNum(String monthName) throws ParseException
	{
		Date date = new SimpleDateFormat("MMMM").parse(monthName);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		return cal.get(Calendar.MONTH);

	}

	public String getCurrentDate(String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}

	public int getDiff(String fromDate, String toDate)
	{
		int diff = 0;
		try
		{
			diff = Integer.parseInt(fromDate) - Integer.parseInt(toDate);
		}catch (Exception e) {

			// TODO: handle exception

		}

		return diff;

	}

	public String selectCalanderDate(String selectDate)
	{
		String locator = "";int dateIndex;String date = "";
		try
		{
		//	locator = objectRepo.getProperty("");
		//	actionMethods.click(locator);
			dateIndex = Integer.parseInt(selectDate);
			if(dateIndex==0)
			{
				date =  getCurrentDate("dd");
				locator = objectRepo.getProperty("Project.selectDate");
				actionMethods.click(locator.replace("Dummy", date));

			} else
			{
				date =  getCurrentDate("dd");
				locator = objectRepo.getProperty("Project.selectDate");
				int i = Integer.parseInt(date)+dateIndex;
				actionMethods.click(locator.replace("Dummy", String.valueOf(i)));
			}

		} catch(Exception e)
		{

		}
		return date;
	}

	public void navigateToCreateProject()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Projects.ClikOnProject");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
		 Constant.statusFlag = "Failed";			
		 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			
	 }
	}
	
	public static String projectName = "";
	public void createProject(String moduleName)
	{
		String locator = ""; 
		try
		{
			//locator = objectRepo.getProperty("Projects.ClikOnProject");
			//actionMethods.click(locator);
			actionMethods.isAlertPresent("accept");
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Project.CreateANewProject");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Project.selectOfficeNmae");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Project.OfficeName");
			actionMethods.selectDropDownData(locator, ProjectTestData.OFFICE_NAME);
			locator = objectRepo.getProperty("Project.StartDate");
			actionMethods.click(locator);
			selectCalanderDate(ProjectTestData.START_DATE);
			locator = objectRepo.getProperty("Project.EndDate");
			actionMethods.click(locator);
			selectCalanderDate(ProjectTestData.END_DATE);
			locator = objectRepo.getProperty("Project.Status");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Project.statusDropdown");
			actionMethods.selectDropDownData(locator, ProjectTestData.STATUS);
			locator = objectRepo.getProperty("Project.TotalEstimatedCostTEC");
			actionMethods.enterInputMandatoryFiled(locator, ProjectTestData.ESTIMATED_COST);
			enteProjectName(moduleName);
			locator = objectRepo.getProperty("Project.Manager");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Project.Magaersdropdown");
			actionMethods.selectDropDownData(locator, ProjectTestData.PROJECT_MANAGER);
			locator = objectRepo.getProperty("Project.Description");
			actionMethods.enterInputMandatoryFiled(locator, ProjectTestData.DESCRIPTION);
			enterAcronymName();
			locator = objectRepo.getProperty("Project.SaveBtn");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void searchProject(String enterName)
	{
		try
		{
			
			String locator = objectRepo.getProperty("Project.searchByName");
			if(actionMethods.isElementPresentOptional(locator))
				
			actionMethods.enterInputMandatoryFiled(locator, enterName);
			locator = objectRepo.getProperty("Dis.Project");
			actionMethods.click(locator);
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Selected Project Name : "+enterName, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void viewProject(String enterName)
	{
		try
		{
			String locator = objectRepo.getProperty("Project.ViewProject");
			actionMethods.click(locator.replace("Dummy", enterName));
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Project Name : "+enterName, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void selectMonitoringTab()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.Montoring");
			actionMethods.click(locator);
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void selectTab()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.Montoring");
			actionMethods.click(locator);
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	
	public void addIndicatorCode(String indicatorCode)
	{
		try
		{
			String locator = objectRepo.getProperty("Project.AddIndicatorCode");
			actionMethods.click(locator.replace("Dummy", indicatorCode));
			actionMethods.waitFor();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Added Indicator code : "+indicatorCode, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void saveIndicatorCodes(String indicatorCode) throws Exception
	{   
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Project.SaveIndicator");
			actionMethods.click(locator);
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			locator = objectRepo.getProperty("Project.CancelIndicator");
			actionMethods.click(locator);
			throw e;
		}
	}
	
	public void validateAddIndicatorMsg()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.AddIndicatorMsg");
			if(actionMethods.isElementPresent(locator))
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Added Indicator message displayed", driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void enteProjectName(String moduleName)
	{
		try
		{
			String locator = objectRepo.getProperty("Project.Name");
			String projectName = ProjectTestData.PROJECT_NAME+actionMethods.genarateRandomNumber(4);
			actionMethods.enterInputMandatoryFiled(locator, projectName);
			excelOperation.updateReferencevalue(moduleName, "projectName",projectName, ProjectTestData.LINK_REF);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ProjectName : "+projectName, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void deLInkIndicatorCode(String indicatorCode)
	{
		try
		{
			String locator = "";
			locator = objectRepo.getProperty("Project.Delink");
			actionMethods.click(locator.replace("Dummy", indicatorCode));
			
			locator = objectRepo.getProperty("Project.clickOnDelink");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Dis.Yes");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Delinked indicator : "+indicatorCode, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void validateDelinkSuccessMsg()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.DelinkMsg");
			if(actionMethods.isElementPresent(locator))
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"getScreenShot", driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}

	public void selectLinkedIndicator()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.LinkedInidiator");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"getScreenShot", driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void enterAcronymName()
	{
		try
		{
			String locator = objectRepo.getProperty("Project.Acronym");
			String acronym = ProjectTestData.ACRONYM+actionMethods.genarateRandomNumber(3);
			actionMethods.enterInputMandatoryFiled(locator, acronym);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Project AcronymName : "+acronym, driver);
		} catch (Exception e) {
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
}
