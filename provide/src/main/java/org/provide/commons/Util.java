package org.provide.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;



public class Util {
    
	
	public static ExtentReports initializeExtendReport() {
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/Output/testReport.html");
		
		System.out.println("initialize"+htmlReporter);
	    
        // create ExtentReports and attach reporter(s)
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
     
         return extent;
         
        // creates a toggle for the given test, adds all log events under it    
       // ExtentTest test = extent.createTest("Searching Youtube", "");
	}
	
	public static Workbook createOutPutSheet(String inputFile, String outputFile ) {
		 FileSystem system = FileSystems.getDefault();
        Path original = system.getPath(inputFile);
	        Path target = system.getPath(outputFile);
           Workbook workbook = null;
	        try {
	            // Throws an exception if the original file is not found.
	            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println(target);
	        	FileInputStream src = new FileInputStream(target.toString());
	        	System.out.println(src);
	        	
				 workbook = new XSSFWorkbook(src);
            
	        } catch (IOException ex) {
	            System.out.println("ERROR");
	        }
	        
	        return workbook;
	}
	
	public static CellStyle cellStyleColor(Workbook workbook,String status) {
           
		 CellStyle style = workbook.createCellStyle();
		 
			if(status.equals("FAIL")) {
				
				 style.setFillForegroundColor(IndexedColors.RED.getIndex());  
			     style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
				
			}else {
				 style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());  
			     style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
				 
			}
		return style ;
	}
	
	public static String  attachedScreenShot(WebDriver driver,String name )throws Exception {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		//String dest = System.getProperty("user.dir") +"\\ErrorScreenshots\\"+name+".png";
		String dest = "./Output/ErrorScreenshots/"+name+".png";
		
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		File destination = new File(dest);
	
		 FileHandler.copy(source, destination);
		//test.log(Status.INFO, "Snapshot below:", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
		 
		return dest;
		
	}


	@SuppressWarnings("unchecked")
	
	public static HashMap<Integer, HashMap<String,String>> getExcelData(String Name) throws Exception {
		String path = System.getProperty("user.dir");
		HashMap<Integer, HashMap<String,String>> map = new HashMap<Integer,HashMap<String,String>>();
		HashMap<String,String> mapInner = new HashMap<String,String>();
		
		try {
			 
			FileInputStream src = new FileInputStream(path + "\\test\\"+Name+".xlsx");
			Workbook workbook = new XSSFWorkbook(src);
			Sheet sheet = workbook.getSheetAt(0);
			 
			int rows = sheet.getLastRowNum();  
			
			for (int i = 1; i <= rows; i++) {
				int columnNum = sheet.getRow(i).getLastCellNum();
				for (int j = 0; j <columnNum; j++) {
					if(sheet.getRow(i).getCell(j)==null)
						//sheet.getRow(i).getCell(j).setCellValue(" ");
					sheet.getRow(i).createCell(j).setCellValue(" ");
					//System.out.println(sheet.getRow(i).getCell(j));
					mapInner.put(sheet.getRow(0).getCell(j).getStringCellValue(),sheet.getRow(i).getCell(j).toString());
				}
				map.put(i,(HashMap<String, String>) mapInner.clone());
				mapInner.clear();
				
			}
			 
			workbook.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			 
			e.printStackTrace();
		}

		return map;
	}
    
	public static WebDriver setUp() {
		String path = System.getProperty("user.dir");
		WebDriver driver;
		//System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
		 WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		// driver.manage().window().setPosition(new Point(-2000,-2000));

		return driver;
	}

	public static void login(WebDriver driver, WebDriverWait wait,Logger log, String userName, String password, String url)
			throws InterruptedException {
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userNameXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userNameXpath))).sendKeys(userName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userPasswordXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userPasswordXpath))).sendKeys(password);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userLogignButtonXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.userLogignButtonXpath))).click();
		log.info("user :  " + userName + "   logged in");

	}
	public static void logOutException(WebDriver driver,WebDriverWait wait,WebElement webelement,JavascriptExecutor javascriptExecutor)throws InterruptedException {
		
		
		if(driver.findElements(By.xpath(ProvidePom.unsavedDataButtonXpath)).size()>0) {
				
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.discardButtonXpath))).click();
 
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostXpath);
		webelement.click();
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostUserXpath);
		webelement.click();
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostLogoutXpath);
		javascriptExecutor.executeScript("arguments[0].click();", webelement);
		 }
		else
		 {
			webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostXpath);
				webelement.click();
				webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostUserXpath);
				
               if(driver.findElements(By.xpath(ProvidePom.unsavedDataButtonXpath)).size()>0) {
					
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.discardButtonXpath))).click();
					
					webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostXpath);
					webelement.click();
					webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostUserXpath);
					webelement.click();
					webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostLogoutXpath);
					
					javascriptExecutor.executeScript("arguments[0].click();", webelement);
					}else {
				webelement.click();
				webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostLogoutXpath);
				
				javascriptExecutor.executeScript("arguments[0].click();", webelement);
				}
				
				if(driver.findElements(By.xpath(ProvidePom.unsavedDataButtonXpath)).size()>0) {
						
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.discardButtonXpath))).click();
		
				 }
		 }
 
		
	}
	
	public static void logOut(WebDriver driver,WebDriverWait wait,WebElement webelement,JavascriptExecutor javascriptExecutor)throws InterruptedException {
		
		
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostXpath);
		webelement.click();
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostUserXpath);
        
    	 webelement.click();
	
		
		webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shadowRootHostLogoutXpath);
		javascriptExecutor.executeScript("arguments[0].click();", webelement);    
		
	}
	
	
	public static void allSessionsLogOut(WebDriverWait wait ) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.allSessionsLogOutXpath))).click();
		
	}
	
	// for beleux entity
	public static void navigateToPurchasing(WebDriverWait wait,String purchaseForm ) {
		
		
		if(purchaseForm.equalsIgnoreCase("Std")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beStandardXpath))).click();
		}
		else if(purchaseForm.equalsIgnoreCase("Log")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beLogisticsXpath))).click();
			
		}
		else
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beSubcontractingXpath))).click();
		
	}

	
	//for Galitt entity
    public static void navigateToPurchasingGalitt(WebDriverWait wait,String purchaseForm ) {
	
		if(purchaseForm.equalsIgnoreCase("Std")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beStandardGalittXpath))).click();
		}
		else if(purchaseForm.equalsIgnoreCase("Log")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beLogisticsXpath))).click();
			
		}
		else
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beSubcontractingXpath))).click();
		
    	}
	
  
	//for France entity
	public static void navigateToPurchasingFR(WebDriverWait wait,String type ) {
		
		if(type.equalsIgnoreCase("DA")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beStandardXpath))).click();
		} else
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beHomeXpath))).click();
		
	}
	
	
	//For SBS136 entity to select form
