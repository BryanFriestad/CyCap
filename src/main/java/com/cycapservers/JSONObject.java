package com.cycapservers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONObject {
	
	private String string_representation;
	private int pairCount;
	
//	public static HashMap<String, Object> parse(String json){
//		if(json == null || json.isEmpty()){
//			throw new IllegalArgumentException("string parameter is null or empty");
//		}
//		if(!json.startsWith("{") || !json.endsWith("}")){
//			throw new IllegalArgumentException("string parameter either does not start with or end with a curly bracket(" + json + ")");
//		}
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		json = json.substring(1, json.length()-1); //removes outside brackets
//		String[] entries = json.split(",+( *\\{)"); //split on commas
//		for(String entry : entries){
//			entry = entry.trim(); //remove leading and trailing whitespace
//			int colon_index = entry.indexOf(":");
//			String key = entry.substring(0, colon_index).trim();
//			String value_string = entry.substring(colon_index+1, entry.length()).trim();
//			if(!key.startsWith("\"") || !key.endsWith("\"")){
//				throw new IllegalArgumentException("key value is not surrounded by quotes(" + key + ")");
//			}
//			key = key.substring(1, key.length()-1); //removes quotes from keyname
//			System.out.println("key = " + key);
//			System.out.println("value = " + value_string);
//			
//			if(value_string.startsWith("{")){
//				map.put(key, parse(value_string));
//			}
//			else if(value_string.startsWith("[")){
//				value_string = value_string.substring(1, value_string.length()-1); //remove square brackets
////				String[] inner_json_strings = value_string.split(",+( *\\{)"); //regex to only get commas outside brackets
//				String[] inner_json_strings = value_string.split(",");
//				List<HashMap<String, Object>> inner_jsons = new ArrayList<HashMap<String, Object>>();
//				for(String s : inner_json_strings){
//					s = s.trim();
//					inner_jsons.add(parse("{" + s)); //the earlier regex removes the bracket, but the parse function needs it
//				}
//				map.put(key, inner_jsons);
//			}
//			else if(value_string.startsWith("\"")){//value is a string
//				map.put(key, value_string.substring(1, value_string.length()-1));
//			}
//			else{ //value is a boolean or number
//				try{
//					map.put(key, Integer.parseInt(value_string));
//				}
//				catch(NumberFormatException e){
//					try{
//						map.put(key, Double.parseDouble(value_string));
//					}
//					catch(NumberFormatException e2){
//						map.put(key, Boolean.parseBoolean(value_string));
//					}
//				}
//			}
//		}
//		return map;
//	}

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
	
	public void put(String name, boolean value) {
		if(pairCount > 0) addComma();
		addName(name);
		string_representation += ":" + value;
		pairCount++;
	}
	
	public void put(String name, JSON_Stringable[] value){
		if(pairCount > 0) addComma();
		addName(name);
		string_representation += ":[";
		for(int i = 0; i < value.length; i++){
			if(i > 0) addComma();
			if(value[i] == null){
				string_representation += "null";
			}
			else{
				string_representation += value[i].toJSONString();
			}
		}
		string_representation += "]";
		pairCount++;
	}
	
	public void put(String name, JSON_Stringable value){
		if(pairCount > 0) addComma();
		addName(name);
		if(value == null){
			string_representation += ":null";
		}
		else{
			string_representation += ":" + value.toJSONString();
		}
		pairCount++;	
	}
	
	public void put(String name, JSONObject value){
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
