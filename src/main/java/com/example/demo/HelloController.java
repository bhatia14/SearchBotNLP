package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import com.example.models.AccountModel;
import com.example.models.AccountRequest;
import com.example.models.ParserNLP;
import com.example.models.Request;
import com.example.models.Response;
import com.example.models.Trans;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {

    //@RequestMapping("/call")
    @RequestMapping(value = "/home", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> home(@RequestHeader Map<String, String> header,
			@RequestBody Request reqPayload){
    	
    	Response res=new Response(); 
	      HttpHeaders responseHeaders = new HttpHeaders();
        	try {

    		URL url = new URL("https://westus.api.cognitive.microsoft.com/linguistics/v1.0/analyze");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "application/json");
    		conn.setRequestProperty("Ocp-Apim-Subscription-Key", "612dfe13094244b08d2c33a62ff8632d");

    		String input = "{\r\n" + 
            		"	\"language\" : \"en\",\r\n" + 
            		"	\"analyzerIds\" : [\"4fa79af1-f22c-408d-98bb-b7d7aeef7f04\", \"22a6b758-420f-4745-8a3c-46835a67c0d2\"],\r\n" + 
            		"	\"text\" : \""+reqPayload.getRequest()+"\" \r\n" + 
            		"}";

    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();

    		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream())));

    		String output;
    		String outputJson="";
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    			outputJson=output;
    			
    		}
    		
    		/*GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            ArrayList<ParserNLP>  paArrayList;
            ArrayList<ParserNLP> parserNLP = gson.fromJson(outputJson, paArrayList);*/
    		String patter= "(?<=\\bVB\\s)(\\w+)";
    		
    		Pattern r = Pattern.compile(patter);
    		String action = "";
    	      // Now create matcher object.
    	      Matcher m = r.matcher(outputJson);
    	      if (m.find( )) {
    	    	  action = m.group(0);
    	         /*System.out.println("Found value: " + m.group(0) );
    	         System.out.println("Found value: " + m.group(1) );
    	         System.out.println("Found value: " + m.group(2) );*/
    	      }else {
    	         System.out.println("NO MATCH");
    	      }
    		
    	      if(action=="") {
    	    	  String patter2= "(?<=\\bVBP\\s)(\\w+)";
    	    		
    	    		Pattern r1 = Pattern.compile(patter2);
    	    		
    	    	      // Now create matcher object.
    	    	      Matcher m2 = r1.matcher(outputJson);
    	    	      if (m2.find( )) {
    	    	    	  action = m2.group(0);
    	    	         /*System.out.println("Found value: " + m.group(0) );
    	    	         System.out.println("Found value: " + m.group(1) );
    	    	         System.out.println("Found value: " + m.group(2) );*/
    	    	      }else {
    	    	         System.out.println("NO MATCH");
    	    	      }
    	    		
    	      }

    	      
    	      patter= "(?<=\\bJJ\\s)(\\w+)";
      		
        		r = Pattern.compile(patter);
        		String accountType="";
        	      // Now create matcher object.
        	      m = r.matcher(outputJson);
        	      if (m.find( )) {
        	    	accountType = m.group(0);
        	         System.out.println("Found value: " + m.group(0) );
        	         //System.out.println("Found value: " + m.group(1) );
        	         //System.out.println("Found value: " + m.group(2) );
        	      }else {
        	         System.out.println("NO MATCH");
        	      }
    	      
        	      if(accountType=="") {
      		patter= "(?<=\\bNN\\s)(\\w+)";
    		
      		r = Pattern.compile(patter);
      		//String accountType="";
      	      // Now create matcher object.
      	      m = r.matcher(outputJson);
      	      if (m.find( )) {
      	    	accountType = m.group(0);
      	         System.out.println("Found value: " + m.group(0) );
      	         //System.out.println("Found value: " + m.group(1) );
      	         //System.out.println("Found value: " + m.group(2) );
      	      }else {
      	         System.out.println("NO MATCH");
      	      }

      	      
        	      }
      	      ArrayList<String> val=new ArrayList<>();
      	      String accountnum="";
        		patter= "(?<=\\bCD\\s)(\\w+)";
      		
        		r = Pattern.compile(patter);

        	      // Now create matcher object.
        	      m = r.matcher(outputJson);
        	      //int i=0;
        	      while(m.find()) {
        	    	  val.add(m.group(0));
        	    	 // i++;
        	    	  //accountnum = m.group(1);
        	         System.out.println("Found value: " + m.group(0) );
        	        // System.out.println("Found value: " + m.group(1) );
        	         //System.out.println("Found value: " + m.group(2) );
        	      }/*else {
        	         System.out.println("NO MATCH");
        	      }*/
    	      
     //   	      apiCall(val, action, accountType );
        	      
        	      AccountModel accountModel=getJson(val.get(0));
        	      
        	      if(accountModel ==null) {
        	    	  res.setResult("No account found");
        	    	 
        	      }
        	      else if(accountModel !=null) {
        	    	  
        	    	  String action2 =getAction(action);
        	    	  
        	    	   if(action2.equalsIgnoreCase("get") && accountType.equalsIgnoreCase("")) {
        	    		  res=accountModel;
        	    		  res.setResult("Success");
        	      }
        	    	   else if(action2.equalsIgnoreCase("get") && accountType.equalsIgnoreCase(accountModel.getAccountType())) {
        	    		   res=accountModel;
         	    		  res.setResult("Success");
        	    	   }
        	    	   else if(action2.equalsIgnoreCase("add") && accountType.equalsIgnoreCase(accountModel.getAccountType())) {
        	    		   
	        	    		   res=accountModel;
	         	    		  res.setResult("Success");
        	    		  
        	    	   }
        	    	    else {
        	    	    	res.setResult("No account found");
        	    	    }
        	    		 
        	    	 
        	    	  
        	    	  //switch(action2)
        	      }
        	      
          		
    		conn.disconnect();

    	  	} catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }
        	ResponseEntity<Response> res2 = new ResponseEntity<Response>(res, responseHeaders, HttpStatus.OK);
				
  			return res2;
    }
    
    
    private String getAction(String action) {
    	Properties prop = new Properties();
		InputStream input = null;
		String url="";
    	try {
			input = getClass().getClassLoader().getResourceAsStream(
					"dic.properties");
			prop.load(input);
			url = prop.getProperty(action);
    	}
    	catch(Exception e) {}
    	return url;
    }
    
    
    /*private AccountModel addJson(AccountModel accountModel) {
    	List<Trans> list=accountModel.getTransactions();
    	Trans trans=new Trans();
    	trans.setAction(action);
    	trans.setAmount();
    	trans.setDate(""+new Date());
    	accountModel.setTransactions(list);
    	return accountModel;
    }*/
    
	private AccountModel getJson(String account) {
		JSONParser parser = new JSONParser();
		AccountModel accountModel =new AccountModel();
        try {
 
            Object obj = parser.parse(new FileReader(
                    account+".json"));
 
            JSONObject jsonObject = (JSONObject) obj;
            List<Trans> trans=new ArrayList<>();
            Trans trans2=new Trans();
            String name = (String) jsonObject.get("name");
            accountModel.setAccount(account);
            accountModel.setName(""+jsonObject.get("name"));
            accountModel.setBalance(""+jsonObject.get("balance"));
            accountModel.setAccountType(""+jsonObject.get("accountType"));
            accountModel.setDateOfOpen(""+jsonObject.get("dateOfOpen"));
            JSONArray jsonArray=(JSONArray)jsonObject.get("transaction");
            for(int i=0;i<jsonArray.size();i++) {
            	JSONObject o = (JSONObject) jsonArray.get(i);
            	trans2.setAction(""+o.get("action"));
            	trans2.setAmount(""+o.get("amount"));
            	trans2.setDate(""+o.get("date"));
            	trans.add(trans2);
            }
            accountModel.setTransactions(trans);
            
            //String author = (String) jsonObject.get("Author");
            //JSONArray companyList = (JSONArray) jsonObject.get("Company List");
 
            System.out.println("Name: " + name);
            //System.out.println("Author: " + author);
            //System.out.println("\nCompany List:");
           // Iterator<String> iterator = companyList.iterator();
            /*while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }*/
 
        } catch (Exception e) {
            e.printStackTrace();
            accountModel= null;
            
        }
        return accountModel;
    
	}

    
    
    
    private void apiCall(ArrayList<String> val, String action, String accountType) {
    	try {

    		URL url = new URL("http://localhost:8181/account/searchAccount");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
    		conn.setRequestProperty("Content-Type", "application/json");
    		conn.setRequestProperty("Ocp-Apim-Subscription-Key", "612dfe13094244b08d2c33a62ff8632d");
    		conn.setRequestProperty("amount", val.get(0));
    		conn.setRequestProperty("amount", val.get(1));

    		/*String input = "{\r\n" + 
            		"	\"language\" : \"en\",\r\n" + 
            		"	\"analyzerIds\" : [\"4fa79af1-f22c-408d-98bb-b7d7aeef7f04\", \"22a6b758-420f-4745-8a3c-46835a67c0d2\"],\r\n" + 
            		"	\"text\" : \"please transfer my $10 from tfsa to rrsp\" \r\n" + 
            		"}";*/
    		
    		String input="{\"account\":\""+val.get(1)+"\"}";

    		OutputStream os = conn.getOutputStream();
    		os.write(input.getBytes());
    		os.flush();

    		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
    			throw new RuntimeException("Failed : HTTP error code : "
    				+ conn.getResponseCode());
    		}

    		BufferedReader br = new BufferedReader(new InputStreamReader(
    				(conn.getInputStream())));

    		String output;
    		String outputJson="";
    		System.out.println("Output from Server .... \n");
    		while ((output = br.readLine()) != null) {
    			System.out.println(output);
    			outputJson=output;
    			
    		}
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	
    }	
}