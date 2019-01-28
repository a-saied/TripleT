import java.io.*;
import java.net.*;

public class BasicClient{
    public static void main(String[] args){
	run(Integer.parseInt(args[0]), "localhost");
    }

    public static void run(int port, String hostname){
	System.out.println("Connecting to " + hostname + "\n");
	try{
	    Socket client = new Socket(hostname, port);


	    System.out.println("Just connected to " + client.getRemoteSocketAddress());
	    OutputStream outToServer = client.getOutputStream();
	    DataOutputStream out = new DataOutputStream(outToServer);
	             
	    out.writeUTF("Hello from " + client.getLocalSocketAddress());
	    InputStream inFromServer = client.getInputStream();
	    DataInputStream in = new DataInputStream(inFromServer);
	             
	    System.out.println("Server says " + in.readUTF());
	    client.close();
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }
}