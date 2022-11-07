
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket sSock = new ServerSocket(6666);

        while(true){
            
            Socket clSock = sSock.accept();
            Thread worker = new Thread(new ConnectionHandler(clSock));
            worker.start();
        }

    }
    
}
