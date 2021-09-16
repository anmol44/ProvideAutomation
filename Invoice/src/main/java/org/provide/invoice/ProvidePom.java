package org.provide.invoice;

public class ProvidePom {

	public static final String inputFile = System.getProperty("user.dir") +"/test/TestDataProvide.xlsx";
	public static final String outputFile = System.getProperty("user.dir") +"/Output/OutputDataProvide.xlsx";
	
	static String url="https://voflusoprastdev.p2p.basware.com";
	
	static String userNameXpath = "//*[@id=\"txtLogin\"]";
	static String userPasswordXpath = "//*[@id=\"txtPassword\"]";
	static String userLogignButtonXpath ="//input[@type='submit' and @id='btnLogin']";     // "/html/body/div[2]/form/div[7]/input"; //input[@id='btnLogin' and @type='submit']";
	
	public static String docType = "Invoice";
	public static String logOutUrl = "https://voflusoprastdev.p2p.basware.com/edge/#/logout/_2Fedge_2Fapi_2Flogout";
	
	static String shadowRootHostXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav > div.pt-navbar-nav.main-nav > ul > li:nth-child(1) > a')";
	static String shadowRootHostUserXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('alusta-navigation-user-menu').shadowRoot.querySelector('#dropdownUserMenu > span')";
	static String shadowRootHostLogoutXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('alusta-navigation-user-menu').shadowRoot.querySelector('div > alusta-navigation-dropdown > div:nth-child(6) > a')";
	static String allSessionsLogOutXpath = "//span[@class='ng-scope' and contains(text(),'All sessions')]";
	
	
	static String taskXpath = "//span[@class='btn btn-link' and contains(text(),'here')]";
	static String beStandardGalittXpath = "//div[@class='card-icon fa fa-map-marker']";
	
	static String userHelloNameXpath ="//span[@class='ng-scope' and contains(text(),'Hello')]";
	
	static String accountsPayableSSGXpath="document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav > div.pt-navbar-nav.main-nav > ul > li:nth-child(6) > a')";
	static String accountsPayableXpath="document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav > div.pt-navbar-nav.main-nav > ul > li:nth-child(7) > a')";
	
	
	static String recievedStageXpath = "//div[@class='chart-caption ng-star-inserted' and contains(text(),'Received')]";
	static String invoiceXpath = "(//input[@class='pt-input ng-untouched ng-pristine ng-valid'])[1]";
	
	static String poXpath="(//div[@class='pt-select-combobox-main'])[1]";
	static String poDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	
	//static String poSearchXpath="(//span[@class='pt-select-placeholder ng-star-inserted'])[1]";
	static String poSearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String poSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String poSelect2Xpath="')]";
	
	static String supplierCodeXpath="(//div[@class='pt-select-combobox-main'])[3]";   //(//ul[@class='pt-select-selections ng-star-inserted'])[1]
	//static String supplierCodeInputXpath="(//div[@class='pt-select-combobox-main'])[3]//child::input"; 
	static String supplierCodeDivXpath="(//div[@class='pt-select-selection-label'])[1]";  // //div[@class='pt-select-dropdown-header ng-star-inserted']
	static String supplierCodeSearchXpath="(//div[@class='pt-select-selection-label'])[2]";
	static String supplierCodeSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String supplierCodeSelect2Xpath="')]";
	
	
	static String supplierCodeButtonXpath="(//button[@class='pt-select-toggle-btn'])[5]"; 
	static String supplierCodePanelXpath="/html/body/pt-floating-panel";
	static String supplierCodeSearchContainerXpath="//div[@class='searchContainer']";
	static String supplierCodeInputXpath="/html/body/pt-floating-panel/div[2]/div/div/ng-component/div/div/div[2]/div[1]/pt-search-input/input";
	static String supplierCodeSelectXpath="(//button[@class='pt-btn action-input-modal-btn'])";
	static String supplierSearchInputXpath="//pt-search-input[@class='search-input']";
	
