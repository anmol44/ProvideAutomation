package org.provide.commons;

public class ProvidePom {

	static String username = "EMEAAD\\smarti";
	static String password="Provide2018";
	
	static final String FILE_NAME = "C:\\Users\\anchaudhary\\Desktop\\MyFirstExcel.xlsx";
	public static final String inputFile = System.getProperty("user.dir") +"/test/";
	public static final String outputFile = System.getProperty("user.dir") +"/Output/OutputDataProvide.xlsx";
	
	static String url="https://voflusoprastdev.p2p.basware.com";
	
	static String userNameXpath = "//*[@id=\"txtLogin\"]";
	static String userPasswordXpath = "//*[@id=\"txtPassword\"]";
	static String userLogignButtonXpath ="//input[@type='submit' and @id='btnLogin']";     // "/html/body/div[2]/form/div[7]/input"; //input[@id='btnLogin' and @type='submit']";
	
	public static String logOutUrl = "https://voflusoprastdev.p2p.basware.com/edge/#/logout/_2Fedge_2Fapi_2Flogout";
	
	static String shadowRootHostXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav > div.pt-navbar-nav.main-nav > ul > li:nth-child(1) > a')";
	static String shadowRootHostUserXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('alusta-navigation-user-menu').shadowRoot.querySelector('#dropdownUserMenu > span')";
	static String shadowRootHostLogoutXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('alusta-navigation-user-menu').shadowRoot.querySelector('div > alusta-navigation-dropdown > div:nth-child(6) > a')";
	static String allSessionsLogOutXpath = "//span[@class='ng-scope' and contains(text(),'All sessions')]";
	
	public static String docType = "Purchase requisition";
	
	static String searchHomeXpath="//span[@class='pal-search-submit-text ng-scope' and contains(text(),'Search')]";
	static String shopFRButtonXpath="document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav > div.pt-navbar-nav.main-nav > ul > li:nth-child(2) > a')";
	
	//select organization
	static String searchSubmitXpath="//button[@class='btn pal-search-submit' and @type='submit']";
	static String shopXpath = "document.querySelector('alusta-navigation').shadowRoot.querySelector('div > nav.pt-navbar-second-level > div:nth-child(2) > ul > li:nth-child(1) > a')";
	static String selectDropDownXpath= "//div[@class='pal-tree-select dropdown']";
	static String modalTitleXpath ="//span[@class='modal-title ng-binding']";
	static String fieldContaierXpath="/html/body/div[1]/div/div/pal-modal/div/div[2]/pal-modal-body/pal-field-container/div/div/div/pal-tree-single-select/div/div[1]/div";
	static String organizationSearchBoxXpath="//div[@class='modal-content']";
	static String organizationSearchButtonXpath="//button[@class='pal-tree-select-toggle-btn']";
	static String organizationSearchXpath="//input[@class='pal-tree-select-input margin-xs form-control ng-pristine ng-untouched ng-valid ng-scope ng-empty']";
	static String organizationCountXpath = "//div[@class='pal-tree-view-item-count ng-scope']";
	static String organizationPanelXpath ="//div[@class='pal-tree-view-nodes']//child::pal-tree-item//child::div[@data-t-node-code=";
	static String organizationSelect1Xpath="//span[@class='pal-tree-item-code ng-binding' and contains(text(),'";
	static String organizationSelect2Xpath="')]//parent::span";
	static String organizationSelectButtonXpath="//button[@class='btn btn-pal-action-button ng-scope']";
	
	
	static String beStandardXpath = "//*/div[@class='card-icon fa fa-shopping-bag']";
	public static String beStandardGalittXpath = "//div[@class='card-icon fa fa-map-marker']";
	static String beLogisticsXpath = "//div[@class='card-icon fa fa-building']";
	static String beSubcontractingXpath = "//div[@class='card-icon fa fa-user']";
	static String beStandardSBS136Xpath = "//div[@class='card-icon fa fa-map-marker']";
	
