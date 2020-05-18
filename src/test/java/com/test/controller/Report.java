package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.*;
import org.openqa.selenium.WebDriver;

import com.test.excelAPI.ExcelOperation;
import com.test.pages.LoginPage;
import com.test.utils.Constant;
import com.relevantcodes.extentreports.LogStatus;

public final class Report
{
	Logger log = LogManager.getLogger(Report.class);
	ActionMethods action_method = new ActionMethods();
	private volatile static Report INSTANCE;
	LoginPage login = new LoginPage();
	//private ScreenRecorder screenRecorder;

	Report(){

		if( Report.INSTANCE != null ) {
			throw new InstantiationError( "Creating of object is not allowed." );
		}
	}
	public  static Report getInstance(){

		if(INSTANCE == null){
			synchronized(Report.class){
				if(INSTANCE == null){
					INSTANCE = new Report();
				}
			}

		}
		return INSTANCE;	
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Cloning of report class is not allowed"); 
	}

	//Handling Serialization
	private Object readResolve(){
		return INSTANCE;
	}

	public  void generateReport(String currentMethodName, String statusFlag, WebDriver driverInstance)
	{
		try
		{
			String currentJourney = Constant.extentTest.getTest().getName();
			ExcelOperation excelOperation = new ExcelOperation();
			String screenshotPath ;
			if(statusFlag == null)
				log.info("Status not Provided. check statusflag variable in Method : " + currentMethodName);
			else if(statusFlag.contains("Passed") & statusFlag!=null)
			{
				log.info("Status in pass block for method  : " + currentMethodName + " And Status " + statusFlag);
				
				/*screenshotPath= action_method.captureScreenshot(driverInstance);
				Constant.extentTest.log(LogStatus.PASS, currentMethodName , "<font size="+3+" color='#7CFC00'>"+"Passed" +"</font>"+ " <br />" + statusFlag.replace("Passed", "")+Constant.extentTest.addBase64ScreenShot(screenshotPath));*/
				//Constant.extentTest.log(LogStatus.PASS, currentMethodName , "<font size="+3+" color='#7CFC00'>"+"Passed" +"</font>"+ " <br />" + statusFlag.replace("Passed", ""));
				
				if(currentMethodName.contains("getScreenShot")) {
					screenshotPath= action_method.captureScreenshot(driverInstance ,statusFlag);
					Constant.extentTest.log(LogStatus.PASS, currentMethodName.replace("getScreenShot", "") , "<font size="+3+" color='#7CFC00'>"+"Passed" +"</font>"+ " <br />" + statusFlag.replace("Passed", "")+Constant.extentTest.addBase64ScreenShot(screenshotPath));
				}	
				else
				{
					Constant.extentTest.log(LogStatus.PASS, currentMethodName , "<font size="+3+" color='#7CFC00'>"+"Passed" +"</font>"+ " <br />" + statusFlag.replace("Passed", ""));
				}	
			}
			else if(statusFlag.contains("Failed") & statusFlag!=null )
			{
				boolean isEligibleForInclusion = false;
				if(currentMethodName.contains("getScreenShot"))
					currentMethodName=currentMethodName.replace("getScreenShot", "");				
				log.info("Status in Failed block for method : " + currentMethodName + " And Status " + statusFlag);

				String loginIssue = "";

				if(!excelOperation.exclusivejourneys.contains(currentJourney))
				{
					isEligibleForInclusion = true;
					//excelOperation.updateTestExecutionStatus("Failed",currentJourney);
					excelOperation.failedJourneys.add(currentJourney + "||" + excelOperation.testSuiteReportingModuleName);
					excelOperation.skippedJourneys.remove(currentJourney + "||" + excelOperation.testSuiteReportingModuleName);
					excelOperation.exclusivejourneys.add(currentJourney);
				}

				screenshotPath= action_method.captureScreenshot(driverInstance ,statusFlag);
				
				log.info("Returned from screen shot method to Generate Report");				
				log.info("<span title='"+Constant.statusFlag.replaceAll("'", "'")+"'><a href='"+Constant.logsPath+"/automationFramework.log'><font size="+3+" color='#DC143C'>FAILED</font></a></span>");
				log.info(statusFlag.replace("Failed", "")+Constant.extentTest.addBase64ScreenShot(screenshotPath));
				log.info("statusFlag for Current Journey :"+currentJourney+" is "+statusFlag);
				Constant.extentTest.log(LogStatus.FAIL, 
						currentMethodName , 
						"<span title='"+Constant.statusFlag.replaceAll("'", "'")+"'><a href='"+Constant.logsPath+"/automationFramework.log'><font size="+3+" color='#DC143C'>FAILED</font></a></span>"+
								statusFlag.replaceAll("[<]","").trim().replace("Failed", "")+Constant.extentTest.addBase64ScreenShot(screenshotPath));
				//.replace("Failed", "")
				log.info("Screen shot added to Report");
				statusFlag = statusFlag.replace("Failed", "");
				if(excelOperation.listOfFailedMethods.equalsIgnoreCase(""))
					excelOperation.listOfFailedMethods = currentMethodName + "-" + statusFlag;
				else
					excelOperation.listOfFailedMethods = excelOperation.listOfFailedMethods + "," + currentMethodName + "-" + statusFlag;
			}
			else if(statusFlag.contains("Skipped") & statusFlag!=null)
			{
				if(!excelOperation.skippedJourneys.contains(currentJourney+ "||" + excelOperation.testSuiteReportingModuleName) && !excelOperation.exclusivejourneys.contains(currentJourney))
				{
					excelOperation.skippedJourneys.add(currentJourney+ "||" + excelOperation.testSuiteReportingModuleName);
					//excelOperation.updateTestExecutionStatus("Failed",currentJourney);
				}

				log.info("Status in Skipped block in Method : " + currentMethodName + " And Status " + statusFlag);
				Constant.extentTest.log(LogStatus.SKIP,currentMethodName, statusFlag);
			}
			else if(statusFlag.contains("Info") & statusFlag!=null )
			{
				log.info("Status in Info block  : " + statusFlag);
				Constant.extentTest.log(LogStatus.INFO,currentMethodName, statusFlag);
			}
			else
				log.info("Status not updated to report.");
			Constant.statusFlag=null;
		}
		catch (Exception e) 
		{
			log.error("Failed in Adding Test Step to Report : " + currentMethodName , e);
		}

	}

