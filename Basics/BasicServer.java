import java.io.*;
import java.net.*;
import java.util.*;

public class BasicServer {
    //class variables
    private ServerSocket serv;
    boolean x;
    private ArrayList<String> Easy;
    private ArrayList<String> Medium;
    private ArrayList<String> Hard;


    public static void main(String[] args){

		BasicServer bs = new BasicServer(Integer.parseInt(args[0]));
		bs.run();
    }

    public BasicServer(int port){
    	Easy = new ArrayList<String>();
    	Medium = new ArrayList<String>();
    	Hard = new ArrayList<String>();
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
		out.writeUTF("You are now conencted to the lobby at " + server.getLocalSocketAddress()
			     + "\nEnter your desired difficulty (Easy, Medium, Hard):" );

		// InputStream serverInStream = server.getInputStream();
		// Scanner serverIn = new Scanner(serverInStream);

		// while(true){
		// 	if(serverIn.hasNextLine()){
		// 		String diff = serverIn.nextLine();
		// 		System.out.println(diff);
		// 		break;
		// 	}
		// }

		String diff = in.readUTF();

		if(diff.equals("Easy") && Easy.isEmpty()) {
			out.writeUTF("SERV\n");
			Easy.add(server.getRemoteSocketAddress().toString());
		}
		else{
			out.writeUTF("CLI " + Easy.remove(Easy.size()-1));
		}

		// if(x){
		// 	out.writeUTF("SERV\n");
		// 	x = false;
		// }
		// else{
		// 	out.writeUTF("CLI\n");
		// 	x = true;
		// }
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
