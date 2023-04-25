package samplePackage;

import org.json.JSONArray;
import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class habitatAPITest {

public static String apiURL="https://odegdcpnma.execute-api.eu-west-2.amazonaws.com/development/prices?";
public static int dnoValue= 10;
public static String voltageValue= "HV"; 
public static String startValue= "01-06-2021";
public static String endValue= "03-06-2021";
public static Response response = null;
public static int responseCode = 200;

// Below scenario is to validate whether the given API is live and checks if response code is 200 OK or something else
public static void scenario1_CheckAPIStatusCode() {
	RestAssured.baseURI = apiURL;
	RequestSpecification httpRequest = RestAssured.given();
	RequestSpecification req = httpRequest.param("dno", dnoValue).param("voltage", voltageValue).param("start", startValue).param("end", endValue); // add query parameter if needed
	response = req.get();
	int responseStatuscode = response.getStatusCode();
	System.out.println("Response is : " + response.asString());
	
	if(responseStatuscode == responseCode) {
		System.out.println("Scenario 1 Response Code is : " + responseStatuscode);
		
	}else {
		System.out.println("Scenario 1 Error Code is : " + responseStatuscode);
	}
	
}

// Below scenario is to validate the API response with valid parameters and store the entry values in a string for comparison with any expected results.
public static void scenario2_validateAPIwithParam() {
	
	RestAssured.baseURI = apiURL;
	RequestSpecification httpRequest = RestAssured.given();
	RequestSpecification req = httpRequest.param("dno", dnoValue).param("voltage", voltageValue).param("start", startValue).param("end", endValue); // add query parameter if needed
	response = req.get();
	int responseStatuscode = response.getStatusCode();

	System.out.println("Scenario 2 Response is : " + response.asString());
	System.out.println("Scenario 2 Response Code is : " + responseStatuscode);
	
	
	JSONObject jsonObject = new JSONObject(response.asString());
	Object Status = jsonObject.getString("status");
	JSONArray entries = jsonObject.getJSONObject("data").getJSONArray("data");
	int entrylenth = entries.length();
	String timestamp = entries.getJSONObject(1).getString("Timestamp");
	double overallValue = entries.getJSONObject(1).getDouble("Overall");

	// we can use for loop using entries length to extract values from each node for our validation

		
System.out.println("Scenario 2 Response Status is : " + Status.toString());
System.out.println("Scenario 2 Response length is : " + entrylenth);
System.out.println("Scenario 2 Response value is : " + overallValue);

}

// Below scenario is to validate the API without parameters and prints the error message & error type
public static void scenario3_validateAPIwithoutParam() {
	String dnoValue1= "";
	
	RestAssured.baseURI = apiURL;
	RequestSpecification httpRequest = RestAssured.given();
	RequestSpecification req = httpRequest.param("dno", dnoValue1).param("voltage", voltageValue).param("start", startValue).param("end", endValue); // add query parameter if needed
	response = req.get();
	int responseStatuscode = response.getStatusCode();

	System.out.println("Scenario 3 Response is : " + response.asString());
	System.out.println("Scenario 3 Response Code is : " + responseStatuscode);
	
	
	JSONObject jsonObject = new JSONObject(response.asString());
	Object ErrorMessage = jsonObject.getString("errorMessage");
	Object errorType = jsonObject.getString("errorType");
	
		
System.out.println("Scenario 3 Error Message is : " + ErrorMessage.toString());
System.out.println("Scenario 3 Error Type is : " + errorType.toString());

}


public static void main (String[] args){
	scenario1_CheckAPIStatusCode();
	scenario2_validateAPIwithParam();
	scenario3_validateAPIwithoutParam();

	
}
}
