package com.test.excelAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.test.controller.FlowMethods;
import com.test.controller.Runner;
import com.test.testData.ActivityTestData;
import com.test.testData.AdminTestData;
import com.test.testData.BudgetTestData;
import com.test.testData.IndicatorTestData;
import com.test.testData.K2TestData;
import com.test.testData.MappingTestData;
import com.test.testData.OverviewTestData;
import com.test.testData.ProjectTestData;
import com.test.testData.ReportsTestData;
import com.test.testData.StrategiesTestData;
import com.test.testData.TestDataObjectLogin;
import com.test.utils.Constant;
import com.test.utils.ModuleTestData;




public class ExcelOperation {

	public static Connection connection = null;
	public static Recordset recordset = null;
	public static String noOfThreds;
	public static String country;
	public static String modules;
	public static String sleepTimeout;
	public static String loadingTimeout;
	public static String globalMaxTimeout;
	public static String listOfFailedMethods;
	public static String bulkImportPaymentsStatus="";
	public static String testSuiteJourneyID;
	public  static String testSuiteModuleName="";
	public  static String testSuiteReportingModuleName="";
	public  static String testSuiteSubModuleName="";
	public  String testSuiteJourneyFlowID;
	public static String previousJourneyID="";
	public static String previousBrowserType="";
	public static List<String> passedJourneysList = new ArrayList<String>();
	public static List<String> executedJourneys = new ArrayList<String>();
	public static List<String> failedJourneys = new ArrayList<String>();
	public static List<String> failedJourneyswithTimeStamp = new ArrayList<String>();
	public static List<String> skippedJourneys = new ArrayList<String>();
	public static List<String> somethingWentWrongJourneys = new ArrayList<String>();
	public static List<String> correlationsList = new ArrayList<String>();
	public static List<String> moduleList_Reporting = new ArrayList<String>();
	public static HashMap<String,String> journey_LinkRef = null;
	public static String executionStartTime = "";
	public static String executionEndTime = "";
	public static String userNameDetails = "";
	public static String exceptionCaptured = "";
	public static String almCaseName = "";
	public static String developer = "";
	public static String owner = "";
	public static String testDataClassName = "";
	public static Boolean testEligibleToSkip = false;

	public static List<String> exclusivejourneys = new ArrayList<String>();

	public static Fillo fillo = new Fillo();
	public static Properties Config = new Properties();;
	static Logger log = LogManager.getLogger(ExcelOperation.class);
	public static String vascoStatus="";
	public static String subModuleName="";
	

	public static void readExecutionVariables(int currentIteration) {

		Connection	executionvariablesConnection = null;
		Recordset executionvariablesRecordset = null;
		
		try 
		{
			String completeIpAddress = Inet4Address.getLocalHost().getHostAddress();
			String[] ipAddress = completeIpAddress.split("\\.");
			String machine = ipAddress[ipAddress.length-1];
			
			if(Constant.Path_TestData.isEmpty())
			{
				FileInputStream fileInputStream = new FileInputStream("./Config.properties");
				Config.load(fileInputStream);
				Constant.Path_TestData=Config.getProperty("Path_TestData").replace("':'", ":");
			}
			
			executionvariablesConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from Settings";
			executionvariablesRecordset = executionvariablesConnection.executeQuery(strQuery);
			while (executionvariablesRecordset.next()) 
			{
				Constant.environment = (executionvariablesRecordset.getField("Environment"));
				Constant.URL = (executionvariablesRecordset.getField("ApplicationURL"));
				country = (executionvariablesRecordset.getField("Country"));
				modules = (executionvariablesRecordset.getField("Modules"));
				Constant.screenshot_path = (executionvariablesRecordset.getField("PathOfScreenshot"));
				//Constant.screenshot_path = System.getProperty("user.dir")+"//src//test//resources//reportsScreenshots//";
				Constant.driverPath_chrome = (executionvariablesRecordset.getField("DriverPath_chrome"));
				//Constant.driverPath_chrome = System.getProperty("user.dir")+"//lib//chromedriver.exe";
				Constant.download_dir = (executionvariablesRecordset.getField("Download_dir"));
				globalMaxTimeout = (executionvariablesRecordset.getField("GLOBAL_MAX_TIMEOUT"));
				sleepTimeout = (executionvariablesRecordset.getField("SLEEP"));
				loadingTimeout = (executionvariablesRecordset.getField("LOADING_TIMEOUT"));
			    Constant.pathOfReport = (executionvariablesRecordset.getField("PathOfReport"));
				//Constant.pathOfReport = System.getProperty("user.dir")+"//src//test//resources//reports//";
				Constant.extentReportConfigFile = (executionvariablesRecordset.getField("ExtentReportConfigFile"));
				Constant.GLOBAL_MAX_TIMEOUT=Integer.parseInt(globalMaxTimeout);
				Constant.SLEEP=Integer.parseInt(sleepTimeout);
				Constant.LOADING_TIMEOUT=Integer.parseInt(loadingTimeout);
				Constant.reRun=Integer.parseInt((executionvariablesRecordset.getField("RERUN")));
				System.out.println("Initialize Extent Report");

				if(currentIteration==0)
				{
					//Create Folder Structure for current execution - Relative Screen prints
					String currentTimeStamp = getCurrentTimeStamp();
					String correspondingMachineName = currentTimeStamp;					
					correspondingMachineName = correspondingMachineName + "-" + machine;
					Constant.pathOfFailedCasesScreePrints = Constant.pathOfReport + correspondingMachineName + "\\" + "ScreenshotsFailed" + "_Run_" + String.valueOf(currentIteration);
					Constant.pathOfPassedCasesScreePrints = Constant.pathOfReport + correspondingMachineName + "\\" + "ScreenshotsPassed" + "_Run_" + String.valueOf(currentIteration);
					Constant.packagePath = Constant.pathOfReport + correspondingMachineName + "\\";
					new File(Constant.pathOfFailedCasesScreePrints).mkdirs();
					new File(Constant.pathOfPassedCasesScreePrints).mkdirs();
					Constant.reportPath = Constant.pathOfReport + correspondingMachineName + "\\" +("Report_" + currentTimeStamp + "_Run_" + String.valueOf(currentIteration) + ".html");
					System.out.println("Constant.reportPath"+Constant.reportPath);
					Constant.pathOfFailedCasesScreePrints = Constant.pathOfFailedCasesScreePrints + "\\";
					Constant.pathOfPassedCasesScreePrints = Constant.pathOfPassedCasesScreePrints +"\\";
				}
				else
				{
					System.out.println(Constant.reportPath.replace("_Run_" + (currentIteration-1), "_Run_" + currentIteration));
					Constant.reportPath = Constant.reportPath.replace("_Run_" + (currentIteration-1), "_Run_" + currentIteration);
					Constant.pathOfFailedCasesScreePrints = Constant.pathOfFailedCasesScreePrints.replace("_Run_" + (currentIteration-1), "_Run_" + currentIteration);
					Constant.pathOfPassedCasesScreePrints = Constant.pathOfPassedCasesScreePrints.replace("_Run_" + (currentIteration-1), "_Run_" + currentIteration);
					new File(Constant.pathOfFailedCasesScreePrints).mkdirs();
					new File(Constant.pathOfPassedCasesScreePrints).mkdirs();
				}

				Constant.extentReporter = new ExtentReports(Constant.reportPath, true,DisplayOrder.OLDEST_FIRST );
				Constant.extentReporter.addSystemInfo("OS", "Windows 10");
				Constant.extentReporter.addSystemInfo("Host Name", "Windows");
				Constant.extentReporter.addSystemInfo("Environment", "DIS");
				Constant.extentReporter.addSystemInfo("User Name", "Automation");

				Constant.extentReporter.addSystemInfo("Environment", Constant.environment);
				Constant.extentReporter.addSystemInfo("ApplicationURL", Constant.URL);
				Constant.extentReporter.addSystemInfo("Test Data","<a href="+Constant.Path_TestData+"><span class='label info'>Test Data</span></a>" );
				Constant.extentReporter.addSystemInfo("Country", country);
				Constant.extentReporter.addSystemInfo("Browser", Constant.browser);
				Constant.extentReporter.addSystemInfo("Global Max Timeout", Constant.GLOBAL_MAX_TIMEOUT +" sec");
				Constant.extentReporter.addSystemInfo("Loading Timeout", Constant.LOADING_TIMEOUT +" sec");
				Constant.extentReporter.addSystemInfo("Sleep Timeout", Constant.SLEEP +" sec");
				Constant.extentReporter.loadConfig(new File(Constant.extentReportConfigFile));
			}
		} catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(executionvariablesConnection!=null) {
				executionvariablesConnection.close();
			} else{
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(executionvariablesRecordset!=null){
				executionvariablesRecordset.close();
			}
		}
	}


