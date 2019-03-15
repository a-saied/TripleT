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
		    acceptPlayers.start();
		}
		catch(Exception e){
		    e.printStackTrace();
		}

		Scanner scn = new Scanner(System.in);
	    String s = scn.next();
		while(true){
			if ("q".equals(s)) {
				try{
					serv.close();
				} catch (IOException e) {
					System.err.println("Could not close lobby.");
		    		System.exit(1);
				} finally {
		            break;
				}
			}
			s = scn.next();
		}
    }

    private Thread acceptPlayers = new Thread() {
    	public void run(){
	    	Writer writer = null;
	    	try{
	    		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("runtimes.txt"), "utf-8"));
	    		while(true){
	    			System.out.println("\nWaiting for client on port " + 
							   serv.getLocalPort() + "...");
					System.out.println("Waiting at INET address" + serv.getInetAddress() + ".\n");

					totalClients += 1;
					Socket server = serv.accept();
					long startTime = System.nanoTime();

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

					long endTime = System.nanoTime();
				    runtime += endTime - startTime;
				    avgRuntime = (long) runtime/totalClients;

				    System.out.println("Clients handled: " + totalClients);
				    System.out.println("This runtime (ns): " + (endTime - startTime));
				    System.out.println("Average runtime (ns): " + avgRuntime);

				    writer.write(Long.toString(endTime - startTime) + "\n");
				}
			} catch (SocketException e) {
					if (serv.isClosed()) {
						System.out.println("Lobby shut down, connection closed.");
					}
			} catch (IOException e) {
					e.printStackTrace();
			} finally {
			   		try {
			   			writer.close();
			   			System.out.println("Runtimes have been written to runtimes.txt");
			   		} catch (Exception ex) {/*ignore*/}
			}

	    }
    };

}
