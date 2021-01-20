import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatRoom {
	private Vector<ServerThread> serverThreads;
	public ChatRoom(int port) {
		ServerSocket ss = null;
		try {
			System.out.println("Trying to bind to port " + port + ".");
			ss= new ServerSocket(port);
			System.out.println("Bound to port " + port + ".");
			serverThreads = new Vector<ServerThread>();
			while(true) { //infinite loop
				Socket s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress() + ".");
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
			}
		} catch(IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		} finally {
			try {
				if(ss!=null) ss.close();
			} catch(IOException ioe) {
				System.out.println("ioe closing stuff: " + ioe.getMessage());
			}
		}
	}
	public void broadcast(String message, ServerThread currentST) {
		if(message != null) {
			System.out.println(message);
			for (ServerThread st: serverThreads) {
				if(st!=currentST) {
					st.sendMessage(message);
				}
			}
		}
	}
	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom(6789);
	}
	
}
