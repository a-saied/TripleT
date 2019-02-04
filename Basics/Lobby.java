import java.io.*;
import java.net.*;

public class Lobby {
    //class variables
    private ServerSocket serv;
    private ArrayList<ClientThread> players;

    public static void main(String[] args){
		Lobby ls = new Lobby(Integer.parseInt(args[0]));
    }

    public Lobby(int port){
    	players = new ArrayList<ClientThread>();
		try{
		    serv = new ServerSocket(port);
		    acceptPlayers();
		}
		catch(Exception e){
		    e.printStackTrace();
		}
    }

    public ArrayList<ClientThread> getPlayers() {
    	return players;
    }

	public static void acceptPlayers(){
		while(true){
			try {
				System.out.println("Waiting for players to join the lobby on port " + 
						   serv.getLocalPort() + "...");
				Socket sock = serv.accept();

				System.out.println("A new player has joined: " + sock);

				// Create new thread for this client server connection
				ClientThread player = new ClientThread(this, sock)
				Thread t = new Thread(player)
				t.start();

				players.add(player);


			} catch (IOException e) {
				e.printStackTrace();
				sock.close()
				break;
			}
		}
	}

}