public static void navigateToPurchasingSBS136FR(WebDriverWait wait,WebElement webelement,JavascriptExecutor javascriptExecutor,String organization,String type ) {
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.searchHomeXpath)));
	
	webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shopFRButtonXpath);
	webelement.click();
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.searchSubmitXpath)));
	webelement = (WebElement) javascriptExecutor.executeScript("return "+ProvidePom.shopXpath);
	webelement.click();
	
	// select organization
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.organizationSearchBoxXpath)));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.selectDropDownXpath)));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.modalTitleXpath)));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.fieldContaierXpath))).click();
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.organizationSearchXpath))).sendKeys(organization);
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.organizationCountXpath)));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.organizationPanelXpath +organization+"]"))).click();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.organizationSelectButtonXpath))).click();
	
	
		if(type.equalsIgnoreCase("DA")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beStandardXpath))).click();
		} else if(type.equalsIgnoreCase("DI")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beHomeXpath))).click();
		}
		else if(type.equalsIgnoreCase("Subcontracting")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beSubcontractingXpath))).click();
			}
		else if(type.equalsIgnoreCase("Logistics")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beLogisticsXpath))).click();
		} else 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.beStandardSBS136Xpath))).click();
			
		
	}
	
	
	public static void newPrRequisition(WebDriverWait wait ,String orderDescription ,String productName,String purchaseCategory,String supplier,String quantity,String unitPrice,String currency,String prType) {
	 

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).sendKeys(orderDescription);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductName))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductName))).sendKeys(productName);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryButtonXpath))).click();
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath))).sendKeys(purchaseCategory);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySelect1Xpath+purchaseCategory+ProvidePom.purchaseCategorySelect2Xpath))).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSearchXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSelect1Xpath+supplier+ProvidePom.supplierSelect2Xpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).sendKeys(quantity);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).sendKeys(unitPrice);
		
		
		
			currency=currency.toUpperCase();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencyButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearchXpath))).sendKeys(currency);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearch1Xpath+currency+ProvidePom.currencySearch2Xpath))).click();
			
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpath))).sendKeys(prType);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSelect1Xpath+prType+ProvidePom.prTypeSelect2Xpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editRequisitionXpath))).click();
	}
	
	public static void newPrRequisitionFR(WebDriverWait wait ,String orderDescription ,String purchaseCategory,String productName,String supplier,String quantity,String unitPrice,String currency,String prType) {
		 

		 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).sendKeys(orderDescription);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryButtonXpath))).click();
		String category= purchaseCategory.substring(0, 1)+purchaseCategory.substring(1,purchaseCategory.length()).toUpperCase();
		System.out.println(category);
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySelect1Xpath+category+ProvidePom.purchaseCategorySelect2Xpath))).click();
	
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameFR))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameFR))).sendKeys(productName);
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSearchXpathFR))).sendKeys(supplier);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSelect1XpathFR+supplier+ProvidePom.supplierSelect2XpathFR))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpathFR))).sendKeys(quantity);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpathFR))).sendKeys(unitPrice);
		
		
			currency=currency.toUpperCase();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencyButtonXpathFR))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearchXpathFR))).sendKeys(currency);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearch1XpathFR+currency+ProvidePom.currencySearch2XpathFR))).click();
			
		
		
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeButtonXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpathFR))).sendKeys(prType);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSelect1XpathFR+prType+ProvidePom.prTypeSelect2XpathFR))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editRequisitionXpath))).click();
	}
	
	
	
	// SBS136 new PR Requisition
	
	public static void newPrRequisitionSBS136(WebDriverWait wait ,String orderDescription ,String purchaseCategory,String codeProduct,String productName,String supplier,String quantity,String unitPrice,String currency,String prType,String PRForm) {
		 
		if(PRForm.equalsIgnoreCase("Subcontracting") || PRForm.equalsIgnoreCase("Logistics")) {

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).sendKeys(orderDescription);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameSBS136SubcontractingXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameSBS136SubcontractingXpath))).sendKeys(productName);
			
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath))).sendKeys(purchaseCategory);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySelect1Xpath+purchaseCategory+ProvidePom.purchaseCategorySelect2Xpath))).click();
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSearchXpath))).sendKeys(supplier);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSelect1XpathFR+supplier+ProvidePom.supplierSelect2XpathFR))).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).sendKeys(quantity);
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).sendKeys(unitPrice);
			
			
				currency=currency.toUpperCase();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencyButtonXpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearchXpath))).sendKeys(currency);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearch1Xpath+currency+ProvidePom.currencySearch2Xpath))).click();
				
			
	
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editRequisitionXpath))).click();
		}
		
		else {
		

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.orderDescription))).sendKeys(orderDescription);
		
		
	    
		if(PRForm.equalsIgnoreCase("Standard")) {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath))).sendKeys(purchaseCategory);
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySelect1Xpath+purchaseCategory+ProvidePom.purchaseCategorySelect2Xpath))).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.codeProductXpathFR))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.codeProductXpathFR))).sendKeys(productName);
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSearchXpath))).sendKeys(supplier);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSelect1XpathFR+supplier+ProvidePom.supplierSelect2XpathFR))).click();
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpath))).sendKeys(quantity);
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpath))).sendKeys(unitPrice);
			
			
				currency=currency.toUpperCase();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencyButtonXpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearchXpath))).sendKeys(currency);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearch1Xpath+currency+ProvidePom.currencySearch2Xpath))).click();
				
			
			
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpath))).sendKeys(prType);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSelect1Xpath+prType+ProvidePom.prTypeSelect2Xpath))).click();
			
		}else {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpathFR))).sendKeys(purchaseCategory);
			
			if(prType.equalsIgnoreCase("DI")) {
				purchaseCategory= purchaseCategory.substring(0, 1)+purchaseCategory.substring(1,purchaseCategory.length()).toUpperCase();
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySelect1Xpath+purchaseCategory+ProvidePom.purchaseCategorySelect2Xpath))).click();
		
		// code Product
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.codeProductXpathFR))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.codeProductXpathFR))).sendKeys(codeProduct);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameFR))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierProductNameFR))).sendKeys(productName);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSearchXpathFR))).sendKeys(supplier);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierSelect1XpathFR+supplier+ProvidePom.supplierSelect2XpathFR))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityXpathFR))).sendKeys(quantity);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.unitPriceXpathFR))).sendKeys(unitPrice);
		
		
			currency=currency.toUpperCase();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencyButtonXpathFR))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearchXpathFR))).sendKeys(currency);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.currencySearch1XpathFR+currency+ProvidePom.currencySearch2XpathFR))).click();
			
		
		
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeButtonXpathFR))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpathFR))).sendKeys(prType);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSelect1XpathFR+prType+ProvidePom.prTypeSelect2XpathFR))).click();
		}
		
		
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editRequisitionXpath))).click();
		}
	}
	
	
	public static void  headerData(WebDriverWait wait,String requester) {
	    if(!requester.isEmpty()) {
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.requesterXpath))).click();
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.requesterSearchXpath))).sendKeys(requester);
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.requesterSelect1Xpath+requester+ProvidePom.requesterSelect2Xpath))).click();
	    }
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.headerDataSaveXpath))).click();
		
	}
	
	
	public static void  addAddress(WebDriverWait wait,String deliveryAddress) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addAdressEditButtonXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.addAdressEditButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addressXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addressSearchXpath))).sendKeys(deliveryAddress);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addressSelect1Xpath+deliveryAddress+ProvidePom.addressSelect2Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addressSaveButtonXpath))).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.headerDataSaveXpath))).click();
		
	}
	
	
	
	public static void  editCoding(WebDriverWait wait,String nature,String agency,String type, String ssp) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodingXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.editCodingXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodingButtonXpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).sendKeys(nature);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).sendKeys(nature);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSelect1Xpath+nature+ProvidePom.natureSelect2Xpath))).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureNameSelect1Xpath+nature+ProvidePom.natureNameSelect2Xpath)));
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencyXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencyDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).sendKeys(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).sendKeys(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearch1Xpath))).click();
		System.out.println(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySelect1Xpath+agency+ProvidePom.agencySelect2Xpath))).click();
		
		
		// ssp coding
		
		if(type.equalsIgnoreCase("A")) {
			
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspDivXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(ssp);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearch1Xpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(ssp);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearch1Xpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSelect1Xpath+ssp+ProvidePom.sspSelect2Xpath))).click();
			/*
			 * if(agency.equalsIgnoreCase("0933")) {
			 * 
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspXpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspDivXpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).sendKeys("AT SBGM 2020 (933)");
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearch1Xpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).clear();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).sendKeys("AT SBGM 2020 (933)");
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearch1Xpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSelect1Xpath+"AT SBGM 2020 (933)"+ProvidePom.sspSelect2Xpath))).click();
			 * 
			 * }
			 * 
			 * if(agency.equalsIgnoreCase("0900")) {
			 * 
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspXpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspDivXpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).sendKeys("99");
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearch1Xpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).clear();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearchXpath))).sendKeys("99");
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSearch1Xpath))).click();
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.
			 * sspSelect1Xpath+"99"+ProvidePom.sspSelect2Xpath))).click();
			 * 
			 * }
			 */
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodeSaveButtonXpath))).click();
		
		
	}
	public static void  editCodingFR(WebDriverWait wait,String nature,String agency,String type,String realEstateReference,String prType,String Approver,String ssp) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodingXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.editCodingXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodingButtonXpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).sendKeys(nature);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearchXpath))).sendKeys(nature);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureSelect1Xpath+nature+ProvidePom.natureSelect2Xpath))).click();
	
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.agencyXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencyXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencyDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).sendKeys(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearchXpath))).sendKeys(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySearch1Xpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySelect1Xpath+agency+ProvidePom.agencySelect2Xpath))).click();
		
		
		// ssp coding
	if(type.equalsIgnoreCase("A")) {
			
			
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspDivXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(ssp);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearch1Xpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).clear();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(ssp);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearch1Xpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSelect1Xpath+ssp+ProvidePom.sspSelect2Xpath))).click();
	
		}
	
	


	
		
		if(!realEstateReference.isEmpty()) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceXpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceDivXpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSearchXpath))).sendKeys(realEstateReference);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSearch1Xpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSearchXpath))).clear();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSearchXpath))).sendKeys(realEstateReference);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSearch1Xpath))).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.realEstateReferenceSelect1Xpath+realEstateReference+ProvidePom.realEstateReferenceSelect2Xpath))).click();
		
		}
		
