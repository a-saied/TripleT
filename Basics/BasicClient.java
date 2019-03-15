import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BasicClient{
    public static void main(String[] args){
    	BasicClient bc = new BasicClient();
    	bc.run(Integer.parseInt(args[0]), args[1], "localhost");
    }


    public void run(int port, String diff, String hostname){
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

            // Tell lobby the desired difficulty
            out.writeUTF(diff);

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
		    	buildClient(address, port, hostname, diff);
		    }
		    if(makeServer) {
		    	client.close();
		    	buildServer(port, hostname, diff);
		    }
		    //System.out.println("Server says " + in.readUTF());
		    //client.close();
		}
		catch(Exception e){
		    e.printStackTrace();
		}
    }

    public void buildServer(int port, String lobby_host, String diff){ 
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
    		run(port, diff, lobby_host);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		System.out.println("Server error! Returning to Game Lobby");
    		//run(port, lobby_host);
    	}

    	System.out.println("Game Over. See you next time!");
    }

    public void buildClient(String address, int port, String lobby_host, String diff){
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
				run(port, diff, lobby_host);
				
			}
			catch(Exception e){
				System.out.println("\nServer connection error! Please restart the game\n");
				e.printStackTrace();
			}

		System.out.println("Game Over. See you next time!");
    }

    private void runGameClient(DataInputStream in, DataOutputStream out){
    	try{
    		int board[][] = new int[3][3];
    		buildBoard(0,0,board);
    		System.out.println("You are PLAYER 2. PLAYER 1 will go first");
    		
    		//board[0][0] = 2;
    		boolean gameRunning = true;
    		//System.out.println(board[0][0]);
    		//out.writeUTF("Row: 2");
    		String row = null;
    		String col = null;
    		while(gameRunning){
    			//receive packet, build board, check win 
    			row = in.readUTF();
    			col = row.split(" ")[0];
    			row = row.split(" ")[1];

    			System.out.println(row);
    			System.out.println(col);
    			int c = Integer.parseInt(col);
    			int r = Integer.parseInt(row);
    			board[r][c] = 1;
    			buildBoard(r, c, board);
    			gameRunning = !checkWin(board, true);
    			if(!gameRunning){
    				System.out.println("Yikes! You lose :(");
    				break;
    			}

    			/// make move, build board, check win
    			makeMove(in , out, board, false);
    			//buildBoard(row, col, board); run within makeMove, no need to get row, col a second time 
    			gameRunning = !checkWin(board, false); 
    			if(!gameRunning){
    				System.out.println("Congrats, you win!");
    				break;
    			}
    		}

    	}catch(Exception e){
    		//e.printStackTrace();
    		//System.out.println("problems");
    	}
    }

    private void runGameServer(DataInputStream in, DataOutputStream out){
    	try{
    		int[][] board = new int[3][3];
    		buildBoard(0,0, board);
    		System.out.println("You are PLAYER 1. Make the first move");
    		boolean gameRunning = true;
    		String row = null;
    		String col = null;
    		while(gameRunning){
    			/// make move, build board, check win
    			makeMove(in , out, board, true);
    			//buildBoard(row, col, board); run within makeMove, no need to get row, col a second time 
    			gameRunning = !checkWin(board, true); 
    			if(!gameRunning){
    				System.out.println("Congrats, you win!");
    				break;
    			}
    			//receive packet, build board, check win 
    			row = in.readUTF();
    			col = row.split(" ")[0];
    			row = row.split(" ")[1];

    			int c = Integer.parseInt(col);
    			int r = Integer.parseInt(row);
    			board[r][c] = 2;
    			System.out.println(row);
    			System.out.println(col);
    			buildBoard(r, c, board);
    			gameRunning = !checkWin(board, false);
    			if(!gameRunning){
    				System.out.println("Yikes! You lose :(");
    				break;
    			}
    		}
    		//System.out.println("Game Over. See you next time!");

    	}catch(Exception e){
    		e.printStackTrace(); 
    		//System.out.println("problems");
    	}
    }

    private void makeMove(DataInputStream in, DataOutputStream out, int[][] board, boolean isServer){
    	Scanner sc = new Scanner(System.in);
    	int row = -1;
    	int col = -1;
    	while(true){
    		try{
	    	System.out.print("What row would you like: ");
    			String r = sc.nextLine();
    			row = Integer.parseInt(r);
    			System.out.print("What column would you like: ");
	    		String c = sc.nextLine();
	    		col = Integer.parseInt(c);
	    		c = c + " " + r; 
	    		out.writeUTF(c);
	    		if(row < 3 && col < 3 &&row >=0 && col >= 0){
	    			if(isServer) board[row][col] = 1;
	    			else board[row][col] = 2;
	    			break;
	    		}else{
	    			System.out.println("out of range");
	    		}
    			//out.writeUTF(c);
    			
    		}catch(Exception e){
    			System.out.println("Invalid inputs: please try again");
    		}
    	}
    	buildBoard(row, col, board);

    }

    private void buildBoard(int x, int y, int[][] board){
    	//String[][] draw = new String[3][3];
    	char[] letters= new char[3];
    	letters[0] = 'R';
    	letters[1] = 'o';
    	letters[2] = 'w';
    	System.out.println("    ----Col----");
    	System.out.println("     0   1   2   ");
    	for(int i = 0; i < board.length; i++){
    		System.out.print(letters[i] + " " + (i) + "  ");
    		for(int j = 0; j < board[0].length; j++){
    			if(board[i][j] == 2){
    				//draw[i][j] = "O";
    				System.out.print("O   ");
    			}
    			else if(board[i][j] == 1){
    				//draw[i][j] = "X";
    				System.out.print("X   ");
    			}
    			else{
    				//draw[i][j] = " ";
    				System.out.print("    ");
    			}
    		}
    		System.out.println("   ");
    	}
    	
    	//System.out.println("  " + draw[0][0] = )


    }

    private boolean checkWin(int[][] board, boolean isServer){
    	int checkValue;
    	if(isServer) checkValue = 1;
    	else checkValue = 2;

    	if(board[1][1] == checkValue){
    		if(board[0][0] == checkValue && board[2][2] == checkValue) return true;
    		else if(board[0][1] == checkValue && board[2][1] == checkValue) return true;
    		else if(board[1][0] == checkValue && board[1][2] == checkValue) return true;
    		else if(board[0][2] == checkValue && board[2][0] == checkValue) return true;
    	}
    	else{
    		if(board[0][0] == checkValue && board[0][1] == checkValue && board[0][2] == checkValue) return true;
    		else if (board[0][0] == checkValue && board[1][0] == checkValue && board[2][0] == checkValue) return true;
    		else if (board[0][2] == checkValue && board[1][2] == checkValue && board[2][2] == checkValue) return true;
    		else if (board[2][0] == checkValue && board[2][1] == checkValue && board[2][2] == checkValue) return true;
    	}

    	return false;
    }

}










