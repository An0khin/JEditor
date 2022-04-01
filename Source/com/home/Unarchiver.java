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
			TreeMap<String, ArrayList<String>> sorted = new TreeMap<>();

			ArrayList<String> list = new ArrayList<>();
			ArrayList<String> tempList = new ArrayList<>();

			classes.forEach((k, v) -> {
				list.clear();
				// if(sorted.get(v.get(0).getName()) != null) {
				// 	//list.addAll(sorted.get(v.get(0).getName()));
				// 	for(Method m : sorted.get(v.get(0).getName())) {
				// 		list.add(m.getName());
				// 	}
				// } else {
				// 	list.add(v.get(0).getName());
				// }
				// sorted.put(list.get(0), list);


				// if(sorted.get(m.getName()) != null) {
				// 	list.addAll(sorted.get(m.getName()));
				// }


				for(Method m : v) {
					list.clear();
					list.add(k);
					if(sorted.containsKey(m.getName())) {
						list.addAll((ArrayList<String>)sorted.get(m.getName()).clone());
					}
					System.out.println(m.getName() + " >>> " + list);
					sorted.put(m.getName(), (ArrayList<String>) list.clone());
				}
				


				// for(Method m : v) { //Doesn't work
				// 	list.add(m.toString());
				// }
				// if(sorted.containsKey(v.get(0).getName())) {
				// 	tempList.clear();
				// 	tempList.addAll((ArrayList<String>) sorted.get(v.get(0).getName()).clone());
				// 	for(String m : tempList) {
				// 		list.add(m);
				// 	}
				// }

				//k - class
				//v - arraylist<Method>

				// System.out.println(k + " >>> " + list);
				// sorted.put(v.get(0).getName(), list);
				// System.out.println(v.get(0).getName());
			});

			for(int i = 0; i < 50; i++) {
				System.out.println();
			}

			sorted.forEach((k, v) -> {
				//k - method
				//v - arrayList<String> of classes
				try {
					System.out.println(k + " >>> " + v);
					bw.write(k + ">>>" + v + "|||");
					bw.flush();
				} catch(Exception ex) {ex.printStackTrace();}
			});

			bw.close();

		} catch(Exception e) {e.printStackTrace();}
	}
}