	static String invoiceSearchXpath="//button[@class='pt-btn' and contains(text(),'Search')]";
	
	static String invoiceDateXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[1]/div[2]/div/div/div/div[2]/div/ia-invoice-details-tabs/pt-tabs/mat-tab-group/div/mat-tab-body[2]/div/ia-invoice-header/form/div[10]/pt-field-container/div/div/div/pt-date-picker/div/div[1]/div/div[1]/input";
	static String netSumXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[1]/div[2]/div/div/div/div[2]/div/ia-invoice-details-tabs/pt-tabs/mat-tab-group/div/mat-tab-body[2]/div/ia-invoice-header/form/div[13]/pt-field-container/div/div/div/gl-general-editor/pt-decimal-field-editor/input";
	static String taxSumXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[1]/div[2]/div/div/div/div[2]/div/ia-invoice-details-tabs/pt-tabs/mat-tab-group/div/mat-tab-body[2]/div/ia-invoice-header/form/div[14]/pt-field-container/div/div/div/gl-general-editor/pt-decimal-field-editor/input";
	///html/body/bw-root/ia-invoices/pt-split-view/div/div[1]/div[2]/div/div/div/div[2]/div/ia-invoice-details-tabs/pt-tabs/mat-tab-group/div/mat-tab-body[2]/div/ia-invoice-header/form/div[13]/pt-field-container/div/div/div/gl-general-editor/pt-decimal-field-editor/input
	
	static String prTypeXpath="(//button[@class='pt-select-toggle-btn' and @data-t-id='dropdown-toggle' ])[2]";
	static String prTypeDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	static String prTypeSearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String prTypeSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String prTypeSelect2Xpath="')]";
	
	static String approverXpath="(//button[@class='pt-select-toggle-btn' and @data-t-id='dropdown-toggle' ])[5]";
	static String approverDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	static String approverSearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String approverSelectXpath="//span[@class='pt-item-list-item-header']";
	static String approverSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String approverSelect2Xpath="')]";
	
	static String addNewLineXpath="//button[@class='pt-btn pt-btn-link ng-star-inserted']";
	static String addNewLineGridXpath="(//div[@class='grid-container'])[2]";
	static String horizontalScrollPortXpath="(//div[@class='ag-body-horizontal-scroll-viewport'])[2]";
	static String horizontalScrollXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[5]/div[2]";
	
	
	static String currencyXpath="(//button[@class='pt-select-toggle-btn' and @data-t-id='dropdown-toggle' ])[3]";
	static String currencySearchXpath="//input[@class='pt-input ng-pristine ng-valid ng-touched' ]";
	static String currencySelect1Xpath="//span[@class='pt-highlighted-text'  and contains(text(),'";
	static String currencySelect2Xpath="')]";
	
	static String saveButtonXpath="//button[@class='pt-btn' and contains(text(),'Save')]";
	
	static String openXpath=   "//button[@class='pt-btn ng-star-inserted']//child::span[contains(text(),'Open')]";     //"(//button[@class='pt-btn ng-star-inserted'])[2]";
	static String openToMatchXpath="//button[@class='pt-btn ng-star-inserted']";
	
	static String sendToValidationXpath= "//button[@class='pt-btn ng-star-inserted']//child::span[contains(text(),'Send to validation')]";    //"(//button[@class='pt-btn ng-star-inserted'])[1]";
	
	static String tryRefreshingXpath="//button[@class='pt-btn-link ng-star-inserted' and contains(text(),'Try refreshing')]";
	static String refreshButtonXpath="//button[@class='pt-btn pt-btn-borderless pt-btn-md link-btn pt-clickable' and contains(text(),'Refresh')]";
	
	static String searchXpath="//button[@class='pt-btn pt-btn-primary search-button' and contains(text(),'Search')]";
	
