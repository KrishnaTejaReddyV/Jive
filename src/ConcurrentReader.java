import java.io.*;
import java.util.concurrent.*;

public class ConcurrentReader {

	public volatile boolean closed = false;
	public BlockingQueue<String> lines = new LinkedBlockingQueue<String>();
	public Thread backgroundReaderThread;
    public int count = 0;

    public ConcurrentReader(BufferedReader bufferedReader) {
        backgroundReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.interrupted()) {
                        int a = bufferedReader.read();
                        if (a  ==  -1) {
                            break;
                        }
                        char c = (char) a;
                        String line = String.valueOf(c);
                        lines.offer(line);
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                } finally {
                    closed = true;
                }
            }
        });

        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }
	
	public String getValue(boolean synchronize) throws IOException {
        try {
			if(synchronize && (count == 0)) {
                synchronized(this) {
                    this.wait(1000);
                }
			    count++;
			}
            return closed && lines.isEmpty() ? null : lines.poll();
        } catch (Exception e) {
            e.printStackTrace();
			return null;
        }
    }

    public void closeReader() {
        if (backgroundReaderThread != null) {
            backgroundReaderThread.interrupt();
            backgroundReaderThread = null;
			System.out.println("Interrupted");
			count = 0;
        }
    }
}