package com.home;
import com.home.JEditorForm;
import com.home.JEditorAutoFill;
import com.home.JCodeFile;
import com.home.JChecker;
import com.home.Unarchiver;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.event.*;

class JEditor {
	//Hashtable<String, String> dataList = new Hashtable<>();
	//String currentFile;
	JEditorAutoFill auto = new JEditorAutoFill();
	boolean showAuto = false;
	JCodeFile currentCode = null;
	ArrayList<JCodeFile> files = new ArrayList<>();
	ArrayList<String> filesName = new ArrayList<>();
	File folderJDK = null;

	ArrayList<Character> brackets = new ArrayList<>(Arrays.asList('(', '{', '[', '<', '\'', '"'));
	ArrayList<Character> bracketsEnds = new ArrayList<>(Arrays.asList(')', '}', ']', '>', '\'', '"'));

	JEditorForm form;
	
	public static void main(String[] args) {
		JEditor editor = new JEditor();
	}
	private JEditor() {
		// for(char c : brackets) {
		// 	System.out.println((int) c);
		// }
		form = new JEditorForm();
		form.setActionListeners(e -> newButtonActionPerformed(e),e -> openButtonActionPerformed(e), e -> saveButtonActionPerformed(e), 
			e -> saveAsButtonActionPerformed(e), e -> listFilesValueChanged(e), e -> closeButtonActionPerformed(e),
			e -> exitButtonActionPerformed(e), new KeyChecker(), e -> chooseJDKFolder(e));
		form.setVisible(true);
		//auto.setVisible(true);
	}
	public void newButtonActionPerformed(ActionEvent e) {
		currentCode = new JCodeFile("untitled");
		int count = checkNumber(files);
		if(count != 0) {
			currentCode.setName("untitled" + "(" + count + ")");
		}
		files.add(currentCode);
		filesName.add(currentCode.getName());

		JCodeFile temp = currentCode;
		form.addData(filesName);
		form.setSelect(temp);
	}
	public void chooseJDKFolder(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int ans = fc.showOpenDialog(form);
		if(ans == JFileChooser.APPROVE_OPTION) {
			folderJDK = fc.getSelectedFile();
			Unarchiver.unzip(new File(folderJDK.toString() + "/src.zip"));
		}
	}
	public int checkNumber(ArrayList<JCodeFile> list) {
		int n = 0;
		for(JCodeFile file : list) {
			if(file.getPath() == null && file.getName().length() >= 8 && file.getName().substring(0,8).equals("untitled"))
				n++;
		}
		return n;
	}
	public void openButtonActionPerformed(ActionEvent e) {
		//String[] texts = JEditor.openFile(chooseFile(0));
		openFile(chooseFile(0));
		// form.addData(dataList.keySet());
		// form.setSelect(currentFile);
		JCodeFile temp = currentCode;
		form.addData(filesName);
		form.setSelect(temp);
		//JChecker.find(form);
		//form.setCode(currentFile);
		//textCode.setText(texts[0]);
	}
	public void saveButtonActionPerformed(ActionEvent e) {
		// if(currentFile != null) {
		// 	saveFile(new File(currentFile), form.getCode());
		if(currentCode != null && (currentCode.getName().length() < 8 || !currentCode.getName().substring(0,8).equals("untitled"))) {
			//System.out.println("Path curr: " + currentCode.getPath());
			saveFile(currentCode.getPath(), form.getCode());
		} else {
			File path = chooseFile(1);
			String name = JOptionPane.showInputDialog("What's name of file and <.format>?");
			//System.out.println("Files: " + files);
			//System.out.println("Code: " + currentCode.getName());
			if(currentCode.getName().substring(0,8).equals("untitled")) {
				//System.out.println("YES");
				filesName.remove(currentCode.getName());
				//System.out.println(currentCode);
				deleteFile(currentCode);
				//files.remove(filesName.indexOf(currentCode.getName())); ////Same function as delete function into closeButtonActionPerformed
			}
			// System.out.println("Files1: " + files);
			// System.out.println("Code1: " + currentCode.getName());
			saveAsFile(path, form.getCode(), name);
		}
	}
	public void saveAsButtonActionPerformed(ActionEvent e) {
		//JEditor.saveAsFile(chooseFile(1), textCode.getText(), JOptionPane.showInputDialog("What's name of file and <.format>?"));
		saveAsFile(chooseFile(1), form.getCode(), JOptionPane.showInputDialog("What's name of file and <.format>?"));
	}
	public void closeButtonActionPerformed(ActionEvent e) {
		// if(currentFile != null) {
		// 	dataList.remove(currentFile);
		// 	form.addData(dataList.keySet());
		// 	form.setSelect(null);
		// }
		if(currentCode != null) {
			// System.out.println(currentCode);
			// System.out.println(files);
			// System.out.println(filesName);
			

			// for(JCodeFile jcf : files) { //////Delete function //Create other void for it
			// 	if(jcf.getName() == currentCode.getName()) {
			// 		files.remove(jcf);
			// 		break;
			// 	}
			// }
			
			deleteFile(currentCode);

			//files.remove(currentCode);
			filesName.remove(currentCode.getName());
			//System.out.println("files " + files);
			//System.out.println("code " + currentCode);
			if(files.size() > 0)
				currentCode = files.get(files.size() - 1);
			else
				currentCode = null;
			//System.out.println("code " + currentCode);
			// System.out.println(currentCode);
			// System.out.println(files);
			// System.out.println(filesName);
			//System.out.println("Bye");
			form.setSelect(currentCode);
			form.addData(filesName);
		}
	}
	public void exitButtonActionPerformed(ActionEvent e) {
		form.dispatchEvent(new WindowEvent(form, WindowEvent.WINDOW_CLOSING));
	}
	public void listFilesValueChanged(ListSelectionEvent e) {
		// TODO add your code here
		if(!e.getValueIsAdjusting())
			{
				// String tempFile = (String) form.getFile();
				// currentFile = tempFile != null ? tempFile : currentFile;
				// if(dataList.containsKey(currentFile))
				// 	form.setCode(dataList.get(currentFile));	
				JCodeFile tempFile = form.getFile();

				for(JCodeFile jcf : files) {
					if(tempFile.getName().equals(jcf.getName())) {
						currentCode = jcf;
						break;
					} else {
						currentCode = tempFile;
					}
				}

				//currentCode = tempFile != null ? tempFile : currentCode;
				//currentCode = tempFile;

				if(currentCode != null){
					//System.out.println("Hello");
					//System.out.println("Files: " + filesName);
					if(filesName.contains(currentCode.getName()))
					{
						//System.out.println(currentCode.getName());
						form.setCode(files.get(filesName.indexOf(currentCode.getName())).getCode());
					}
				} else {
					form.setCode("");
				}
				JChecker.find(form);
			}
	}

