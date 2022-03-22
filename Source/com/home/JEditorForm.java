package com.home;
import com.home.JEditor;
import com.home.JCodeFile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.*;
import java.util.*;

public class JEditorForm extends JFrame {
	String file = null;
	JCodeFile cFile = null;
	Highlighter.HighlightPainter red = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
	Highlighter highlighter;
	public JEditorForm() {
		initComponents();
		try { 
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); //It's default
		}
        catch(Exception e) {}
		highlighter = textCode.getHighlighter();
	}
	public Highlighter getHighlighter() {
		return highlighter;
	}
	public void addHighlight(int begin, int end, Color color) {
		try {
			highlighter.addHighlight(begin, end, new DefaultHighlighter.DefaultHighlightPainter(color));
		} catch(Exception e) {};
	}
	public String getCode() {
		return textCode.getText();
	}
	public int getLastSymbol() {
		return textCode.getSelectionEnd();
	}
	public JCodeFile getFile() {
		if(listFiles.getSelectedValue() != null) {
			JCodeFile temp = new JCodeFile((String) listFiles.getSelectedValue());
			return temp;
		}
		else
			return cFile;

	}
	public void setCode(String code) {
		textCode.setText(code);
		// try{
		// 	highlighter.addHighlight(0, 5, red);
		// } catch(Exception e) {}
	}
	public void insertCode(String code, int position) {
		Thread insertThr = new Thread(() -> {
			try {
				Thread.sleep(1);
			} catch(Exception e) {}
			if(position >= getCode().length()) {
				textCode.append(code);
			} else {
				textCode.insert(code, position+1);
			}
			this.validate();
			this.repaint();
		});
		insertThr.start();
	}
	public void removeCode(int positionB, int positionE) {
		textCode.replaceRange("", positionB, positionE);
		this.validate();
		this.repaint();
	}
	// public void setSelect(String file) {
	// 	file = file;
	// 	listFiles.setSelectedValue(file, true);
	// }
	public void setSelect(JCodeFile file) {
		cFile = file;
		if(cFile != null) {
			//System.out.println(cFile.getName());
			listFiles.setSelectedValue(cFile.getName(), true);
		} else {
			// System.out.println("Null");
			// System.out.println(cFile);
			listFiles.setSelectedValue(cFile, true);
		}
	}
	// public void addData(Set<String> data) {
	// 	System.out.println(data);
	// 	listFiles.setListData(data.toArray());
	// 	this.validate();
	// 	this.repaint();
	// }
	public void addData(ArrayList<String> data) {
		//System.out.println(data);
		listFiles.setListData(data.toArray());
		this.validate();
		this.repaint();
	}
	public void addChars(String str) {
		setCode(getCode() + str);
	}

	public void setActionListeners(ActionListener alNew, ActionListener alOpen, 
		ActionListener alSave, ActionListener alSaveAs, ListSelectionListener alSelect,
		ActionListener alClose, ActionListener alExit, KeyListener alKey, ActionListener alJDK) {
		newButton.addActionListener(alNew);
		openButton.addActionListener(alOpen);
		saveButton.addActionListener(alSave);
		saveAsButton.addActionListener(alSaveAs);
		closeButton.addActionListener(alClose);
		exitButton.addActionListener(alExit);
		listFiles.addListSelectionListener(alSelect);
		textCode.addKeyListener(alKey);
		chooseJDKFolder.addActionListener(alJDK);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menuFile = new JMenu();
		newButton = new JMenuItem();
		openButton = new JMenuItem();
		saveButton = new JMenuItem();
		saveAsButton = new JMenuItem();
		closeButton = new JMenuItem();
		exitButton = new JMenuItem();
		menuEdit = new JMenu();
		menuView = new JMenu();
		menuHelp = new JMenu();
		chooseJDKFolder = new JMenuItem();
		scrollPane1 = new JScrollPane();
		listFiles = new JList();
		scrollPane2 = new JScrollPane();
		textCode = new JTextArea();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("JEditor");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menuFile ========
			{
				menuFile.setText("File");

				//---- newButton ----
				newButton.setText("New");
				menuFile.add(newButton);

				//---- openButton ----
				openButton.setText("Open");
				menuFile.add(openButton);

				//---- saveButton ----
				saveButton.setText("Save");
				menuFile.add(saveButton);

				//---- saveAsButton ----
				saveAsButton.setText("Save as");
				menuFile.add(saveAsButton);

				//---- closeButton ----
				closeButton.setText("Close");
				menuFile.add(closeButton);

				//---- exitButton ----
				exitButton.setText("Exit");
				menuFile.add(exitButton);
			}
			menuBar1.add(menuFile);

			//======== menuEdit ========
			{
				menuEdit.setText("Edit");
			}
			menuBar1.add(menuEdit);

			//======== menuView ========
			{
				menuView.setText("View");
			}
			menuBar1.add(menuView);

			//======== menuHelp ========
			{
				menuHelp.setText("Help");

				//---- chooseJDKFolder ----
				chooseJDKFolder.setText("Choose JDK");
				menuHelp.add(chooseJDKFolder);
			}
			menuBar1.add(menuHelp);
		}
		setJMenuBar(menuBar1);

		//======== scrollPane1 ========
		{
			scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			scrollPane1.setPreferredSize(new Dimension(95, 34));
			scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

			//---- listFiles ----
			listFiles.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listFiles.setVisibleRowCount(1);
			listFiles.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			scrollPane1.setViewportView(listFiles);
		}
		contentPane.add(scrollPane1, BorderLayout.PAGE_START);

		//======== scrollPane2 ========
		{

			//---- textCode ----
			textCode.setFont(new Font("Miriam Mono CLM", Font.BOLD, 12));
			textCode.setTabSize(4);
			scrollPane2.setViewportView(textCode);
		}
		contentPane.add(scrollPane2, BorderLayout.CENTER);
		setSize(580, 565);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu menuFile;
	private JMenuItem newButton;
	private JMenuItem openButton;
	private JMenuItem saveButton;
	private JMenuItem saveAsButton;
	private JMenuItem closeButton;
	private JMenuItem exitButton;
	private JMenu menuEdit;
	private JMenu menuView;
	private JMenu menuHelp;
	private JMenuItem chooseJDKFolder;
	private JScrollPane scrollPane1;
	private JList listFiles;
	private JScrollPane scrollPane2;
	private JTextArea textCode;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
