package com.example.gemdemo;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;





@RestController
//@DependsOn({"gemfireCache", "ActiveItem"})
public class MyController {

	@Autowired
	Region<String, Greeting> greetRegion;


	@RequestMapping("/")
	public String home() {
		return "Search Service -- Available APIs: <br/>"
				+ "<br/>"
				+ "GET /add?key={email}&value={name}  - insert a value <br/>";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/add")
	@ResponseBody
	public String insertCustomerData()  {

		Greeting gt = new Greeting();
		gt.setId(new Timestamp(new Date().getTime()).toString());
        gt.setMessage("GoodMorning");
        gt.setName("Bob");
		greetRegion.put(gt.getId(), gt);
		return "customer data successfully inserted into cache.";
	}
	@RequestMapping(method = RequestMethod.GET, path = "/get")
	@ResponseBody	
	public String getCacheDetails(){
		String result = "";
		try{
		Set<String> keys =greetRegion.keySetOnServer();
		for(String key :keys){
			System.out.println("#### Keyy :"+key);
			result = result +"\n" +greetRegion.get(key).toString();
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
