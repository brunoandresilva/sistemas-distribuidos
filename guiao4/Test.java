import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Barrier{
    private int T;
    private int counter;
    private Lock l;
    private Condition cond;
    private int epoch;

    Barrier(int T){
        this.T = T;
        this.counter = 0;
        this.l = new ReentrantLock();
        this.cond = this.l.newCondition();
    }

    void waitforit() throws InterruptedException{
        this.l.lock();
        try{
            int e = epoch;
            this.counter += 1;
            if(this.counter < this.T)
                while(epoch == e)
                    cond.await();
            else{
                cond.signalAll();
                this.counter = 0;
                epoch += 1;
            }
        }finally{
            this.l.unlock();
        }
        
        
        //old version
        // this.l.lock();
        // try{
        //     int e = epoch;
        //     this.counter += 1;
        //     System.out.println("counter: " + this.counter);

        //     if(this.counter < this.T){
        //         while(epoch == e){
        //             this.cond.await();
        //         }
        //     }else{
        //         cond.signalAll();
        //         this.counter = 0;
        //         this.epoch += 1;
        //     }
        // }finally{
        //     this.l.unlock();
        // }

    }

    

    
}

class Client implements Runnable{
    Barrier b;

    Client(Barrier b){
        this.b = b;
    }

    @Override
    public void run() {
        try {
            System.out.println("Antes de entrar na barreira");
            b.waitforit();
            System.out.println("Depois de passar a barreira");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class Test {
    public static void main(String[] args) {
        int NT = 15;
        Thread [] threads = new Thread[NT];
        Barrier b = new Barrier(5);

        for(int i = 0; i < NT; i++){
            Client c = new Client(b);
            threads[i] = new Thread(c);
        }

        for(int i = 0; i < NT; i++){
            threads[i].start();
            
        }

        for(int i = 0; i < NT; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