	public  void readTestSuite() 
	{		
		String testcaseDescription = null;		
		try {
			testSuiteJourneyID = "";
			testSuiteModuleName="";
			testSuiteSubModuleName="";
			testSuiteJourneyFlowID="";
			ArrayList<String> journeysList = new ArrayList<String>();

			//Dummy conditional block - Just To restrict connection object scope
			if(true)
			{
				Connection readTestSuiteConnection = null;
				Recordset readTestSuiteRecordset = null;
				//To define the scope of Connection and Record Set objects
				readTestSuiteConnection = fillo.getConnection(Constant.Path_TestData);
				System.out.println("connection to test data established");
				String strQuery = "Select * from TestSuite where Execution_Status='Yes'";
				System.out.println("Test Suite Query =" + strQuery);
				readTestSuiteRecordset = readTestSuiteConnection.executeQuery(strQuery);

				//Fetch List of "Yes" Marked Cases from excel
				while (readTestSuiteRecordset.next()) 
				{
					journeysList.add(readTestSuiteRecordset.getField("Journey_ID") + "~" + readTestSuiteRecordset.getField("Module_Name") + "~" + readTestSuiteRecordset.getField("Sub_Module_Name") + "~" + readTestSuiteRecordset.getField("FlowMethods"));
				}
				
				readTestSuiteConnection.close();
				readTestSuiteRecordset.close();	
			}

			ListIterator<String> valuesIterator = journeysList.listIterator(); 

			while(valuesIterator.hasNext()) 
			{
				String value=valuesIterator.next();
				String[] journeysArray = value.split("~");

				System.out.println("*****************************Start of Tessuite =  "+ testSuiteJourneyID +"*****************************");				
				testSuiteJourneyID = journeysArray[0];
				testSuiteModuleName = journeysArray[1];
				testSuiteSubModuleName = journeysArray[2];
				testSuiteJourneyFlowID = journeysArray[3];
				System.out.println("testSuiteJourneyID  "+testSuiteJourneyID);
				System.out.println("testSuiteModuleName  "+testSuiteModuleName);
				System.out.println("testSuiteSubModuleName  "		+testSuiteSubModuleName);
				System.out.println("testSuiteJourneyFlowID  "+ testSuiteJourneyFlowID);

				if(testSuiteModuleName.equalsIgnoreCase("Login")) {
					loginModuleTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = ModuleTestData.TEST_DESCRIPTION;
				} else if(testSuiteModuleName.equalsIgnoreCase("Overview"))
				{
					populateOverviewTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = OverviewTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = OverviewTestData.LOGIN_DETAILS;
				} else if(testSuiteModuleName.equalsIgnoreCase("Strategis"))
				{
					populateStratigesTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = StrategiesTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = StrategiesTestData.LOGIN_DETAILS;
				} else if(testSuiteModuleName.trim().equalsIgnoreCase("Budget"))
				{
					populateBudgetTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = BudgetTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = BudgetTestData.LOGIN_DETAILS;
				} else if(testSuiteModuleName.trim().equalsIgnoreCase("Project"))
				{
					populateProjectTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = ProjectTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = ProjectTestData.LOGIN_DETAILS;
				} else if(testSuiteModuleName.trim().equalsIgnoreCase("Activity"))
				{
					populateActivityTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = ActivityTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = ActivityTestData.LOGIN_DETAILS;
				}else if(testSuiteModuleName.trim().equalsIgnoreCase("Indicators"))
				{ 
					System.out.println("came in Indicators");
					populateindicatorTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = IndicatorTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = IndicatorTestData.LOGIN_DETAILS;
				}else if(testSuiteModuleName.trim().equalsIgnoreCase("Reports"))
				{
					populateReportsTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = ReportsTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = ReportsTestData.LOGIN_DETAILS;
				} else if(testSuiteModuleName.trim().equalsIgnoreCase("Mapping"))
				{
					populateMappingTestData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = MappingTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = MappingTestData.LOGIN_DETAILS;
				} 
				else if(testSuiteModuleName.trim().equalsIgnoreCase("K2"))
				{
					populatek2TravelData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = K2TestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = K2TestData.LOGIN_DETAILS;
				}
				else if(testSuiteModuleName.trim().equalsIgnoreCase("Admin"))
				{
					populateAdminTesttData(testSuiteModuleName, testSuiteJourneyID);
					testcaseDescription = AdminTestData.TEST_DESCRIPTION;
					TestDataObjectLogin.LOGIN_DETAILS = AdminTestData.LOGIN_DETAILS;
				}
				Constant.extentTest = Constant.extentReporter.startTest(testSuiteJourneyID, testcaseDescription);  
				Constant.extentTest.assignAuthor(System.getProperty("user.name"));				
				if(testSuiteSubModuleName.equalsIgnoreCase(testSuiteModuleName))
					Constant.extentTest.assignCategory(testSuiteModuleName);
				else 
				{
					Constant.extentTest.assignCategory(testSuiteSubModuleName+" ["+testSuiteModuleName+"]");
				}
				executeFlowMethods(testSuiteModuleName, testSuiteJourneyFlowID);
				Constant.extentReporter.endTest(Constant.extentTest);

				System.out.println("*****************************End of Tessuite =  " +testSuiteJourneyID+Constant.browser+ "*****************************");
			}
			//clearTestDataFromVariables();

		} 
		catch (Exception e) 
		{
			log.error("Failed to execute step", e);
		} 
	}

