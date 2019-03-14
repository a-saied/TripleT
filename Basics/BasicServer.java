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
    long runtime;
    long avgRuntime;
    int totalClients;


    public static void main(String[] args){

		BasicServer bs = new BasicServer(Integer.parseInt(args[0]));
		bs.run();
    }

    public BasicServer(int port){
    	Easy = new ArrayList<String>();
    	Medium = new ArrayList<String>();
    	Hard = new ArrayList<String>();
	    x = true;
	    runtime = 0;
		avgRuntime = 0;
		totalClients = 0;
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
			totalClients += 1;
			long startTime = System.nanoTime();
		    try {
				System.out.println("\nWaiting for client on port " + 
						   serv.getLocalPort() + "...");
				System.out.println("Waiting at INET address" + serv.getInetAddress() + ".\n");
				Socket server = serv.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
		            
				System.out.println(in.readUTF());

				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("You are now conencted to the lobby at " + server.getLocalSocketAddress());

				String address = server.getRemoteSocketAddress().toString();
				address = address.split(":")[0];
				address = address.split("/")[1];

				String diff = in.readUTF();
				System.out.println("Player entered difficuly: " + diff);
				switch(diff) {
					case "Easy":
						if(Easy.isEmpty()) {
							System.out.println("Asking player to host...");
							out.writeUTF("SERV\n");
							Easy.add(address);
						}
						else{
							System.out.println("Asking player to become client...");
							out.writeUTF("CLI " + Easy.remove(Easy.size()-1) + "\n");
						}
						break;
					case "Medium":
						if(Medium.isEmpty()) {
							System.out.println("Asking player to host...");
							out.writeUTF("SERV\n");
							Medium.add(address);
						}
						else{
							System.out.println("Asking player to become client...");
							out.writeUTF("CLI " + Medium.remove(Medium.size()-1) + "\n");
						}
						break;
					case "Hard":
						if(Hard.isEmpty()) {
							System.out.println("Asking player to host...");
							out.writeUTF("SERV\n");
							Hard.add(address);
						}
						else{
							System.out.println("Asking player to become client...");
							out.writeUTF("CLI " + Hard.remove(Hard.size()-1) + "\n");
						}
						break;
					default:
						System.out.println("Player entered invalid difficulty.");
						out.writeUTF("Invalid difficulty entered. Disconnecting.");
						server.close();
				}

		            
		    } catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
		    } catch (IOException e) {
				e.printStackTrace();
				break;
		    }

		    long endTime = System.nanoTime();
		    runtime += endTime - startTime;

		    avgRuntime = (long) runtime/totalClients;

		}
    }

}
