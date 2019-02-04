import java.io.*;
import java.net.*;

public class ClientThread extends Runnable  { 
    private Lobby server;
    private Socket socket; 
    private DataInputStream in;
    private DataOutputStream out; 
      
    public ClientThread(Lobby serv, Socket sock) { 
        this.socket = sock; 
        this.server = serv;
        this.in = new DataInputStream(s.getInputStream());
        this.out = new DataOutputStream(s.getOutputStream());
    } 
  
    @Override
    public void run() { 
        String response; 
        while (true)  
        { 
            try { 
  
                // Get difficulty
                out.writeUTF("Pick a difficulty: \nEASY \nMEDIUM \nHARD \n"+ 
                            "OR Exit with command q"); 
                  
                // Get response
                response = in.readUTF(); 
                  
                if(response.equals("q")) 
                {  
                    System.out.println("Closing connection from client: " + socket); 
                    socket.close(); 
                    System.out.println("Connection closed"); 
                    break; 
                } 
                  
                // write on output stream based on the 
                // answer from the client 
                switch (response) { 
                  
                    case "EASY" : 
                        out.writeUTF("Setting difficulty to EASY"); 
                        break; 
                          
                    case "MEDIUM" : 
                        out.writeUTF("Setting difficulty to MEDIUM"); 
                        break; 

                    case "HARD" : 
                        out.writeUTF("Setting difficulty to HARD"); 
                        break; 
                          
                    default: 
                        out.writeUTF("Invalid input"); 
                        break; 
                } 
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
          
        try
        { 
            // closing resources 
            this.in.close(); 
            this.out.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 