//		if(prType.equalsIgnoreCase("DI")) {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverXpath))).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverDivXpath))).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).sendKeys(Approver);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearch1Xpath))).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).clear();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).sendKeys(Approver);
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearch1Xpath))).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSelect1Xpath+Approver+ProvidePom.approverSelect2Xpath))).click();
//	
//	}
		
		
		
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.editCodeSaveButtonXpath))).click();
		
		
	}
	
	
	
	public static void  viewDetails(WebDriverWait wait,String quantity,String unitPrice,String paymentTerms) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.viewDetailsXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.viewDetailsXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.viewDetailsButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataXpath))).click();
		
		double price;
		double quant;
		double uPrice;
		quant= Double.parseDouble(quantity.replace(",", "."));
		uPrice= Double.parseDouble(unitPrice.replace(",", "."));
		price= quant*uPrice; 
		String sellingPrice= Double.toString(price).replace(".", ",");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sellingPriceXpath))).sendKeys(sellingPrice);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsSearchXpath))).sendKeys(paymentTerms);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsSelect1Xpath+paymentTerms+ProvidePom.paymentTermsSelect2Xpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataSaveXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataSaveButtonVisibleXpath)));
		
		
		
	}
public static void  viewDetailsFR(WebDriverWait wait,String quantity,String unitPrice,String paymentTerms) {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.viewDetailsXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.viewDetailsXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.viewDetailsButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataXpath))).click();
		
		int price;
		
		price= Integer.parseInt(quantity)*Integer.parseInt(unitPrice); 
		String sellingPrice= Integer.toString(price);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sellingPriceXpath))).sendKeys(sellingPrice);
		if(!paymentTerms.isEmpty()) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsButtonXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsSearchXpath))).sendKeys(paymentTerms);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.paymentTermsSelect1Xpath+paymentTerms+ProvidePom.paymentTermsSelect2Xpath))).click();
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataSaveXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.lineDataSaveButtonVisibleXpath)));
		
		
		
	}
	
	
	
	
	public static void  getApproval(WebDriverWait wait)throws Exception {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.backPageXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.getApproveXpath))).click();
		
		
		
	}
	
	
	
	public static String  getPrNumber(WebDriver driver,WebDriverWait wait)throws Exception {
		
		Thread.sleep(3000);
		driver.navigate().back();
		driver.navigate().back();
		driver.navigate().back();
		String Pr = driver.findElement(By.xpath(ProvidePom.getPrApproveNumberXpath)).getText();
		System.out.println(Pr);
		
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.backPageXpath))).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.getApproveXpath))).click();
		
		return Pr;
	}
	
	
	public static void  getApprove(WebDriverWait wait)throws Exception {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taskListXpath))).click();
		
		
	}
	
	
	
	public static void  getReview( WebDriverWait wait)throws Exception {
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approveTaskListXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.allTasksXpath)));
		
		
		
	}

	
	public static String getTaskList(WebDriverWait wait, String orderDescription,String docType) throws Exception {

		int count = 0;
		String invoice=orderDescription;
		String concOrderDesc;
		concOrderDesc = "\"" + orderDescription+ "\"";
		String totalRows = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.totalRowsTaskListXpath)))
				.getText();
		int total = Integer.parseInt(totalRows);
		

		int rows = 50;
		int temp = rows;
		int noMatch = 0;

		int Total = total; 

		String document;
		 String orderDesc = " ";
		 
		//String docType = ProvidePom.docType;
		 
		for (int i = 1; i <= rows; i++) {

			count++;
			noMatch++;
			
			// get document type from approval page.
			document = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(ProvidePom.docType1Xpath + i + ProvidePom.docType2Xpath)))
					.getText();
			
			
			 
			
			if(docType.equalsIgnoreCase("Purchase requisition")) {    // For Pr requisition part we have to match invoice no.
				if (docType.equalsIgnoreCase(document)) {
					orderDesc="";
					
					
					
					orderDesc = wait
							.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath(ProvidePom.orderDescription1Xpath+i+ProvidePom.orderDescription2Xpath)))
							.getText();
					System.out.println(orderDesc);
					System.out.println(concOrderDesc);
					
					if (orderDesc.toLowerCase().equalsIgnoreCase(concOrderDesc)) {
						
						wait
						.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath(ProvidePom.orderDescription1Xpath + i + ProvidePom.orderDescription2Xpath)))
						.click();

						return concOrderDesc;
	                    
					} else {

						if (noMatch == Total) {
							return " ";
						}
					}

				}
			 else {

				if (noMatch == Total) {
					return " ";
				}
				if (count == rows) {

					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn-link']")))
							.click();

					total = total - temp;
					if (total < temp) {
						rows += total;

					} else
						rows += temp;

				}

			} 
			
		}  else {
			
			if(docType.equalsIgnoreCase("Invoice")) {    // For Invoice part we have to match invoice no.
			
			
				if (docType.equalsIgnoreCase(document)) {
					orderDesc="";
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceMatchButton1Xpath+i+ProvidePom.invoiceMatchButton2Xpath))).click();
					
					orderDesc = wait
							.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath(ProvidePom.invoiceMatchText1Xpath+i+ProvidePom.invoiceMatchText2Xpath)))
							.getText();
					System.out.println(orderDesc);
					System.out.println(concOrderDesc);
					
					if (orderDesc.toLowerCase().equalsIgnoreCase(orderDescription)) {
						
						//open invoice
						
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceOPenPanel1Xpath+i+ProvidePom.invoiceOPenPanel2Xpath))).click();
						
						return orderDesc;
	                    
					} else {

						if (noMatch == Total) {
							return " ";
						}
					}

				}else {

					if (noMatch == Total) {
						return " ";
					}
					if (count == rows) {

						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn-link']")))
								.click();

						total = total - temp;
						if (total < temp) {
							rows += total;

						} else
							rows += temp;

					}

				} 
				
				
			
			} 
		}

		}

		return " ";

	}

	public static void approveTaskList(WebDriver driver,WebDriverWait wait,String docType) throws Exception {
		
		
		if(docType.equalsIgnoreCase("Invoice")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceApproveButtonXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceApproveButtonXpath)));
		     if(driver.findElements(By.xpath(ProvidePom.allTasksXpath)).size()>0) {
		    	 
		     }
		}
		else
			if(docType.equalsIgnoreCase("Purchase requisition")) {
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approveTaskListXpath))).click();
		System.out.println(driver.findElements(By.xpath(ProvidePom.allTasksXpath)).size());
 	    if(driver.findElements(By.xpath(ProvidePom.allTasksXpath)).size()>0) {
		    	 
		     }
	}
		}
	
	
	}




