	public void updateReferencevalue(String moduleName, String columnName, String referenceValue, String linkRef) {

		Connection	updateReferencevalueConnection = null;
		try {
			updateReferencevalueConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Update "+moduleName+" Set "+columnName+"='"+referenceValue+"'where LINK_REF='"+linkRef+"'";
			updateReferencevalueConnection.executeUpdate(strQuery);
			System.out.println("Test data Sheet updated for LINK_REF=" +linkRef + "& with REFERENCE_VALUE =" +referenceValue);
		} catch (Exception e) {
			log.error("Failed to execute step", e);
		}  finally {
			if(updateReferencevalueConnection!=null) {
				updateReferencevalueConnection.close();
			}
		}
	}

	public void loginModuleTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				TestDataObjectLogin.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				TestDataObjectLogin.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				TestDataObjectLogin.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateOverviewTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				OverviewTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				OverviewTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				OverviewTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				OverviewTestData.OFFICE_NAME = (moduleTestDataRecordset.getField("OfficeName"));
				OverviewTestData.OFFICE_ACRONYM = (moduleTestDataRecordset.getField("OfficeAcronym"));
				OverviewTestData.STARTDATE = (moduleTestDataRecordset.getField("StartDate"));
				OverviewTestData.OFFICE_DESCRIPTION = (moduleTestDataRecordset.getField("Office_Description"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateMappingTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				MappingTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				MappingTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				MappingTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				MappingTestData.GIS_LAYERTYPE = (moduleTestDataRecordset.getField("GisLayerType"));
				MappingTestData.LAYER_NAME = (moduleTestDataRecordset.getField("LayerName"));
				MappingTestData.LAYER_DESCR = (moduleTestDataRecordset.getField("LayerDesc"));
				MappingTestData.SOURCE_DATE = (moduleTestDataRecordset.getField("SourceDate"));
				MappingTestData.GEOGRAPHICLAYER = (moduleTestDataRecordset.getField("GegraphicLayer"));
			//	MappingTestData.TYPE_GEGRAPHICLAYER = (moduleTestDataRecordset.getField("TypeOfGrgraphicLayer"));
				MappingTestData.SOURCE = (moduleTestDataRecordset.getField("Source"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateActivityTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				ActivityTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				ActivityTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				ActivityTestData.LINK_REF = (moduleTestDataRecordset.getField("LINK_REF"));
				ActivityTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				ActivityTestData.ACTIVITY_NAME = (moduleTestDataRecordset.getField("activityName"));
				ActivityTestData.ACTIVITY_ACRONYM = (moduleTestDataRecordset.getField("activityAcronym"));
				ActivityTestData.ACTIVITY_DESCRIPTION = (moduleTestDataRecordset.getField("activityDesc"));
				ActivityTestData.PUBLIC_DESCRIPTION = (moduleTestDataRecordset.getField("publicDesc"));
				ActivityTestData.ACTIVITY_STATUS = (moduleTestDataRecordset.getField("activityStatus"));
				ActivityTestData.REPORTED_BY = (moduleTestDataRecordset.getField("reportedBy"));
				ActivityTestData.USAIDOFFICE = (moduleTestDataRecordset.getField("usaOffice"));
				ActivityTestData.USAIDMANGER = (moduleTestDataRecordset.getField("usaIdManager"));
				ActivityTestData.ALTERNATIVE_AOR = (moduleTestDataRecordset.getField("alternativeAor"));
				ActivityTestData.ALTERNATIVE_MANAGER = (moduleTestDataRecordset.getField("alternativeManager"));
				ActivityTestData.COP_NAME = (moduleTestDataRecordset.getField("copName"));
				ActivityTestData.COPEMAIL = (moduleTestDataRecordset.getField("copEmail"));
				ActivityTestData.COP_COUNTRY = (moduleTestDataRecordset.getField("copCountry"));
				ActivityTestData.COP_PHONE = (moduleTestDataRecordset.getField("copHome"));
				ActivityTestData.COP_EXTN = (moduleTestDataRecordset.getField("copExtn"));
				ActivityTestData.COP_ADDRESS = (moduleTestDataRecordset.getField("copAddress"));
				ActivityTestData.AWARD_NAME = (moduleTestDataRecordset.getField("awardName"));
				ActivityTestData.AWARD_NO = (moduleTestDataRecordset.getField("awardNo"));
				ActivityTestData.ORDER_NO = (moduleTestDataRecordset.getField("orderNo"));
				ActivityTestData.START_DATE = (moduleTestDataRecordset.getField("startDate"));
				ActivityTestData.END_DATE = (moduleTestDataRecordset.getField("endDate"));
				ActivityTestData.ACTIVITY_TYPE = (moduleTestDataRecordset.getField("activityType"));
				ActivityTestData.KEYPOINT_OF_CONTACT = (moduleTestDataRecordset.getField("keyPointofContact"));
				ActivityTestData.ESTIMATED_COST = (moduleTestDataRecordset.getField("estimatedCost"));
				ActivityTestData.PARTNER = (moduleTestDataRecordset.getField("partnerName"));
				ActivityTestData.CITY = (moduleTestDataRecordset.getField("city"));
				ActivityTestData.STATE = (moduleTestDataRecordset.getField("state"));
				ActivityTestData.POSTALCODE = (moduleTestDataRecordset.getField("postalCode"));
				ActivityTestData.PARTNER_COUNTRY = (moduleTestDataRecordset.getField("partnerCountry"));
				ActivityTestData.INDICATOR_CODE = (moduleTestDataRecordset.getField("IndicatorCode"));
				ActivityTestData.BASELINE_VALUE = (moduleTestDataRecordset.getField("BaseLineValue"));
				ActivityTestData.BASELINE_MONTH= (moduleTestDataRecordset.getField("BaseLineMonth"));
				ActivityTestData.BASELINE_YEAR = (moduleTestDataRecordset.getField("BaseLineYear"));
				ActivityTestData.BASELINE_DESC = (moduleTestDataRecordset.getField("BaseLineDesc"));
				ActivityTestData.DISTRIBUTIONCODE = (moduleTestDataRecordset.getField("DistribtionCode"));
				ActivityTestData.SOURCE_OF_FUND = (moduleTestDataRecordset.getField("SourceOfFunding"));
				ActivityTestData.EXTERNAL_SOURCENAME = (moduleTestDataRecordset.getField("ExternalSouceName"));
				ActivityTestData.FUNDING_OFFICE = (moduleTestDataRecordset.getField("FundingOffice"));
				ActivityTestData.FUNDACCOUNT = (moduleTestDataRecordset.getField("FundAccount"));
				ActivityTestData.PHONIXACCOUNT = (moduleTestDataRecordset.getField("PhonixAccount"));
				ActivityTestData.CATEGORY = (moduleTestDataRecordset.getField("Category"));
				ActivityTestData.PROGAM_AREA = (moduleTestDataRecordset.getField("ProgramArea"));
				ActivityTestData.PROGAM_ELEMENT = (moduleTestDataRecordset.getField("ProgramElement"));
				ActivityTestData.FUNDING_TYPE = (moduleTestDataRecordset.getField("FundingType"));
				ActivityTestData.ACTION = (moduleTestDataRecordset.getField("Action"));
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateindicatorTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			
			System.out.println("strQuery populateindicatorTestData "+strQuery);
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				IndicatorTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				IndicatorTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				IndicatorTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				IndicatorTestData.LINK_REF = (moduleTestDataRecordset.getField("LINK_REF"));
				IndicatorTestData.NAME_OF_INDICATOR = (moduleTestDataRecordset.getField("indicatorName"));
				IndicatorTestData.CUSTOM_STATUS = (moduleTestDataRecordset.getField("customStatus"));
				IndicatorTestData.CATEGORY = (moduleTestDataRecordset.getField("category"));
				IndicatorTestData.AREA = (moduleTestDataRecordset.getField("area"));
				IndicatorTestData.ELEMENT = (moduleTestDataRecordset.getField("element"));
				IndicatorTestData.DATATYPE = (moduleTestDataRecordset.getField("dataType"));
				IndicatorTestData.DISAGGREGATOR = (moduleTestDataRecordset.getField("disaggregator"));
				IndicatorTestData.UNITOF_MEASURE = (moduleTestDataRecordset.getField("unitOfMeasure"));
				IndicatorTestData.PPRYEAR = (moduleTestDataRecordset.getField("pprYear"));
				IndicatorTestData.PMPYEAR = (moduleTestDataRecordset.getField("pmpYear"));
				IndicatorTestData.DEFINITIATION = (moduleTestDataRecordset.getField("definitiation"));
				IndicatorTestData.RFI = (moduleTestDataRecordset.getField("rfi"));
				IndicatorTestData.RESPONSIBLE_USAID = (moduleTestDataRecordset.getField("responsibleUSAId"));
				IndicatorTestData.REPORTING_FREQUENCY = (moduleTestDataRecordset.getField("reportingFreq"));
				IndicatorTestData.DATASOURCE = (moduleTestDataRecordset.getField("dataSource"));
				IndicatorTestData.COLLECTION_CONST = (moduleTestDataRecordset.getField("collectionConst"));
				IndicatorTestData.TIME_FRAME = (moduleTestDataRecordset.getField("timeFrame"));
				IndicatorTestData.BASELINE_MONTH = (moduleTestDataRecordset.getField("baseLineMonth"));
				IndicatorTestData.BASELINE_YEAR = (moduleTestDataRecordset.getField("baseLineYear"));
				IndicatorTestData.RATIONAL_TARGET = (moduleTestDataRecordset.getField("rationalTarget"));
				IndicatorTestData.PIRS_OPTIONS = (moduleTestDataRecordset.getField("PIRSOptions"));
				IndicatorTestData.TYPE = (moduleTestDataRecordset.getField("Type"));
				IndicatorTestData.VALUE = (moduleTestDataRecordset.getField("Value"));
				IndicatorTestData.TAGS = (moduleTestDataRecordset.getField("Tags"));
			//	IndicatorTestData.STANDRD_FA_INDICATOR = (moduleTestDataRecordset.getField("StandardFAIndicator"));
				IndicatorTestData.ORIGIN = (moduleTestDataRecordset.getField("Origin"));
				IndicatorTestData.OLD_INDICATOR_CODE = (moduleTestDataRecordset.getField("OldIndicatorCode"));
				IndicatorTestData.INDICATOR_CODE = (moduleTestDataRecordset.getField("IndicatorCode"));
				IndicatorTestData.ACTION= (moduleTestDataRecordset.getField("Action"));
				IndicatorTestData.HEADERDATA= (moduleTestDataRecordset.getField("HeaderData"));
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateStratigesTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				StrategiesTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				StrategiesTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				StrategiesTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				StrategiesTestData.GOALSTMT = (moduleTestDataRecordset.getField("GoalStmt"));
				StrategiesTestData.ACTIONS = (moduleTestDataRecordset.getField("Actions"));
				StrategiesTestData.STARTDATE = (moduleTestDataRecordset.getField("StartDate"));
				StrategiesTestData.ENDDATE = (moduleTestDataRecordset.getField("EndDate"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateReportsTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				ReportsTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				ReportsTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				ReportsTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				ReportsTestData.REPORTNAME = (moduleTestDataRecordset.getField("ReportName"));
				ReportsTestData.FISCAL_YEAR = (moduleTestDataRecordset.getField("FiscalYear"));
				ReportsTestData.REASONFOR_UNLOCKING = (moduleTestDataRecordset.getField("ReasonForUnlock"));
				ReportsTestData.STATUS = (moduleTestDataRecordset.getField("Status"));
				ReportsTestData.PROJECT_NAMES = (moduleTestDataRecordset.getField("projectNames"));
				ReportsTestData.OFFICE_NAMAES = (moduleTestDataRecordset.getField("officeNames"));
				ReportsTestData.PARTNER_NAMAES = (moduleTestDataRecordset.getField("PartnerName"));
				ReportsTestData.ACTIVITY_STATUS = (moduleTestDataRecordset.getField("ActivityStatus"));
				ReportsTestData.OPERATING_UNIT = (moduleTestDataRecordset.getField("OperatingUnit"));
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateProjectTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				ProjectTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				ProjectTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				ProjectTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				ProjectTestData.LINK_REF = (moduleTestDataRecordset.getField("LINK_REF"));
			    ProjectTestData.PROJECT_NAME = (moduleTestDataRecordset.getField("projectName"));
			    ProjectTestData.OFFICE_NAME = (moduleTestDataRecordset.getField("officeName"));
			    ProjectTestData.START_DATE = (moduleTestDataRecordset.getField("startDate"));
			    ProjectTestData.END_DATE = (moduleTestDataRecordset.getField("endDate"));
			    ProjectTestData.STATUS = (moduleTestDataRecordset.getField("status"));
			    ProjectTestData.ESTIMATED_COST = (moduleTestDataRecordset.getField("estimatedCost"));
			    ProjectTestData.PROJECT_MANAGER = (moduleTestDataRecordset.getField("projectManager"));
			    ProjectTestData.DESCRIPTION = (moduleTestDataRecordset.getField("description"));
			    ProjectTestData.ACRONYM = (moduleTestDataRecordset.getField("acronym"));
			    ProjectTestData.ACTIONS = (moduleTestDataRecordset.getField("Actions"));
			    ProjectTestData.INDICATOR_CODE = (moduleTestDataRecordset.getField("IndicatorCode"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateAdminTesttData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				AdminTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				AdminTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				AdminTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				AdminTestData.LINK_REF = (moduleTestDataRecordset.getField("LINK_REF"));
			    AdminTestData.FIRSTNMAE = (moduleTestDataRecordset.getField("FirstName"));
			    AdminTestData.LASTNAME = (moduleTestDataRecordset.getField("LastName"));
			    AdminTestData.STATUS = (moduleTestDataRecordset.getField("Status"));
			    AdminTestData.EMAIL = (moduleTestDataRecordset.getField("Email"));
			    AdminTestData.AUTHENTICATE_BY = (moduleTestDataRecordset.getField("AuthenticatedBy"));
			    AdminTestData.ACTION = (moduleTestDataRecordset.getField("Action"));
			    AdminTestData.ROLES = (moduleTestDataRecordset.getField("Roles"));
			    AdminTestData.POSITION = (moduleTestDataRecordset.getField("Position"));
			    AdminTestData.ORGANIZATION = (moduleTestDataRecordset.getField("Organization"));
			    AdminTestData.EXPDATE = (moduleTestDataRecordset.getField("ExpDate"));
				
			    
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populatek2TravelData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				K2TestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				K2TestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				K2TestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				K2TestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				K2TestData.STATDATE = (moduleTestDataRecordset.getField("StartDate"));
				K2TestData.ENDDATE = (moduleTestDataRecordset.getField("EndDate"));
				K2TestData.JUSTIFICATION = (moduleTestDataRecordset.getField("Justification"));
				K2TestData.DESTINATION_COUNTRY = (moduleTestDataRecordset.getField("DestinationCountry"));
				K2TestData.TRAVELTYPE = (moduleTestDataRecordset.getField("TravelType"));
				K2TestData.UNIQNAME = (moduleTestDataRecordset.getField("UniqName"));
				K2TestData.TOTALCOST = (moduleTestDataRecordset.getField("TotalCost"));
				K2TestData.COMMENTS = (moduleTestDataRecordset.getField("Comments"));
				K2TestData.GMAILLOGINDETAILS = (moduleTestDataRecordset.getField("GmailLoginDetails"));
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
	
	public void populateBudgetTestData(String ModuleName, String JourneyID) {

		Connection moduleTestDataConnection = null;
		Recordset moduleTestDataRecordset = null;
		try {
			moduleTestDataConnection = fillo.getConnection(Constant.Path_TestData);
			String strQuery = "Select * from "+ ModuleName +" where Journey_ID='"+JourneyID+"'";
			moduleTestDataRecordset = moduleTestDataConnection.executeQuery(strQuery);
			while (moduleTestDataRecordset.next()) {
				BudgetTestData.JOURNEY_ID = (moduleTestDataRecordset.getField("Journey_ID"));
				BudgetTestData.LOGIN_DETAILS = (moduleTestDataRecordset.getField("LoginDetails"));
				BudgetTestData.TEST_DESCRIPTION = (moduleTestDataRecordset.getField("TEST_CASE_DESCRIPTION"));
				BudgetTestData.REPORT_NAME = (moduleTestDataRecordset.getField("Report_Name"));
			    BudgetTestData.ADD_REMOVE_COLUMNS = (moduleTestDataRecordset.getField("AddOrRemoveColumns"));
				
			}
		}catch (Exception e) {
			log.error("Failed to execute step", e);
		} finally {
			if(moduleTestDataConnection!=null) {
				moduleTestDataConnection.close();
			} else {
				log.error("Unable to create connection with Test Data sheet.");
			}
			if(moduleTestDataRecordset!=null){
				moduleTestDataRecordset.close();
			}
		}
	}
		
	public void executeFlowMethods(String testSuiteModuleName, String testSuiteJourneyFlowID) {
		try {
			testEligibleToSkip = false;
			//System.out.println("testSuiteJourneyFlowID.split(\",\")"+ testSuiteJourneyFlowID.split(","));
			for(String s:testSuiteJourneyFlowID.split(",")) {
				//System.out.println("testSuiteJourneyFlowID.split(\",\")"+ s);
			}
			String[] flowMethodDetails = testSuiteJourneyFlowID.split(",");
			for(int flowIndex=0; flowIndex<flowMethodDetails.length;flowIndex++) {				
				if(!testEligibleToSkip)
				{
					String[] flowMetodsWithFlags = flowMethodDetails[flowIndex].split("\\|\\|");
					System.out.println("flowID= " + flowMetodsWithFlags[0]);
					
					if(flowIndex>0 && FlowMethods.driver==null)
						break;
					Runner.execute_Actions(flowMetodsWithFlags[0], testSuiteModuleName);
				}
			}
		} catch (Exception e) {
			log.error("Failed to execute step", e);
		}
	}
	
	
	public void createTempFile(int iteration)
	{
		try {

			//Record Start Time
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd-HH-mm-ss");
			Date date = new Date();
			executionStartTime = dateFormat.format(date);

			if(Constant.Path_TestData.equalsIgnoreCase(""))
			{
				FileInputStream fileInputStream = new FileInputStream("./Config.properties");
				Config.load(fileInputStream);
				Constant.Path_TestData=Config.getProperty("Path_TestData").replace("':'", ":");
			}	

			//Connecting to Main Test Data Sheet and loading the content to required Objects
			System.out.println("Connecting to Main Test Data Sheet");
			XSSFWorkbook mainTestData_WorkBook = new XSSFWorkbook(new FileInputStream(Constant.Path_TestData));	
			mainTestData_WorkBook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);

			//Copying "Settings" sheet from Main Test Data Sheet to Newly created workbook
			XSSFSheet mainTD_SettingsSheet = mainTestData_WorkBook.getSheet("Settings");			
			XSSFWorkbook newlyCreated_TestData_WorkBook = new XSSFWorkbook();			
			XSSFSheet sheetOfSettings = newlyCreated_TestData_WorkBook.createSheet("Settings");
			newlyCreated_TestData_WorkBook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);

			//Code to move content inside Settings sheet Main Test Data Sheet to Newly created workbook
			for (int rowIndex=0;rowIndex <= mainTD_SettingsSheet.getLastRowNum(); rowIndex++ )
			{
				if(rowIndex>1)
					break;
				sheetOfSettings.createRow(rowIndex);
				for (int cellIndex=0;cellIndex < mainTD_SettingsSheet.getRow(rowIndex).getLastCellNum(); cellIndex++ )
				{
					Cell exictingCell = mainTD_SettingsSheet.getRow(rowIndex).getCell(cellIndex, Row.RETURN_NULL_AND_BLANK);
					if(!(exictingCell==null))
					{
						switch (exictingCell.getCellType()) {
						case Cell.CELL_TYPE_BOOLEAN:
							sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getStringCellValue());
							break;
						case Cell.CELL_TYPE_BLANK:
							sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue("");
							break;
						default:
							sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue("");
						}
					}
					else
						sheetOfSettings.getRow(sheetOfSettings.getLastRowNum()).createCell(cellIndex).setCellValue("");
				}
			}
			System.out.println("Settings Sheet Copied From Main WorkBook to New WorkBook");

			//Copy Header Row of TestSuite from main Test Data Sheet to Newly created workbook
			XSSFSheet sheetOfTestSuite = newlyCreated_TestData_WorkBook.createSheet("TestSuite") ;
			newlyCreated_TestData_WorkBook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);
			XSSFSheet mainTestSuite = mainTestData_WorkBook.getSheet("TestSuite");
			sheetOfTestSuite.createRow(0);

			//Fetch and Store indexes of required columns from TestSuite sheet. which will be used in further steps
			int executionStatusIndex =0;
			int moduleNameIndex =0;
			int journeyIDIndex =0;
			for (int cellIndex=0;cellIndex < mainTestSuite.getRow(0).getLastCellNum(); cellIndex++ )
			{
				Cell exictingCell = mainTestSuite.getRow(0).getCell(cellIndex, Row.RETURN_NULL_AND_BLANK);
				if(!(exictingCell==null))
				{
					switch (exictingCell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						sheetOfTestSuite.getRow(0).createCell(cellIndex).setCellValue(exictingCell.getStringCellValue());
						if(exictingCell.getStringCellValue().equalsIgnoreCase("Execution_Status"))
							executionStatusIndex = cellIndex;
						else if(exictingCell.getStringCellValue().equalsIgnoreCase("Journey_ID"))
							journeyIDIndex = cellIndex;
						else if(exictingCell.getStringCellValue().equalsIgnoreCase("Module_Name"))
						{
							moduleNameIndex = cellIndex;
							break;
						}
					default:
						System.out.println("Inside Test Suite Header Copy Section");
					}
				}
			}
			System.out.println("TestSuite Sheet - Header Section Copied From Main WorkBook to New WorkBook");

			//Keep list of distinct modules for which journeys are marked as YES
			ArrayList<String> Available_ModuleList = new ArrayList<String>();
			//Keep list of Journeys and corresponding modules for each journeys
			ArrayList<String> JourneyList = new ArrayList<String>();			
			//Moving "Yes" Marked Journeys from Main Test Suite to newly created test suite
			//Need to analyze this variable
			int testSuiteRowIndex = 0;
			for (int rowIndex=0;rowIndex <= mainTestSuite.getLastRowNum(); rowIndex++ )
			{
				if(mainTestSuite.getRow(rowIndex).getCell(executionStatusIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue().equalsIgnoreCase("Yes"))
				{
					sheetOfTestSuite.createRow(sheetOfTestSuite.getLastRowNum()+1);

					//Adding modules names to list - Only Distinct module names will be available in this list
					if(!Available_ModuleList.contains(mainTestSuite.getRow(rowIndex).getCell(moduleNameIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue()))
						Available_ModuleList.add(mainTestSuite.getRow(rowIndex).getCell(moduleNameIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue());
					//Add each Journey along with corresponding module name to the list (Format-JourneyID~ModuleName)
					JourneyList.add(mainTestSuite.getRow(rowIndex).getCell(journeyIDIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue() + "~" + mainTestSuite.getRow(rowIndex).getCell(moduleNameIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue());
					for (int cellIndex=0;cellIndex < mainTestSuite.getRow(rowIndex).getLastCellNum(); cellIndex++ )
					{
						Cell exictingCell = mainTestSuite.getRow(rowIndex).getCell(cellIndex, Row.RETURN_NULL_AND_BLANK);
						if(!(exictingCell==null))
						{
							switch (exictingCell.getCellType()) {
							case Cell.CELL_TYPE_BOOLEAN:
								sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getBooleanCellValue());
								break;
							case Cell.CELL_TYPE_NUMERIC:
								sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getNumericCellValue());
								break;
							case Cell.CELL_TYPE_STRING:
								sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getStringCellValue());
								break;
							case Cell.CELL_TYPE_BLANK:
								sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue("");
								break;
							default:
								sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue("");
							}
						}
						else
							sheetOfTestSuite.getRow(sheetOfTestSuite.getLastRowNum()).createCell(cellIndex).setCellValue("");
					}
					testSuiteRowIndex++;
				}
			}
			System.out.println("Required data - Only YES Marked Journeys - copied to TestSuite Sheet From Main WorkBook to New WorkBook");			

			//Update File Name Accordingly - To male filename unique
			String [] removeExtension=Constant.Path_TestData.split("\\.xlsx");
			String completeFileName=removeExtension[0];
			Random randomValue=new Random();
			double randomStr1=randomValue.nextInt(20000);
			String[] ipAddress = Inet4Address.getLocalHost().getHostAddress().split("\\.");
			String machine = "";
			if(ipAddress.length>0)
				machine = ipAddress[ipAddress.length-1];

			if(iteration==0)
				completeFileName = completeFileName + String.valueOf((int)randomStr1) + "_Machine_" + machine + "_Run_0.xlsx";
			else if(iteration>0)
				completeFileName = completeFileName.replace("_Run_" + (iteration-1), "_Run_" + iteration) + ".xlsx";
			else
				completeFileName = completeFileName + "_Run_" + iteration + ".xlsx";

			//Keep index of LINK_REF column and corresponding module name
			HashMap<String,Integer> mapLinkRefIndex = new HashMap<String,Integer>();
			//New instance of MAP for every iteration
			journey_LinkRef = new HashMap<String,String>();

			//Create Sheet for each module - newly created document - Also copy header to respective module sheet - Store index of "Link_Ref" column for each module 
			Iterator<String> moduleslistIterator = Available_ModuleList.iterator();
			while(moduleslistIterator.hasNext())
			{
				String currentModuleName = moduleslistIterator.next().trim();										
				newlyCreated_TestData_WorkBook.createSheet(currentModuleName);
				System.out.println("Sheet Created: " + currentModuleName + " in Iteration: " + iteration);
				XSSFSheet sheetofTD = mainTestData_WorkBook.getSheet(currentModuleName);
				XSSFSheet sheetofNewDoc = newlyCreated_TestData_WorkBook.getSheet(currentModuleName);				
				//Creating row for current module and copying header row from main workbook 
				sheetofNewDoc.createRow(0);
				for (int cellIndex=0;cellIndex < sheetofTD.getRow(0).getLastCellNum(); cellIndex++ )
				{
					Cell exictingCell = sheetofTD.getRow(0).getCell(cellIndex, Row.RETURN_NULL_AND_BLANK);
					if(!(exictingCell==null))
					{
						switch (exictingCell.getCellType()) 
						{
						case Cell.CELL_TYPE_BOOLEAN:
							sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue(exictingCell.getBooleanCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue(exictingCell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue(exictingCell.getStringCellValue());
							//Fetching and storing the index of LINK_REF for this module - which will be used in next steps
							if(exictingCell.getStringCellValue().equalsIgnoreCase("LINK_REF"))
								mapLinkRefIndex.put(currentModuleName, cellIndex);
							break;

						case Cell.CELL_TYPE_BLANK:
							sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue("");
							break;
						default:
							sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue("");
						}
					}
					else
						sheetofNewDoc.getRow(0).createCell(cellIndex).setCellValue("");
				}
			}
			System.out.println("New Sheet is created for each Module and respective header sections are copied From Main WorkBook to New WorkBook");

			//Copy test data for each YES marked journey from respective module sheet (MAIN workbook to Newly created workbook)
			Iterator<String> journeyvaluesIterator = JourneyList.iterator(); 
			int newRowIndex = 1;
			while(journeyvaluesIterator.hasNext()) 
			{
				String currentItem = journeyvaluesIterator.next();
				String currentJourney = currentItem.split("~")[0];
				String currentModule = currentItem.split("~")[1];
				XSSFSheet sheetofTD = mainTestData_WorkBook.getSheet(currentModule);
				XSSFSheet sheetofNewDoc = newlyCreated_TestData_WorkBook.getSheet(currentModule);				
				int k = 0;
				int JouneyIndex=0;
				for (int rowIndex=0;rowIndex <= sheetofTD.getLastRowNum(); rowIndex++ )
				{
					Cell journeyCell = sheetofTD.getRow(rowIndex).getCell(0, Row.CREATE_NULL_AS_BLANK);

					//Fetching the index of Journey_ID inside the sheet
					if(k==0)
					{
						journeyCell = sheetofTD.getRow(rowIndex).getCell(0, Row.CREATE_NULL_AS_BLANK);
						JouneyIndex = 0;
						//Check if first column of header is "Journey_ID" - else look for "Journey_ID" column in next cells - currently looking in first 3 cells						
						if(journeyCell.getCellType()==Cell.CELL_TYPE_NUMERIC)
						{
							journeyCell = sheetofTD.getRow(rowIndex).getCell(1, Row.CREATE_NULL_AS_BLANK);
							JouneyIndex = 1;
						}
						else if(!journeyCell.getStringCellValue().equalsIgnoreCase("Journey_ID"))
						{
							journeyCell = sheetofTD.getRow(rowIndex).getCell(1, Row.CREATE_NULL_AS_BLANK);
							JouneyIndex = 1;
						}
						//If First cell is not Journey_ID the Next cell will get loaded in previous condition - Check if this cell is Journey_ID else get next cell and assume it as Journey_ID 
						if(JouneyIndex==1)
						{
							if(journeyCell.getCellType()==Cell.CELL_TYPE_NUMERIC)
							{
								journeyCell = sheetofTD.getRow(rowIndex).getCell(2, Row.CREATE_NULL_AS_BLANK);
								JouneyIndex = 2;
							}
							else if(!journeyCell.getStringCellValue().equalsIgnoreCase("Journey_ID"))
							{
								journeyCell = sheetofTD.getRow(rowIndex).getCell(1, Row.CREATE_NULL_AS_BLANK);
								JouneyIndex = 2;
							}
						}
						k++;
					}					
					else
					{
						//Start fetching value from Journey_ID column and Comparing with our value - If matching pull the data to newly created sheet
						journeyCell = sheetofTD.getRow(rowIndex).getCell(JouneyIndex, Row.CREATE_NULL_AS_BLANK);
						if(journeyCell.getStringCellValue().equalsIgnoreCase(currentJourney))
						{
							boolean linkRefIdentified = false;
							sheetofNewDoc.createRow(sheetofNewDoc.getLastRowNum()+1);		
							for (int cellIndex=0;cellIndex < sheetofTD.getRow(rowIndex).getLastCellNum(); cellIndex++ )
							{					
								Cell exictingCell = sheetofTD.getRow(rowIndex).getCell(cellIndex, Row.CREATE_NULL_AS_BLANK);
								if(!(exictingCell==null))
								{
									switch (exictingCell.getCellType()) 
									{
									case Cell.CELL_TYPE_BOOLEAN:
										sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getBooleanCellValue());
										break;
									case Cell.CELL_TYPE_NUMERIC:
										sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getNumericCellValue());
										break;
									case Cell.CELL_TYPE_STRING:
										sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue(exictingCell.getStringCellValue());
										//If the index is "Link_Ref" for this journey - Pull Journey_ID and its Link_Ref
										//This will be used for marking failed cases and dependent journeys for next iterations
										if(cellIndex==mapLinkRefIndex.get(currentModule))
										{
											journey_LinkRef.put(currentJourney,exictingCell.getStringCellValue());
											linkRefIdentified = true;
										}
										break;
									case Cell.CELL_TYPE_BLANK:
										sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue("");
										break;
									default:
										sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue("");
									}
								}
								else
									sheetofNewDoc.getRow(sheetofNewDoc.getLastRowNum()).createCell(cellIndex).setCellValue("");
								//Start *********************************************

							}
							if(!linkRefIdentified)
								journey_LinkRef.put(currentJourney,"NA");
							newRowIndex ++;
							break;
						}
					}
				}
			}

			System.out.println("Test Data for all the YES marked journeys are copied From Main WorkBook to New WorkBook - Copying Process Successful");
			FileOutputStream fileOut = new FileOutputStream(completeFileName);
			newlyCreated_TestData_WorkBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			System.out.println("New File Generated Successfully and path: " + completeFileName);
			Constant.Path_TestData = completeFileName;
		}
		catch (Exception e) {
			log.error("Failed to create new Test data sheet with required content and execption is", e);
		} 
	}

	public static String getCurrentTimeStamp() 
	{
		String currentTime = "";
		try
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MMMM-dd-HH-mm-a");
			Date date = new Date();
			currentTime = dateFormat.format(date);			
		}
		catch (Exception e) 
		{
			System.out.println("Error Occured whhile Fetchiing Time Stamp");
		}		
		return currentTime;
	}
	
	public int markFailedCasesAlone()
	{
		int FailedCasesCount=0;
		try {
			//Connecting to TD Sheet and loading the content to required Objects
			XSSFWorkbook mainTD_WorkBook = new XSSFWorkbook(new FileInputStream(Constant.Path_TestData));	
			mainTD_WorkBook.setMissingCellPolicy(Row.RETURN_NULL_AND_BLANK);
			XSSFSheet mainTestSuite = mainTD_WorkBook.getSheet("TestSuite");

			//Store indexes of required columns from TestSuite sheet. which is required in further steps
			int testRunStatusIndex =0;
			int journeyIDIndex =0;
			int executionStatus =0;
			for (int cellIndex=0;cellIndex < mainTestSuite.getRow(0).getLastCellNum(); cellIndex++ )
			{
				Cell exictingCell = mainTestSuite.getRow(0).getCell(cellIndex, Row.RETURN_NULL_AND_BLANK);				
				switch (exictingCell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					if(exictingCell.getStringCellValue().equalsIgnoreCase("Test_Run_Status"))
						testRunStatusIndex = cellIndex;
					else if(exictingCell.getStringCellValue().equalsIgnoreCase("Journey_ID"))
						journeyIDIndex = cellIndex;
					else if(exictingCell.getStringCellValue().equalsIgnoreCase("Execution_Status"))
						executionStatus = cellIndex;
					else
						break;
				default:
					int test = 0;
				}
			}

			//Fetch List of Journeys which are failed in past execution
			ArrayList<String> failedJourneyList = new ArrayList<String>();
			for (int rowIndex=0;rowIndex <= mainTestSuite.getLastRowNum(); rowIndex++ )
			{
				if(mainTestSuite.getRow(rowIndex).getCell(testRunStatusIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue().equalsIgnoreCase("Failed"))
					failedJourneyList.add(mainTestSuite.getRow(rowIndex).getCell(journeyIDIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue());
			}

			System.out.println("Re Executable Journeys Addition Started");
			//Fetch All Dependent Journeys of Failed Cases
			ArrayList<String> reExecutableJourneysList = new ArrayList<String>();
			for(int failedJourneyIndex=0;failedJourneyIndex<failedJourneyList.size();failedJourneyIndex++)
			{
				String currentLinkRef = journey_LinkRef.get(failedJourneyList.get(failedJourneyIndex));

				if(!currentLinkRef.equalsIgnoreCase("") && !currentLinkRef.equalsIgnoreCase("NA"))
				{
					Iterator it = journey_LinkRef.entrySet().iterator();
					while(it.hasNext())
					{
						Map.Entry pair = (Map.Entry)it.next();
						if(String.valueOf(pair.getValue()).equalsIgnoreCase(currentLinkRef) && !reExecutableJourneysList.contains(String.valueOf(pair.getKey())))
						{
							reExecutableJourneysList.add(String.valueOf(pair.getKey()));
							System.out.println("Jorney Added :" + String.valueOf(pair.getKey()) + " And Link Ref :" + currentLinkRef);
						}
					}
				}
				else
				{
					reExecutableJourneysList.add(failedJourneyList.get(failedJourneyIndex));
					System.out.println("Jorney Added in NA Case :" + failedJourneyList.get(failedJourneyIndex) + " And Link Ref :" + currentLinkRef);
				}
			}

			//Mark all other Journeys as "NO"
			for (int rowIndex=1;rowIndex <= mainTestSuite.getLastRowNum(); rowIndex++ )
			{
				String currentJourney = mainTestSuite.getRow(rowIndex).getCell(journeyIDIndex, Row.RETURN_NULL_AND_BLANK).getStringCellValue();
				if(!reExecutableJourneysList.contains(currentJourney))
					mainTestSuite.getRow(rowIndex).createCell(executionStatus).setCellValue("NO");
			}

			//Get Count of failed cases needs to be executed
			if(reExecutableJourneysList.size()>0)
				FailedCasesCount = reExecutableJourneysList.size();
			FileOutputStream fileOut = new FileOutputStream(Constant.Path_TestData);
			mainTD_WorkBook.write(fileOut);
			fileOut.flush();
			fileOut.close();

			System.out.println("Failed Cases Marked Successfully - Failed and Dependant cases count : " + FailedCasesCount);
		}
		catch (Exception e) {
			log.error("Failed to mark YES for failed test cases", e);
		}

		return FailedCasesCount;
	}

	public static void clearTestDataFromVariables() 
	{
		try
		{			
			String[] testDataClassNamesList = testDataClassName.split("\\|\\|");			
			for(int classIndex=0; classIndex<testDataClassNamesList.length;classIndex++)
			{
				for(Field field : Class.forName("com.standardchartered.s2b.testData." + testDataClassNamesList[classIndex]).getFields())
				{
					field.set(Class.forName("com.standardchartered.s2b.testData." + testDataClassNamesList[classIndex]), "");
				}
			}
			System.out.println("Successfully Cleared Data in variables for module : " + testSuiteModuleName + "Classes : " + testDataClassName);
		}
		catch (Throwable e) 
		{
			log.error("Failed to Cleared Data in variables for modlue : " + testSuiteModuleName, e);
		}		
	}
	
	public static String fetchElementFromRecordSet(Recordset executionvariablesRecordset, String columnName)
	{
		String currentColumnValue = "";
		try
		{
			currentColumnValue = executionvariablesRecordset.getField(columnName);
		}
		catch(Exception e)
		{
			log.error("Column Missing in Excel : " + columnName, e);
		}	
		return currentColumnValue;
	}

    
}




	