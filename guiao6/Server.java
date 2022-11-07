import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket sSock = new ServerSocket(6666);

        while(true){
            int sum = 0;
            int n = 0;
            Socket clSock = sSock.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clSock.getInputStream()));

            PrintWriter out = new PrintWriter(clSock.getOutputStream());

            String line;

            while((line = in.readLine()) != null){
                sum += Integer.parseInt(line);
                n++;
                out.println(sum);
                out.flush();
            }

            out.println(sum / n);
            out.flush();

            clSock.shutdownOutput();
            clSock.shutdownInput();
            clSock.close();
            sSock.close();
        }

    }
    
}
