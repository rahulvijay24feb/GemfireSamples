package com.example.gemdemoSDG;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;
@Region("Hello")
public class Greeting implements Serializable{
	/**
	 * 
	 */
	@Id
	private String id;
	private String name;
	private String message;
	public String toString(){
		return " id : "+id+" name : "+name+" message : "+message;
	}
    public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setId(String id) {
		this.id = id;
	}
}
