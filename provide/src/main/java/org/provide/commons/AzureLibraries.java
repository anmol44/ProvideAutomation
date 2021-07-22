
package org.provide.commons;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.io.output.ByteArrayOutputStream;

import org.apache.log4j.Level;
import org.apache.log4j.lf5.LogLevel;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * @author Shubham.gupta3@Soprasteria.com
 *
 * Automation Developer
 */
public class AzureLibraries {

	
	static String Azure_BaseUri =null;
	static String Attachment_ApiVersion=null;
	static String Result_ApiVersion=null;
	static String Points_Apiversion=null;
	static String CreateApi_Version=null;
	static String updateResult_version=null;

	public void PropertyReader() {
		
		
		Properties pro = new Properties();
		try {
			InputStream input = new FileInputStream(System.getProperty("user.dir") +"/config.properties");
			pro.load(input);
			 Azure_BaseUri = pro.getProperty("BaseUri");
			 Attachment_ApiVersion=pro.getProperty("Attachment_ApiVersion");
			 Result_ApiVersion=pro.getProperty("Result_ApiVersion");
			 Points_Apiversion=pro.getProperty("Points_ApiVersion");
			 CreateApi_Version=pro.getProperty("CreateID_Version");
			 updateResult_version=pro.getProperty("updateResult_Version");	
		}
		catch(Exception e)
		{e.printStackTrace();
		}	
		
	}
	

	
	String Azure_AccessToken = "76gk4keu7kofd36ylsnx4no5dkrhufmsifozq3apaiexgdxe4b4q";
	String Azure_Path="/soprasteria-is/ISSSG";	
	public String RunById ="1000";	
	public String OwnerById = "anmol44";	
	
	String AzureID = Azure_Path.split("/")[1];
	
	
	
	public static  Map<String,String> planidlist = new HashMap();// TestCaseId & TestPlanID
	public static ArrayList<String> AllStatusResult=new ArrayList<String>();
	public static String PlanID;
	public static String RunId ;
	File fresponse;
//	public static Log logger;
	
	/**
	* AzureAPICall() method perform / execute all Azure API call with their consolidated data.
	* 
	* @param dependency - this Map collection had all details of execution like testCase as key and corresponding result as Value.
	* @param testCaseNamelist - list of all the name of test case id.
	* @param SuitID - Module name from the excel sheet.
	* @param startTimeModule - start time of the module.
	* @param endTimeModule - end time of the module.
	* @param durationTime- All the time duration of the execution.
	* @param log - object's log
	* @param testCaseIDs All testCaseIds from the excel
	* @throws Exception   java.lang.Exception: for if entered wrong testCaseid from different TestSuits.
	*/
	
