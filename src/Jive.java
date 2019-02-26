import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;

public class Jive extends JFrame implements ActionListener
{
	
	public static Map<Component, Jive> map = new HashMap<Component, Jive>();  

	public static JTabbedPane tab;
	public static int counttab = 1;
	public JTextArea input;
	public JTextArea output;
	public JTextPane tp;
	public JMenuBar menuBar;
	public JMenu fileMenu, editMenu, runMenu;
	public JScrollPane scpane;
	public JMenuItem exitMenuItem, saveMenuItem, openMenuItem, newMenuItem;
	public JMenuItem runBasicJava, runBasicJavaWithArgs, runOneClientAndServer, runTwoClientsAndServer;
	public JToolBar toolBar;
	public JFileChooser chooser;
	public StyledDocument document;
	public StyleContext context;

	public Jive()
	{
		
	}

	public Jive(String title)
	{
		super(title);

		setSize(StyleManager.width / 2, StyleManager.height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = getContentPane();
		pane.setLayout(new BorderLayout());

		Interface.landingInterface(this, pane);
		setVisible(true);

		Jive obj = new Jive();
		obj.newTab("New File " + counttab);
	}
	

	public void newTab(String TabName) {
		this.context = new StyleContext();
		this.document = new DefaultStyledDocument(this.context);
		this.tp = new JTextPane(this.document);	
		this.scpane = new JScrollPane(this.tp);

		tab.addTab(TabName, this.scpane);
		tab.setSelectedIndex(tab.getTabCount() - 1);

		counttab++;
		map.put(tab.getSelectedComponent(), this);
		
		Font f = new Font("Arial", Font.PLAIN, StyleManager.width/100);
		StyleManager.setFont(this.scpane, f);
	}


	public void actionPerformed(ActionEvent e)
	{
		JMenuItem choice = (JMenuItem) e.getSource();

		if (choice == newMenuItem)
		{
			Jive objNew = new Jive();
			objNew.newTab("New File " + counttab);
		}
		else if (choice == saveMenuItem)
		{	
			String userDir = System.getProperty("user.home");
			chooser = new JFileChooser(userDir  + "/Desktop"); 

			StyleManager.setFileChooserFont(chooser.getComponents());
			chooser.setAcceptAllFileFilterUsed(true);
			
			if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
			{ 
				String name = "" + chooser.getSelectedFile(); 
				
			  System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
				System.out.println("getSelectedFile() : " + name);
				
			  tab.setTitleAt(tab.getSelectedIndex(), chooser.getSelectedFile().getName());
				Jive objct = map.get(tab.getSelectedComponent());
				
			  try {
				  FileWriter fw = new FileWriter(name);
				  objct.tp.write(fw); 
				}
			  catch(Exception ex)
			  {
			  	ex.printStackTrace();
			  }
			}
			else {
			  System.out.println("No Selection ");
			}
		}
		else if (choice == openMenuItem)
		{
			String userDir = System.getProperty("user.home");
			chooser = new JFileChooser(userDir  + "/Desktop"); 

			StyleManager.setFileChooserFont(chooser.getComponents());
			chooser.setAcceptAllFileFilterUsed(true);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) 
			{ 
			  try {
				  Jive objLoad = new Jive();
					objLoad.newTab("" + chooser.getSelectedFile().getName());
					
				  Scanner scan = new Scanner(new FileReader(chooser.getSelectedFile().getPath()));
				  while(scan.hasNext())
				  {
						try {
							objLoad.document.insertString(objLoad.document.getLength(), "" + scan.nextLine() + "\n", null);
						} 
						catch(BadLocationException exc) {
							exc.printStackTrace();
						}
				  }
				}
			  catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else {
			  System.out.println("No Selection ");
			}
		}
		else if (choice == exitMenuItem) {
			System.exit(0);
		}
		else if (choice == runBasicJava)
		{	
			Interface.basicJava(tab, runBasicJava);
		}
		else if (choice == runBasicJavaWithArgs)
		{			
			Interface.basicJavaWithArgs(tab, runBasicJavaWithArgs);
		}
		else if (choice == runOneClientAndServer)
		{
			Interface.OneClientAndServer(tab, runOneClientAndServer);
		}
		else if (choice == runTwoClientsAndServer)
		{
			Interface.twoClientsAndServer(tab, runTwoClientsAndServer);		
		}
	}
}