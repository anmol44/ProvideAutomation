package org.provide.spain;

import java.io.FileOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.provide.commons.AzureLibraries;
import org.provide.commons.ProvidePom;
import org.provide.commons.Util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class Runnable {

	static Logger log = Logger.getLogger(Runnable.class.getName());
	static WebDriver driver;
	static WebDriverWait wait;
	static WebElement webelement;
	static JavascriptExecutor javascriptExecutor;
	static HashMap<Integer, HashMap<String,String>> excelData;
	static HashMap<String,String> tests;
	static List<String> testCaseNameList;
	static ArrayList<String> testCaseIdList;
	static String startTimeModule=" ";
	static String endTimeModule=" ";
	static String testStartTime=" ";
	static String testEndTime=" ";
	static LinkedHashMap<String,String> durationTime;
	static  XSSFWorkbook workbook;
	static  XSSFSheet sheet;
	static   Row row ;
	static Cell cell;
	static String status=" ";
	static String testCase="TestCase";
	static String concate=".";
	 static CellStyle style;

	@SuppressWarnings("deprecation")
	public static void main(String a[]) throws Exception {
		ExtentReports extent = Util.initializeExtendReport();
		ExtentTest test = extent.createTest("Reading Excel File", "");
		try {
			// Util.login(driver, log, ProvidePom.username, ProvidePom.password,
			// ProvidePom.url);

			 System.out.println(Util.getExcelData());

			System.out.println(extent);
			tests= new HashMap<String,String>();
			testCaseNameList= new ArrayList<String>();
			testCaseIdList = new ArrayList<String>();
			durationTime = new LinkedHashMap<String,String>();
			
			excelData = Util.getExcelData();
			test.log(Status.PASS, "Data imported");
			  
			driver = Util.setUp();
			wait = new WebDriverWait(driver, 50);
			javascriptExecutor = (JavascriptExecutor) driver;
			
			workbook=	 (XSSFWorkbook) Util.createOutPutSheet(ProvidePom.inputFile,ProvidePom.outputFile);
			 sheet=workbook.getSheetAt(0);
			 System.out.println(excelData.get(1).size());
			 cell = sheet.getRow(0).createCell(excelData.get(1).size()+1);
			 cell.setCellValue("Test Status");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			test.log(Status.FAIL, "Data imported Failed");
			extent.flush();
			e.printStackTrace();
			System.exit(0);
			
		}
		
         Instant instant = Instant.now();
	     startTimeModule = instant.toString();
	 
	
	
     for (int i = 1; i <= excelData.size(); i++) {
			status="Passed";
			test = extent.createTest("PR REQUISITION : "+i, "");
			testCaseNameList.add("PR REQUISITION : "+i);
			testCaseIdList.add(excelData.get(i).get("TestCaseNo"));
			Instant instant2 = Instant.now();
			testStartTime = instant2.toString();
			try {
				try	{
				test.log(Status.INFO ,"Login as PR creator. ");
			Util.login(driver, wait, log, excelData.get(i).get("User"),excelData.get(i).get("Password"), excelData.get(i).get("Url"));
			test.log(Status.PASS ,"Log in PR creator : Successful.");}
				catch(Exception x) {
					status="Failed";
					test.log(Status.FAIL ,"Unable to login : Invalid  User/Password/url. ");
					
					continue;
					
				}
			
			
			Util.navigateToPurchasing(wait,excelData.get(i).get("PRForm"));
			//test.log(Status.PASS, "Edit Pr Requisition Page");
			test.log(Status.INFO, "Entering Data for Edit Requisition.");
			Util.newPrRequisition(wait, excelData.get(i).get("OrderDesc"),excelData.get(i).get("Product Name"), excelData.get(i).get("PurchaseCategory"),
					excelData.get(i).get("Supplier"), excelData.get(i).get("Quantity"),
					excelData.get(i).get("UnitPrice"),excelData.get(i).get("Currency"), excelData.get(i).get("PR tYPE"));
			test.log(Status.PASS, "Data Entered for Edit Requisition Page.");
			test.log(Status.INFO, "Entering Header Data");
			Util.headerData(wait, excelData.get(i).get("Requestor"));
			test.log(Status.PASS, "Header Data Saved");
			test.log(Status.INFO, "Entering Address Data.");
			Util.addAddress(wait, excelData.get(i).get("Delivery Address"));
			test.log(Status.PASS, "Address saved.");
			test.log(Status.INFO, "Entering  Edit Coding Data.");
			Util.editCoding(wait, excelData.get(i).get("Nature"), excelData.get(i).get("Agency"),excelData.get(i).get("Type"),excelData.get(i).get("SSP"));
			test.log(Status.PASS, "Edit coding saved for Agency and SSP.");
			test.log(Status.INFO, "Entering and verifying View Details.");
			Util.viewDetails(wait, excelData.get(i).get("Quantity"), excelData.get(i).get("UnitPrice"), excelData.get(i).get("Payment Terms"));
			test.log(Status.PASS, "View Details saved and verified for the Amount.");
			//test.log(Status.INFO, "Getting the PR Approved");
			Util.getApproval(wait);
			String PRNumber =Util.getPrNumber(driver, wait);
			test.log(Status.PASS, "PR: "+PRNumber+" Successfuly created.");
			//test.log(Status.INFO, "Get PR Number");
			
		//	test.log(Status.PASS, "PR Number Recieved");
			
			Util.logOut(driver,wait,webelement, javascriptExecutor);
			//test.log(Status.PASS, "Logout f PR Creator");
			
			Thread.sleep(5000);
			if (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
				Util.allSessionsLogOut(wait);
			//	test.log(Status.PASS, "All Sessions Logout");
			}
			test.log(Status.INFO, "Approving Process Started");
			int j = 1;
			String Approver = "Approver";
			String orderDescription = ""; //
			while (excelData.get(i).containsKey(Approver + j)  && ! excelData.get(i).get(Approver + j).isEmpty()) {

				Util.login(driver, wait, log, excelData.get(i).get(Approver + j),excelData.get(i).get("Password"), excelData.get(i).get("Url"));
				test.log(Status.PASS, "Login for Approver"+j);

				Util.getApprove(wait);
				//test.log(Status.PASS, "Task List");

				orderDescription = Util.getTaskList(wait, excelData.get(i).get("OrderDesc"),ProvidePom.docType);
				//test.log(Status.PASS, "Order description matched "+orderDescription);

				if (orderDescription.toLowerCase().equalsIgnoreCase("\"" + excelData.get(i).get("OrderDesc") + "\"")) {
				//	test.log(Status.INFO, "Approve");
					try {
						// driver argument added for checking elements in approval process
					Util.approveTaskList(driver,wait,ProvidePom.docType);
					test.log(Status.PASS, "PR: "+PRNumber+" approved by approver "+j);
					Util.logOut(driver,wait,webelement, javascriptExecutor);
					//test.log(Status.PASS, "Logout for Approver"+j);
					Thread.sleep(5000);
					if (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
						Util.allSessionsLogOut(wait);
						//test.log(Status.PASS, "All Sessions Logout");
					}
					}catch(Exception ex) {
						
						try {
							String errorSc =concate+ Util.attachedScreenShot(driver,"PR_REQUISITION_"+i);
							
							test.log(Status.FAIL, "Error Snapshot below:", MediaEntityBuilder.createScreenCaptureFromPath(errorSc).build());
								
							} catch(Exception e) {
								
								
								test.log(Status.INFO, "Error Screenshot not found");
							}
						ex.printStackTrace();
					}
					
						/*
						 * try { Util.logOut(webelement, javascriptExecutor); // test.log(Status.PASS,
						 * "Logout for Approver"+j); Thread.sleep(5000); if
						 * (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
						 * Util.allSessionsLogOut(wait); //test.log(Status.PASS, "All Sessions Logout");
						 * }
						 * 
						 * 
						 * } catch(Exception ex) { ex.printStackTrace(); }
						 */
				} else {
					status="Failed";
					test.log(Status.FAIL, "NO PR Found for Approver"+j);
					try {
						String errorSc =concate+ Util.attachedScreenShot(driver,"PR_REQUISITION_"+i+"_Approver_"+j);
						
						test.log(Status.FAIL, "Error Snapshot below:", MediaEntityBuilder.createScreenCaptureFromPath(errorSc).build());
							
						} catch(Exception ex) {
							
							
							test.log(Status.INFO, "Error Screenshot not found");
						}
					try {	
						Util.logOut(driver,wait,webelement, javascriptExecutor);
				//	test.log(Status.PASS, "Logout for Approver"+j);
					Thread.sleep(5000);
					if (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
						Util.allSessionsLogOut(wait);
						//test.log(Status.PASS, "All Sessions Logout");
					}
				}catch(Exception ex) {
					ex.printStackTrace();
				}
					}

				j++;
			}

		}catch(Exception e){
			
			 test.log(Status.FAIL, "PR REQUISITION: "+i+e.toString());
			
			try {
			String errorSc =concate+ Util.attachedScreenShot(driver,"PR_REQUISITION_"+i);
			
			test.log(Status.FAIL, "Error Snapshot below:", MediaEntityBuilder.createScreenCaptureFromPath(errorSc).build());
				
			} catch(Exception ex) {
				
				
				test.log(Status.INFO, "Error Screenshot not found");
			}
			
			status="Failed";
			try {
				Util.logOut(driver,wait,webelement, javascriptExecutor);
				//	test.log(Status.PASS, "Logout for Approver"+j);
					Thread.sleep(5000);
					if (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
						Util.allSessionsLogOut(wait);
						//test.log(Status.PASS, "All Sessions Logout");
					}
					
					test.log(Status.INFO, "Creator Logged out");
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			 
				e.printStackTrace();
			
		}
			
			if(status=="Failed") {
				cell = sheet.getRow(i).createCell(excelData.get(1).size()+1);
				 cell.setCellValue("FAIL");
				style = Util.cellStyleColor( workbook, status);
				cell.setCellStyle(style); 
			}else {
				cell = sheet.getRow(i).createCell(excelData.get(1).size()+1);
				 cell.setCellValue("PASS");
				 style = Util.cellStyleColor( workbook, status);
				 cell.setCellStyle(style); 
				 
			}

			// Hashmap data regarding all test cases status.

			testEndTime = instant.toString();
			testCase=testCase+i+" : "+ excelData.get(i).get("TestCaseNo");
			tests.put(testCase, status);
			testCase="TestCase";
			Instant instant3 = Instant.now();
			testEndTime  = instant3.toString();
			
			/*
			 * public void AzureAPICall(HashMap<String, String> dependency,List<String>
			 * testCaseNamelist, ArrayList<String> testCaseIDs, String SuitID, String
			 * startTimeModule, String endTimeModule, LinkedHashMap<String, String>
			 * durationTime) ;
			 * 
			 */
			
			durationTime.put(testStartTime, testEndTime);
			
			Instant instant1 = Instant.now();
		 	endTimeModule  = instant1.toString();
			
			AzureLibraries azureApiCAll = new AzureLibraries();
			azureApiCAll.AzureAPICall(tests, testCaseNameList, testCaseIdList, "suitId", startTimeModule, endTimeModule, durationTime);

			tests.put(testCase+" : "+i,  status);

			
			// testcaseNmaelist  test cases name in sequence    testcaseId all testacse id in sequence  
			
		}
     FileOutputStream outputStream = new FileOutputStream(ProvidePom.outputFile);
     workbook.write(outputStream);
     workbook.close();
		
		
		extent.flush();
		driver.close();
	}

}
