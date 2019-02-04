import java.io.*;
import java.net.*;

public class BasicServer {
    //class variables
    private ServerSocket serv;
    boolean x;

    public static void main(String[] args){
	BasicServer bs = new BasicServer(Integer.parseInt(args[0]));
	bs.run();
    }

    public BasicServer(int port){
    	x = true;
	try{


	    serv = new ServerSocket(port);
	    //serv.setSoTimeout(10000);
	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }

    public void run(){
	while(true){
	    try {
		System.out.println("Waiting for client on port " + 
				   serv.getLocalPort() + "...\n");
		System.out.println("Waiting at INET address" + serv.getInetAddress() + ".\n");
		Socket server = serv.accept();

		System.out.println("Just connected to " + server.getRemoteSocketAddress());
		DataInputStream in = new DataInputStream(server.getInputStream());
            
		System.out.println(in.readUTF());
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
		out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
			     + "\nGoodbye!\n" );
		if(x){
			out.writeUTF("SERV\n");
			x = false;
		}
		else{
			out.writeUTF("CLI\n");
			x = true;
		}
		//server.close();
            
	    } catch (SocketTimeoutException s) {
		System.out.println("Socket timed out!");
		break;
	    } catch (IOException e) {
		e.printStackTrace();
		break;
	    }
	}
    }

}
