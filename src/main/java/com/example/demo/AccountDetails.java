package com.example.demo;


import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.AccountModel;
import com.example.models.AccountRequest;
import com.example.models.Trans;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.JsonArray;

@RestController
@RequestMapping("/account")
public class AccountDetails {
	
	@RequestMapping(value = "/searchAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountModel> searchAccount(@RequestHeader Map<String, String> header,
			@RequestBody AccountRequest reqPayload){
		
		
		AccountModel accountModel = getJson(reqPayload.getAccount());
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.NO_CONTENT;
		
		if(accountModel.getResult() !=null){
			status = HttpStatus.OK;
		}
		ResponseEntity<AccountModel> res = new ResponseEntity<AccountModel>(accountModel, responseHeaders, status);
				
		return res;
		
	}	

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
            JSONArray jsonArray=(JSONArray)jsonObject.get("transactions");
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
            
        }
        return accountModel;
    
	}
	/*
	
	@RequestMapping(value = "/searchAccount/resp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AccountModel> searchAccount(@RequestHeader Map<String, String> header,
			@RequestBody AccountRequest reqPayload){
		
		
		HttpHeaders responseHeaders = new HttpHeaders();
		HttpStatus status = HttpStatus.NO_CONTENT;
		AccountModel accountModel = new AccountModel();
		if(accountModel.getResult() !=null){
			status = HttpStatus.OK;
		}
		ResponseEntity<AccountModel> res = new ResponseEntity<AccountModel>(accountModel, responseHeaders, status);
				
		return res;
		
	}	*/

}



