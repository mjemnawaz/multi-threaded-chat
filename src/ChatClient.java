import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Thread{
	private BufferedReader br;
	private PrintWriter pw;
	public ChatClient(String hostname, int port) {
		Socket s = null;
		Scanner scan = new Scanner(System.in);
		try {
			System.out.println("Trying to connect to " + hostname + ":" + port + ".");
			s = new Socket(hostname, port);
			System.out.println("Connected to " + hostname + ":" + port + ".");
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream());
			this.start();
			while(true) {
				String line = scan.nextLine();
				pw.println("Somebody: " + line);
				pw.flush();
			}
		} catch(IOException ioe) {
			System.out.println("ioe : " + ioe.getMessage());
		} finally {
			try {
				if (s != null) s.close();
				if (scan != null) scan.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing socket : " + ioe.getMessage());
			} 
			
		}
	}
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				System.out.println(line);
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading from the server " + ioe.getMessage());
		}
	}
	public static void main(String [] args) {
		ChatClient cc = new ChatClient("localhost",6789);
	}
}
