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

		    if(makeClient) {
		    	client.close();
		    	buildClient(address, port, hostname);
		    }
		    if(makeServer) {
		    	client.close();
		    	buildServer(port, hostname);
		    }
		    //System.out.println("Server says " + in.readUTF());
		    //client.close();
		}
		catch(Exception e){
		    e.printStackTrace();
		}
    }

    public void buildServer(int port, String lobby_host){ 
    	System.out.println("Creating game as host");
    	try{
	    	ServerSocket serv = new ServerSocket(6666);
    		while(true){
	    		Socket server = serv.accept();

				//System.out.println("Just connected to " + server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(server.getInputStream());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				System.out.println(in.readUTF());
				runGameServer(in, out);
				//System.out.println(in.readUTF());

				// server.close();
				break;
			}
    	}
    	catch(ConnectException ce){
    		System.out.println("connection error: returning to game lobby");
    		run(port, lobby_host);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		System.out.println("Server error! Returning to Game Lobby");
    		//run(port, lobby_host);
    	}

    	System.out.println("Game Over. See you next time!");
    }

    public void buildClient(String address, int port, String lobby_host){
    	System.out.println("Entering game as client");
    	System.out.println("Connecting to: " + address);

    		try{
			    Socket client = new Socket(address, 6666);


			    System.out.println("Just connected to " + client.getRemoteSocketAddress());
			    OutputStream outToServer = client.getOutputStream();
			    DataOutputStream out = new DataOutputStream(outToServer);
																			
			    out.writeUTF("Hello from " + client.getLocalSocketAddress());
			    InputStream inFromServer = client.getInputStream();
			    DataInputStream in = new DataInputStream(inFromServer);




			    //runGame();
			    runGameClient(in, out);
			    
			    boolean makeServer = false;
			    boolean makeClient = false;
			    BufferedReader br = new BufferedReader(new InputStreamReader(inFromServer));
			}
			catch (ConnectException ce) {
				System.out.println("connection error returning to game lobby");
				run(port, lobby_host);
				
			}
			catch(Exception e){
				System.out.println("\nServer connection error! Please restart the game\n");
				e.printStackTrace();
			}

		System.out.println("Game Over. See you next time!");
    }

    private void runGameClient(DataInputStream in, DataOutputStream out){
    	try{
    		System.out.println("You are PLAYER 2. PLAYER 1 will go first");
    		int board[][] = new int[3][3];
    		board[0][0] = 2;
    		System.out.println(board[0][0]);
    		//out.writeUTF("Row: 2");
    		while(true){
    			System.out.println(in.readUTF());
    			out.writeUTF("GANG");
    			// System.out.println(in.readUTF());
    		}

    	}catch(Exception e){

    	}
    }

    private void runGameServer(DataInputStream in, DataOutputStream out){
    	try{
    		int[][] board = new int[3][3];
    		System.out.println("You are PLAYER 1. Make the first move");
    		boolean gameRunning = true;
    		String row = null;
    		String col = null;
    		while(gameRunning){
    			makeMove(in , out, board);
    			row = in.readUTF();
    			col = row.split(" ")[0];
    			row = row.split(" ")[1];
    			
    			System.out.println(row);
    			System.out.println(col);
    			//gameRunning = false;
    			//buildBoard(row, col, board);
    			//gameRunning = checkWin(board); 
    		}
    		//System.out.println("Game Over. See you next time!");

    	}catch(Exception e){

    	}
    }
    private void makeMove(DataInputStream in, DataOutputStream out, int[][] board){
    	Scanner sc = new Scanner(System.in);
    	System.out.print("What row would you like: ");
    	String r = sc.nextLine();
    	System.out.print("What column would you like: ");
    	String c = r + sc.nextLine();
    	try{
	    	out.writeUTF(c);
    		//out.writeUTF(c);
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }

    // private void buildBoard(String x, String y){
    // 	// fill = "X", "O",
    // }

    // private boolean checkWin(int[][] board){

    // }

}










