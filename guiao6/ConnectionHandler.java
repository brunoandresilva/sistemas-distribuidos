import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    Socket socket;

    public ConnectionHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        int sum = 0;
        int n = 0;  
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            PrintWriter out = new PrintWriter(this.socket.getOutputStream());

            String line;

            while((line = in.readLine()) != null){
                sum += Integer.parseInt(line);
                n++;
                out.println(sum);
                out.flush();
            }

            out.println(sum / n);
            out.flush();

            this.socket.shutdownOutput();
            this.socket.shutdownInput();
            this.socket.close();
        }catch(IOException exception){
            System.out.println("Exception");
        }
        
        
    }
    
}