	static String beHomeXpath="//*/div[@class='card-icon fa fa-home']";
	static String orderDescription = "/html/body/div/section/section/section/div/form/ul/li[1]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-text-field/pal-field-container/div/div/div/input";
	static String supplierProductName = "/html/body/div/section/section/section/div/form/ul/li[2]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-text-field/pal-field-container/div/div/div/input";
	static String supplierProductNameFR="/html/body/div/section/section/section/div/form/ul/li[4]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-text-field/pal-field-container/div/div/div/input";
	static String purchaseCategoryButtonXpath = "//button[@type='button' and @class='pal-tree-select-toggle-btn ng-invalid']";
	static String purchaseCategorySearchXpath = "/html/body/div/section/section/section/div/form/ul/li[3]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-category-field/pal-field-container/div/div/div/pal-tree-single-select/div/div[2]/pal-tree-view/div/div[1]/div/input";
    static String purchaseCategorySelect1Xpath = "//span[@class='pal-tree-item-label ng-binding' and contains(text(),'";
    static String purchaseCategorySelect2Xpath = "')]/parent::span";
    static String purchaseCategorySearchXpathFR  ="/html/body/div/section/section/section/div/form/ul/li[2]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-category-field/pal-field-container/div/div/div/pal-tree-single-select/div/div[2]/pal-tree-view/div/div[1]/div/input";

    static String codeProductXpathFR="//input[@class='form-control ng-pristine ng-untouched ng-valid ng-scope ng-isolate-scope ng-empty ng-valid-required ng-valid-minlength ng-valid-maxlength']";
    
    static String supplierButtonXpath = "//button[@type='button' and @class='pal-single-select-toggle-btn'][1]";
	static String supplierSearchXpath = "/html/body/div/section/section/section/div/form/ul/li[4]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-category-supplier-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String supplierSearchXpathFR="/html/body/div/section/section/section/div/form/ul/li[5]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-category-supplier-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String supplierSelect1Xpath = "//span[@class='ng-binding'and @ng-bind-html='item.name | highlight:vm.searchString' and contains(text(),'";
	static String supplierSelect2Xpath = "')]";
	
	static String supplierSelect1XpathFR="//span[@class='ui-select-highlight' and contains(text(),'";
	static String supplierSelect2XpathFR = "')]";
	
	static String supplierProductNameSBS136SubcontractingXpath= "/html/body/div/section/section/section/div/form/ul/li[2]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-text-field/pal-field-container/div/div/div/input";
	
	static String quantityXpath = "/html/body/div/section/section/section/div/form/ul/li[5]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-decimal-field/pal-field-container/div/div/div/input";
	static String quantityXpathFR="/html/body/div/section/section/section/div/form/ul/li[6]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-decimal-field/pal-field-container/div/div/div/input";

	static String unitPriceXpath = "/html/body/div/section/section/section/div/form/ul/li[6]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-decimal-field/pal-field-container/div/div/div/input";
	static String unitPriceXpathFR="/html/body/div/section/section/section/div/form/ul/li[7]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-decimal-field/pal-field-container/div/div/div/input";
	
	static String currencyButtonXpath="/html/body/div/section/section/section/div/form/ul/li[7]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[1]/button";
	static String currencySearchXpath="/html/body/div/section/section/section/div/form/ul/li[7]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String currencySearch1Xpath="//span[@class='ui-select-highlight' and contains(text(),'";
	static String currencySearch2Xpath = "')]";
	
	
	static String currencyButtonXpathFR="/html/body/div/section/section/section/div/form/ul/li[8]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[1]/button";
	static String currencySearchXpathFR="/html/body/div/section/section/section/div/form/ul/li[8]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String currencySearch1XpathFR="//span[@class='ui-select-highlight' and contains(text(),'";
	static String currencySearch2XpathFR = "')]";
	