	public void AzureAPICall(HashMap<String, String> dependency,List<String> testCaseNamelist, ArrayList<String> testCaseIDs, String SuitID,
			String startTimeModule, String endTimeModule, LinkedHashMap<String, String> durationTime) throws Exception {
		
		ArrayList<String> AllPointIDs = new ArrayList<String>();
		ArrayList<String> AllPriorityList = new ArrayList<String>();
		ArrayList<String> AllResultIDs = new ArrayList<String>();		
		ArrayList<String> keysForStartTimeTestCase = new ArrayList<String>();
		ArrayList<String> valuesForEndTImeTestCase = new ArrayList<String>();
		   for ( Map.Entry<String, String> entry : durationTime.entrySet()) {
			   keysForStartTimeTestCase.add(entry.getKey());
			   valuesForEndTImeTestCase.add(entry.getValue());
			}
   //     logger = log;
	//	log.LogMessage(Level.INFO," Calling Azure APIs to Update Status and Result for current Module - "+SuitID);
		AzureLibraries azureLib = new AzureLibraries();
		azureLib.PropertyReader();
		
	//	log.LogMessage(Level.INFO," Creating Plan Id, All Points Id & fetch Priority details based on TestCases ");	
	
		for (int i=0;i<testCaseIDs.size();i++) {
			String testCaseid = (String) testCaseIDs.get(i);			
			azureLib.getPlanID(testCaseid,SuitID);
			
			if(planidlist.size()!=(i+1)) {
	//			log.LogMessage(Level.ERROR, "Wrong excel Sheet data. "
	//					+ " "
	//					+"Entered different testcase id "+testCaseid+" Which may belongs to different test Suite.");
				throw new Exception("Wrong excel Sheet data."+"Entered different testcase id "+testCaseid+" Which may belongs to different test Suite.");
				
			}
			AllPointIDs.addAll(azureLib.getPointIDs(testCaseid,PlanID, SuitID));			
			AllPriorityList.add(getPriorityList(testCaseid, PlanID, SuitID));
			
		}
	//	log.LogMessage(Level.INFO," Plan id of this Module - "+PlanID);
	//	log.LogMessage(Level.INFO," Checking for the Multiple Configuration of Module -"+SuitID);
		if(testCaseIDs.size()!=AllPointIDs.size()) {	
	//		  log.LogMessage(Level.ERROR, " Please check the number of Configuration of the TestSuite ID - "+SuitID+" in Azure.it should not selected more than 1 ");
			  throw new Exception(" Please check the number of Configuration of the TestSuite ID - "+SuitID+" in Azure.it should not selected more than 1 ");
	     }		
		
	//	log.LogMessage(Level.INFO," Creating the Run ID for Module "+SuitID);
		azureLib.CreateRunID(PlanID, AllPointIDs);
	//	log.LogMessage(Level.INFO," Run id of this Module - "+RunId);
		AllResultIDs.addAll(azureLib.GetResultIDs());		
		featchAllStatus(dependency);	
	//	log.LogMessage(Level.INFO," Updating the Result Status for all the TestCases");
		for (int i=0;i<AllResultIDs.size();i++) {
			UpdateResultStatus(RunId, AllResultIDs.get(i), AllStatusResult.get(i),AllPriorityList.get(i),keysForStartTimeTestCase.get(i),valuesForEndTImeTestCase.get(i));
		}
	//	log.LogMessage(Level.INFO," Updating Run Time Information with Start and end run exceution of current module - "+SuitID);
		updateRunTimeInformation(RunId,startTimeModule,endTimeModule);	
	//	log.LogMessage(Level.INFO," Updating the all Attachments into Azure result of current module -"+SuitID);
		
	
		
	//	ResultUpdateAsZip(AllResultIDs,testCaseIDs, testCaseNamelist);
		
		
	//	log.LogMessage(Level.INFO,"");
	//	log.LogMessage(Level.INFO," ********************************************************************************************** ");
	//	log.LogMessage(Level.INFO," ********  Execution of Module - "+SuitID +" is Successfully Completed ****************** ");
	//	log.LogMessage(Level.INFO," ********************************************************************************************** ");

	}

	/**
	* GetPlanID() -  Method needs 2 parameters and
	* based in the Parameters, Method will Generate 'PlanID ' for the current Execution.
	* @param  testCaseid  testCaseid is nothing but TestCase No. from ApiTestCases.excel sheet.
	* @param  Suitid      SuitId nothing but module name of the APITestCases.excel sheet of current execution.
	* 
	* 
	
	*/
	
	public void getPlanID(String testCaseid, String Suitid ) {
		RestAssured.reset();
		
		RestAssured.baseURI = Azure_BaseUri;		
		String response1 = RestAssured.given().
				auth().preemptive().
                basic(Azure_AccessToken,Azure_AccessToken).
                contentType("application/json").
                queryParam("api-version", Result_ApiVersion).
                queryParam("testCaseId", testCaseid).                
                when().get("/"+AzureID+"/_apis/test/suites").then().extract().response().asString();
		
		System.out.println(AzureID);
		
		System.out.println("anmol");
		System.out.println(response1);
		
		JsonPath js = new JsonPath(response1);	
		//try catch
		try {
		int totalcount = js.get("count");
		
		for(int i=0;i<totalcount;i++) {	
			
			if(Integer.toString((Integer) js.get("value["+i+"].id")).equalsIgnoreCase(Suitid))
			{ 
				 planidlist.put(testCaseid, (String) js.get("value["+i+"].plan.id")); 
				 PlanID=js.get("value["+i+"].plan.id");
				
			}		  
		  }
		}catch (NullPointerException e) {
//			logger.LogMessage(Level.ERROR, " Maybe this Testcase no "+testCaseid+ " is not present as a Testcase into the Azure TestPlan. ");
		}       
	}
	
