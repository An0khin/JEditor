package com.home;
import java.awt.*;
import javax.swing.*;

public class JEditorAutoFill extends JPopupMenu {
	public JEditorAutoFill() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		listOfFillable = new JList();

		//======== this ========
		setName("FrameAutoFill");

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(listOfFillable);
		}
		add(scrollPane1);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JList listOfFillable;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
