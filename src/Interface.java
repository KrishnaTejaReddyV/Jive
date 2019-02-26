import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Interface {

	private static BufferedReader runInputReader;
	private static BufferedReader runErrorReader;
	private static BufferedWriter runOutputWriter;
	 
	private static BufferedReader runClientInputReader;
	private static BufferedReader runClientErrorReader;
	private static BufferedWriter runClientOutputWriter;
	 
	private static BufferedReader runClient2InputReader;
	private static BufferedReader runClient2ErrorReader;
	private static BufferedWriter runClient2OutputWriter;
	
	private static BufferedReader compileInputReader;
	private static BufferedReader compileErrorReader;
	private static BufferedWriter compileOutputWriter;
	 
	private static BufferedReader compileClientInputReader;
	private static BufferedReader compileClientErrorReader;
	private static BufferedWriter compileClientOutputWriter;
	 
	private static BufferedReader compileClient2InputReader;
	private static BufferedReader compileClient2ErrorReader;
    private static BufferedWriter compileClient2OutputWriter;
    
    public static void landingInterface(Jive obj, Container pane) {
		
		obj.menuBar = new JMenuBar(); 
		obj.fileMenu = new JMenu("File"); 
		obj.runMenu = new JMenu("Run"); 
		
		obj.exitMenuItem = new JMenuItem("Exit");
		obj.saveMenuItem = new JMenuItem("Save"); 
		obj.openMenuItem = new JMenuItem("Open"); 
		obj.newMenuItem = new JMenuItem("New File"); 
		
		obj.runBasicJava = new JMenuItem("Basic Java Codes");
		obj.runBasicJavaWithArgs = new JMenuItem("Java codes with Command-line arguments");
		obj.runOneClientAndServer = new JMenuItem("Client-Server with 1 Client");
		obj.runTwoClientsAndServer = new JMenuItem("Client-Server with 2 Clients");
		
		Jive.tab = new JTabbedPane();
		obj.toolBar = new JToolBar();

		pane.add(Jive.tab, BorderLayout.CENTER);
		pane.add(obj.toolBar, BorderLayout.SOUTH);
		
		obj.setJMenuBar(obj.menuBar);
		obj.menuBar.add(obj.fileMenu);
		obj.menuBar.add(obj.runMenu);

		obj.fileMenu.add(obj.newMenuItem);
		obj.fileMenu.add(obj.openMenuItem);
		obj.fileMenu.add(obj.saveMenuItem);
		obj.fileMenu.add(obj.exitMenuItem);

		obj.runMenu.add(obj.runBasicJava);
		obj.runMenu.add(obj.runBasicJavaWithArgs);
		obj.runMenu.add(obj.runOneClientAndServer);        
		obj.runMenu.add(obj.runTwoClientsAndServer);


		obj.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		obj.newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		obj.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		obj.runBasicJava.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		obj.runBasicJavaWithArgs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		obj.runOneClientAndServer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		obj.runTwoClientsAndServer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));

		obj.saveMenuItem.addActionListener(obj);
		obj.newMenuItem.addActionListener(obj);
		obj.openMenuItem.addActionListener(obj);
		obj.exitMenuItem.addActionListener(obj);
		obj.runBasicJava.addActionListener(obj);
		obj.runBasicJavaWithArgs.addActionListener(obj);
		obj.runOneClientAndServer.addActionListener(obj);
		obj.runTwoClientsAndServer.addActionListener(obj);

		Font f = new Font("Arial", Font.PLAIN, StyleManager.width / 100);
		StyleManager.setFont(pane, f);
		StyleManager.setFont(obj.menuBar, f);
		StyleManager.setFont(obj.saveMenuItem, f);
		StyleManager.setFont(obj.newMenuItem, f);
		StyleManager.setFont(obj.openMenuItem, f);
		StyleManager.setFont(obj.exitMenuItem, f);
		StyleManager.setFont(obj.runBasicJava, f);
		StyleManager.setFont(obj.runBasicJavaWithArgs, f);
		StyleManager.setFont(obj.runOneClientAndServer, f);
		StyleManager.setFont(obj.runTwoClientsAndServer, f);

    }
    
    public static void basicJava(JTabbedPane tab, JMenuItem item) {
        JButton compile = new JButton("Upload .java file to compile");
        JButton run = new JButton("Upload .class file to run");
        JButton submit = new JButton("Submit Inputs");
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPanel.add(compile);
        btnPanel.add(run);
        
        
        JLabel inputl = new JLabel("Input Field");
        JTextArea input = new JTextArea();
        JScrollPane inputsp = new JScrollPane(input);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputl, BorderLayout.PAGE_START);
        inputPanel.add(inputsp);
        
        JLabel outputl = new JLabel("Output Field");
        JTextArea output = new JTextArea();
        JScrollPane outputsp = new JScrollPane(output);
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputl, BorderLayout.PAGE_START);
        outputPanel.add(outputsp);
            
        JPanel btmPanel = new JPanel(new BorderLayout());
        btmPanel.setLayout(new BoxLayout(btmPanel, BoxLayout.LINE_AXIS));
        btmPanel.add(inputPanel);
        btmPanel.add(submit);
        btmPanel.add(outputPanel);
        
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(btnPanel, BorderLayout.PAGE_START);
        panel.add(btmPanel);
        
        Font f = new Font("Arial", Font.PLAIN, StyleManager.width/100);
        StyleManager.setFont(panel, f);
    
        tab.addTab("Run Basic Java Files", panel);
        tab.setSelectedIndex(tab.getTabCount()-1);
        
        
        compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileInputReader, compileErrorReader, output, null);
            }
        });
        
        run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runInputReader, runErrorReader, output, fileName);
            }
        });
        
        submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            ProcessManager.write(runInputReader, runErrorReader, runOutputWriter, input, output);
            }
        });
    }

    public static void basicJavaWithArgs(JTabbedPane tab, JMenuItem item) {
        JButton compile = new JButton("Upload .java file to compile");
        JButton run = new JButton("Upload .class file to run");
        JButton submit = new JButton("Submit Inputs");
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPanel.add(compile);
        btnPanel.add(run);
        
        
        JLabel inputl = new JLabel("Input Field");
        JTextArea input = new JTextArea();
        JScrollPane inputsp = new JScrollPane(input);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputl, BorderLayout.PAGE_START);
        inputPanel.add(inputsp);
        
        JLabel outputl = new JLabel("Output Field");
        JTextArea output = new JTextArea();
        JScrollPane outputsp = new JScrollPane(output);
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputl, BorderLayout.PAGE_START);
        outputPanel.add(outputsp);
            
        JPanel btmPanel = new JPanel(new BorderLayout());
        btmPanel.setLayout(new BoxLayout(btmPanel, BoxLayout.LINE_AXIS));
        btmPanel.add(inputPanel);
        btmPanel.add(submit);
        btmPanel.add(outputPanel);
        
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(btnPanel, BorderLayout.PAGE_START);
        panel.add(btmPanel);
        
        Font f = new Font("Arial", Font.PLAIN, StyleManager.width/100);
        StyleManager.setFont(panel, f);
    
        tab.addTab("Java codes with Command-line arguments", panel);
        tab.setSelectedIndex(tab.getTabCount()-1);
            
            
        compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileInputReader, compileErrorReader, output, null);
            }
        });
        
        run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, true);
                String fileName = Program.getFileName();

                runInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runInputReader, runErrorReader, output, fileName);
            }
        });
        
        submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            ProcessManager.write(runInputReader, runErrorReader, runOutputWriter, input, output);
            }
        });
    }

    public static void OneClientAndServer(JTabbedPane tab, JMenuItem item) {
        JButton compile = new JButton("Upload .java file of server to compile");
        JButton run = new JButton("Upload .class file of server to run");
        JButton submit = new JButton("Submit Server Inputs");
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPanel.add(compile);
        btnPanel.add(run);
        
        
        JButton c_compile = new JButton("Upload .java file of client to compile");
        JButton c_run = new JButton("Upload .class file of client to run");
        JButton c_submit = new JButton("Submit Client Inputs");
        
        JPanel c_btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        c_btnPanel.add(c_compile);
        c_btnPanel.add(c_run);
        
        
        JLabel inputl = new JLabel("Server Input Field");
        JTextArea input = new JTextArea();
        JScrollPane inputsp = new JScrollPane(input);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputl, BorderLayout.PAGE_START);
        inputPanel.add(inputsp);
        
        JLabel outputl = new JLabel("Server Output Field");
        JTextArea output = new JTextArea();
        JScrollPane outputsp = new JScrollPane(output);
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputl, BorderLayout.PAGE_START);
        outputPanel.add(outputsp);
            
        JPanel btmPanel = new JPanel(new BorderLayout());
        btmPanel.setLayout(new BoxLayout(btmPanel, BoxLayout.LINE_AXIS));
        btmPanel.add(inputPanel);
        btmPanel.add(submit);
        btmPanel.add(outputPanel);
        
        
        JLabel c_inputl = new JLabel("Client Input Field");
        JTextArea c_input = new JTextArea();
        JScrollPane c_inputsp = new JScrollPane(c_input);
        JPanel c_inputPanel = new JPanel(new BorderLayout());
        c_inputPanel.add(c_inputl, BorderLayout.PAGE_START);
        c_inputPanel.add(c_inputsp);
        
        JLabel c_outputl = new JLabel("Client Output Field");
        JTextArea c_output = new JTextArea();
        JScrollPane c_outputsp = new JScrollPane(c_output);
        JPanel c_outputPanel = new JPanel(new BorderLayout());
        c_outputPanel.add(c_outputl, BorderLayout.PAGE_START);
        c_outputPanel.add(c_outputsp);
            
        JPanel c_btmPanel = new JPanel(new BorderLayout());
        c_btmPanel.setLayout(new BoxLayout(c_btmPanel, BoxLayout.LINE_AXIS));
        c_btmPanel.add(c_inputPanel);
        c_btmPanel.add(c_submit);
        c_btmPanel.add(c_outputPanel);
        
        
        JPanel panelup = new JPanel(new BorderLayout());
        panelup.add(btnPanel, BorderLayout.PAGE_START);
        panelup.add(c_btnPanel);
        
        JPanel paneldn = new JPanel(new GridLayout(2, 1));
        paneldn.add(btmPanel);
        paneldn.add(c_btmPanel);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panelup, BorderLayout.PAGE_START);
        panel.add(paneldn);
        
        Font f = new Font("Arial", Font.PLAIN, StyleManager.width/100);
        StyleManager.setFont(panel, f);
    
        tab.addTab("Client-Server with 1 Client", panel);
        tab.setSelectedIndex(tab.getTabCount()-1);
        
        
        compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileInputReader, compileErrorReader, output, null);
            }
        });
        
        run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runInputReader, runErrorReader, output, fileName);
            }
        });
        
        submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProcessManager.write(runInputReader, runErrorReader, runOutputWriter, input, output);
                ProcessManager.read(runInputReader, runErrorReader, output, null);
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, null);
            }
        });
        
        c_compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileClientInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileClientOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileClientErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileClientInputReader, compileClientErrorReader, c_output, null);
            }
        });
        
        c_run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runClientInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runClientOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runClientErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, fileName);
            }
        });
        
        c_submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProcessManager.write(runClientInputReader, runClientErrorReader, runClientOutputWriter, c_input, c_output);
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, null);
                ProcessManager.read(runInputReader, runErrorReader, output, null);
            }
        });
    }
    
    public static void twoClientsAndServer(JTabbedPane tab, JMenuItem item) {
        JButton compile = new JButton("Upload .java file of server to compile");
        JButton run = new JButton("Upload .class file of server to run");
        JButton submit = new JButton("Submit Server Inputs");
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPanel.add(compile);
        btnPanel.add(run);
        
        
        JButton c_compile = new JButton("Upload .java file of first client to compile");
        JButton c_run = new JButton("Upload .class file of first client to run");
        JButton c_submit = new JButton("Submit First Client Inputs");
        
        JPanel c_btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        c_btnPanel.add(c_compile);
        c_btnPanel.add(c_run);
        
        
        JButton c2_compile = new JButton("Upload .java file of second client to compile");
        JButton c2_run = new JButton("Upload .class file of second client to run");
        JButton c2_submit = new JButton("Submit Second Client Inputs");
        
        JPanel c2_btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        c2_btnPanel.add(c2_compile);
        c2_btnPanel.add(c2_run);
        
        
        JLabel inputl = new JLabel("Server Input Field");
        JTextArea input = new JTextArea();
        JScrollPane inputsp = new JScrollPane(input);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputl, BorderLayout.PAGE_START);
        inputPanel.add(inputsp);
        
        JLabel outputl = new JLabel("Server Output Field");
        JTextArea output = new JTextArea();
        JScrollPane outputsp = new JScrollPane(output);
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(outputl, BorderLayout.PAGE_START);
        outputPanel.add(outputsp);
            
        JPanel btmPanel = new JPanel(new BorderLayout());
        btmPanel.setLayout(new BoxLayout(btmPanel, BoxLayout.LINE_AXIS));
        btmPanel.add(inputPanel);
        btmPanel.add(submit);
        btmPanel.add(outputPanel);
        
        
        JLabel c_inputl = new JLabel("First Client Input Field");
        JTextArea c_input = new JTextArea();
        JScrollPane c_inputsp = new JScrollPane(c_input);
        JPanel c_inputPanel = new JPanel(new BorderLayout());
        c_inputPanel.add(c_inputl, BorderLayout.PAGE_START);
        c_inputPanel.add(c_inputsp);
        
        JLabel c_outputl = new JLabel("First Client Output Field");
        JTextArea c_output = new JTextArea();
        JScrollPane c_outputsp = new JScrollPane(c_output);
        JPanel c_outputPanel = new JPanel(new BorderLayout());
        c_outputPanel.add(c_outputl, BorderLayout.PAGE_START);
        c_outputPanel.add(c_outputsp);
            
        JPanel c_btmPanel = new JPanel(new BorderLayout());
        c_btmPanel.setLayout(new BoxLayout(c_btmPanel, BoxLayout.LINE_AXIS));
        c_btmPanel.add(c_inputPanel);
        c_btmPanel.add(c_submit);
        c_btmPanel.add(c_outputPanel);
        
        
        JLabel c2_inputl = new JLabel("Second Client Input Field");
        JTextArea c2_input = new JTextArea();
        JScrollPane c2_inputsp = new JScrollPane(c2_input);
        JPanel c2_inputPanel = new JPanel(new BorderLayout());
        c2_inputPanel.add(c2_inputl, BorderLayout.PAGE_START);
        c2_inputPanel.add(c2_inputsp);
        
        JLabel c2_outputl = new JLabel("Second Client Output Field");
        JTextArea c2_output = new JTextArea();
        JScrollPane c2_outputsp = new JScrollPane(c2_output);
        JPanel c2_outputPanel = new JPanel(new BorderLayout());
        c2_outputPanel.add(c2_outputl, BorderLayout.PAGE_START);
        c2_outputPanel.add(c2_outputsp);
            
        JPanel c2_btmPanel = new JPanel(new BorderLayout());
        c2_btmPanel.setLayout(new BoxLayout(c2_btmPanel, BoxLayout.LINE_AXIS));
        c2_btmPanel.add(c2_inputPanel);
        c2_btmPanel.add(c2_submit);
        c2_btmPanel.add(c2_outputPanel);
        
        
        JPanel panelup = new JPanel(new GridLayout(3, 1));
        panelup.add(btnPanel);
        panelup.add(c_btnPanel);
        panelup.add(c2_btnPanel);
        
        JPanel paneldn = new JPanel(new GridLayout(3, 1));
        paneldn.add(btmPanel);
        paneldn.add(c_btmPanel);
        paneldn.add(c2_btmPanel);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panelup, BorderLayout.PAGE_START);
        panel.add(paneldn);
        
        Font f = new Font("Arial", Font.PLAIN, StyleManager.width/100);
        StyleManager.setFont(panel, f);
    
        tab.addTab("Client-Server with 2 Client", panel);
        tab.setSelectedIndex(tab.getTabCount()-1);
        
        
        compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileInputReader, compileErrorReader, output, null);
            }
        });
        
        run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runInputReader, runErrorReader, output, fileName);
            }
        });
        
        submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProcessManager.write(runInputReader, runErrorReader, runOutputWriter, input, output);
                ProcessManager.read(runInputReader, runErrorReader, output, null);
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, null);
                ProcessManager.read(runClient2InputReader, runClient2ErrorReader, c2_output, null);
            }
        });
        
        c_compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileClientInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileClientOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileClientErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileClientInputReader, compileClientErrorReader, c_output, null);
            }
        });
        
        c_run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runClientInputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runClientOutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runClientErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, fileName);
            }
        });
        
        c_submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProcessManager.write(runClientInputReader, runClientErrorReader, runClientOutputWriter, c_input, c_output);
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, null);
                ProcessManager.read(runInputReader, runErrorReader, output, null);
                ProcessManager.read(runClient2InputReader, runClient2ErrorReader, c2_output, null);
            }
        });
        
        c2_compile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.compile(item);
            
                compileClient2InputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                compileClient2OutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                compileClient2ErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(compileClient2InputReader, compileClient2ErrorReader, c2_output, null);
            }
        });
        
        c2_run.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Process process = Program.run(item, false);
                String fileName = Program.getFileName();

                runClient2InputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                runClient2OutputWriter =  new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                runClient2ErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                
                ProcessManager.read(runClient2InputReader, runClient2ErrorReader, c2_output, fileName);
            }
        });
        
        c2_submit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ProcessManager.write(runClient2InputReader, runClient2ErrorReader, runClient2OutputWriter, c2_input, c2_output);
                ProcessManager.read(runClient2InputReader, runClient2ErrorReader, c2_output, null);
                ProcessManager.read(runClientInputReader, runClientErrorReader, c_output, null);
                ProcessManager.read(runInputReader, runErrorReader, output, null);
            }
        });
    }
}