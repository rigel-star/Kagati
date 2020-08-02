package org.lemon.utils;

import java.util.ArrayList;
import java.util.List;

public class Information {

	
	private List<Object> allInfo = new ArrayList<Object>();
	
	
	public Information add(Object obj) {
		allInfo.add(obj);
		return this;
	}
	
	
	public List<Object> getAllInfo(){
		return allInfo;
	}
	
}