	public void deleteFile(JCodeFile file) { //deleting file from files array
		for(JCodeFile jcf : files) { //////Delete function //Create other void for it
			if(jcf.getName() == file.getName()) {
				files.remove(jcf);
				break;
			}
		}
	}

	public File chooseFile(int filter) { //chosing file(0) or directory(1)
		int ans;
		JFileChooser fc = new JFileChooser();
		switch(filter) {
			case 0:
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				ans = fc.showOpenDialog(form);
				break;
			case 1:
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				ans = fc.showSaveDialog(form);
				break;
			default:
				ans = 0;
		}
		if(ans == JFileChooser.APPROVE_OPTION)
			return fc.getSelectedFile();
		else
			return null;
		
	}

	public void saveFile(File path, String text) { //saving file to its directory
		//System.out.println(path.toString());
		try {
			//path.delete();
			//FileWriter fw = new FileWriter(path);	
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));	
			bw.write(text);
			//fw.flush();
			bw.close();
			//openFile(new File(path + "/" + name));
		} catch (Exception e) {System.out.print("Hi");}
	}
	public void saveAsFile(File path, String text, String name) { //saving file to chosen directory
		try {
			FileWriter fw = new FileWriter(path + "/" + name);
			fw.write(text);
			fw.close();
			openFile(new File(path + "/" + name));
			JCodeFile temp = currentCode;//////////////////////////////////////////////////
			form.addData(filesName);
			form.setSelect(temp);///////////////////////////////////////////////////////////
		} catch(Exception e) {}
	}
	public void openFile(File path) { //opening file
		String[] texts = new String[2];
		texts[0] = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null) {
				texts[0] += line + "\n";
			}
			texts[0] = texts[0].substring(0, texts[0].length() - 1);
			texts[1] = path.toString();
			// currentFile = texts[1];
			// dataList.put(texts[1], texts[0]);
			//System.out.println("Path: " + path.toString());
			currentCode = new JCodeFile(path, texts[0]);
			//System.out.println("CurPath: " + currentCode.getPath());
			files.add(currentCode);
			filesName.add(currentCode.getName());
		} catch(Exception e) {}
	}
	class KeyChecker extends KeyAdapter {
		char prevC;
		@Override
		public void keyReleased(KeyEvent e) {
			JChecker.find(form); //highlight
		}
		@Override
		public void keyPressed(KeyEvent e) {
			auto.setFocusable(false); //popupmenu
			if(!showAuto) {		
				auto.setVisible(false); //popupmenu
			} else {
				Point p = form.getFillLocation();//popupmenu
				auto.show(form, p.x, p.y);//popupmenu
			}

			char c = e.getKeyChar();

			if(e.isControlDown() && c == ' ') {
				showAuto = !showAuto;
			}

			int pos = form.getLastSymbol(); //position of last written symbol
			String nextC = form.getCode().length() > pos ? Character.toString(form.getCode().charAt(pos)) : ""; //next after caret symbol

			if(prevC == '{' && c == '\n' && (!nextC.equals("")  && nextC.equals("}"))) { //for writing new line when caret into {} and ENTER was pressed
				form.insertCode("\n", pos);
			}

			// if(nextC.equals(Character.toString(c)))
			// 	bracketAllow = false;

			prevC = c;

			if(bracketsEnds.contains(c) && nextC.equals(Character.toString(c))) {
				form.removeCode(pos, pos+1);
			} else {
				if(brackets.contains(c)) {//for writing "{}" when was writed only "{"
					char sym = bracketsEnds.get(brackets.indexOf(c));

					form.insertCode(String.valueOf(sym), pos);		
				}
			}

			JChecker.find(form); //highlight
			
		}
	}
}