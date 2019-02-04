import java.io.*;
import java.net.*;

public class BasicClient{
    public static void main(String[] args){
    	BasicClient bc = new BasicClient();
    	bc.run(Integer.parseInt(args[0]), "localhost");
    }


    public void run(int port, String hostname){
		System.out.println("Connecting to " + hostname + "\n");
		try{
		    Socket client = new Socket(hostname, port);


		    System.out.println("Just connected to " + client.getRemoteSocketAddress());
		    OutputStream outToServer = client.getOutputStream();
		    DataOutputStream out = new DataOutputStream(outToServer);
		             
		    out.writeUTF("Hello from " + client.getLocalSocketAddress());
		    // out.writeUTF("EASY");
		    InputStream inFromServer = client.getInputStream();
		    // DataInputStream in = new DataInputStream(inFromServer);

		    boolean makeServer = false;
		    boolean makeClient = false;
		    BufferedReader br = new BufferedReader(new InputStreamReader(inFromServer));
		    while(true){
		    	String sample = "";
		    	sample = br.readLine();
		    	// if(sample != ""){
		    		System.out.println(sample);
		    		//System.out.println("split indicator");
		    	// }

		    	if(sample.trim().equals("SERV")){
		    		makeServer = true;
		    		break;
		    	}
		    	if(sample.trim().equals("CLI")){
		    		makeClient = true;
		    		break;
		    	}
		    }

		    if(makeClient) buildClient();
		    if(makeServer) buildServer();
		    //System.out.println("Server says " + in.readUTF());
		    //client.close();
		}
		catch(Exception e){
		    e.printStackTrace();
		}
    }

    public void buildServer(){ 
    	System.out.println("Creating game as host");
    	try{
	    	ServerSocket serv = new ServerSocket(6666);
    		while(true){
	    		Socket server = serv.accept();

				System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
	            
				System.out.println(in.readUTF());

				server.close();
				break;
			}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

    public void buildClient(){
    	System.out.println("Entering game as client");
    	//Thread.sleep(500);
    		try{
			    Socket client = new Socket("localhost", 6666);


			    System.out.println("Just connected to " + client.getRemoteSocketAddress());
			    OutputStream outToServer = client.getOutputStream();
			    DataOutputStream out = new DataOutputStream(outToServer);
			             
			    out.writeUTF("Hello from " + client.getLocalSocketAddress());
			    // out.writeUTF("EASY");
			    InputStream inFromServer = client.getInputStream();
			    // DataInputStream in = new DataInputStream(inFromServer);

			    boolean makeServer = false;
			    boolean makeClient = false;
			    BufferedReader br = new BufferedReader(new InputStreamReader(inFromServer));
			}
			catch(Exception e){
				e.printStackTrace();
			}
    }
}