	public String getTimeStampForFailedCase_HKT()
	{
		String currentTime = "";
		try
		{		
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy-HH:mm:ss");
			Date currentDate = new Date();
			dateFormat.setTimeZone(TimeZone.getTimeZone("HKT"));
			currentTime = dateFormat.format(currentDate);
		}
		catch(Exception e)
		{
			log.error("Failed get current time stamp for failed case in HKT Time Zone", e);
		}
		return currentTime;
	}

	/*public void startRecording() throws Exception {
    File file = new File(Constant.videoRecordPath);
    String completeIpAddress=Inet4Address.getLocalHost().getHostAddress();
	String[] ipAddress = completeIpAddress.split("\\.");
	String machine = ipAddress[ipAddress.length-1];
	String correspondingMachineName=ExcelOperation.getCurrentTimeStamp();
	if(completeIpAddress.contains("10.198.155."))
		machine = correspondingMachineName + "-AWS" +machine;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;

    Rectangle captureSize = new Rectangle(0, 0, width, height);

    GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    this.screenRecorder = new SpecializedScreenRecorder(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
                    Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file,machine);
    this.screenRecorder.start();

}*/

	/*public void stopRecording() throws Exception {
    this.screenRecorder.stop();
}*/


}
/*class SpecializedScreenRecorder extends ScreenRecorder {

    private String name;

    public SpecializedScreenRecorder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name)
            throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");

        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }
 */