	/**
	* GetPointID() -  Method needs 3 parameters (testCaseid , Plan ID , SuitID) and
	* based in the Parameters, Method will Generate 'PointID ' for All individual corresponding to the TestCases.
	* @param  testCaseidPara  testCaseid is nothing but TestCase No. from ApiTestCases.excel sheet.
	* @param  SuitIDPara      Suit ID nothing but module name of the APITestCases.excel sheet of current execution.
	* @param   PlanIDPara      Same Plan id which is generated in GetPlanID() Method.
	* @return             Array of String which have all point Id of TestCases.
	* 
	*/
	public ArrayList<String> getPointIDs(String testCaseidPara,String PlanIDPara, String SuitIDPara)  {
		
		ArrayList<String> AllPointIDslocal = new ArrayList<String>();
		RestAssured.baseURI = Azure_BaseUri;		
		String response1 = RestAssured.given().
				auth().preemptive().
                basic(Azure_AccessToken,Azure_AccessToken).
                contentType("application/json").
                queryParam("api-version", Points_Apiversion).
                queryParam("testCaseid",testCaseidPara).
                when().get(Azure_Path+"/_apis/testplan/Plans/"+PlanIDPara+"/Suites/"+SuitIDPara+"/TestPoint").then().extract().response().asString();
		
		JsonPath js = new JsonPath(response1);		
		int totalcount = js.get("count");		
		for(int i=0;i<totalcount;i++) {			
			
			AllPointIDslocal.add(Integer.toString((Integer) js.get("value["+i+"].id")));
								 
			}
		return AllPointIDslocal;
	}	
	

	/**
	* getPriorityList() -  Method needs 3 (testCaseid , PlanId, SuitID) parameters and
	* based in the Parameters, Method will get ' Priority ' of the testCase from Azure's TestCase.
	* @param  PlanIDPara      The same Plan id which we generated in getPlanId method.
	* @param  testCaseidPara  testCaseid is nothing but TestCase No. from ApiTestCases.excel sheet.
	* @param  SuitIDPara      SuitId nothing but module name of the APITestCases.excel sheet of current execution.
	* @return String      Return the Priority of the testCase.
	* 
	*/
	public String getPriorityList(String testCaseidPara,String PlanIDPara, String SuitIDPara)  {
		
		RestAssured.baseURI = Azure_BaseUri;		
		String response1 = RestAssured.given().
				auth().preemptive().
                basic(Azure_AccessToken,Azure_AccessToken).
                contentType("application/json").
                queryParam("api-version", Points_Apiversion).
                queryParam("witFields","priority").
                when().get(Azure_Path+"/_apis/testplan/Plans/"+PlanIDPara+"/Suites/"+SuitIDPara+"/TestCase/"+testCaseidPara).then().extract().response().asString();
		
		JsonPath js = new JsonPath(response1);
	    int totalcount = js.get("count");
	    String s = null;		    
	    for(int i=0;i<totalcount;i++) {
	    	if((js.get("value["+i+"].workItem.id").toString().equalsIgnoreCase(testCaseidPara))) {	    		
	    		s =js.get("value["+i+"].workItem.workItemFields.priority").toString();
	 		    s=s.replace("[", "");
	 		    s=s.replace("]", "");
	    	}
	    }	   
		return s;
	}	

