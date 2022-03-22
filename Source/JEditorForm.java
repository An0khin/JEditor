import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/*
 * Created by JFormDesigner on Sun Feb 06 17:40:18 YEKT 2022
 */



/**
 * @author get
 */
public class JEditorForm extends JFrame {
	public JEditorForm() {
		initComponents();
	}

	private void openButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void saveButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void saveAsButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void listFilesValueChanged(ListSelectionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		menuFile = new JMenu();
		openButton = new JMenuItem();
		saveButton = new JMenuItem();
		saveAsButton = new JMenuItem();
		menuEdit = new JMenu();
		menuView = new JMenu();
		menuHelp = new JMenu();
		scrollPane1 = new JScrollPane();
		listFiles = new JList();
		scrollPane2 = new JScrollPane();
		textCode = new JTextPane();

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

				//---- openButton ----
				openButton.setText("Open");
				openButton.addActionListener(e -> openButtonActionPerformed(e));
				menuFile.add(openButton);

				//---- saveButton ----
				saveButton.setText("Save");
				saveButton.addActionListener(e -> saveButtonActionPerformed(e));
				menuFile.add(saveButton);

				//---- saveAsButton ----
				saveAsButton.setText("Save as");
				saveAsButton.addActionListener(e -> saveAsButtonActionPerformed(e));
				menuFile.add(saveAsButton);
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
			}
			menuBar1.add(menuHelp);
		}
		setJMenuBar(menuBar1);

		//======== scrollPane1 ========
		{
			scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			scrollPane1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

			//---- listFiles ----
			listFiles.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listFiles.setVisibleRowCount(1);
			listFiles.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			listFiles.addListSelectionListener(e -> listFilesValueChanged(e));
			scrollPane1.setViewportView(listFiles);
		}
		contentPane.add(scrollPane1, BorderLayout.PAGE_START);

		//======== scrollPane2 ========
		{

			//---- textCode ----
			textCode.setFont(new Font("Miriam Mono CLM", Font.PLAIN, 12));
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
	private JMenuItem openButton;
	private JMenuItem saveButton;
	private JMenuItem saveAsButton;
	private JMenu menuEdit;
	private JMenu menuView;
	private JMenu menuHelp;
	private JScrollPane scrollPane1;
	private JList listFiles;
	private JScrollPane scrollPane2;
	private JTextPane textCode;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
