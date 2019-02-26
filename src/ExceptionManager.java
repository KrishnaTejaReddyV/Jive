import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.JOptionPane;
import com.sun.speech.freetts.Voice; 
import com.sun.speech.freetts.VoiceManager; 

class ExceptionManager {
    
    private static int lineno = 0;
    private static boolean exceptionOccured = false;
    private static String exception = null;
    private static String message = null;
    private static String exceptionText;

    private static Voice voice;
    private static VoiceManager voiceManager ;
    private static final String VOICENAME = "kevin16";

    private static int error_start = -1;
    private static int error_end = -1;

    public static void parseException(String msg[]) {
        int lineCount = 0;
        int startIndex = 0;
        int endIndex = 0; 
        int lineIndex = 0; 
        int length = 0;

        for(String s: msg) {
            exceptionOccured = true;
            if(lineCount == 0)
            {						
                startIndex = s.indexOf("lang.") + 5;
                endIndex = s.indexOf(":");
                length = s.length();

                if(endIndex != -1)
                {
                    exception = s.substring(startIndex, endIndex);
                    message = s.substring(endIndex + 2, length);
                }
                else{
                    exception = s.substring(startIndex, length);
                }				
                lineCount++;
            }
            else if(lineCount == 1)
            {
                lineIndex = s.lastIndexOf(":") + 1;
                if(!(lineIndex == 0))
                {
                    length = s.length();
                    lineno = Integer.parseInt(s.substring(lineIndex, length - 2));
                    lineCount++;
                }
            }
        }
    }

    public static void showException(Jive exceptionTab, String fileName) {
        if(exceptionOccured)
        {
            generateExceptionMessage();

            styleMessage(exceptionTab, fileName);
            
            voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice(VOICENAME);
            voice.allocate();
            voice.setRate(160);
            voice.setPitch(100);
                
            exceptionTab.tp.addMouseListener(new MouseAdapter()
            {
                public void mouseClicked(MouseEvent me)
                {
                    int x = me.getX();
                    int y = me.getY();

                    System.out.println("X : " + x);
                    System.out.println("Y : " + y);

                    int startOffset = exceptionTab.tp.viewToModel(new Point(x, y));
                    System.out.println("Start Offset : " + startOffset);

                    if ((startOffset >= error_start) && (startOffset < error_end))
                    {
                        voice.speak(exceptionText);
                        JLabel label = new JLabel(exceptionText);
                        label.setFont(new Font("Arial", Font.BOLD, StyleManager.width/100));
                        JOptionPane.showMessageDialog(null, label);
                    }
                }
            });
        }
    }

    public static void generateExceptionMessage() {
        if(exception.equals("ArrayIndexOutOfBoundsException"))
        {
            exceptionText = "An array index out of bounds exception has occured in your program."
                + " It is present on line number " 
                + lineno + ". "
                + "This has occured as you are trying to access " 
                + message 
                + " index of the array which does not exist. Rectify this to continue.";
        }
        else if(exception.equals("ArithmeticException"))
        {
            exceptionText = "An Arithmetic exception has occured in your program."
                + " It is present on line number " 
                + lineno + ". "
                + "This has occured as you are trying to perform an unauthorized operation of " 
                + message 
                + ". Rectify this to continue.";
        }
        else if(exception.equals("NumberFormatException"))
        {
            exceptionText = "An number format exception has occured in your program."
                + " It is present on line number " 
                + lineno + ". "
                + "This has occured as you are trying convert an invalid string to integer as given in the message (" 
                + message + "). Rectify this to continue.";
        }
        else
        {
            exceptionText = "A " + exception + " has occurred in your program."
                + " It is present on line number " 
                + lineno + ". "
                + "This has occured with the message (" + message + "). Rectify this to continue.";
        }
    }

    public static void styleMessage(Jive exceptionTab, String fileName) {

        Style style = exceptionTab.context.getStyle(StyleContext.DEFAULT_STYLE);

        StyleConstants.setBackground(style, Color.yellow);
        StyleConstants.setForeground(style, Color.black);
        
        try {
            Scanner scan = new Scanner(new FileReader(fileName));
            int counter = 1;
            
            while(scan.hasNext())
            {
                if(counter == lineno)
                {
                    try {
                        error_start = exceptionTab.document.getLength();
                        exceptionTab.document.insertString(error_start, "" + scan.nextLine() + "\n", style);
                        error_end = exceptionTab.document.getLength();
                    } 
                    catch(BadLocationException exc) {
                        exc.printStackTrace();
                    }
                }
                else {
                    try {
                        exceptionTab.document.insertString(exceptionTab.document.getLength(), "" + scan.nextLine() + "\n", null);
                    } 
                    catch(BadLocationException exc) {
                        exc.printStackTrace();
                    }
                }
                counter++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}