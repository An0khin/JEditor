package com.home;
import java.io.*;

public class JCodeFile {
	private String name;
	private File absPath;
	private String code;
	
	JCodeFile(File path) {
		absPath = path;
		name = absPath.toString();
	}

	JCodeFile(String name1) {
		name = name1;
	}
	
	JCodeFile(File path, String text) {
		absPath = path;
		name = absPath.toString();
		code = text;
	}

	public String getName() {
		return name;
	}

	public File getPath() {
		return absPath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String text) {
		code = text;
	}
	public void setName(String name1) {
		name = name1;
	}
}