	/**
	* CreateRunID() -  Method needs 2 (PlanID , AllPOintID ) parameters and
	* based in the Parameters, Method will generate ' RunID ' for the current execution.
	* @param  PlanidParas      The same Plan id which we generated in getPlanId method.
	* @param  AllPointIDs    List of all points which is generate from GetPointsIDs method.
	*
	* 
	*/
    public void CreateRunID(String PlanidParas, ArrayList<String> AllPointIDs) {
    	
    	  String GenerateRunIdBody = "{ \r\n" + 
    			"    \"name\": \"ISSSG\", \r\n" + 
    			"    \"plan\": { \r\n" + 
    			"        \"id\": \""+PlanidParas+"\" \r\n" + 
    			"    }, \r\n" + 
    			"    \"automated\": \"true\", \r\n" + 
    			"    \"pointIds\": "+AllPointIDs+"" + 
    			"}";
		
		RestAssured.baseURI = Azure_BaseUri;
		String response1 = RestAssured.given().
	                auth().preemptive().
	                basic(Azure_AccessToken,Azure_AccessToken).
	                contentType("application/json").
	                queryParam("api-version", CreateApi_Version).
	                body(GenerateRunIdBody).
	                when().post(Azure_Path+"/_apis/test/runs").then().extract().response().asString();
		
	       JsonPath js = new JsonPath(response1);
	       RunId = Integer.toString((Integer) js.get("id"));
	       
    }
    
    /**
	* GetResultIDs() -  no Parameter is required to call this Method and this method generate Result id for the Previous 
	*                   generated RunID.
	* @return String     Return the list of All Result IDs.
	* 
	*/
    public ArrayList<String> GetResultIDs() {
    	 
    	 ArrayList<String> AllResultIDslocal = new ArrayList<String>();
 		RestAssured.baseURI = "https://dev.azure.com";		
 		String response1 = RestAssured.given().
 				auth().preemptive().
                 basic(Azure_AccessToken,Azure_AccessToken).
                 contentType("application/json").
                 queryParam("api-version", "6.1-preview.6").
                 when().get(Azure_Path+"/_apis/test/Runs/"+RunId+"/results").then().extract().response().asString();
 		
 		JsonPath js = new JsonPath(response1);
 		
 		int totalcount = js.get("count");		
 		for(int i=0;i<totalcount;i++) {			
 			AllResultIDslocal.add(Integer.toString((Integer) js.get("value["+i+"].id")));								 
 		}		  
     	return AllResultIDslocal;
     }
   
    /**
	* UpdateResultStatus() -  Method needs 6 parameters , which will update the status of the testCases one by one.
	*                         This method is called by the AzureAPICall();
	* @param  RunIdPara       The same Run id which we generated in CreateRunID() method.
	* @param  ResultIdPara    List of all ResultIds which is generate from GetResultIDs() method.
	* @param  StatusPara      Results of the testCases as a status.
	* @param  priority     Get all the priority list from the Azure TestCases through getPriorityList() method.
	* @param StartTestCaseTime       Start time of the TestCases execution.
	* @param EndTestCaseTime         End time of the TestCases execution.
	* 
	* 
	* 
	*/    
    
