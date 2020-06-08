package com.test.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.test.excelAPI.ExcelOperation;
import com.test.utils.Constant;

//@gmail.com/Door1room3@#!~Door1room3@#!
public class Runner2 {

	public WebDriver driver;
	static Logger log = LogManager.getLogger(Runner.class);

	@Test
	public void start() throws InterruptedException {
		hello();
	}

	public static void hello() throws InterruptedException {
		ExcelOperation excelOpr = new ExcelOperation();
		try {
			log.info("Before Temp File Creation in First Iteration :");
			excelOpr.createTempFile(0);
			log.info("After Temp File Creation in First Iteration :");
			ExcelOperation.readExecutionVariables(0);

			log.info("Actual Execution in First Iteration is started");
			excelOpr.readTestSuite();
			log.info("Actual Execution in First Iteration is started");
			System.out.println("Actual Execution in First Iteration is started");

			// Generate Report and Close the Report
			exitMethod();
			// FlowMethods.driver.quit();

			// Update Passed and Failed Journeys count - As per extent report data
			// excelOpr.updateResultSetCount();

			/*
			 * int failedCaseCount=0; failedCaseCount = excelOpr.markFailedCasesAlone(); int
			 * value = Constant.reRun;
			 * 
			 * for(int iteration=1;iteration <= value;iteration++) { if(failedCaseCount>0) {
			 * excelOpr.createTempFile(iteration);
			 * ExcelOperation.readExecutionVariables(iteration); excelOpr.readTestSuite();
			 * 
			 * //Generate Report and Close the Report exitMethod(); //Update Passed and
			 * Failed Journeys count - As per extent report data
			 * //excelOpr.updateResultSetCount(); //failedCaseCount =
			 * excelOpr.markFailedCasesAlone(); } else break; }
			 */

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void exitMethod() throws IOException, InterruptedException {
		log.info("Generating Report Started in :" + Thread.currentThread().getName());
		System.out.println();
		Constant.extentReporter.flush();
		//System.out.println("Generating Report Started in :" + Thread.currentThread().getName());
		Constant.extentReporter.close();
		 deleteFilesinFolder();
		 Thread.sleep(10000);
		File f = latestFolder();
		File f2 = getLastModifiedFile(f);
		// copy(f2);
		CopyFile(f2);
		log.info("Generating Report Started in");
		
		ActionMethods.driver.quit();
	}

	public static File latestFolder() {
		// Path path = System.getProperty("user.dir")+"\\POMFramework\\Reports\\";
		String path = System.getProperty("user.dir") + "\\POMFramework\\Reports\\";
		System.out.println("path "  + path);
		File dir = new File(path);
		File max = null;
		for (File file : dir.listFiles()) {
			if (file.isDirectory() && (max == null || max.lastModified() < file.lastModified())) {
				max = file;
				//System.out.println(" The latest folder in reports is "+ max);
			}
		}
		return max;

	}

	public static File getLastModifiedFile(File f) {
		System.out.println("getLastModified");
		String directoryFilePath = f.getAbsolutePath();
		System.out.println("directoryFilePath " + directoryFilePath);
		File directory = new File(directoryFilePath);
		File[] files = directory.listFiles(File::isFile);
		long lastModifiedTime = Long.MIN_VALUE;
		File chosenFile = null;

		if (files != null) {
			for (File file : files) {
				if (file.lastModified() > lastModifiedTime) {
					chosenFile = file;
					lastModifiedTime = file.lastModified();
					//System.out.println("chosenFile " + chosenFile);
				}
			}
		}

		System.out.println(chosenFile + "System.out.println(lastModifiedTime);");

		return chosenFile;
	}

	public static void CopyFile(File src) throws IOException {
		String source = "C:/sample.txt";
		source = src.getAbsolutePath();
		System.out.println("src CopyFile " + source);
		// directory where file will be copied
		String target = "C:/Test/";
		target = System.getProperty("user.dir") + "\\POMFramework\\LatestReport\\";
		System.out.println("target" + target);

		// name of source file
		File sourceFile = new File(source);
		String name = sourceFile.getName();

		File targetFile = new File(target + name);
		System.out.println("Copying file : " + sourceFile.getName() + " from Java Program");

		// copy file from one location to other
		FileUtils.copyFile(sourceFile, targetFile);

	}

	private static void deleteFilesinFolder() {
		String directoryTodelete = System.getProperty("user.dir") + "\\POMFramework\\LatestReport\\";
		try {
			Arrays.stream(new File(directoryTodelete).listFiles()).forEach(File::delete);
			System.out.println("Deleted the file");
		} catch (Exception e) {
			System.out.println(" No Files to Delete");
		}

	}

	/*
	 * private static void copy(File src) throws IOException {
	 * System.out.println("src.getAbsolutePath()"+src.getAbsolutePath() ); String
	 * filecopyPath = System.getProperty("user.dir") +
	 * "\\POMFramework\\Reports\\LatestReport"; System.out.println("filecopyPath" +
	 * filecopyPath); File dest = new File(filecopyPath); InputStream is = null;
	 * OutputStream os = null; try { is = new
	 * FileInputStream(src.getAbsolutePath()); os = new FileOutputStream(dest);
	 * 
	 * // buffer size 1K byte[] buf = new byte[1024];
	 * 
	 * int bytesRead; while ((bytesRead = is.read(buf)) > 0) { os.write(buf, 0,
	 * bytesRead); } } finally { is.close(); os.close(); } }
	 */

	public static void execute_Actions(String flowID, String testSuiteModuleName) throws Exception {
		FlowMethods flowMethods = new FlowMethods();
		flowMethods.getClass().getMethod(flowID.trim(), String.class).invoke(flowMethods, testSuiteModuleName);
	}
}
