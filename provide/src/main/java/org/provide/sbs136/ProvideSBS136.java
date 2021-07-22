package org.provide.sbs136;

   

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

	import org.provide.commons.ProvidePom;
	import org.provide.spain.Runnable;
	import org.provide.commons.Util;

	import org.provide.commons.ProvidePom;

	import org.provide.commons.Util;


	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.MediaEntityBuilder;
	import com.aventstack.extentreports.Status;

	public class ProvideSBS136  extends Util {
		
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
		
		public static void main(String args[]) throws IOException {
			
			ExtentReports extent = Util.initializeExtendReport();
			ExtentTest test = extent.createTest("Reading Excel File", "");
			try {
				// Util.login(driver, log, ProvidePom.username, ProvidePom.password,
				// ProvidePom.url);

				 System.out.println(Util.getExcelData());

				System.out.println(extent);
				
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
			
			 for (int i = 1; i <= excelData.size(); i++) {
					status="PASS";
					test = extent.createTest("PR REQUISITION : "+i, "");
					try {
						try	{
						test.log(Status.INFO ,"Login as PR creator. ");
					Util.login(driver, wait, log, excelData.get(i).get("User"),excelData.get(i).get("Password"), excelData.get(i).get("Url"));
					test.log(Status.PASS ,"Log in PR creator : Successful.");}
						catch(Exception x) {
							status="FAIL";
							test.log(Status.FAIL ,"Unable to login : Invalid  User/Password/url. ");
							
							continue;
							
						}
					
					
					Util.navigateToPurchasingFR(wait,excelData.get(i).get("PR tYPE"));
					//test.log(Status.PASS, "Edit Pr Requisition Page");
					test.log(Status.INFO, "Entering Data for Edit Requisition.");
					Util.newPrRequisitionFR(wait, excelData.get(i).get("OrderDesc"), excelData.get(i).get("PurchaseCategory"),excelData.get(i).get("Product Name"),
							excelData.get(i).get("Supplier"), excelData.get(i).get("Quantity"),
							excelData.get(i).get("UnitPrice"),excelData.get(i).get("Currency"), excelData.get(i).get("PR tYPE"));
					test.log(Status.PASS, "Data Entered for Edit Requisition Page.");
					test.log(Status.INFO, "Entering Header Data");
					Util.headerData(wait,excelData.get(i).get("Requestor"));
					test.log(Status.PASS, "Header Data Saved");
					test.log(Status.INFO, "Entering Address Data.");
					Util.addAddress(wait, excelData.get(i).get("Delivery Address"));
					test.log(Status.PASS, "Address saved.");
					test.log(Status.INFO, "Entering  Edit Coding Data.");
					Util.editCodingFR(wait, excelData.get(i).get("Nature"), excelData.get(i).get("Agency/Site"),excelData.get(i).get("Type"),excelData.get(i).get("RealEstateReference"),excelData.get(i).get("PR tYPE"),excelData.get(i).get("Approver"),excelData.get(i).get("SSP"));
					test.log(Status.PASS, "Edit coding saved for Agency and SSP.");
					test.log(Status.INFO, "Entering and verifying View Details.");
					Util.viewDetailsFR(wait, excelData.get(i).get("Quantity"), excelData.get(i).get("UnitPrice"),excelData.get(i).get("Payment Terms"));
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
					
					String orderDescription = "";
					String reviewer=excelData.get(i).get("Reviewer");
					if(!reviewer.equals(null)) {
					test.log(Status.INFO, "Review processs");
					Util.login(driver, wait, log, excelData.get(i).get("Reviewer"),excelData.get(i).get("Password"), excelData.get(i).get("Url"));
					test.log(Status.PASS, "Login for Review");
					Util.getApprove(wait);
					orderDescription = Util.getTaskList(wait, excelData.get(i).get("OrderDesc"),ProvidePom.docType);
					
					
					if (orderDescription.toLowerCase().equalsIgnoreCase("\"" + excelData.get(i).get("OrderDesc") + "\"")) {
						//	test.log(Status.INFO, "Approve");
							try {
							Util.getReview(wait);
							test.log(Status.PASS, "PR: "+PRNumber+" reviewed  by reviewer ");
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
							
								
						} else {
							status="FAIL";
							test.log(Status.FAIL, "NO PR Found for Reviewer");
							try {
								String errorSc =concate+ Util.attachedScreenShot(driver,"PR_REQUISITION_"+i+"_Reviewer_");
								
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
					
					
					
					}
					test.log(Status.INFO, "Approving Process Started");
					int j = 1;
					String Approver = "Approver";
					System.out.println( excelData.get(i).get(Approver + j));
					 //
					while (excelData.get(i).containsKey(Approver + j)) {

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
							status="FAIL";
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
					
					status="FAIL";
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

	

