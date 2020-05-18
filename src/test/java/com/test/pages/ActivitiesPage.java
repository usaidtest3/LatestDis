package com.test.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.controller.ActionMethods;
import com.test.controller.FlowMethods;
import com.test.controller.Report;
import com.test.excelAPI.ExcelOperation;
import com.test.testData.ActivityTestData;
import com.test.utils.Constant;

public class ActivitiesPage extends FlowMethods{
	
	Logger log = LogManager.getLogger(ActionMethods.class);	
	ActionMethods actionMethods = new ActionMethods();
	ExcelOperation excelOperation = new ExcelOperation();
	ProjectsPage project;
	public static String activityName = "";
	public static String activityAcronym = "";
	
	public void selectGlassActivity(String activityName)
	{
		String locator = "";
		try
		{
           actionMethods.isAlertPresent("accept");
           selectOngoingTab();
           locator = objectRepo.getProperty("GlaasActivities.SelecctGlaasActivity");
           actionMethods.click(locator);
           locator = objectRepo.getProperty("GlassActivities.AwardRadiBtn");
           actionMethods.click(locator);
           locator = objectRepo.getProperty("GlaasActivities.SelectBtn");
           actionMethods.click(locator);
           actionMethods.waitFor();
           locator = objectRepo.getProperty("GlaasActivities.ActivityMessageHeader");
           if(actionMethods.isElementPresent(locator))
           {
        	   addActivityDetails(activityName);
        	   addUSAIDOfficeDetails();
        	   enterPartnerName(ActivityTestData.COP_NAME);
        	   enterPartnerEmail(ActivityTestData.COPEMAIL);
        	   enterPartnerAddres(ActivityTestData.COP_ADDRESS);
        	   selectPhoneNum(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
        	   saveNonGlassAcivityDetails();
        	  
           } else
        	   System.out.println("CREATE AN ACTIVITY BASED ON GLAAS AWARD : not presented ");
           
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Create glass activity successfully created", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void selectActivityName(String activitynmae)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Dis.CommonText");
			actionMethods.click(locator.replace("Dummy", activitynmae));
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User selected ActivityName : "+activitynmae, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void selectOngoingTab()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Activity.OngoingTab");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User navigated to OngoingTab", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void addActivityDetails(String activityName1)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("GlaasActivities.activityName");
			actionMethods.enterInputMandatoryFiled(locator, activityName1);
			locator = objectRepo.getProperty("GlaasActivities.activityAcronym");
			activityAcronym = ActivityTestData.ACTIVITY_ACRONYM+actionMethods.genarateRandomNumber(4);
			actionMethods.enterInputMandatoryFiled(locator, activityAcronym);
			locator = objectRepo.getProperty("GlaasActivities.activityDescription");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.ACTIVITY_DESCRIPTION);
			locator = objectRepo.getProperty("GlaasActivities.publicDescription");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.PUBLIC_DESCRIPTION);
			locator = objectRepo.getProperty("GlaasActivities.activityStatus");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("GlaasActivities.activityStatusdropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.ACTIVITY_STATUS);
			locator = objectRepo.getProperty("GlaasActivities.reportedBy");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("GlaasActivities.reportedByDropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.REPORTED_BY);
			if(ActivityTestData.ACTIVITY_TYPE.contains("Non-Glass"))
			{
				locator = objectRepo.getProperty("NonGlassActivity.estimatedCost");
				actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.ESTIMATED_COST+actionMethods.genarateRandomNumber(2));
				locator = objectRepo.getProperty("NonGlassActivity.awardType");
				actionMethods.click(locator);
				locator = objectRepo.getProperty("NonGlassActivity.awardtYpeDropdown");
				actionMethods.selectDropDownData(locator, ActivityTestData.AWARDTYPE);
			}
			locator = objectRepo.getProperty("GlaasActivities.YesButton");
			actionMethods.click(locator);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ActivityName :"+activityName, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void addUSAIDOfficeDetails()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("UsaIdOffice.selectOffice");
			actionMethods.scrollToElement(locator);
	//		actionMethods.click(locator);
			locator = objectRepo.getProperty("UsaIdOffice.OfficeDropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.USAIDOFFICE);
			locator = objectRepo.getProperty("UsaIdOffice.activityManager");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("UsaIdOffice.activityManagerDropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.USAIDMANGER);
			if(ActivityTestData.ACTIVITY_TYPE.contains("Non-Glass"))
			{
				locator = objectRepo.getProperty("NonGlassActivity.KeyPointofContact");
				actionMethods.click(locator);
				locator = objectRepo.getProperty("NonGlassActivity.KeyPointofContactDropdwon");
				actionMethods.selectDropDownData(locator, ActivityTestData.KEYPOINT_OF_CONTACT);
			} else
			{
				locator = objectRepo.getProperty("UsaIdOffice.Alternativeor");
				actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.ALTERNATIVE_AOR);
				locator = objectRepo.getProperty("UsaIdOffice.AlternativeActiveManger");
				actionMethods.click(locator);
				locator = objectRepo.getProperty("UsaIdOffice.AlternativeActiveMnagerDropdown");
				actionMethods.selectDropDownData(locator, ActivityTestData.ALTERNATIVE_MANAGER);
			}
			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
				
		}
	}
	
	public void enterPartnerName(String partnerName)
	{
		try
		{
			String locator = objectRepo.getProperty("PartnerDetails.Name");
			actionMethods.enterInputMandatoryFiled(locator, partnerName);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ParterName : "+partnerName, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PartnerName not presented", driver);
		}
	}
	
	public void enterBaseLineValue(String baselineValue)
	{
		try
		{
			String locator = objectRepo.getProperty("Activity.EnterBaseLineValue");
			actionMethods.enterInputMandatoryFiled(locator, baselineValue);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"BaseLine value : "+baselineValue, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline value not presented", driver);
		}
	}
	
	public void selectBaseLineIndicatorCode(String indiCode)
	{
		try
		{
			String locator = objectRepo.getProperty("Dis.ExpectedText");
			actionMethods.click(locator.replace("Dummy", indiCode));
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"BaseLine Indicator : "+indiCode, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline value not presented", driver);
		}
	}
	
	
	public void selectBaseLineTab()
	{
		try
		{
			actionMethods.waitFor();
			String locator = objectRepo.getProperty("Activity.BaseLineTab");
			actionMethods.click(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"user selected BaseLine tab", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline tab not presented", driver);
		}
	}
	
	public void saveBaseLineData()
	{
		try
		{
			String locator = objectRepo.getProperty("Dis.SaveBtn");
			actionMethods.scrollToElement(locator);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			String locator = objectRepo.getProperty("Dis.CancelBtn");
			actionMethods.click(locator);
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Cancel button selected", driver);
		}
	}
	
	public void selectBaseLineDate(String month)
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("Activity.BaelineMonth");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Activity.BaselineMonthDropdown");
			actionMethods.selectDropDownData(locator, month);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"BaseLine Month : "+month, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline month presented", driver);
		}
	}
	
	public void discardChanges()
	{
		String locator = "";
		try
		{
			 actionMethods.isAlertPresent("accept");
			locator = objectRepo.getProperty("Actiity.CloseBtn");
			if(actionMethods.isElementPresentOptional(locator))
				actionMethods.scrollToElement(locator);

			locator = objectRepo.getProperty("Activity.Discard");
			if(actionMethods.isElementPresentOptional(locator))
				actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline month presented", driver);
		}
	}
	
	public void selectBaseLineYear(String year)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.BaselineYear");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Activity.BaselineYearDropdown");
			actionMethods.selectDropDownData(locator, year);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"BaseLine Year : "+year, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline Year presented", driver);
		}
	}
	
	public void selectCANCELBtn()
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.CancelBtn");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("Activity.UnsavedCahnges");
			if(actionMethods.isElementPresentOptional(locator))
			{
				locator = objectRepo.getProperty("Strategis.YesBtns");
				actionMethods.click(locator);
			}
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Selected Cancel after Edit", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline Year presented", driver);
		}
	}
	
	public void enterbaseLineInfo(String baselineInfo)
	{
		try
		{
			String locator = objectRepo.getProperty("Activity.AddInfo");
			actionMethods.enterInputMandatoryFiled(locator, baselineInfo);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Enterd baseline info : "+baselineInfo, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline info not presented", driver);
		}
	}
	
	public void enterPartnerEmail(String partneremail)
	{
		try
		{
			String locator = objectRepo.getProperty("PartnerDetails.email");
			actionMethods.enterInputMandatoryFiled(locator, partneremail);
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ParterEmail : "+partneremail, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PartnerEmail not presented", driver);
		}
	}
	
	public void validateBaselineInfoMsg()
	{
		try
		{
			String locator = objectRepo.getProperty("Activity.BaselineMsg");
			if(actionMethods.isElementPresent(locator))
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"getScreenShot", driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Baseline message not presented", driver);
		}
	}
	
	public void enterPartnerAddres(String partnerAddress)
	{
		try
		{
			String locator = objectRepo.getProperty("PartnerDetails.AddressLine1");
			actionMethods.enterInputMandatoryFiled(locator, partnerAddress);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ParterAddress : "+partnerAddress, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"ParterAddress not address", driver);
		}
	}
	
	public void selectPhoneNum(String countryCode,String phoneNum,String extnNum)
	{
		String locator;
		try
		{
			
			locator = objectRepo.getProperty("PartnerDetails.SeelctCountryCode");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("PartnerDetails.SeelctCountryCodeDropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.COP_COUNTRY);
			locator = objectRepo.getProperty("PartnerDetails.MobileNum");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_PHONE);
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_PHONE);
			locator = objectRepo.getProperty("PartnerDetails.extn");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_EXTN);
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_EXTN);

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PhoneNumber : "+phoneNum, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PhoneNUm not address", driver);
		}
	}
	
	public void selectPhoneNum2(String countryCode,String phoneNum,String extnNum)
	{
		String locator;
		try
		{
			
			locator = objectRepo.getProperty("PartnerDetails.SeelctCountryCode2");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("PartnerDetails.SeelctCountryCodeDropdown2");
			actionMethods.selectDropDownData(locator, ActivityTestData.COP_COUNTRY);
			locator = objectRepo.getProperty("PartnerDetails.PhoneNUm2");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_PHONE);
			locator = objectRepo.getProperty("PartnerDetails.extn2");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.COP_EXTN);

			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PhoneNumber : "+phoneNum, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"PhoneNUm not address", driver);
		}
	}
	
	public void saveNonGlassAcivityDetails()
	{
		String locator= "";
		try
		{
			locator = objectRepo.getProperty("PartnerDetails.SaveBtn");
			actionMethods.scrollToElement(locator);
		//	actionMethods.click(locator);
			actionMethods.waitFor();
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName()+"getScreenShot", Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"Save button not enabled", driver);
				
		}
	}
	
	public void createAddNonGlassActivity(String activityName)
	{
		try
		{
			selectAwardDetails();
			addActivityDetails(activityName);
			addUSAIDOfficeDetails();
			addPartnerOrganization();
			enterPartnerName(ActivityTestData.COP_NAME);
			enterPartnerEmail(ActivityTestData.COPEMAIL);
			enterPartnerAddres(ActivityTestData.COP_ADDRESS);
			selectPhoneNum(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			selectPhoneNum2(ActivityTestData.COP_COUNTRY, ActivityTestData.COP_PHONE, ActivityTestData.COP_EXTN);
			saveNonGlassAcivityDetails();
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}

	public void navigateToActivityScreen()
	{
		try
		{
			String locator = objectRepo.getProperty("Activities.ClickOnActivity");
	        actionMethods.click(locator);
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void ClickOnAddFundingStrip()
	{
		try
		{
			String locator = objectRepo.getProperty("Activity.AddFundingStripBtn");
	        actionMethods.click(locator);
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectFundingOffice(String fundingOffice)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.FundingOffice");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, fundingOffice);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+fundingOffice, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectsSourceIfFunding(String SourceOfFund)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.SelectSourceOfFunding");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, SourceOfFund);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+SourceOfFund, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectExternalSourceName(String externalSourceName)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.EnterExternalSourceName");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, externalSourceName);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+externalSourceName, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectDistibutionCode(String distCode)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.DestinationCode");
	        actionMethods.enterInputMandatoryFiled(locator, distCode);
	       
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+distCode, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectFundAccount(String fundAccount)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.FundAccount");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, fundAccount);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+fundAccount, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectPhonixCode(String phonixNUm)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.PhonixAccount");
	        actionMethods.enterInputMandatoryFiled(locator, phonixNUm);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+phonixNUm, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectCategory(String category)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.SelectCategory");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, category);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+category, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectProgramArea(String programArea)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.SelectProgramArea");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, programArea);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+programArea, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectProgramEleemnt(String programElement)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.ProgramEleemnt");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, programElement);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+programElement, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void selectFundingType(String fundType)
	{
		String locator;
		try
		{
			locator = objectRepo.getProperty("Activity.FundingType");
	        actionMethods.click(locator);
	        locator = objectRepo.getProperty("Activity.Dropdowndata");
	        actionMethods.selectDropDownData(locator, fundType);
	        
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag+"User has selected: "+fundType, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void navigateToFundingTab()
	{
		try
		{
			String locator = objectRepo.getProperty("Activity.FundingTab");
	        if(actionMethods.isElementPresentOptional(locator))
			actionMethods.click(locator);
	        Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void editAwardName(String awardName)
	{
		try
		{
		String locator = objectRepo.getProperty("NonGlassActivity.AWardName");
		actionMethods.enterInputMandatoryFiled(locator,awardName);
		Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
		
	public void selectAwardDetails()
	{
		project = new ProjectsPage();
		String locator = "";
		try
		{
			
	        actionMethods.isAlertPresent("accept");
	        actionMethods.waitFor();
	        selectOngoingTab();
			locator = objectRepo.getProperty("NonGlassActivity.addNonGlassBtn");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("NonGlassActivity.AWardName");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.AWARD_NAME+actionMethods.genarateRandomNumber(6));
			locator = objectRepo.getProperty("NonGlassActivity.AwardNum");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.AWARD_NO+actionMethods.genarateRandomNumber(3));
			locator = objectRepo.getProperty("NonGlassActivity.OrderNo");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.ORDER_NO+actionMethods.genarateRandomNumber(3));
			locator = objectRepo.getProperty("NonGlassActivity.StartDate");
			actionMethods.click(locator);
			project.selectCalanderDate(ActivityTestData.START_DATE);
			locator = objectRepo.getProperty("NonGlassActivity.EndDate");
			actionMethods.click(locator);
			project.selectCalanderDate(ActivityTestData.END_DATE);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}

	public void addPartnerOrganization()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("PartnerDetails.PartnerOrganization");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.PARTNER);
			locator = objectRepo.getProperty("PartnerDetails.City");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.CITY);
			locator = objectRepo.getProperty("PartnerDetails.State");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.STATE);
			locator = objectRepo.getProperty("PartnerDetails.postcode");
			actionMethods.enterInputMandatoryFiled(locator, ActivityTestData.POSTALCODE);
			locator = objectRepo.getProperty("PartnerDetails.selectCountry");
			actionMethods.click(locator);
			locator = objectRepo.getProperty("PartnerDetails.CountryDropdown");
			actionMethods.selectDropDownData(locator, ActivityTestData.PARTNER_COUNTRY);
			
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
	
	public void editActivity()
	{
		String locator = "";
		try
		{
			locator = objectRepo.getProperty("GlaasActivities.Edit");
			actionMethods.click(locator);
			Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		} catch (Exception e) {
			 Constant.statusFlag = "Failed";			
			 Report.getInstance().generateReport(Thread.currentThread().getStackTrace()[1].getMethodName(), Constant.statusFlag, driver);
		}
	}
}
