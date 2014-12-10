package com.complover116.projectinstallerV3;

import static java.nio.file.StandardCopyOption.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.SwingUtilities;

import javafx.scene.shape.Path;

import org.json.simple.JSONObject;

public class InstallThread implements Runnable {
	JSONObject project;
	public InstallThread(JSONObject proj) {
		project = proj;
	}
	@Override
	public void run() {
		String getmethod = (String) project.get("getmethod");
		if(getmethod.equalsIgnoreCase("dl")) {
			System.out.println("Get method is direct link, connecting to the site...");
			GUI.topBar.setString("Connecting...");
			GUI.topBar.setIndeterminate(true);
			File result = Network.download((String) project.get("location"), (String) project.get("name"));
			
			if(result == null) {
				System.err.println("Error downloading the file!");
				GUI.topBar.setMaximum(100);
				GUI.progress(100, "Download error!");
			} else {
				System.out.println("Downloaded!");
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						GUI.topBar.setString("Installing...");
						GUI.topBar.setIndeterminate(true);
					}
					
				});
				
				try {
					Files.copy(Paths.get(result.getAbsolutePath()), Paths.get(Repository.workingfolder+(String) project.get("name")+"/Exec.jar"), REPLACE_EXISTING);
					Repository.setVersion((String) project.get("name"), (String) project.get("version"));
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							GUI.topBar.setMaximum(100);
							GUI.progress(100, "DONE!");
							GUI.update();
						}
						
					});
				} catch (IOException e) {
					e.printStackTrace();
					System.err.println("Error installing!");
					
					SwingUtilities.invokeLater(new Runnable(){

						@Override
						public void run() {
							GUI.topBar.setMaximum(100);
							GUI.progress(100, "Installation error!");
						}
						
					});
				}
			}
		} else {
			System.err.println("Unknown aquisition method!");
		}
	}

}
