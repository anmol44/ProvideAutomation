package org.provide.beleux.invoice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
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
import org.provide.commons.Util;
import org.provide.invoice.ProvidePom;
import org.provide.invoice.UtilInvoice;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


public class ProvideBeleux extends Util  {

static Logger log = Logger.getLogger(Runnable.class.getName());
static WebDriver driver;
static WebDriverWait wait;
static WebElement webelement;
static JavascriptExecutor javascriptExecutor;
static HashMap<Integer, HashMap<String,String>> excelData;
static  XSSFWorkbook workbook;
static  XSSFSheet sheet;
static   Row row ;
static Cell cell;
static String status=" ";
static String concate=".";
 static CellStyle style;
 
 

   @Test
   @Parameters("Name")
	public static void beleux(String Name) throws IOException {
		
		ExtentReports extent = Util.initializeExtendReport();
		ExtentTest test = extent.createTest("Reading Excel File", "");
		try {
			// Util.login(driver, log, ProvidePom.username, ProvidePom.password,
			// ProvidePom.url);

			 System.out.println(Util.getExcelData(Name));

			System.out.println(extent);
			
			excelData = Util.getExcelData(Name);
			test.log(Status.PASS, "Data imported");
			  
			driver = Util.setUp();
			wait = new WebDriverWait(driver, 50);
			javascriptExecutor = (JavascriptExecutor) driver;
			
			workbook =	(XSSFWorkbook) Util.createOutPutSheet(ProvidePom.inputFile+Name+".xlsx",ProvidePom.outputFile);
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
		
		 for (int i = 1; i <= excelData.size(); i++) {
				status="PASS";
				test = extent.createTest("INVOICE : "+i, "");
				try {
					try	{
					test.log(Status.INFO ,"Login as INVOICE creator. ");
				Util.login(driver, wait, log, excelData.get(i).get("User"),excelData.get(i).get("Password"), excelData.get(i).get("Url"));															
				test.log(Status.PASS ,"Log in INVOICE creator : Successful.");}
					catch(Exception x) {
						status="FAIL";
						test.log(Status.FAIL ,"Unable to login : Invalid  User/Password/url. ");
						
						continue;
						
					}
				
				
				
				UtilInvoice.navigateToAccountsPayable(webelement,wait,javascriptExecutor);
				//test.log(Status.PASS, "Edit Pr Requisition Page");
				test.log(Status.INFO, "Entering Data for Edit Invoice");
				
				UtilInvoice.receivedStage(wait);
				
				test.log(Status.INFO, "Recieved Stage");
				
				UtilInvoice.invoiceSearch(wait, excelData.get(i).get("InvoiceNo"));
						
				test.log(Status.PASS, "Data Entered for Search Invoice.");
				
				
				
				// save invoice foe po type
				if(!excelData.get(i).get("PO Number").isEmpty()) {
				UtilInvoice.savePoInvoice(driver,wait,excelData.get(i).get("InvoiceNo"),excelData.get(i).get("PO Number"),excelData.get(i).get("SupplierCode"),excelData.get(i).get("InvoiceDate"),excelData.get(i).get("InvoiceAmt"),excelData.get(i).get("TaxAmt"));
				}
				
				//save invoice for non-po type
				if(!excelData.get(i).get("PR Type").isEmpty() && excelData.get(i).get("PO Number").isEmpty()) {
				UtilInvoice.editNonPoInvoice(driver,wait,excelData.get(i).get("InvoiceNo"),excelData.get(i).get("PR Type"),excelData.get(i).get("SupplierCode"),excelData.get(i).get("InvoiceDate"),excelData.get(i).get("InvoiceAmt"),excelData.get(i).get("TaxAmt"),excelData.get(i).get("Requestor"));
	            UtilInvoice.saveAndAddLines(driver,wait,javascriptExecutor,excelData.get(i).get("LineNo"),excelData.get(i).get("PurchaseCAtegory"),excelData.get(i).get("Agency"),excelData.get(i).get("Type"),excelData.get(i).get("SSP"));
				}
				test.log(Status.PASS, "Invoice Saved");
				
				
				
				test.log(Status.INFO, "Send to validation");
				UtilInvoice.sendToValidate(wait);
				test.log(Status.PASS, "Sent to validate.");
				
				
				
				if(!excelData.get(i).get("PO Number").isEmpty()) {
				test.log(Status.INFO, "Open invoice for Matching.");
				UtilInvoice.openAndMatchInvoice(wait, excelData.get(i).get("MatchQty"));
				test.log(Status.PASS, "Invoice opened.");
				test.log(Status.INFO, "Invoice confirmed");
				}
				
				
	
				
				Util.logOut(driver,wait,webelement, javascriptExecutor);
				test.log(Status.PASS, "Logout f PR Creator");
				
				Thread.sleep(5000);
				if (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
					Util.allSessionsLogOut(wait);
				//	test.log(Status.PASS, "All Sessions Logout");
				}
				
					
				String orderDescription = "";
			
				test.log(Status.INFO, "Approving Process Started");
				int j = 1;
				String Approver = "Approver";
				System.out.println( excelData.get(i).get(Approver + j));
				 //
				while (excelData.get(i).containsKey(Approver + j) && !excelData.get(i).get(Approver + j).isEmpty()) {
					
					if(excelData.get(i).get(Approver + j).equalsIgnoreCase("Support.SSG")) {
					Util.login(driver, wait, log, excelData.get(i).get(Approver + j),excelData.get(i).get("Password1"), excelData.get(i).get("Url"));
					test.log(Status.PASS, "Login for Approver"+j);}
					else
					{
						Util.login(driver, wait, log, excelData.get(i).get(Approver + j),"Provide2018", excelData.get(i).get("Url"));
						test.log(Status.PASS, "Login for Approver"+j);	
						
					}

					Util.getApprove(wait);
					//test.log(Status.PASS, "Task List");

					orderDescription = Util.getTaskList(wait, excelData.get(i).get("InvoiceNo"),ProvidePom.docType);
					//test.log(Status.PASS, "Order description matched "+orderDescription);

					if (orderDescription.toLowerCase().equalsIgnoreCase( excelData.get(i).get("InvoiceNo"))) {
					//	test.log(Status.INFO, "Approve");
						try {
						Util.approveTaskList(driver,wait,ProvidePom.docType);
						test.log(Status.PASS, "PR: "+orderDescription+" approved by approver "+j);
						
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
						status="FAIL";
						test.log(Status.FAIL, "NO INVOICE Found for Approver"+j);
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
				
				status="FAIL";
				
				  try { 
					  Util.logOutException(driver,wait,webelement, javascriptExecutor);
				  test.log(Status.PASS,
				  "Logout for Creater"+i); Thread.sleep(5000); if
				  (driver.getCurrentUrl().contains(ProvidePom.logOutUrl)) {
				  Util.allSessionsLogOut(wait); 
				  test.log(Status.PASS, "All Sessions Logout");
				  }
				  
				  test.log(Status.INFO, "Creator Logged out"); } catch(Exception ex) {
				  ex.printStackTrace(); }
				 
				 
					e.printStackTrace();
				
			}
				
				if(status=="FAIL") {
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
				
			}

		 FileOutputStream outputStream = new FileOutputStream(ProvidePom.outputFile);
	     workbook.write(outputStream);
	     workbook.close();
			
			
			extent.flush();
			driver.close();
	}

}
