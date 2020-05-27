package com.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import com.test.excelAPI.ExcelOperation;
import com.test.pages.ActivitiesPage;
import com.test.pages.AdminPage;
import com.test.pages.BudgetPage;
import com.test.pages.IndicatorPage;
import com.test.pages.K2WindowsSTSPage;
import com.test.pages.LoginPage;
import com.test.pages.MappingPage;
import com.test.pages.OverviewPage;
import com.test.pages.ProjectsPage;
import com.test.pages.ReportPage;
import com.test.pages.StrategiesPage;
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

public class FlowMethods {

	public static WebDriver driver;
	static  LoginPage login = new LoginPage();
	static ActionMethods actionMethod = new ActionMethods();
	public static Properties objectRepo = new Properties();
	public static OverviewPage overview = new OverviewPage();
	
	static
	{
		File src = new File("./ObjectRepo.properties");
		try
		{
			FileInputStream stream = new FileInputStream(src);
		    objectRepo.load(stream);
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	
	public static void login(String moduleName) throws Exception
	{
		try
		{
			System.out.println("FlowMethods");
			//boolean Do_you_wantRunOn_SingleBroser = true;
			if(driver==null)
			{
				System.out.println("in flow methods ");
				driver = actionMethod.openBrowser();
				actionMethod.launchURL(Constant.URL);
				System.out.println("Constant.URL" +Constant.URL);
				String loginDetails = TestDataObjectLogin.LOGIN_DETAILS;
				login.loginDISApplication(loginDetails.split("/")[0],loginDetails.split("/")[1].split("~")[1]);
			} else
			{
				/*
				 * if(!Do_you_wantRunOn_SingleBroser) { driver.quit(); driver =
				 * actionMethod.openBrowser(); actionMethod.launchURL(Constant.URL); String
				 * loginDetails = TestDataObjectLogin.LOGIN_DETAILS;
				 * login.loginDISApplication(loginDetails.split("/")[0],loginDetails.split("/")[
				 * 1].split("~")[1]); }
				 */
					System.out.println("User already Login into the application");
					String locator = objectRepo.getProperty("Header.Home");
					actionMethod.waitFor();
					if(actionMethod.isElementPresentOptional(locator))
					{
						actionMethod.waitFor();
						actionMethod.waitFor();
						overview.navigateToHomeScreen();
					}
					actionMethod.waitFor();
					
			}
			//Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			actionMethod.refreshBrowser();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"getScreenShot", driver);
			throw e;
		}
		
		
	}
	
	public void getLoginOTP(String moduleName) {
		try
		{
			actionMethod.launchChrome();
			String gmailLoginDetails = TestDataObjectLogin.LOGIN_DETAILS;
			login.loginGmailAccount(gmailLoginDetails.split("/")[0],gmailLoginDetails.split("/")[1].split("~")[0]);
			login.getOtP();
			actionMethod.closeWindow();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
		
	}
	
	public void createOffice(String moduleName)
	{
	
		OverviewPage overView = new OverviewPage();
		try
		{
			overView.createNewOffice(OverviewTestData.OFFICE_NAME, OverviewTestData.OFFICE_ACRONYM, OverviewTestData.STARTDATE, OverviewTestData.TEST_DESCRIPTION);
			overView.validateTheNewOffice();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void uploadStrategisDocuments(String moduleNmae)
	{
		StrategiesPage strategis = new StrategiesPage();
		try
		{
			strategis.navigteToStrategisScreen();
			strategis.uploadStrategiesDocument();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void editGoalStatement(String moduleNmae)
	{
		StrategiesPage strategis = new StrategiesPage();
		try
		{
			strategis.navigteToStrategisScreen();
			strategis.selectGoalStatement();
			if(StrategiesTestData.ACTIONS.equalsIgnoreCase("EditGoal"))
			{
				strategis.editGoalStatement(StrategiesTestData.GOALSTMT+" "+actionMethod.genaratePassword());
				return;
			} else if(StrategiesTestData.ACTIONS.equalsIgnoreCase("DiscardGoalStatement"))
			{
				strategis.discardGoalStatementChanges(StrategiesTestData.GOALSTMT+" "+actionMethod.genaratePassword(), "Yes");
				strategis.discardGoalStatementChanges(StrategiesTestData.GOALSTMT+" "+actionMethod.genaratePassword(), "No");
				return;
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void saveBudgetReportData(String moduleName)
	{
		BudgetPage budget = new BudgetPage();
		try
		{
			budget.saveBudgetReport(BudgetTestData.REPORT_NAME);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void createNewProject(String moduleNmae)
	{
		ProjectsPage project = new ProjectsPage();
		try
		{
			project.navigateToCreateProject();
			project.createProject(moduleNmae);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void addIndicatorToProject(String moduleName)
	{
		System.out.println("addIndicatorToProject");
		ProjectsPage project = new ProjectsPage();
		try
		{
			project.navigateToCreateProject();
			System.out.println("ProjectTestData.PROJECT_NAME"+ProjectTestData.PROJECT_NAME);
			project.searchProject(ProjectTestData.PROJECT_NAME);
			project.viewProject(ProjectTestData.PROJECT_NAME);
			project.selectMonitoringTab();
			project.addIndicatorCode(ProjectTestData.INDICATOR_CODE);
			project.saveIndicatorCodes("");
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void deLinkIndicatorFromProject(String moduleName)
	{
		ProjectsPage project = new ProjectsPage();
		try
		{
			System.out.println("Flow Methods deLinkIndicatorFromProject");
			project.selectLinkedIndicator();
			project.deLInkIndicatorCode(ProjectTestData.INDICATOR_CODE);
			project.validateDelinkSuccessMsg();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	
	public void createGlassActivity(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		try
		{
			activity.navigateToActivityScreen();
			activity.selectGlassActivity(ActivityTestData.ACTIVITY_NAME);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void addIndicatorsToActivity(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		ProjectsPage project = new ProjectsPage();
		try
		{
			activity.navigateToActivityScreen();
            actionMethod.searchProject(ActivityTestData.ACTIVITY_NAME);
			activity.selectActivityName(ActivityTestData.ACTIVITY_NAME);
			project.selectMonitoringTab();
			project.addIndicatorCode(ActivityTestData.INDICATOR_CODE);
			project.saveIndicatorCodes("");
			project.validateAddIndicatorMsg();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void addFundingStrip(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		try
		{
			activity.navigateToActivityScreen();
            actionMethod.searchProject(ActivityTestData.ACTIVITY_NAME);
			activity.selectActivityName(ActivityTestData.ACTIVITY_NAME);
			activity.navigateToFundingTab();
			actionMethod.waitFor();
			actionMethod.waitFor();
			actionMethod.waitFor();
			
			activity.ClickOnAddFundingStrip();
			activity.selectFundingOffice(ActivityTestData.FUNDING_OFFICE);
			activity.selectsSourceIfFunding(ActivityTestData.SOURCE_OF_FUND);
			actionMethod.waitFor();
			activity.selectExternalSourceName(ActivityTestData.EXTERNAL_SOURCENAME);
			System.out.println("After external source");
			activity.selectDistibutionCode(ActivityTestData.DISTRIBUTIONCODE);
			//activity.selectAppropriationAccount(ActivityTestData.FUNDACCOUNT);
			activity.selectFundAccount(ActivityTestData.FUNDACCOUNT);
			activity.selectPhonixCode(ActivityTestData.PHONIXACCOUNT);
			activity.selectCategory(ActivityTestData.CATEGORY);
			activity.selectProgramArea(ActivityTestData.PROGAM_AREA);
			activity.selectProgramEleemnt(ActivityTestData.PROGAM_ELEMENT);
		
			activity.selectFundingType(ActivityTestData.FUNDING_TYPE);
			activity.selectDirectAndPdl();
			
			activity.saveBaseLineData();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			System.out.println("came into exception");
			activity.discardChanges();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void deLinkIndicatorsToActivity(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		ProjectsPage project = new ProjectsPage();
		try
		{
			activity.navigateToActivityScreen();
			actionMethod.searchProject(ActivityTestData.ACTIVITY_NAME);
			activity.selectActivityName(ActivityTestData.ACTIVITY_NAME);
			project.selectMonitoringTab();
            project.selectLinkedIndicator();
			project.deLInkIndicatorCode(ActivityTestData.INDICATOR_CODE);
			project.validateDelinkSuccessMsg();
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void addActivityBaselineData(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		ProjectsPage project = new ProjectsPage();
		try
		{
			activity.navigateToActivityScreen();
			actionMethod.searchProject(ActivityTestData.ACTIVITY_NAME);
			activity.selectActivityName(ActivityTestData.ACTIVITY_NAME);
			project.selectMonitoringTab();
            activity.selectBaseLineTab();
            activity.selectActivityName(ActivityTestData.INDICATOR_CODE);
			activity.enterBaseLineValue(ActivityTestData.BASELINE_VALUE);
			activity.selectBaseLineDate(ActivityTestData.BASELINE_MONTH);
			activity.selectBaseLineYear(ActivityTestData.BASELINE_YEAR);
			activity.enterbaseLineInfo(ActivityTestData.BASELINE_DESC);
			activity.saveBaseLineData();
			activity.validateBaselineInfoMsg();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			activity.discardChanges();
		}
	}
	
	
	public void editNonAddGlassActivity(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		try
		{
			actionMethod.searchProject(ActivityTestData.ACTIVITY_NAME);
			activity.selectActivityName(ActivityTestData.ACTIVITY_NAME);
			activity.editActivity();
			activity.editAwardName(ActivityTestData.AWARD_NAME+actionMethod.genarateRandomNumber(4));
			if(ActivityTestData.ACTION.equalsIgnoreCase("Save"))
			{
				activity.saveBaseLineData();
				activity.validateBaselineInfoMsg();
			} else if(ActivityTestData.ACTION.equalsIgnoreCase("Cancel"))
			{
				activity.selectCANCELBtn();
			}
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			activity.discardChanges();
		}
	}
	
	
	public void editTheActivityFields(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		ExcelOperation excelOpr = new ExcelOperation();
		try
		{
			activity.navigateToActivityScreen();
			activity.selectAwardDetails();
			String activityName = ActivityTestData.ACTIVITY_NAME+actionMethod.genarateRandomNumber(5);
			excelOpr.updateReferencevalue(moduleName, "activityName", activityName, ActivityTestData.LINK_REF);
			activity.addActivityDetails(activityName);
			activity.addUSAIDOfficeDetails();
			activity.addPartnerOrganization();
			activity.enterPartnerName(ActivityTestData.COP_NAME);
			activity.enterPartnerEmail(ActivityTestData.COPEMAIL);
			activity.enterPartnerAddres(ActivityTestData.COP_ADDRESS);
			activity.selectPhoneNum(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			activity.selectPhoneNum2(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			activity.saveNonGlassAcivityDetails();
			//Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			activity.discardChanges();
		}
	}
	
	
	public void manageCustomIndicator(String moduleName)
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage OU Indicators - Custom");
			IndicatorTestData.NAME_OF_INDICATOR = IndicatorTestData.NAME_OF_INDICATOR+actionMethod.genarateRandomNumber(4);
			indicator.createCustomIndicator(IndicatorTestData.NAME_OF_INDICATOR, IndicatorTestData.CUSTOM_STATUS);
			indicator.selectCategory(IndicatorTestData.CATEGORY);
			indicator.selectArea(IndicatorTestData.AREA);
			indicator.selectElement(IndicatorTestData.ELEMENT);
			indicator.selectdataType(IndicatorTestData.DATATYPE);
			indicator.enterDescriptionData(IndicatorTestData.DISAGGREGATOR, IndicatorTestData.UNITOF_MEASURE);
			indicator.selectPPRIndicator(IndicatorTestData.PPRYEAR);
			indicator.selectPMPIndicator(IndicatorTestData.PMPYEAR);
			indicator.enterdefination(IndicatorTestData.DEFINITIATION, IndicatorTestData.RFI);
			indicator.dataCollectionByUSAID(IndicatorTestData.RESPONSIBLE_USAID, IndicatorTestData.REPORTING_FREQUENCY, IndicatorTestData.DATASOURCE, IndicatorTestData.COLLECTION_CONST);
			indicator.targetAndBaseLine(IndicatorTestData.BASELINE_MONTH, IndicatorTestData.BASELINE_YEAR, IndicatorTestData.RATIONAL_TARGET);
			indicator.saveCustomIndicatorData();
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void validateStandardFAIndicatorScreen(String moduleName)
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage OU Indicators - Standard FA");
			indicator.selectStandardFACategory(IndicatorTestData.CATEGORY);
			indicator.validateStandrdFAHeader(IndicatorTestData.HEADERDATA);
			
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void downloadManageIndicatorReport(String moduleName)
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage OU Indicator");
			indicator.selectIndicatorCode(IndicatorTestData.NAME_OF_INDICATOR);
			indicator.changeIndicatorAttributes(IndicatorTestData.PMPYEAR);
			indicator.selectDisaggregates("Add Custom Disaggregates");
			indicator.clickOnSaveBtn();
			indicator.viewOrEditIndicatorCode(IndicatorTestData.NAME_OF_INDICATOR, IndicatorTestData.PIRS_OPTIONS);
			indicator.downloadReport();
		}  catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void validateIndicatorDetails(String moduleName)
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage OU Indicators - Standard FA");
			indicator.selectIndicator(IndicatorTestData.INDICATOR_CODE);
			indicator.getIndicatorCode(IndicatorTestData.INDICATOR_CODE);
			indicator.getOldIndicatorCode(IndicatorTestData.OLD_INDICATOR_CODE);
			indicator.getIndicatorName(IndicatorTestData.NAME_OF_INDICATOR);
			indicator.getIndicatorDataTYpe(IndicatorTestData.DATATYPE);
			indicator.getIndicatorOrigin(IndicatorTestData.ORIGIN);
			indicator.getStandardFAIndiStatus(IndicatorTestData.CUSTOM_STATUS);
			indicator.getReportFreq(IndicatorTestData.REPORTING_FREQUENCY);
			indicator.getTags(IndicatorTestData.TAGS);
			indicator.validatePPR_PMP_IndicatorNames();
			indicator.cancelStandrdFAScreen();
		}  catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void lockAndUnlockReports(String moduleName)
	{
		ReportPage reports = new ReportPage();
		try
		{
			reports.selectPreferableReport(ReportsTestData.REPORTNAME);
			reports.selectFiscalYear(ReportsTestData.FISCAL_YEAR);
			reports.lockAndUnlockPPRReport(ReportsTestData.REASONFOR_UNLOCKING, ReportsTestData.STATUS);
		}
		catch (Exception e) 
		{
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void createNewDisaggragate(String moduleName) throws Exception
	{
		IndicatorPage indicator = new IndicatorPage();
		ExcelOperation excelOpr = new ExcelOperation();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage Disaggregate");
			actionMethod.waitFor();
			indicator.createCustomDisaggretes();
			IndicatorTestData.NAME_OF_INDICATOR = IndicatorTestData.NAME_OF_INDICATOR+actionMethod.genarateRandomNumber(5); 
			excelOpr.updateReferencevalue(moduleName, "indicatorName",IndicatorTestData.NAME_OF_INDICATOR, IndicatorTestData.LINK_REF);
			indicator.enterCustomDisaggreteName(IndicatorTestData.NAME_OF_INDICATOR);
			indicator.selectdisaggregateTypeAndDataType(IndicatorTestData.TYPE, IndicatorTestData.DATATYPE);
			indicator.enterDisaggreteDesciption(IndicatorTestData.TEST_DESCRIPTION);
			
			/* Disaggregate Max Value length is 20, more then should not allow */
			
			if(IndicatorTestData.ACTION.equalsIgnoreCase("Custom_OU_Disaggregate_Value_Length"))
			{
				for(int i=0;i<21;i++)
				{
					IndicatorTestData.VALUE = ""+actionMethod.genarateRandomNumber(4);
					indicator.enterDisaggreteValue(IndicatorTestData.VALUE);
				}
                indicator.validateDisaggregateValueLength();
                indicator.clickOnDisaggragetCreateBtn();
                return;
			}
			IndicatorTestData.VALUE = IndicatorTestData.VALUE+actionMethod.genarateRandomNumber(4);
			excelOpr.updateReferencevalue(moduleName, "Value",IndicatorTestData.VALUE, IndicatorTestData.LINK_REF);
			indicator.enterDisaggreteValue(IndicatorTestData.VALUE);
			indicator.clickOnDisaggragetCreateBtn();
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateTheNewDisaggragate(String moduleName) throws Exception
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage Disaggregate");
			/*
			 * After create Disaggregate then validate in OU indicator Page based to filter
			 * base
			 */

			if(IndicatorTestData.ACTION.equalsIgnoreCase("VerifyNewDisaggregateIndicator"))
			{
				indicator.selectDisaggragetType(IndicatorTestData.TYPE);
				indicator.selectDisaggragetdataType(IndicatorTestData.DATATYPE);
				indicator.selectDisaggragetSelectTypeStatus(IndicatorTestData.CUSTOM_STATUS);
				indicator.selectDisaggragetSearchByName(IndicatorTestData.NAME_OF_INDICATOR);
				indicator.validateDisaggregateName(IndicatorTestData.NAME_OF_INDICATOR);
				
			}/* Validating custom OU Disaggregate Values */ 
			else if(IndicatorTestData.ACTION.equalsIgnoreCase("DuplicateValues"))
			{
				indicator.selectIndicatorCode(IndicatorTestData.NAME_OF_INDICATOR);
				indicator.selectdisaggregateTypeAndDataType(IndicatorTestData.TYPE, IndicatorTestData.DATATYPE);
				indicator.enterDisaggreteValue(IndicatorTestData.VALUE);
				indicator.validateDuplicateDisaggreteValue();
				indicator.clickOnDisaggragetCreateBtn();
			}
			
		}
		catch (Exception e) 
		{   Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	
	}
	
	public void deactivateAndDeleteManageDissagrgator(String moduleName) throws Exception
	{
		IndicatorPage indicator = new IndicatorPage();
		try
		{
			indicator.navigateToIndicatorSubTask("Manage Disaggregate");
			indicator.selectDisaggragetSearchByName(IndicatorTestData.NAME_OF_INDICATOR);
			indicator.deactivateManageCustomDisaggregateName(IndicatorTestData.NAME_OF_INDICATOR);
			indicator.entercoomentsforDeactivate();
			if(IndicatorTestData.ACTION.contains("Delete"))
			{
				indicator.selectDisaggragetSearchByName(IndicatorTestData.NAME_OF_INDICATOR);
				indicator.deleteManageCustomDisaggregateName(IndicatorTestData.NAME_OF_INDICATOR);
			}
				
			
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	
	}
	
	
	public void validateTheCurrentYearReport(String moduleName) throws Exception
	{
		ReportPage reports = new ReportPage();
		try
		{
			reports.selectPreferableReport(ReportsTestData.REPORTNAME);
			reports.selectFiscalYear(ReportsTestData.FISCAL_YEAR);
			reports.selectFiscalYearReport();
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	
	}
	
	public void mappingGISLayerFileTagSameFile(String moduleName) throws Exception
	{
		MappingPage mapping = new MappingPage();
		try
		{
			mapping.navigateToMappingScreen();
			Thread.sleep(2000);
	        String userName = System.getProperty("user.name");
			mapping.uploadDocument("C:\\Users\\"+userName+"\\Desktop\\access-to-telecommunication-systems-2018.csv");
			mapping.tagMyGisFile(MappingTestData.GIS_LAYERTYPE);
			MappingTestData.LAYER_NAME = MappingTestData.LAYER_NAME+actionMethod.genarateRandomNumber(4);
			mapping.enterLayerName(MappingTestData.LAYER_NAME);
			MappingTestData.LAYER_DESCR = MappingTestData.LAYER_DESCR+actionMethod.genarateRandomNumber(4);
			mapping.enterLayerDesciption(MappingTestData.LAYER_DESCR);
			mapping.sourceDate(MappingTestData.SOURCE_DATE);
			mapping.typeOfGeographicLayer(MappingTestData.GEOGRAPHICLAYER);
			MappingTestData.SOURCE = MappingTestData.SOURCE+actionMethod.genarateRandomNumber(3);
			mapping.enterSource(MappingTestData.SOURCE);
			mapping.saveGisFileContent();
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	
	}
	
	public void exportToExcelofPMPReport(String moduleName) throws Exception
	{
		ReportPage report = new ReportPage();
		try
		{
			report.selectPreferableReport(ReportsTestData.REPORTNAME);
			report.selectPMPOffices(ReportsTestData.OFFICE_NAMAES);
			report.selectPMPProjects(ReportsTestData.PROJECT_NAMES);
			report.runReport();
			report.report_ExcelToExport();
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void createNonAddGlassActivity(String moduleName)
	{
		ActivitiesPage activity = new ActivitiesPage();
		ExcelOperation excelOpr = new ExcelOperation();
		try
		{
			activity.navigateToActivityScreen();
			activity.selectAwardDetails();
			String activityName = ActivityTestData.ACTIVITY_NAME+actionMethod.genarateRandomNumber(5);
			excelOpr.updateReferencevalue(moduleName, "activityName", activityName, ActivityTestData.LINK_REF);
			activity.addActivityDetails(activityName);
			activity.addUSAIDOfficeDetails();
			activity.addPartnerOrganization();
			activity.enterPartnerName(ActivityTestData.COP_NAME);
			activity.enterPartnerEmail(ActivityTestData.COPEMAIL);
			activity.enterPartnerAddres(ActivityTestData.COP_ADDRESS);
			System.out.println(ActivityTestData.COP_COUNTRY+ActivityTestData.COP_PHONE+ActivityTestData.COP_EXTN);
			activity.selectPhoneNum(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			System.out.println(ActivityTestData.COP_COUNTRY+ActivityTestData.COP_PHONE+ActivityTestData.COP_EXTN);
			activity.selectPhoneNum2(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			activity.saveNonGlassAcivityDetails();
			//Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			activity.discardChanges();
		}
	}
	
	public void selectOUPortfolioReport(String moduleName) throws Exception
	{
		ReportPage report = new ReportPage();
		try
		{
			report.selectPreferableReport(ReportsTestData.REPORTNAME);
			report.selectOperationUnit(ReportsTestData.OPERATING_UNIT);
			report.selectOfficeName(ReportsTestData.OFFICE_NAMAES);
			report.selectPartnerName(ReportsTestData.PARTNER_NAMAES);
			report.activityStatus(ReportsTestData.ACTIVITY_STATUS);
			report.runPortfioliReport();
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void addOrRemoveBudgetReportColumns(String moduleName) throws Exception
	{
		BudgetPage budget = new BudgetPage();
		try
		{
			budget.navigateToBudgetScreen();
		//	budget.navigateToStandardBudgetReport();
			budget.SelectAddOrRemoveColumn();
			budget.SelectAddOrRemoveCheckBoxColumns(BudgetTestData.ADD_REMOVE_COLUMNS);
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void addBudgetReportColumns(String moduleName) throws Exception
	{
		BudgetPage budget = new BudgetPage();
		try
		{
			budget.navigateToBudgetScreen();
			budget.SelectAddOrRemoveColumn();
			budget.SelectAddOrRemoveCheckBoxColumns(BudgetTestData.ADD_REMOVE_COLUMNS);
			budget.validateTheNewleyAddedColumnsList(BudgetTestData.ADD_REMOVE_COLUMNS);
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void removeBudgetReportColumns(String moduleName) throws Exception
	{
		BudgetPage budget = new BudgetPage();
		try
		{
			budget.navigateToBudgetScreen();
			budget.SelectAddOrRemoveColumn();
			budget.SelectAddOrRemoveCheckBoxColumns(BudgetTestData.ADD_REMOVE_COLUMNS);
			budget.validateTheRemovedColumnsList(BudgetTestData.ADD_REMOVE_COLUMNS);
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void LoginK2App(String moduleName)
	{
		K2WindowsSTSPage k2Login = new K2WindowsSTSPage();
		try
		{
			//
			//zhussin@usaid.gov
			//Password:Azraq4595@#!!
			//https://zhussin@usaid.gov:Azraq4595@#!!@
			String appUrl = "https://dis2dev.usaid.gov/Runtime/_trust/Login.aspx?ReturnUrl=%2fRuntime%2fRuntime%2fForm%2fcom.K2.App.TravelRequest.Form.Landing%2f";
			//driver = actionMethod.launchChrome(Constant.driverPath_chrome, appUrl);
			driver = actionMethod.openBrowser();
			actionMethod.launchURL(appUrl);
			k2Login.navigateToK2Application("USAIDAzureId");
			String loginDetails = TestDataObjectLogin.LOGIN_DETAILS;
			k2Login.loginIntoUSAIdAzureId(loginDetails.split("/")[0],loginDetails.split("/")[1].split("~")[1]);
			
			
		} catch(Exception e)
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void k2Windows(String moduleName)
	{
		K2WindowsSTSPage k2Windows = new K2WindowsSTSPage();
		try
		{
		//	String appUrl = "https://dis2dev.usaid.gov/Runtime/_trust/Login.aspx?ReturnUrl=%2fRuntime%2fRuntime%2fForm%2fcom.K2.App.TravelRequest.Form.Landing%2f";
		//	K2.launchChrome(Constant.driverPath_chrome, appUrl);
			//k2Windows.navigateToK2Application("USAIDAzureId");  //K2_Windows
			
			k2Windows.selectTravelREquest();
			k2Windows.selectTravelType(K2TestData.TRAVELTYPE);
			k2Windows.selectDEstCountry(K2TestData.DESTINATION_COUNTRY);
			k2Windows.selectStartDate(K2TestData.STATDATE);
			k2Windows.selectEndDate(K2TestData.ENDDATE);
			k2Windows.enterJustification(K2TestData.JUSTIFICATION);
			k2Windows.enterTotalCost(K2TestData.TOTALCOST);
            k2Windows.enterUniqName(actionMethod.genarateRandomNumber(4)+K2TestData.UNIQNAME);
            k2Windows.enterComments(K2TestData.COMMENTS);
            k2Windows.selectAction("");
			
			
		} catch (Exception e) {
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void validateAdminDetails(String moduleName) throws Exception
	{
		AdminPage admin = new AdminPage();
		try
		{
			admin.navigatToAdminPage();
			admin.validateMyProfileDetails();
			admin.validateAdminRoles(AdminTestData.ROLES);
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void validateAdminRolesAndnavigateToAddRoles(String moduleName) throws Exception
	{
		AdminPage admin = new AdminPage();
		try
		{
			admin.navigatToAdminPage();
			admin.selectdminRolesAndAddRoles(AdminTestData.ROLES);
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			throw e;
		}
	}
	
	public void addImplementingPartner(String moduleName)
	{
		AdminPage admin = new AdminPage();
		try
		{
			admin.navigatToAdminPage();
			admin.navigatToManageUsers();
			admin.navigatToNonUsaIdEmployee();
			admin.navigatToaddAnImplementingPartner();
			admin.enterNonUsaIdEmployeeDetails1(AdminTestData.FIRSTNMAE, AdminTestData.LASTNAME, AdminTestData.EMAIL);
			admin.enterNonUsaIdEmployeeDetails2(AdminTestData.POSITION, AdminTestData.ORGANIZATION);
		    admin.addAccountExpiryDate(AdminTestData.EXPDATE);	
		    if(AdminTestData.ACTION.equalsIgnoreCase("Save"))
		    	admin.saveImplementingPartnerDetails();
		    else
		    {
		    	admin.unsavedChanges();
		    }
		    
		}
		catch (Exception e) 
		{
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
}
