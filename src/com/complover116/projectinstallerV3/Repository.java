package com.complover116.projectinstallerV3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.simple.JSONObject;

public class Repository {
	public static String workingfolder = "C:/ProjectInstaller3/";
	public static void init() {
		if(!new File(workingfolder).exists()){
			System.out.println("Working Folder does not exist, creating a new one...");
			new File(workingfolder).mkdirs();
		}
	}
	public static String localVersion(String name) {
		
		if(new File(workingfolder+name+"/version.pi3").exists()){
			try {
				byte b[] = new byte[20];
				FileInputStream fis = new FileInputStream(new File(workingfolder+name+"/version.pi3"));
				fis.read(b);
				fis.close();
				System.out.println(name+" version:"+new String(b));
				return new String(b);
			} catch (FileNotFoundException e) {
				System.out.println(name+" is not installed");
				return "not installed";
			} catch (IOException e) {
				System.out.println(name+" is not installed");
				return "not installed";
			}
			
			
		} else {
			new File(workingfolder+name).mkdirs();
		}
		System.out.println(name+" is not installed");
		return "not installed";
	}
	
	public static boolean install(JSONObject project) {
		new Thread(new InstallThread(project)).start();
		return false;
	}
	
	public static void createWD(String name) {
		new File(workingfolder+name).mkdirs();
	}
	public static void setVersion(String name, String version) {
		new File(workingfolder+name+"/version.pi3").delete();
		try {
			new FileOutputStream(new File(workingfolder+name+"/version.pi3")).write(version.getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File getTempFile() {
		if(!new File(workingfolder+"temp").exists()){
			System.out.println("Temp Folder does not exist, creating a new one...");
			new File(workingfolder+"temp").mkdirs();
		}
		try {
			return File.createTempFile("download_", ".pi3", new File(workingfolder+"temp"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
