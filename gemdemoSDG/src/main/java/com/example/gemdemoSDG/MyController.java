package com.example.gemdemoSDG;


import java.sql.Timestamp;
import java.util.Date;

import org.apache.geode.cache.client.ClientCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;







@RestController
@DependsOn({"gemfireCache", "Greeting"})
public class MyController {

    @Autowired
    private ClientCache gemfireCache;

    @Autowired
    private HelloRepository repository;
	


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
        repository.save(gt);
		return "Data successfully inserted into cache.";
	}
	@RequestMapping(method = RequestMethod.GET, path = "/get")
	@ResponseBody	
	public String getCacheDetails(){
		String result = "";
		try{
		Iterable<Greeting> greetings =repository.findAll();
		for(Greeting greet :greetings){
			
			result = result +"\n" +greet.toString();
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