	static String quantityToMatchXpath="/html/body/bw-root/bw-order-matching/bw-manual-matching/div/div/pt-split-view/div/div[1]/div[2]/div/div/bw-matching-search-results/div/div[3]/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[4]";

	static String quantityToMatchInputXpath="/html/body/bw-root/bw-order-matching/bw-manual-matching/div/div/pt-split-view/div/div[1]/div[2]/div/div/bw-matching-search-results/div/div[3]/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[4]/gl-grid-general-editor/gl-general-editor/pt-decimal-field-editor/input";
	static String matchXpath="//button[@class='pt-btn pt-btn-secondary pt-btn-md match-button' and @data-t-rel='match']";
	
	static String purchaseCategoryXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[9]/gl-lookup-list-renderer/span";
	                                 //    /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[9]/gl-lookup-list-renderer/span   
	                                 //    /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[9]/gl-lookup-list-renderer/span
	                                 //    /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[1]/div[6]/gl-lookup-list-renderer/span
	static String purchaseCategoryDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	static String purchaseCategorySearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String purchaseCategorySelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String purchaseCategorySelect2Xpath="')]";
	static String purchaseCategorySelectXpath="//span[@class='pt-item-list-item-header']";
	
	static String natureCodeXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[11]/gl-lookup-list-renderer//child::span[@title!='']";
	
	static String agencySiteXpath= "/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[12]/gl-lookup-list-renderer/span";
	                             //   /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[12]/gl-lookup-list-renderer/span
	static String agencySiteDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	static String agencySiteSearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String agencySiteSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String agencySiteSelect2Xpath="')]";
	static String agencySiteSelectXpath="//span[@class='pt-item-list-item-header']";
	static String agencySiteVisibleXpath="/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[13]/gl-lookup-list-renderer//child::span[@title!='']";
									//	  /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[13]/gl-lookup-list-renderer                                    
	static String sspXpath= "/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[14]/gl-lookup-list-renderer/span";
						   //  /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[14]/gl-lookup-list-renderer/span			  
	static String sspDivXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']";
	static String sspSearchXpath="//div[@class='pt-select-dropdown-header ng-star-inserted']//child::input";
	static String sspSelect1Xpath="//span[@class='pt-highlighted-text' and contains(text(),'";
	static String sspSelect2Xpath="')]";
	static String sspVisibleXpath="(/html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div/div[1])//child::span[@title!='']";
	                             //   /html/body/bw-root/ia-invoices/pt-split-view/div/div[2]/div[2]/div/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/gl-document-rows/div/gl-fields-grid/gl-grid/pt-grid/ag-grid-angular/div/div[2]/div[2]/div[3]/div[2]/div/div/div[2]/div[1]
	
	// Div class for confirmation of matching
	static String spinnerXpath="//pt-busy";
	static String divXpath="(//div[@class='ag-body-viewport ag-layout-normal ag-row-no-animation' and @role='presentation'])[3]";
	static String addCodingXpath="//span[@class='ng-star-inserted' and contains(text(),'Add coding')]";
	static String taxCodeXpath="/html/body/bw-root/bw-order-matching/bw-manual-matching/div/div/pt-split-view/div/div[2]/div[2]/div/span/ia-invoice-coding/pt-tabs/mat-tab-group/div/mat-tab-body/div/div/div/bw-document-rows/div/bw-fields-grid/bw-grid/pt-grid/ag-grid-angular/div/div[2]/div[1]/div[3]/div[2]/div/div/div[1]/div[4]/bw-grid-lookup-list-editor-more/pt-select-more/div/div/ul/li/div";
    
	static String confirmButtonXpath="//button[@class='pt-btn ng-star-inserted']//child::span[contains(text(),'Confirm')]";
	
	
	// open invoice button for approve  
	public static String invoiceMatchButton1Xpath="/html/body/div/div/bw-task-list-all/div/div/pal-list/div/table/tbody[";
	public static String invoiceMatchButton2Xpath="]/tr[1]/td[1]/button/i//parent::button";
	
	
	

}    
