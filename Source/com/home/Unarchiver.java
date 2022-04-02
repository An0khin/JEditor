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

			File doc = new File(path.toString() + File.separatorChar + "JEditorMethodsClasses.dat"); //path to My Documents
			BufferedWriter bwMethodsClasses = new BufferedWriter(new FileWriter(doc));

			File doc1 = new File(path.toString() + File.separatorChar + "JEditorClassesMethods.dat");
			BufferedWriter bwClassesMethods = new BufferedWriter(new FileWriter(doc1));
			
			TreeMap<String, ArrayList<String>> sortedClasses = new TreeMap<>();
			TreeMap<String, TreeMap<String, ArrayList<String>>> sortedMethods = new TreeMap<>();

			ArrayList<String> list = new ArrayList<>(); //For Method -> Classes
			//ArrayList<String> listMethods = new ArrayList<>(); 
			TreeMap<String, ArrayList<String>> listMethods = new TreeMap<>(); //For Class -> Methods

			classes.forEach((k, v) -> {
				//list.clear();
				listMethods.clear();

				// if(sortedClasses.get(v.get(0).getName()) != null) {
				// 	//list.addAll(sortedClasses.get(v.get(0).getName()));
				// 	for(Method m : sortedClasses.get(v.get(0).getName())) {
				// 		list.add(m.getName());
				// 	}
				// } else {
				// 	list.add(v.get(0).getName());
				// }
				// sortedClasses.put(list.get(0), list);


				// if(sortedClasses.get(m.getName()) != null) {
				// 	list.addAll(sortedClasses.get(m.getName()));
				// }

				for(Method m : v) { //For Class -> Methods
					list.clear();	
					// methodsArray.clear();
					// methodsArray.add(m.getName());
					// methodsArray.add(m.toString());
					// listMethods.add((ArrayList<String>) methodsArray.clone());
					if(listMethods.containsKey(m.getName())) {
						list.addAll((ArrayList<String>) listMethods.get(m.getName()).clone());
					}
					list.add(m.toString());
					listMethods.put(m.getName(), (ArrayList<String>) list.clone());
				}

				sortedMethods.put(k, (TreeMap<String, ArrayList<String>>) listMethods.clone());

				for(Method m : v) { //For Method -> Classes
					list.clear();
					if(sortedClasses.containsKey(m.getName())) {
						list.addAll((ArrayList<String>)sortedClasses.get(m.getName()).clone());
						if(!list.contains(k)) {
							list.add(k);
						}
					} else {
						list.add(k);
					}
					System.out.println(m.getName() + " >>> " + list);
					sortedClasses.put(m.getName(), (ArrayList<String>) list.clone());
				}
				


				// for(Method m : v) { //Doesn't work
				// 	list.add(m.toString());
				// }
				// if(sortedClasses.containsKey(v.get(0).getName())) {
				// 	tempList.clear();
				// 	tempList.addAll((ArrayList<String>) sortedClasses.get(v.get(0).getName()).clone());
				// 	for(String m : tempList) {
				// 		list.add(m);
				// 	}
				// }

				//k - class
				//v - arraylist<Method>

				// System.out.println(k + " >>> " + list);
				// sortedClasses.put(v.get(0).getName(), list);
				// System.out.println(v.get(0).getName());
			});

			for(int i = 0; i < 50; i++) {
				System.out.println();
			}

			sortedClasses.forEach((k, v) -> {
				//k - method
				//v - arrayList<String> of classes
				try {
					System.out.println(k + " >>> " + v);
					bwMethodsClasses.write(k + ">>>" + v + "|||");
					bwMethodsClasses.flush();
				} catch(Exception ex) {ex.printStackTrace();}
			});

			sortedMethods.forEach((k, v) -> {
				try {
					bwClassesMethods.write(k + ">>>" + v + "|||");
					bwClassesMethods.flush();
				} catch(Exception ex) {ex.printStackTrace();}
			});

			bwClassesMethods.close();
			bwMethodsClasses.close();

		} catch(Exception e) {e.printStackTrace();}
	}
}