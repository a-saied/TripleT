import java.io.*;
import java.net.*;
import java.util.Scanner;

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
		    InputStream inFromServer = client.getInputStream();
		    DataInputStream in = new DataInputStream(inFromServer);
		    System.out.println(in.readUTF());

		    Scanner scanner = new Scanner(System.in);

			while(true){
				if(scanner.hasNextLine()){
					String diff = scanner.nextLine();
					out.writeUTF(diff);
					break;
				}
			}

		    boolean makeServer = false;
		    boolean makeClient = false;
		    BufferedReader br = new BufferedReader(new InputStreamReader(inFromServer));

		    String address = "";

		    while(true){
		    	String sample = "";
		    	sample = br.readLine().trim();
		    	System.out.println(sample);

		    	String[] s = sample.split(" ");

		    	if(sample.equals("SERV")){
		    		makeServer = true;
		    		break;
		    	}
		    	if(s[0].equals("CLI")){
		    		makeClient = true;
		    		address = s[1];
		    		break;
		    	}
		    }

		    if(makeClient) buildClient(address);
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

    public void buildClient(String address){
    	System.out.println("Entering game as client");
    	System.out.println("Connecting to: " + address);

    		try{
			    Socket client = new Socket(address, 6666);


			    System.out.println("Just connected to " + client.getRemoteSocketAddress());
			    OutputStream outToServer = client.getOutputStream();
			    DataOutputStream out = new DataOutputStream(outToServer);
			             
			    out.writeUTF("Hello from " + client.getLocalSocketAddress());
			    InputStream inFromServer = client.getInputStream();

			    boolean makeServer = false;
			    boolean makeClient = false;
			    BufferedReader br = new BufferedReader(new InputStreamReader(inFromServer));
			}
			catch(Exception e){
				e.printStackTrace();
			}
    }
}