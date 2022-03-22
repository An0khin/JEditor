package com.home;

import java.util.zip.*;
import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.lang.reflect.*;

public class Unarchiver {
	public static void unzip(File zip) {
		HashMap<String, ArrayList<Method>> classes = new HashMap<>();
		try {
			ZipFile file = new ZipFile(zip);
			String name;
			for (Enumeration<? extends ZipEntry> e = file.entries(); e.hasMoreElements();) {
				ZipEntry el = e.nextElement();
				name = el.getName();
				if(name.endsWith(".java") && (name.startsWith("java") || name.startsWith("javax")) && !name.endsWith("package-info.java")) {
					// BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(el)));

					// String line;
					// while((line = br.readLine()) != null) {
					// 	if(line.indexOf("{") != -1 && line.indexOf("}") == -1) {
					// 		if(line.indexOf("class") == -1) {
					// 			System.out.println(line);
					// 		}
					// 	}
					// }
					//System.out.println(name);
					name = name.replaceAll("/", ".");
					name = name.substring(0, name.length() - 5);
					try {
						//System.out.println(Arrays.toString(ClassLoader.getSystemClassLoader().loadClass(name).getMethods()));
						ClassLoader classLoader = ClassLoader.getSystemClassLoader();
						if(Modifier.isPublic(classLoader.loadClass(name).getModifiers())) {
							Method[] m = classLoader.loadClass(name).getDeclaredMethods();
							ArrayList<Method> methods = new ArrayList<>();
							for(Method method : m) {
								if(Modifier.isPublic(method.getModifiers())) {
									methods.add(method);
								}
							}
							if(!methods.isEmpty())
								classes.put(name, methods);
						}
						
					} catch(Exception ex) {}
				}
			}
			
			//classes.forEach((k, v) -> System.out.println("Key : " + k + ", Value : " + v));
			// classes.get("java.lang.String").forEach((t) -> System.out.println(t));
			// classes.get("java.lang.String").forEach((t) -> {
			// 	if(t.toString().indexOf("[") == -1) {
			// 		System.out.println(t.getReturnType());
			// 	}});
			// //classes.get("java.lang.String").forEach((t) -> System.out.println(t.getGenericReturnType()));
			// System.out.println(classes.containsKey("javax.swing.plaf.nimbus.NimbusStyle"));

			File path = new File(System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar + "JEditor");
			path.mkdir();

			File doc = new File(path.toString() + File.separatorChar + "JEditorMethods.dat"); //path to My Documents
			BufferedWriter bw = new BufferedWriter(new FileWriter(doc));
			TreeMap<String, String> sorted = new TreeMap<>();

			classes.forEach((k, v) -> {
				for(Method m : v) {
					sorted.put(m.getName(), m.toString());
				}
			});

			sorted.forEach((k, v) -> {
				try {
					bw.write(k + ">>>" + v + "|||");
					bw.flush();
				} catch(Exception ex) {ex.printStackTrace();}
			});

			bw.close();

		} catch(Exception e) {e.printStackTrace();}
	}
}