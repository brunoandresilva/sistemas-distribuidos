import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Warehouse {
  private Map<String, Product> map =  new HashMap<String, Product>();
  private Lock l = new ReentrantLock();

  public Warehouse(){
  }

  private class Product { 
    int quantity = 0;
    private Condition cond; 
    private Lock l;
  
    public Product(){
      this.l = new ReentrantLock();
      this.cond = this.l.newCondition();
    }

  }

  private Product get(String item) {
    Product p = map.get(item);
    if (p != null) return p;
    p = new Product();
    map.put(item, p);
    return p;
  }

  public void supply(String item, int quantity) {
    l.lock();
    try{
      Product p = get(item);
      p.quantity += quantity;
      p.cond.signalAll();
    }finally{
      l.unlock();
    }
  }

  // Errado se faltar algum produto...
  public void consume(Set<String> items) throws InterruptedException{
    l.lock();
    try{
      for (String s : items){
        Product p = get(s);
        while(p.quantity == 0){
          p.cond.await();
        }
        get(s).quantity--;
      }
    }finally{
      l.unlock();
    }
    
  }

  

  class Client implements Runnable{
    Warehouse w;
    Set<String> set = new HashSet<String>();

    public Client(Warehouse w, Set<String> set){
      this.w = w;
      this.set = set;
    }

    @Override
    public void run() {
      try {
        this.w.consume(this.set);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      
      
    }
    
  }

  class Supply implements Runnable{
    Warehouse w;

    public Supply(Warehouse w){
      this.w = w;
    }

    @Override
    public void run() {
      this.w.supply("Banana", 1);
      
    }
    
  }
 
  public static void main(String[] args) {
    Warehouse w = new Warehouse();
    Set<String> productSet = new HashSet<String>();
    productSet.add("Banana");
    Client c = w.new Client(w, productSet);
    Supply s = w.new Supply(w);
    Thread t1 = new Thread(c);
    Thread t2 = new Thread(s);
    t1.start();
    t2.start();
  }

}


