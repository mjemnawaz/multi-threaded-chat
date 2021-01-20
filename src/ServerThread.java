import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	private ChatRoom cr;
	public ServerThread(Socket s, ChatRoom cr) {
		this.cr = cr;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	public void sendMessage(String line) {
		pw.println(line);
		pw.flush();
	}
	public void run() {
		try {
			 while(true) {
				 String line = br.readLine(); // throws an IOException when client disconnects, breaks out of infinite loop
				 System.out.println("To broadcast: " + line);
				 cr.broadcast(line, this);
			 }
		} catch (IOException ioe) {
			System.out.println("ioe reading fom br: " + ioe.getMessage());
		}
	}
}
