import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class FramedConnection implements AutoCloseable{
    Socket s;
    DataInputStream is;

    DataOutputStream os;

    ReentrantLock rlock;
    ReentrantLock wlock;

    public FramedConnection(Socket s) throws IOException {
        this.s = s;
        this.is = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.os = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.rlock = new ReentrantLock();
        this.wlock = new ReentrantLock();
    }

    public void send(byte[] data) throws IOException {
        this.wlock.lock();
        try{
            this.os.writeInt(data.length);
            this.os.write(data);
            this.os.flush();
        }finally{
            this.wlock.unlock();
        }
    }

    public byte[] receive() throws IOException{
        this.rlock.lock();
        try{
            //definir o tamanho correto para ler a mensagem completa
            byte[] read_bytes = new byte[this.is.readInt()];
            this.is.readFully(read_bytes);
            return read_bytes;
        }finally{
            this.rlock.unlock();
        }
    }

    

    public void close() throws IOException {
        this.is.close();
        this.os.close();
        this.s.close();
    }

}