    public void UpdateResultStatus(String RunIdPara , String ResultIdPara , String StatusPara,String priority,String StartTestCaseTime, String EndTestCaseTime) {
     	
     	 String updateResultBody="[\r\n" + 
     			"    {\r\n" + 
     			"        \"id\": "+ResultIdPara+", \r\n" + 
     			"        \"state\": \"Completed\",\r\n" + 
     			"        \"comment\": \"ISSSG\", \r\n" + 
     			"        \"outcome\":\""+StatusPara+"\", \r\n" + 
     			"        \"runBy\": \r\n" + 
     			"        {\r\n" + 
     			"            \"id\": \""+RunById+"\" \r\n" + 
     			"        } \r\n" + 
     			"        ,\r\n" + 
     			"        \"owner\":\r\n" + 
     			"        { \r\n" + 
     			"            \"id\": \""+OwnerById+"\" \r\n" + 
     			"        },\r\n" + 
     			"        \"priority\": "+priority+",\r\n" + 
     			"	         \"startedDate\": \""+StartTestCaseTime+"\",\r\n" + 
     			"             \"completedDate\": \""+EndTestCaseTime+"\"\r\n" + 
     			"    }\r\n" + 
     			"]";
     	
     	RestAssured.baseURI = Azure_BaseUri;	
 		  RestAssured.given().
 				auth().preemptive().
                 basic(Azure_AccessToken,Azure_AccessToken).
                 contentType("application/json").
                 queryParam("api-version", updateResult_version).
                 body(updateResultBody).
                 when().patch(Azure_Path+"/_apis/test/Runs/"+RunIdPara+"/results").then().extract().response().asString();
 		
 	 }     
    /**
    * 
	* featchAllStatus()   This method will help to fetch list of results. 
	* @param dependency   All execution details.
	* 
	*/  
    public void featchAllStatus(HashMap<String, String> dependency) {
 		
 		for (String key: dependency.keySet()) {
 			    AllStatusResult.add(dependency.get(key));
 		}	
 	}
     
    
    /**
	* updateRunTimeInformation()   Update the start and end time of the whole execution of the RunSuite into the RunId.
	* @param  RunID       The same Run id which we generated in CreateRunID() method.
	* @param startTimeModule       Start time of the TestCases execution.
	* @param endTimeModule         End time of the TestCases execution.
	* 
	* 
	*/
    public void updateRunTimeInformation(String RunID,String startTimeModule, String endTimeModule) {
    	 
    	 String updateResultBody = "{\r\n" + 
    	 		"  \"state\": \"Completed\",\r\n" + 
    	 		"  \"startedDate\": \""+startTimeModule+"\",\r\n" + 
    	 		"  \"completedDate\": \""+endTimeModule+"\"\r\n" + 
    	 		"}";
      	
      	  RestAssured.baseURI = Azure_BaseUri;	
  		  RestAssured.given().
  				auth().preemptive().
                  basic(Azure_AccessToken,Azure_AccessToken).
                  contentType("application/json").
                  queryParam("api-version", CreateApi_Version).
                  body(updateResultBody).
                  when().patch(Azure_Path+"/_apis/test/runs/"+RunID).then().extract().response().asString();
  		
     }
     
    /**
  	* ResultUpdateAsZip() -        convert particular testCase result and files into ZIP file for the azure attachment.
  	* @param AllResultIDsPara      All Result ID , which is generated by GetResultIDs() Method.
  	* @param  testCaseIDs          All the testCase id.
  	* @param testCaseNamelist      All TestCaseName.
  	* @param log                   log's Object
  	* @exception Exception         If folder Path is not correct.
  	* @throws Exception            file not found exception.
  	*/
     public void ResultUpdateAsZip(List<String> AllResultIDsPara,ArrayList<String> testCaseIDs,List<String> testCaseNamelist ) throws Exception {
    	 for(int i=0;i<AllResultIDsPara.size();i++) {
    		 
    			File f = createFolder(testCaseIDs.get(i));    			
    			CopyHTMLFiles(testCaseIDs.get(i),f);    			
    			RenameFolder(testCaseIDs.get(i),f,testCaseNamelist.get(i));    			
    			searchfullfileName(testCaseIDs.get(i),testCaseNamelist.get(i),f);
    		
			   attachments(testCaseIDs.get(i),RunId,AllResultIDsPara.get(i),f); 
			   String  DeletePath = f.toString();
			   //FileToDelete(DeletePath);	
			   		 
    	 }
     }
     //demo
     public String getStringFile(File f) {
         InputStream inputStream = null; 
         String encodedFile= "", lastVal;
         try {
             inputStream = new FileInputStream(f.getAbsolutePath());

         byte[] buffer = new byte[10240];//specify the size to allow
         int bytesRead;
         ByteArrayOutputStream output = new ByteArrayOutputStream();
         Base64OutputStream output64 = new Base64OutputStream(output,true);

             while ((bytesRead = inputStream.read(buffer)) != -1) {
                 output64.write(buffer, 0, bytesRead);
             }
         output64.close();
         encodedFile =  output.toString();
         } 
          catch (FileNotFoundException e1 ) {
                 e1.printStackTrace();
             }
             catch (IOException e) {
                 e.printStackTrace();
             }
         lastVal = encodedFile;
         return lastVal;
     }
     
