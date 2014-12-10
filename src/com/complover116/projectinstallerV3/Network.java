package com.complover116.projectinstallerV3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.SwingUtilities;

public class Network {
	public static File download(String address, String name) {
		File result;
		URL oracle;
		try {
			result = Repository.getTempFile();
			oracle = new URL(address);
			BufferedInputStream bif = new BufferedInputStream(oracle.openStream());
			FileOutputStream fos = new FileOutputStream(result);
			int temp;
			int counter = 0;
			URLConnection connection = oracle.openConnection();
			connection.connect();
			GUI.topBar.setMaximum(connection.getContentLength());
			while ((temp = bif.read()) != -1){
				fos.write(temp);
				counter ++;
				SwingUtilities.invokeLater(new ParamRN(counter, "Downloading "+name) {

					@Override
					public void run() {
						GUI.progress(da1, da2);
					}
					
				});
				
			}
			bif.close();
			fos.close();
			return result;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String getString(String address) {
		URL oracle;
		String result = "";
		try {
			oracle = new URL(address);
			 BufferedReader in = new BufferedReader(
				        new InputStreamReader(oracle.openStream()));
			 			result = "";
				        String inputLine;
				        while ((inputLine = in.readLine()) != null)
				        result = result + inputLine+"\n";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		
		
       return result;
       
	}
}
