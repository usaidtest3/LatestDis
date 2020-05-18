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
import com.test.utils.Constant;

public class IndicatorPage extends FlowMethods{

	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	
	public void navigateToIndicatorSubTask(String subTaskIndicator)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Indicator.ClcikOnIndicator");
			 actionMethods.click(locator);
			 actionMethods.isAlertPresent("accept");
			 locator = objectRepo.getProperty("Indicator.ManageOuIndicator");
			 actionMethods.click(locator);
			 if(subTaskIndicator.equalsIgnoreCase("Manage OU Indicators - Custom"))
			 {
				 locator = objectRepo.getProperty("Indicator.CreateCustomIndicators");
			 } else if(subTaskIndicator.equalsIgnoreCase("Manage OU Indicator"))
			 {
				 locator = objectRepo.getProperty("ManageOuIndicatorBtn");
			 } else if(subTaskIndicator.equalsIgnoreCase("Manage Disaggregate"))
			 {
				 locator = objectRepo.getProperty("Indicator.ManageCustomDisaggregates");
			 } else if(subTaskIndicator.equalsIgnoreCase("Manage OU Indicators - Standard FA"))
			 {
				 locator = objectRepo.getProperty("Indicator.ManageOuStandardFA");
			 }
			 actionMethods.click(locator);
			 
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User selected : "+subTaskIndicator, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void createCustomIndicator(String nameOfIndicator, String status)
	{
		String locator = "";
		try
		{
		    locator = objectRepo.getProperty("Indicator.NameOfIndicator");
		    actionMethods.enterInputMandatoryFiled(locator, nameOfIndicator);
		    locator = objectRepo.getProperty("Indicator.SelectStatus");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.SelectStatusDropdown");
		    actionMethods.selectDropDownData(locator, status);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"IndicatorName : "+nameOfIndicator, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void selectCategory(String category)
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Indicator.SelectCategory");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.SelectCategoryDropdown");
		    actionMethods.selectDropDownData(locator, category);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Category : "+category, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void selectStandardFACategory(String category)
	{
		String locator = "";
		try
		{
			actionMethods.waitFor();
			locator = objectRepo.getProperty("Indicator.StandrdFACategoty");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.CategoryDropdown");
		    actionMethods.selectDropDownData(locator, category);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Selected Category : "+category, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void selectArea(String area)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.SelectArea");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.SelectAreaDropdown");
		    actionMethods.selectDropDownData(locator, area);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Area : "+area, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void selectElement(String element)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.selectElement");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.SelectElementDropdown");
		    actionMethods.selectDropDownData(locator, element);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Element : "+element, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void selectdataType(String dataType)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.SelectDataType");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.SelectDataTypeDropdown");
		    actionMethods.selectDropDownData(locator, dataType);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"DataType : "+dataType, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void enterDescriptionData(String desc, String unitOfMeasure )
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.DisaggratedBy");
		    actionMethods.enterInputMandatoryFiled(locator, desc);
		    locator = objectRepo.getProperty("Indicator.unitOfMeasure");
		    actionMethods.enterInputMandatoryFiled(locator, unitOfMeasure);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void enterdefination(String def, String rationalIndi )
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.Definitiation");
		    actionMethods.enterInputMandatoryFiled(locator, def);
		    locator = objectRepo.getProperty("Indicator.RationaleIndicator");
		    actionMethods.enterInputMandatoryFiled(locator, rationalIndi);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	
	}
	
	public void selectPPRIndicator(String pprYear)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.ppri_Yes");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.ReportYear");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.PPRYearDropdown");
		    actionMethods.selectDropDownData(locator, pprYear);
		    locator = objectRepo.getProperty("Indicator.SelectPPRDownBtn");
		    actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PPRYear : "+pprYear, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void selectPMPIndicator(String pmpYear)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.PMPYesBtn");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.PMPYear");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.PMPYeardropdown");
		    actionMethods.selectDropDownData(locator, pmpYear);
		    locator = objectRepo.getProperty("Indicator.PMPDownArrow");
		    actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PMPYear : "+pmpYear, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void configurePMPIndicator(String year)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.PMPYear");
			if(actionMethods.isElementPresentOptional(locator))
			{
				actionMethods.click(locator);
				locator = objectRepo.getProperty("ManageOu.PMPYearDropdown");
				actionMethods.selectDropDownData(locator, year);
			} else
			{
				locator = objectRepo.getProperty("ManageOu.PMPYearExistDropdown");
				String gettext = actionMethods.getText(locator);
				if(gettext.contains(year))
				{
					year = String.valueOf(Integer.parseInt(year)+(Integer)(actionMethods.genarateRandomNumber(1)));
				}
				actionMethods.click(locator);
				locator = objectRepo.getProperty("ManageOu.PMPYearDropdown");
				actionMethods.selectDropDownData(locator, year);
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void dataCollectionByUSAID(String usaID, String reportFreq, String dataSource, String dataCollection)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.usaIdselect");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.usaIdselectDropdwon");
		    actionMethods.selectDropDownData(locator, usaID);
		    locator = objectRepo.getProperty("Indicator.usaIdCloseBtn");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.ReportingFrequency");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.ReportingFreqDropdown");
		    actionMethods.selectDropDownData(locator, reportFreq);
		    locator = objectRepo.getProperty("Indicator.DataSource");
		    actionMethods.enterInputMandatoryFiled(locator, dataSource);
		    locator = objectRepo.getProperty("Indicator.MethodOfData");
		    actionMethods.enterInputMandatoryFiled(locator, dataCollection);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"USAID : "+usaID, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void targetAndBaseLine(String month, String year, String target)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.Baseline");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.BaseLIneDropdwon");
		    actionMethods.selectDropDownData(locator, month);
		    locator = objectRepo.getProperty("Indicator.year");
		    actionMethods.click(locator);
		    locator = objectRepo.getProperty("Indicator.yeardropdown");
		    actionMethods.selectDropDownData(locator, year);
		    locator = objectRepo.getProperty("Indicator.RationalTarget");
		    actionMethods.enterInputMandatoryFiled(locator, target);
		   
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Month&Year : "+month+"/"+year, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void saveCustomIndicatorData() throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.saveBtn");
			actionMethods.scrollToElement(locator);
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectIndicatorCode(String code) throws Exception 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.Search");
			actionMethods.enterInputMandatoryFiled(locator, code);
			Thread.sleep(2000);
			locator = objectRepo.getProperty("ManageOu.IndicatorCode");
			actionMethods.scrollToElement(locator.replace("Dummy", code));
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Code : "+code, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectType(String type) 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.selectType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("ManageOu.selectTypeDropdown");
			actionMethods.selectDropDownData(locator, type);
			locator = objectRepo.getProperty("ManageOu.downArrow");
			actionMethods.click(locator.replace("Dummy", type));
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Type : "+type, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectDataType(String dataType) 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.dataType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("ManageOu.dataTypeDropdown");
			actionMethods.selectDropDownData(locator, dataType);
			locator = objectRepo.getProperty("ManageOu.DataTypedownArrow");
			actionMethods.click(locator.replace("Dummy", dataType));
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"DataType : "+dataType, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}

	public void selectDisaggregates(String typeOfDisaggregates)
	{
		String locator = "";
		try
		{
			if(typeOfDisaggregates.equalsIgnoreCase("Add Custom Disaggregates")) 
				locator = objectRepo.getProperty("ManageOu.AddcustomDisaggregates");	
			else
				locator = objectRepo.getProperty("");	
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Type ofDisaggregator : "+typeOfDisaggregates, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectcCustomDisagreegates(String type, String dataType) 
	{
		try
		{
			selectType(type);
			selectDataType(dataType);
			selectCheckbox(type, dataType);
			clickOnSaveBtn();
			Thread.sleep(3000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectCheckbox(String type, String dataType) 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.SelectTypeCheckbox");
			actionMethods.click(locator.replace("Dummy", type));
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}

	public void viewOrEditIndicatorCode(String indicatorCode, String PIRS) 
	{
		String locator = " ";
		try
		{
			locator = objectRepo.getProperty("ManageOu.SelectViewOrEdit");
			locator = locator.replace("Dummy", indicatorCode);
			locator= locator.replace("Option", PIRS);
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Selected PIRS : "+PIRS, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void downloadReport() 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.DownloadBtn");
			actionMethods.click(locator);
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void changeIndicatorAttributes(String pmpYear) 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.PMPRadioBtnStatus"); 
			String getText = actionMethods.getText(locator); 
			if(getText.equalsIgnoreCase("Yes"))
			{ 
				//locator = objectRepo.getProperty("ManageOu.PMPIndicatorNoBtn");
			   // actionMethods.click(locator);
				configurePMPIndicator(pmpYear);
			} else 
			{
				locator = objectRepo.getProperty("ManageOu.PMPIndicatorYesBtn");
				actionMethods.click(locator); 
				configurePMPIndicator(pmpYear);
			}

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void clickOnSaveBtn() throws Exception 
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.AddCustomSaveBtn");
			actionMethods.click(locator);
			Thread.sleep(2000);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void createCustomDisaggretes()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.createNewDisaggregate");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void enterCustomDisaggreteName(String name) throws Exception
	{
		String locator = "";
		try
		{
			Thread.sleep(2000);
			locator = objectRepo.getProperty("Manage.EnterDisaggregatorName");
		//	name = name+actionMethods.genarateRandomNumber(5);
			actionMethods.enterInputMandatoryFiled(locator, name);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"DisaggregateName : "+name, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void enterDisaggreteDesciption(String desciption)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.EnterDesc");
			actionMethods.enterInputMandatoryFiled(locator, desciption);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Description : "+desciption, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectdisaggregateTypeAndDataType(String type, String dataType)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.selectType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Manage.TypeDropdown");
			actionMethods.selectDropDownData(locator, type);
			
			locator = objectRepo.getProperty("Manage.DataType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Manage.DataTypeDropdown");
			actionMethods.selectDropDownData(locator, dataType);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User select Type & DataType as  : "+type+"&"+dataType, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void enterDisaggreteValue(String value)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.EnterUniqName");
			actionMethods.enterInputMandatoryFiled(locator, value);
			locator = objectRepo.getProperty("Manage.Addvalue");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"EnterUniqValue : "+value, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateDuplicateDisaggreteValue()
	{
		String locator = "", expectedMsg="A disaggregate with this name already exists. Please enter a unique name";
		try
		{
			locator = objectRepo.getProperty("Dis.ExpectedText");
			if(actionMethods.isElementPresent(locator.replaceAll("Dummy", expectedMsg)))
				
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+expectedMsg, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void clickOnDisaggragetCreateBtn() throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.DisaggregateCreateBtn");
			if(actionMethods.isElementPresentOptional(locator))
			{
				actionMethods.click(locator);
				Thread.sleep(2000);
			} else
			{
				locator = objectRepo.getProperty("Indicator.CancelBtn");
				actionMethods.click(locator);
				locator = objectRepo.getProperty("Indicator.YesBtn");
				actionMethods.click(locator);
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectDisaggragetType(String type) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.Type");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Indicator.PPRYearDropdown");
			actionMethods.selectDropDownData(locator, type);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Type: "+type, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectDisaggragetdataType(String dataType) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.SelectDataType");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Indicator.PMPYeardropdown");
			actionMethods.selectDropDownData(locator, dataType);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"DataType: "+dataType, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectDisaggragetSelectTypeStatus(String status) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.selectTypeStatus");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("ManageSelectTYpeStatusDropdown");
			actionMethods.selectDropDownData(locator, status);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Status: "+status, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void selectDisaggragetSearchByName(String name) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Manage.SearchByName");
			actionMethods.enterInputMandatoryFiled(locator, name);
			Thread.sleep(3000);
			actionMethods.enterInputMandatoryFiled(locator, name);
			locator = objectRepo.getProperty("Manage.customText");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Search by name: "+name, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+e.getMessage(), driver);
			throw e;
		}
	}
	
	public void validateDisaggregateName(String name) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("ManageOu.IndicatorCode");
			actionMethods.isElementPresent(locator.replace("Dummy", name));
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Search by name presented successfully!: "+name, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateDisaggregateValueLength() throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.DisaggregatorTotalValues");
			if(actionMethods.getListOfdata(locator).size()==20)
			{
				locator = objectRepo.getProperty("Indicator.DisaggregatorMaxValue");
				actionMethods.isElementPresent(locator);
			}
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Max entries 20 only: ", driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void deactivateManageCustomDisaggregateName(String indicator) throws Exception
	{
		String locator = "", msg="";
		try
		{
			locator = objectRepo.getProperty("Indicator.DisaggrgateName");
			if(actionMethods.isElementPresent(locator.replaceAll("Dummy", indicator)))
			{
				actionMethods.scrollToElement(locator.replaceAll("Dummy", indicator));
				locator = objectRepo.getProperty("Indicator.Deactivate");
				actionMethods.click(locator);
			} else
				msg=indicator+": not presentedin list";
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Deactivated indicator: "+indicator, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
			throw e;
		}
	}
	
	public void deleteManageCustomDisaggregateName(String indicator) throws Exception
	{
		String locator = "", msg="";
		try
		{
			locator = objectRepo.getProperty("Dis.ExpectedText");
			actionMethods.click(locator.replaceAll("Dummy", indicator));
			locator = objectRepo.getProperty("Indicator.DisaggregateDeleteBtn");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Dis.Yes");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User deleted indicator: "+indicator, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
			throw e;
		}
	}
	
	public void entercoomentsforDeactivate() throws Exception
	{
		String locator = "", msg="";
		try
		{
			locator = objectRepo.getProperty("Indicator.entercomments");
			actionMethods.enterInputMandatoryFiled(locator, "deactivating.");
			locator = objectRepo.getProperty("Indicator.ConfirmComments");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
			throw e;
		}
	}
	
	
	public void selectIndicator(String IndicatorName) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.Search");
			actionMethods.enterInputMandatoryFiled(locator, IndicatorName);
			Thread.sleep(2000);
			locator = objectRepo.getProperty("ManageOu.IndicatorCode");
			actionMethods.scrollToElement(locator.replace("Dummy", IndicatorName));
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Userselected indicatoCode: "+IndicatorName, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getIndicatorCode(String indiCode) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetIndiCode");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(indiCode))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"GetIndicatorCode: "+indiCode, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getOldIndicatorCode(String oldIndiCode) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetOldIndiCode");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(oldIndiCode))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Old IndicatorCode: "+oldIndiCode, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getIndicatorName(String indiName) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetName");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(indiName))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Indicator Name: "+indiName, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getIndicatorDataTYpe(String indiDatatYpe) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetDataType");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(indiDatatYpe))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Indicator Name: "+indiDatatYpe, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getIndicatorOrigin(String origin) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetOrigin");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(origin))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Origin: "+origin, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getStandardFAIndiStatus(String FAstatus) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.StandardFAStatus");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(FAstatus))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Standard FA Indicator: "+FAstatus, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getReportFreq(String reportFreq) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetReportFreq");
			if(actionMethods.getText(locator).trim().equalsIgnoreCase(reportFreq))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get Reporting Freq: "+reportFreq, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void getTags(String reportFreq) throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.GetTag");
			if(actionMethods.getAttributeValue(locator, "value").trim().equalsIgnoreCase(reportFreq))
			{
				Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Get TagName: "+reportFreq, driver);
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void clickOnIndicatorCode() throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.ClickIndiCode");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateStandrdFAHeader(String testData) throws Exception
	{
		String locator = "", msg = "";
		try
		{
			List<String> getData = Arrays.asList(testData.split("-"));
			locator = objectRepo.getProperty("Indicator.StandardFAheaders");
			List<WebElement> headers = actionMethods.getListOfdata(locator);
			for(int i=0;i<headers.size();i++)
			{
				if(headers.get(i).getText().contains(getData.get(i)))
				{
					msg = msg+"Header Data presented "+headers.get(i).getText();
				} else
				{
					msg = msg+"Header Data not presented "+headers.get(i).getText();
					Constant.statusFlag = "Failed";
					return;
				}
			}
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
		} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+msg, driver);
			locator=objectRepo.getProperty("Indicator.StandrdFaXbutton");
			actionMethods.click(locator);
			throw e;
		}
	}
	
	public void validatePPR_PMP_IndicatorNames() throws Exception
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Indicator.PPRText");
			if(actionMethods.isElementPresent(locator))
			{
				locator = objectRepo.getProperty("Indicator.PMPText");
				if(actionMethods.isElementPresent(locator))
				{
					Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PPR & PMP Indicator headers displayed successfully!", driver);
				}
			} 
			else
				Constant.statusFlag = "Failed";	
			} catch (Exception e) {
			Constant.statusFlag = "Failed";			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void cancelStandrdFAScreen()
	{
		String locator = objectRepo.getProperty("Indicator.CancelBtn");
		actionMethods.click(locator);
		Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
	}
}