     public File getLastModified(String directoryFilePath)
    {  
 	 File dir = new File(directoryFilePath);
      File[] files = dir.listFiles();
      File lastModified = Arrays.stream(files).filter(File::isDirectory).max(Comparator.comparing(File::lastModified)).orElse(null);
    
 	 return lastModified;
    }
     /**
      * CreateFolder will create a folder with the name of TestCaseid, which stores all the results.
      * @param TestId   -        all TestCase id.
      * @param log      -        Object of log.
      * @return File  creating file path of the Create folder.
      */
     public File createFolder(String TestId) { 

     	String currentDir = System.getProperty("user.dir");
         File reporttFolder =  getLastModified(currentDir+"\\Reports");
         File f = new File(reporttFolder+"\\Report-TC "+TestId);
         fresponse = new File(f+"\\Responses");
          try{
             if(f.mkdir()&&fresponse.mkdir()) {  
                 
    //             log.LogMessage(Level.DEBUG,"Directory Created"+TestId);
             } else { 
     //            log.LogMessage(Level.DEBUG,"Directory is not created"+TestId);
             } 
         } catch(Exception e){ 
             e.printStackTrace();
         }
         return f;
     }
     
     public void CopyHTMLFiles(String TestId , File f) {
         Path temp = null;        
         String currentDir = System.getProperty("user.dir");
         File reporttFolder =  getLastModified(currentDir+"\\Reports");      
         try { 
             temp = Files.move 
             (Paths.get(reporttFolder+"\\"+TestId+".html"),
             Paths.get(f+"\\"+TestId+".html"));
         } catch (IOException e) { 
         	e.printStackTrace(); 
         }  
         if(temp != null)
         { 
  //       	 log.LogMessage(Level.DEBUG,"File renamed and moved successfully For id"+TestId);
         }  
         else 
         { 
    //         log.LogMessage(Level.DEBUG,"Failed to move the file"+TestId);
         } 
     }  

     public void searchfullfileName(String TestCaseno, String currentTestName,File f) throws Exception {
     	
     	String currentDir = System.getProperty("user.dir");
        File lastedtFolder =  getLastModified(currentDir+"\\Reports"); 
         
     	File dir = new File(lastedtFolder+"\\Responses");    	 
     	String replaceString=currentTestName.replace(' ','-');
     	     	
     	FileFilter fileFilter = new WildcardFileFilter("*"+replaceString+"*.json");
     	File[] files = dir.listFiles(fileFilter);
     	for (int i = 0; i < files.length; i++) {
     	   copyResposeFile(files[i],files[i].getName(),TestCaseno,f);
     	   
     	}    	
     	FileFilter fileFilter1 = new WildcardFileFilter("*"+replaceString+"*.txt");
     	File[] files1 = dir.listFiles(fileFilter1);
     	for (int i = 0; i < files1.length; i++) {
     	   copyResposeFile(files1[i],files1[i].getName(),TestCaseno,f);
     	  
     	}
     }
    
     public void copyResposeFile(File files, String string,String TestCaseNo,File f) throws Exception {
     	
     	String src = files.getPath();
     	String dst =fresponse+"\\"+string;
     			//+".json";
     	moveFile(src, dst);    	
     	
     	//.............................................Zipping................................... 
     
         String zipName = f+".zip";           
        // Path file1 = Paths.get(folderToZip);
         Path file1=f.toPath();
         Path file2 = Paths.get(zipName);   
         zipFolder(file1,file2);
               
     }  
   
