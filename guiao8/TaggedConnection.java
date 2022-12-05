import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;



public class TaggedConnection implements AutoCloseable{
    public static class Frame {
        public final int tag;
        public final byte[] data;
        public Frame(int tag, byte[] data) { this.tag = tag; this.data = data; }
    }
    
    
    Socket s;
    DataInputStream is;
    DataOutputStream os;

    ReentrantLock rlock;
    ReentrantLock wlock;

    public TaggedConnection(Socket s) throws IOException {
        this.s = s;
        this.is = new DataInputStream(new BufferedInputStream(s.getInputStream()));
        this.os = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
        this.rlock = new ReentrantLock();
        this.wlock = new ReentrantLock();
    }


    public void send(Frame frame) throws IOException{
        this.send(frame.tag, frame.data);
    }

    public void send(int tag, byte[] data) throws IOException {
        this.wlock.lock();
        try{
            this.os.writeInt(4 + data.length);
            this.os.writeInt(tag);
            this.os.write(data);
            this.os.flush();
        }finally{
            this.wlock.unlock();
        }
    }

    public Frame receive() throws IOException {
        
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        
    }
    
}