	static String prTypeButtonXpath = "/html/body/div/section/section/section/div/form/ul/li[8]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[1]/div";
	static String prTypeSearchXpath = "/html/body/div/section/section/section/div/form/ul/li[8]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String prTypeSelect1Xpath = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String prTypeSelect2Xpath = "')]";
	
	static String prTypeButtonXpathFR="/html/body/div/section/section/section/div/form/ul/li[9]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[1]/button";
	static String prTypeSearchXpathFR="/html/body/div/section/section/section/div/form/ul/li[9]/span/div/bw-freetext-field-switcher/div/div/bw-freetext-lookup-field/pal-field-container/div/div/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String prTypeSelect1XpathFR = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String prTypeSelect2XpathFR = "')]";
	
	static String editRequisitionXpath = "(//button[@class='btn btn-pal-actions-button ng-scope' and @title ='Edit requisition'])[1]";
	
	static String requesterXpath="//button[@class='pal-single-select-toggle-btn']";
	static String requesterSearchXpath="/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-purchase-header-data-panel/div/div/div[2]/div/div[1]/div/pal-detail-fields/div/pal-detail-field[6]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String requesterSelect1Xpath="//span[@class='ng-binding' and contains(text(),'";
	static String requesterSelect2Xpath="')]";
	
	static String headerDataSaveXpath = "(//button[@type='button' and @class='btn ng-binding'])[2]";

	static String addAdressEditButtonXpath = "//button[@class='btn btn-pal-actions-button ng-scope btn-action-icon btn-secondary' and @type='button' and @title='Edit']";
	static String addressXpath = "(//span[@class='btn btn-default form-control ui-select-toggle'])[1]";
	static String addressSearchXpath = "/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-purchase-common-header-address-panel/div/div[2]/div/div[1]/div[2]/div/bw-common-purchase-address-editor/div/section[1]/div[1]/div/input[1]";
	static String addressSelect1Xpath = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String addressSelect2Xpath = "')]";
	static String addressSaveButtonXpath = "//button[@type='button' and @class='btn btn-pal-actions-button ng-scope']";
	
	static String editCodingXpath = "(//button[@class='btn dropdown-toggle ng-scope dropdown-toggle-secondary use-dropdown-style'])[1]";
	static String editCodingButtonXpath =  "//button[@class='dropdown-btn btn-pal-actions-button']//child::span[contains(text(),'Edit coding')]";   //"(//button[@class='dropdown-btn btn-pal-actions-button'])[2]";
	
	static String agency = "A";
	
