import java.io.*;
import javax.swing.*;
import javax.swing.JOptionPane;

class ProcessManager {

	private	static ConcurrentReader inputReader = null;
	private	static ConcurrentReader errorReader = null;
    private static boolean firstRead = true;
    
    public static void read(BufferedReader in, BufferedReader err, JTextArea output, String fileName) {
        String inputLine = null;
		String errorLine = null;

		try {
			if(!firstRead) {
                inputLine = inputReader.getValue(false);
				if(inputLine != null) {
				   output.append("" + inputLine);
				   System.out.println("" + inputLine);
				}
			}
			else {
				firstRead = false;
			}

			inputReader = new ConcurrentReader(in);
			while((inputLine = inputReader.getValue(true)) != null) {
			   output.append("" + inputLine);
			   System.out.print("" + inputLine);
			}

			StringBuilder full = new StringBuilder();
			errorReader = new ConcurrentReader(err);
			while((errorLine = errorReader.getValue(true)) != null)
			{
				System.out.print(errorLine);
				output.append("" + errorLine);
				full.append(errorLine);
            }

            String[] lines = full.toString().split("\\n");
            
            if(fileName != null){
                ExceptionManager.parseException(lines);
    
                Jive exceptionTab = new Jive();
                exceptionTab.newTab(fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length()));
    
                ExceptionManager.showException(exceptionTab, fileName);
            }
			
			
		} catch (Exception ex) {
				ex.printStackTrace();
		}  
		finally {
			inputReader.closeReader();
			errorReader.closeReader();
		  }
	}
	
	public static void write(BufferedReader in, BufferedReader err, BufferedWriter out, JTextArea input, JTextArea output) {
		try {
			String inp[] = input.getText().split("\\n");
            int i = 0;
            
			try {
				while (inp[i] != null) {
					out.write("" + inp[i]);
					System.out.println("Written-" + inp[i]);
					i++;
				}
			}
			catch(Exception e) {
				System.out.println("Write time out");
			}
			finally {
				out.write("\n");
				out.flush();
				input.setText("");
				read(in, err, output, null);
			}
		}
		catch(IOException e) {
				System.out.println("Data not required");
				JOptionPane.showMessageDialog(null, "Data not required");
		}
	}
}