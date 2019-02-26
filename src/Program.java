import java.io.*;
import javax.swing.*;
import javax.swing.JOptionPane;

class Program {

    public static String fileName;

    public static Process compile(JMenuItem item) {

        String userDir = System.getProperty("user.home");
        JFileChooser chooser = new JFileChooser(userDir  + "/Desktop");

        StyleManager.setFileChooserFont(chooser.getComponents());
        chooser.setAcceptAllFileFilterUsed(true);
        
        if(chooser.showOpenDialog(item) == JFileChooser.APPROVE_OPTION) 
        { 
            try {
                String path = chooser.getSelectedFile().getPath();

                Runtime run = Runtime.getRuntime();
                Process process = run.exec("javac " + path);

                return process;
            }
            catch(Exception ec)
            {
                ec.printStackTrace();
            }
        }
        else {
            System.out.println("No Selection ");
        }
        return null;
    }

    public static Process run(JMenuItem item, boolean args) {
        String arguments = "";
        String userDir = System.getProperty("user.home");
        
        JFileChooser chooser = new JFileChooser(userDir  + "/Desktop"); 
        StyleManager.setFileChooserFont(chooser.getComponents());
        chooser.setAcceptAllFileFilterUsed(true);
        
        if(chooser.showOpenDialog(item) == JFileChooser.APPROVE_OPTION) 
        { 
            try {
                String name = "" + chooser.getSelectedFile().getName();
                String path = "" + chooser.getSelectedFile();
                File directory = new File("" + path.substring(0, path.lastIndexOf("\\")) + "\\");
                
                fileName = path.substring(0, path.indexOf(".class")) + ".java";

                if(args) {
                    arguments = " " + JOptionPane.showInputDialog(null,"Add command line arguments separated by space");
                }

                Runtime run = Runtime.getRuntime();
                Process process = run.exec("java " + name.substring(0, name.indexOf(".class")) + arguments, null, directory);

                return process;
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
        }
        else {
            System.out.println("No Selection ");
        }
        return null;
    }

    public static String getFileName() {
        return fileName;
    }
}