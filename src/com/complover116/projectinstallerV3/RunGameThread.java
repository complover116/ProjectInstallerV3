package com.complover116.projectinstallerV3;

public class RunGameThread implements Runnable {
	Process process;
	public RunGameThread(Process proc) {
		process = proc;
	}
	@Override
	public void run() {
		try {
			process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GUI.topBar.setIndeterminate(false);
		GUI.topBar.setString("READY");
		GUI.update();
	}
	
}