	static String natureXpath = "(//button[@class='pal-single-select-toggle-btn' ])[2]";
	static String natureDivXpath = "(//div[@class='pal-single-select-dropdown-header'])[2]";
	static String natureSearchXpath = "/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-personal-requisition-details-lines/div/div/div[2]/div/div/pal-catalog/div[1]/div/pal-catalog-list/div/div[2]/pal-catalog-items/pal-catalog-item/div/div[2]/div[2]/div[3]/div/bw-purchase-requisition-coding-row-editor/div/div[1]/pal-detail-fields/div/pal-detail-field[4]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String natureSearch1Xpath ="(//span[@class='psl-icon-search ng-scope'])[2]";
	static String natureSelect1Xpath = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String natureSelect2Xpath = "')]";
	static String natureNameSelect1Xpath = "//span[@class='ng-binding' and @data-t-id='AccountingAssignmentCategoryName'  and contains(text(),'";
	static String natureNameSelect2Xpath = "')]";
	
	static String agencyXpath = "(//button[@class='pal-single-select-toggle-btn' ])[3]";
	static String agencyDivXpath = "(//div[@class='pal-single-select-dropdown-header'])[3]";
	static String agencySearchXpath = "/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-personal-requisition-details-lines/div/div/div[2]/div/div/pal-catalog/div[1]/div/pal-catalog-list/div/div[2]/pal-catalog-items/pal-catalog-item/div/div[2]/div[2]/div[3]/div/bw-purchase-requisition-coding-row-editor/div/div[1]/pal-detail-fields/div/pal-detail-field[6]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String agencySearch1Xpath ="(//span[@class='psl-icon-search ng-scope'])[3]";
	static String agencySelect1Xpath = "//span[@class='ui-select-highlight' and starts-with(text(),'";
	static String agencySelect2Xpath = "')]//parent::span[@class='ng-binding' and not(text()[normalize-space(.)])]";
	
	
	static String sspXpath = "(//button[@class='pal-single-select-toggle-btn' ])[4]";
	static String sspDivXpath = "(//div[@class='pal-single-select-dropdown-header'])[4]";
	static String sspSearchXpath = "/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-personal-requisition-details-lines/div/div/div[2]/div/div/pal-catalog/div[1]/div/pal-catalog-list/div/div[2]/pal-catalog-items/pal-catalog-item/div/div[2]/div[2]/div[3]/div/bw-purchase-requisition-coding-row-editor/div/div[1]/pal-detail-fields/div/pal-detail-field[8]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String sspSearch1Xpath ="(//span[@class='psl-icon-search ng-scope'])[4]";
	static String sspSelect1Xpath = "//span[@class='ui-select-highlight' and starts-with(text(),'";
	static String sspSelect2Xpath = "')]//parent::span[@class='ng-binding' and not(text()[normalize-space(.)])]";
	
	static String realEstateReferenceXpath="(//button[@class='pal-single-select-toggle-btn' ])[7]";
	static String realEstateReferenceDivXpath="(//div[@class='pal-single-select-dropdown-header'])[7]";
	static String realEstateReferenceSearchXpath="/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-personal-requisition-details-lines/div/div/div[2]/div/div/pal-catalog/div[1]/div/pal-catalog-list/div/div[2]/pal-catalog-items/pal-catalog-item/div/div[2]/div[2]/div[3]/div/bw-purchase-requisition-coding-row-editor/div/div[1]/pal-detail-fields/div/pal-detail-field[15]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String realEstateReferenceSearch1Xpath ="(//span[@class='psl-icon-search ng-scope'])[7]";
	static String realEstateReferenceSelect1Xpath = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String realEstateReferenceSelect2Xpath = "')]";
	
	static String approverXpath ="(//button[@class='pal-single-select-toggle-btn' ])[8]";
	static String approverDivXpath="(//div[@class='pal-single-select-dropdown-header'])[8]";
	static String approverSearchXpath="/html/body/div/bw-purchase-requisition-details/section/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-personal-requisition-details-lines/div/div/div[2]/div/div/pal-catalog/div[1]/div/pal-catalog-list/div/div[2]/pal-catalog-items/pal-catalog-item/div/div[2]/div[2]/div[3]/div/bw-purchase-requisition-coding-row-editor/div/div[1]/pal-detail-fields/div/pal-detail-field[17]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String approverSearch1Xpath ="(//span[@class='psl-icon-search ng-scope'])[8]";
	static String approverSelect1Xpath = "//span[@class='ui-select-highlight' and contains(text(),'";
	static String approverSelect2Xpath = "')]";
	
	static String editCodeSaveButtonXpath = "//button[@class='btn btn-pal-actions-button ng-scope']";
	
 static String viewDetailsXpath = "(//button[@class='btn dropdown-toggle ng-scope dropdown-toggle-secondary use-dropdown-style'])[1]";
	static String viewDetailsButtonXpath = "(//button[@class='dropdown-btn btn-pal-actions-button'])[1]";
	
	static String lineDataXpath = "//button[@class='btn btn-secondary btn-action-icon ng-scope']";
	static String sellingPriceXpath = "/html/body/div/bw-purchase-requisition-line-details/div/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-purchase-line-data-panel/div/div/div[2]/div/div[1]/div/pal-detail-fields/div/pal-detail-field[16]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/input";
	static String paymentTermsButtonXpath="(//button[@class='pal-single-select-toggle-btn'])[3]";
	static String paymentTermsSearchXpath="/html/body/div/bw-purchase-requisition-line-details/div/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-purchase-line-data-panel/div/div/div[2]/div/div[1]/div/pal-detail-fields/div/pal-detail-field[20]/div/div/bw-purchase-requisition-panel-field/div/bw-purchase-panel-field-switch/div/div/div/bw-lookup-list/div/pal-single-select/div/div[2]/pal-single-select-dropdown/div/div[1]/div/input";
	static String paymentTermsSelect1Xpath="//span[@class='ui-select-highlight' and contains(text(),'";
	static String paymentTermsSelect2Xpath="')]";
	
	static String lineDataSaveXpath = "(//button[@class='btn ng-binding'])[1]";
	static String lineDataSaveButtonVisibleXpath = "/html/body/div/bw-purchase-requisition-line-details/div/pal-two-col-row/div/div[1]/div/pal-two-col-row-left-content/div/bw-purchase-line-data-panel/div/div/div[1]/div/span/span/section[2]/button";
	static String backPageXpath = "(//i[@class='psl-icon-angle-left'])[1]";
	
	static String getApproveXpath = "(//button[@class='btn btn-pal-actions-button ng-scope use-dropdown-style'])[2]";
	static String getPrApproveNumberXpath = "//*//span[@class='pal-main-title ng-binding']";
	
	
	static String taskListXpath = "/html/body/div/div/div[3]/div[1]/pal-narrow-layout/div/ng-transclude/div/div/div/div/div[2]/div[2]/div/a/span";
	                               
	static String totalRowsTaskListXpath = "//span[@class='badge ng-binding']";
	
	static String docType1Xpath= "//*/tbody[";
	static String docType2Xpath= "]/tr[1]/td[4]/pal-list-item-secondary/bw-task-item-secondary/bw-task-item-type-and-organization-field/div/span[1]";
	static String orderDescription1Xpath = "//*/tbody[";
	static String orderDescription2Xpath= "]/tr[3]/td[2]/pal-list-item-description/bw-purchase-requisition-description-component/div/span[2]";
	
	static String approveTaskListXpath = "/html/body/div/bw-purchase-requisition-details/section/div[1]/pal-title-bar/div/div/div[2]/div/div[2]/pal-title-bar-actions/bw-purchase-requisition-details-actions/div/pal-actions/div/div/div/div[1]/div/button";
	static String allTasksXpath= "/html/body/div/div/bw-task-list-all/div/div/div[1]/div/span[1]";
	
	// open invoice button for approve  
	 static String invoiceMatchButton1Xpath="/html/body/div/div/bw-task-list-all/div/div/pal-list/div/table/tbody[";
	 static String invoiceMatchButton2Xpath="]/tr[1]/td[1]/button/i//parent::button";
		
	 static String invoiceMatchText1Xpath="/html/body/div/div/bw-task-list-all/div/div/pal-list/div/table/tbody[";
	 static String invoiceMatchText2Xpath="]/tr[6]/td[2]/div/div/pal-list-item-expand-section/bw-coding-details-panel/div/div/div[2]/div[1]";
	
	 static String invoiceOPenPanel1Xpath="/html/body/div/div/bw-task-list-all/div/div/pal-list/div/table/tbody[";
	 static String invoiceOPenPanel2Xpath="]/tr[1]/td[3]/pal-list-item-primary/bw-task-item-primary/bw-task-item-supplier-field/div";
	 
	 static String invoiceApproveButtonXpath="/html/body/div/section/div[1]/pal-title-bar/div/div/div[2]/div/div[2]/pal-title-bar-actions/div/div/bw-details-action-panel-commands/div/div/bw-task-actions/div/div/pal-actions/div/div/div/div[1]/div/button";
	
	 
	 // unsaved data button
	 static String unsavedDataButtonXpath="//div[@class='pt-modal-content']";
	 static String discardButtonXpath="//button[@class='pt-btn pt-btn-secondary' and contains(text(),'Discard changes')]";
}








