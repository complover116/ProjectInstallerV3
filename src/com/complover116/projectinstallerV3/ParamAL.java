package com.complover116.projectinstallerV3;

import java.awt.event.ActionListener;

public abstract class ParamAL implements ActionListener {
	int da1;
	String da2;
	Process proc;
	public ParamAL(int d1){
		da1 = d1;
	}
	public ParamAL(String d1){
		da2 = d1;
	}
	public ParamAL(Process process){
		proc = process;
	}
}
