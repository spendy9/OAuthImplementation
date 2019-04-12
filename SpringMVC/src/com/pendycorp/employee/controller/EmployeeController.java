package com.pendycorp.employee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.pendycorp.employee.model.Employee;



@Controller
public class EmployeeController {

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model){
		model.addAttribute("employee", new Employee());
		return "index";
	}
	
	
	@RequestMapping(value="/newEmployee", method= RequestMethod.GET)
	public ModelAndView enterEmployee(){
		return new ModelAndView("newEmployee", "employee", new Employee());
	}
	
	@RequestMapping(value="/addEmployee", method = RequestMethod.POST)
	public String submit(@ModelAttribute("employee")Employee employee, BindingResult result, ModelMap model){
		model.addAttribute("name", employee.getName());
		model.addAttribute("id", employee.getId());
		model.addAttribute("contactNumber",employee.getContactNumber());
		System.out.println(employee.getName());
		return "employeeDetails";
	}
	
	@RequestMapping(value="/processauthcode", method = RequestMethod.POST)
	public String processAccessToken(HttpServletRequest request,ModelMap modelMap) {
		
		String authCode = request.getParameter("authCode");
		

		// Set path to the Web application client_secret_*.json file you downloaded from the
		// Google API Console: https://console.developers.google.com/apis/credentials
		// You can also find your Web application client ID and client secret from the
		// console and specify them directly when you create the GoogleAuthorizationCodeTokenRequest
		// object.
		String CLIENT_SECRET_FILE  = "/resources/static/client_secret.json";
		String CLIENT_ID = "";
		String CLIENT_SECRET ="";
		//String REDIRECT_URI = "http://localhost:8090/SpringMVC/newEmployee";
		String REDIRECT_URI = "postmessage";
		
		// Exchange auth code for access token
		/*try {
			String filePath = getClass().getClassLoader().getResource(CLIENT_SECRET_FILE).getFile();
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(new JacksonFactory(), new FileInputStream(new File(filePath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	GoogleTokenResponse tokenResponse = null;
	try {
		//tokenResponse = new GoogleAuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(), CLIENT_ID, CLIENT_SECRET, authCode, REDIRECT_URI);
		tokenResponse = new GoogleAuthorizationCodeTokenRequest(
		      new NetHttpTransport(),
		      new JacksonFactory(),
		      CLIENT_ID,
		      CLIENT_SECRET,
		      authCode,
		      REDIRECT_URI)  // Specify the same redirect URI that you use with your web
		                     // app. If you don't have a web version of your app, you can
		                     // specify an empty string.
		      .execute();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		String accessToken = tokenResponse.getAccessToken();

		// Use access token to call API
		/*
		 * GoogleCredential credential = new
		 * GoogleCredential().setAccessToken(accessToken); Drive drive = new
		 * Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
		 * credential) .setApplicationName("Auth Code Exchange Demo") .build(); File
		 * file = drive.files().get("appfolder").execute();
		 */

		// Get profile info from ID token
		GoogleIdToken idToken = null;
		try {
			idToken = tokenResponse.parseIdToken();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GoogleIdToken.Payload payload = idToken.getPayload();
		//String userId = payload.getSubject();  // Use this value as a key to identify a user.

		
		System.out.println("email = " + (String) payload.get("email"));
		System.out.println("email_verified = " +  payload.get("email_verified"));
		
		return "employeeDetails";
	}
}
