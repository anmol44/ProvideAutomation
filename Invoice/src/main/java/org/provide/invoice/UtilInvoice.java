package org.provide.invoice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class UtilInvoice {

	public static void navigateToAccountsPayable(WebElement webElement,WebDriverWait wait, JavascriptExecutor javascriptExecutor)  {
       
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.userHelloNameXpath)));
		// account payable xpath different for each entity.
		//  webElement = (WebElement)javascriptExecutor.executeScript("return "+ProvidePom.accountsPayableSSGXpath);
		webElement = (WebElement)javascriptExecutor.executeScript("return "+ProvidePom.accountsPayableXpath);
		   webElement.click();
		 

	}
	
	public static void receivedStage(WebDriverWait wait)  {
	       
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.recievedStageXpath))).click();
		
		 

	}
	
	public static void invoiceSearch(WebDriverWait wait,String invoice)  {
	       
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceXpath))).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceXpath))).sendKeys(invoice);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.invoiceSearchXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceSearchXpath))).click();
		 

	}
	
	public static void savePoInvoice(WebDriver driver,WebDriverWait wait,String invoice,String po,String supplierCode,String invoiceDate,String invoiceAmt,String taxAmt) throws InterruptedException  {

		
		//elememt header data section
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='mat-tab-labels'])[1]")));
		WebElement element = driver.findElement(By.xpath("(//div[@class='mat-tab-labels'])[1]"));
		List<WebElement> elements = element.findElements(By.xpath("./child::*"));
		elements.get(1).click();
		
		try {
			
			
		}
		catch(Exception e) {
			
		}
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.poDivXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poSearchXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poSearchXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poSearchXpath))).sendKeys(po);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.poSelect1Xpath+po+ProvidePom.poSelect2Xpath))).click();
		
	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeButtonXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.supplierCodeButtonXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeButtonXpath))).click();
     	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodePanelXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.supplierCodeSearchContainerXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//pt-search-input[@class='search-input']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeInputXpath))).sendKeys(supplierCode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeSelectXpath))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.invoiceDateXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceDateXpath))).sendKeys(invoiceDate);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(invoiceAmt);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(taxAmt);
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.saveButtonXpath))).click();
		Thread.sleep(2000);

	}
	
	public static void editNonPoInvoice(WebDriver driver,WebDriverWait wait,String invoice,String prType,String supplierCode,String invoiceDate,String invoiceAmt,String taxAmt,String approver) throws InterruptedException  {

		//elememt header data section
		
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='mat-tab-labels'])[1]")));
				List<WebElement> elements = driver.findElements(By.xpath("(//div[@class='mat-tab-labels'])[1]"));
				elements.get(0).click();
				
				
				
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='mat-tab-label-0-1']"))).click();		
				
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.prTypeDivXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSearchXpath))).sendKeys(prType);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.prTypeSelect1Xpath+prType+ProvidePom.prTypeSelect2Xpath))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeButtonXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.supplierCodeButtonXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeButtonXpath))).click();
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodePanelXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.supplierCodeSearchContainerXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.supplierSearchInputXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeInputXpath))).sendKeys(supplierCode);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.supplierCodeSelectXpath))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.invoiceDateXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.invoiceDateXpath))).sendKeys(invoiceDate);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.netSumXpath))).sendKeys(invoiceAmt);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.taxSumXpath))).sendKeys(taxAmt);
		
		
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverXpath))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.approverDivXpath)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverDivXpath))).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).sendKeys(Keys.CONTROL+"a");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).sendKeys(Keys.BACK_SPACE);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSearchXpath))).sendKeys(approver);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.approverSelectXpath))).click();
			
			
			


	}
	
	
	public static void saveAndAddLines(WebDriver driver,WebDriverWait wait,JavascriptExecutor javascriptExecutor, String lines,String purchaseCategoryList,String agencyList,String typeList,String sspList) throws InterruptedException  {


		// add new line
		
		
		int line =Integer.parseInt(lines);
	
		ArrayList<String> purchaseList= new ArrayList<String>(Arrays.asList(purchaseCategoryList.split("\\r?\\n")));
		ArrayList<String> agencylist= new ArrayList<String>(Arrays.asList(agencyList.split("\\r?\\n")));
		ArrayList<String> typelist= new ArrayList<String>(Arrays.asList(typeList.split("\\r?\\n")));
		ArrayList<String> ssplist= new ArrayList<String>(Arrays.asList(sspList.split("\\r?\\n")));
		
		String purchasecategory = purchaseList.get(0);
		String agency = agencylist.get(0);
		String type = typelist.get(0);
		String ssp = ssplist.get(0);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addNewLineXpath))).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.addNewLineGridXpath)));
		driver.switchTo().activeElement();
		
		

		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.purchaseCategoryDivXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategoryDivXpath)))
				.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath)))
				.sendKeys(Keys.CONTROL + "a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath)))
				.sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.purchaseCategorySearchXpath)))
				.sendKeys(purchasecategory);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				ProvidePom.purchaseCategorySelect1Xpath + purchasecategory + ProvidePom.purchaseCategorySelect2Xpath)))
				.click();

		// wait for nature code
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.natureCodeXpath)));
		

		
		
		//agency site
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.agencySiteDivXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteSearchXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteSearchXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteSearchXpath))).sendKeys(agency);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteSelect1Xpath+agency+ProvidePom.agencySiteSelect2Xpath))).click();

		
	
		driver.findElement(By.xpath(ProvidePom.horizontalScrollPortXpath));
		WebElement element1 = driver.findElement(By.xpath(ProvidePom.horizontalScrollXpath));
		
	
    	javascriptExecutor.executeScript("arguments[0].scrollLeft += 600;",element1);
		
    	Thread.sleep(1000);
    	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.agencySiteVisibleXpath)));
		
        
		// ssp 
        if(type.equalsIgnoreCase("A")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspXpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.sspDivXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspDivXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSearchXpath))).sendKeys(ssp);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sspSelect1Xpath+ssp+ProvidePom.sspSelect2Xpath))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.sspVisibleXpath)));
        }
        
        
        for(int k=1;k<line;k++) {
        	 purchasecategory = purchaseList.get(k);
        	
        	wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.addCodingXpath))).click();
        	Thread.sleep(1000);
        	
        	
        	
        }
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.saveButtonXpath))).click();
	

	}
	
	public static void sendToValidate(WebDriverWait wait) throws InterruptedException  {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.openXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.sendToValidationXpath))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.tryRefreshingXpath))).click();
	}
	
	public static void openAndMatchInvoice(WebDriverWait wait,String matchQty) throws Exception   {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.tryRefreshingXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.tryRefreshingXpath))).click();
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.refreshButtonXpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.refreshButtonXpath)));
		
		Thread.sleep(10000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.openToMatchXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.openToMatchXpath))).click();
		
		
		
		
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.searchXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityToMatchXpath))).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityToMatchInpuXpath))).clear();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityToMatchInputXpath))).sendKeys(Keys.CONTROL+"a");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityToMatchInputXpath))).sendKeys(Keys.BACK_SPACE);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.quantityToMatchInputXpath))).sendKeys(matchQty);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.matchXpath))).click();
		
		//wait until confirm button active
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.spinnerXpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ProvidePom.confirmButtonXpath))).click();
		
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ProvidePom.tryRefreshingXpath)));

	}
}
