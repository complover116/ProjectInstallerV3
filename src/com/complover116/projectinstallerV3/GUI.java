package com.complover116.projectinstallerV3;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

public class GUI {
	public static JPanel launcherPanel;
	public static JProgressBar topBar;
	public static JFrame frame;
	public static void init() {
		frame = new JFrame("PI3 "+PermaData.version);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel rootPanel = new JPanel();
		launcherPanel = new JPanel();
		frame.add(rootPanel);
		topBar = new JProgressBar(0, 100);
		
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		rootPanel.add(topBar);
		rootPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		rootPanel.add(launcherPanel);
		
		topBar.setStringPainted(true);
		topBar.setString("Contacting the main repository...");
		launcherPanel.setLayout(new BoxLayout(launcherPanel, BoxLayout.Y_AXIS));
		topBar.setIndeterminate(true);
		frame.pack();
		frame.setVisible(true);
	}
	public static void progress(long counter, String string) {
		topBar.setValue((int) counter);
		topBar.setString(string);
		topBar.setIndeterminate(false);
	}
	public static void update() {
		launcherPanel.removeAll();
		
		for(int i = 0; i < Main_Class.projects.size(); i++){
			String name = (String) ((JSONObject)Main_Class.projects.get(i)).get("name");
			JPanel projpanel = new JPanel();
			JPanel toptext = new JPanel();
			JPanel text = new JPanel();
			JPanel buttons = new JPanel();
			projpanel.setLayout(new BoxLayout(projpanel, BoxLayout.Y_AXIS));
			projpanel.add(toptext);
			projpanel.add(text);
			projpanel.add(buttons);
			toptext.add(new JLabel(name));
			
			String localStatus = Repository.localVersion(name);
			JLabel localVersion = new JLabel( "Local version: "+localStatus );
			localVersion.setForeground(Color.RED);
			text.add(localVersion);
			
			JLabel remoteVersion = new JLabel( "Remote version: "+(String) ((JSONObject)Main_Class.projects.get(i)).get("version") );
			remoteVersion.setForeground(Color.GREEN);
			text.add(remoteVersion);
			if(localStatus == "not installed"){
			JButton install = new JButton("Install");
			
			install.addActionListener(new ParamAL(i){
				@Override
				public void actionPerformed(ActionEvent e) {
					Repository.install((JSONObject)Main_Class.projects.get(da1));
				}
				
			});
			buttons.add(install);
			} else {
				JButton install = new JButton("Update");
				install.addActionListener(new ParamAL(i){
					@Override
					public void actionPerformed(ActionEvent e) {
						Repository.install((JSONObject)Main_Class.projects.get(da1));
					}
					
				});
				buttons.add(install);
				
				JButton launch = new JButton("Launch");
				launch.addActionListener(new ParamAL((String) ((JSONObject)Main_Class.projects.get(i)).get("name") ){

					@Override
					public void actionPerformed(ActionEvent e) {
						GUI.topBar.setIndeterminate(true);
						GUI.topBar.setString("Launching "+da2+"...");
						try {
							Process proc = Runtime.getRuntime().exec(" java -jar "+new File(Repository.workingfolder+da2+"/Exec.jar").getAbsolutePath());
							//Thread.sleep(1000);
							GUI.topBar.setString(da2+" is running...");
							launcherPanel.removeAll();
							JButton kill = new JButton("Kill "+da2);
							kill.addActionListener(new ParamAL(proc){
								@Override
								public void actionPerformed(ActionEvent e) {
									proc.destroyForcibly();
								}
								
							});
							launcherPanel.add(kill);
							frame.pack();
							frame.repaint();
							new Thread(new RunGameThread(proc), "RunGame Thread").start();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				});
				buttons.add(launch);
				
				JButton remove = new JButton("Remove");
				remove.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						
					}
					
				});
				buttons.add(remove);
			}
			projpanel.add(new JSeparator(SwingConstants.HORIZONTAL));
			launcherPanel.add(projpanel);
		}
		frame.pack();
		frame.repaint();
	}
}
