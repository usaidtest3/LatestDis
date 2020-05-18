package com.test.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.utils.Constant;

public class MappingPage extends FlowMethods{

	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	ProjectsPage project = new ProjectsPage();
	
	public void navigateToMappingScreen() throws Exception
	{
		String locator = "";
		try
		{
			Thread.sleep(3000);
			 locator = objectRepo.getProperty("Mapping.Link");
			 actionMethods.scrollToElement(locator);
			 actionMethods.isAlertPresent("accept");
			 locator = objectRepo.getProperty("Mapping.uploadGisLayer");
			 actionMethods.click(locator);
			 
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Navigated to Mapping Screen ", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void uploadDocument(String filPath)
	{
		String locator = "";
		try
		{
			// locator = objectRepo.getProperty("Mapping.chooseFile");
			 locator = "//span[contains(@class,'ui-button ui-fileupload-choose')]~Xpath";
			 actionMethods.scrollToElement(locator);
			 actionMethods.click(locator);
 			 actionMethods.uploadFile(filPath);
			 
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Upload filePath : "+filPath, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void tagMyGisFile(String gisFile)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Mapping.SelectGisLayerType");
			 actionMethods.click(locator);
			 locator = objectRepo.getProperty("Mapping.SelectGisDRopdown");
			 actionMethods.selectDropDownData(locator, gisFile);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Selected Gisfile : "+gisFile, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void enterLayerName(String layerName)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Mapping.LayerName");
			 actionMethods.enterInputMandatoryFiled(locator, layerName);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"LayerName : "+layerName, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void enterLayerDesciption(String layerDesc)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Mapping.LayerDescription");
			 actionMethods.enterInputMandatoryFiled(locator, layerDesc);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"LayerDesc : "+layerDesc, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void sourceDate(String date)
	{
		String locator = "", selectDate;
		try
		{
			 locator = objectRepo.getProperty("Mapping.SourceDate");
			 actionMethods.click(locator);
			 selectDate = project.selectCalanderDate(date);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User Selected Date is : "+selectDate, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void typeOfGeographicLayer(String geoLayerType)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Mapping.SelectGeographyType");
			 actionMethods.click(locator);
			 locator = objectRepo.getProperty("Mapping.SelectGeographyDRopdown");
			 actionMethods.selectDropDownData(locator, geoLayerType);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Geographic LayeType : "+geoLayerType, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void enterSource(String source)
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("Mapping.Source");
			 actionMethods.enterInputMandatoryFiled(locator, source);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"SourceData : "+source, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
	
	public void saveGisFileContent()
	{
		String locator = "";
		try
		{
			 locator = objectRepo.getProperty("OverView.OffceSaveBtn");
			 actionMethods.click(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			 throw e;
		}
	}
}
