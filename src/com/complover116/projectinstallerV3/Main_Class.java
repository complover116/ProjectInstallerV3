package com.complover116.projectinstallerV3;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main_Class {
	public static JSONArray projects;
	public static void main(String args[]) {
		Repository.init();
		GUI.init();
		JSONParser parser = new JSONParser();
		try {
			projects = (JSONArray) parser.parse(Network.getString(Config.repo+"MainList"));
			GUI.progress(100, "IDLE");
			GUI.update();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
