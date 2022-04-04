package com.home;

import java.io.*;
import java.util.*;

public class Loader {
	private static Loader instance;
	private Loader() {}

	// public static void main(String[] args) {
	// 	Loader.getInstance().loadMethodsClasses();
	// 	Loader.getInstance().loadClassesMethods();
	// }

	public static Loader getInstance() {
		if(instance == null) {
			instance = new Loader();
		}
		return instance;
	}

	private TreeMap<String, String> loadMethodsClasses() {
		TreeMap<String, String> tm = new TreeMap<>();
		String line;
		String[] classes;
		String[] methods;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + File.separatorChar + "Documents" + 
				File.separatorChar + "JEditor" + File.separatorChar + "JEditorMethodsClasses.dat")));
			
			line = br.readLine();
			classes = line.split("[|][|][|]");
			
			for(int i = 0; i < classes.length; i++) {
				methods = classes[i].split(">>>");
				tm.put(methods[0], methods[1]);
			}

			tm.forEach((k, v) -> {
				System.out.println(k + " >>> " + v);
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

		return tm;
	}

	private TreeMap<String, String> loadClassesMethods() {
		TreeMap<String, String> tm = new TreeMap<>();
		String line;
		String[] classes;
		String[] methods;

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + File.separatorChar + "Documents" + 
				File.separatorChar + "JEditor" + File.separatorChar + "JEditorMethodsClasses.dat")));
			
			line = br.readLine();
			classes = line.split("[|][|][|]");
			
			for(int i = 0; i < classes.length; i++) {
				methods = classes[i].split(">>>");
				tm.put(methods[0], methods[1]);
			}

			tm.forEach((k, v) -> {
				System.out.println(k + " >>> " + v);
			});
		} catch(Exception e) {
			e.printStackTrace();
		}

		return tm;
	}
}