     public void moveFile(String src, String dest ) {
         Path result = null;
         try {
            result = Files.move(Paths.get(src), Paths.get(dest));
         } catch (IOException e) {
   //         log.LogMessage(Level.INFO,"Exception while moving file: " + e.getMessage());
         }
         if(result != null) {
            
   //         log.LogMessage(Level.DEBUG,"Failed to move the file");
         }else{
    //        log.LogMessage(Level.DEBUG,"File movement failed.");
         }
      }
    
     public void zipFolder(Path sourceFolderPath, Path zipPath) throws Exception {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath.toFile()));
        Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));
                Files.copy(file, zos);
                zos.closeEntry();
                return FileVisitResult.CONTINUE;
            }
        });
        zos.close();
    }  
    
     public void FileToDelete(String file1) {           
         
     	try {
			FileUtils.deleteDirectory(new File(file1));
     		
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
         }
         
     public void recursiveDelete(File file) {
             //to end the recursive loop
             if (!file.exists())
                 return;
             
             //if directory, go inside and call recursively
             if (file.isDirectory()) {
                 for (File f : file.listFiles()) {
                     //call recursively
                     recursiveDelete(f);
                 }
             }
             //call delete to delete files and empty directory
             file.delete();
            
         }
     public void RenameFolder(String TestCaseNo,File f,String currentTestName) {
    	 System.out.println("  TestCAse Name : "+currentTestName);
    	 
     	String sourceFileString = f+"\\"+TestCaseNo+".html";
     	//Execution Report TC XXXXX â€“  Name.html
     	String destFileString =f+"\\Execution Report TC "+TestCaseNo+ " - "+currentTestName+".html";
        System.out.println(" Dest File String :"+destFileString);
         
     	File sourceFile = new File(sourceFileString);
     	File destFile = new File(destFileString);
     	
 		 if (sourceFile.renameTo(destFile)) {
 		     
 //		    log.LogMessage(Level.DEBUG,"Directory renamed successfully");
 		    
 		 } else {
 //		     log.LogMessage(Level.DEBUG,"Failed to rename directory");
 	     }
 	 }
    
     
     /**
 	* attachments() -  All the result will attached to the testCase status.
 	* @param  TestCaseNo      All the TestCaseno one by one.
 	* @param Runid       Run id which is generated by CreatRunID.
 	* @param ResultID    List of all the Result id.
 	* @param  f   Path of the file , where result ZIP file is present.
 	* @exception InterruptedException  if we give wrong path of the result file.
 	* @throws InterruptedException if we give wrong path of the result file.
 	*/
     public void attachments(String TestCaseNo,String Runid, String ResultID,File f) throws InterruptedException { 		
 		// Encode 
 		String enocdedFile = null;		           
         File filePath= new File(f+".zip");
 		enocdedFile = getStringFile(filePath);
 		 		  
         //********************************** Attachment Body **********************************************************
 		String attachmentBody ="{\r\n" + 
                 "  \"stream\": \""+enocdedFile+"\",\r\n" + 
                 "  \"fileName\": \""+"Report-TC "+TestCaseNo+".zip\",\r\n" + 
                 "  \"comment\": \"Test result uploaded by automation\",\r\n" + 
                 "  \"attachmentType\": \"GeneralAttachment\"\r\n" + 
                 "}";
 				
 		        RestAssured.reset();
 				RestAssured.baseURI = Azure_BaseUri;
 		     				
 		        Response response1 = RestAssured.given().
 			                auth().preemptive().
 			                basic(Azure_AccessToken, Azure_AccessToken).
 			                contentType("application/json").
 			                queryParam("api-version", Attachment_ApiVersion).
 			                body(attachmentBody).
 			                when().post(Azure_Path+"/_apis/test/Runs/"+Runid+"/Results/"+ResultID+"/attachments");
 				
 	}
}


