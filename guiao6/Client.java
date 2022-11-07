import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 6666);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        String stdinLine, line;

        while((stdinLine = stdin.readLine()) != null){
            out.println(stdinLine);
            out.flush();
            line = in.readLine();
            System.out.println("Servidor respondeu com soma: " + line);
        }

        socket.shutdownOutput();
        line = in.readLine();
        System.out.println("Servidor respondeu com media: " + line);
        socket.shutdownInput();
        socket.close();
    }
}
