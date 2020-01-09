package main.by.htp.ts.dao.impl;

import java.util.ResourceBundle;

public class DBResourceManager {
	private final static DBResourceManager instances = new DBResourceManager();
	private ResourceBundle bundle = ResourceBundle.getBundle("resources.db");
	
	public static DBResourceManager getInstance(){
		return instances;
	}
	public String getValue(String key){
		return bundle.getString(key);		
	}
	
}
