package com.cycapservers;

public class JSONObject {
	
	private String string_representation;
	private int pairCount;

	public JSONObject() {
		string_representation = "{ ";
		pairCount = 0;
	}
	
	public void put(String name, String value){
		if(pairCount > 0) addComma();
		addName(name);
		string_representation += ":\"" + value + "\"";
		pairCount++;
	}
	
	public void put(String name, int value){
		if(pairCount > 0) addComma();
		addName(name);
		string_representation += ":" + value;
		pairCount++;
	}
	
	public void put(String name, double value){
		if(pairCount > 0) addComma();
		addName(name);
		string_representation += ":" + value;
		pairCount++;
	}
	
	private void addName(String name){
		string_representation += "\"" + name + "\"";
	}
	
	private void addComma(){
		string_representation += ", ";
	}

	@Override
	public String toString(){
		return string_representation + " }";
	}
}
