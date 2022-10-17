import java.util.Random;

class Mover implements Runnable {
  Bank b;
  int s; // Number of accounts

  public Mover(Bank b, int s) { this.b=b; this.s=s; }

  public void run() {
    final int moves=100000;
    int from, to;
    Random rand = new Random();

    for (int m=0; m<moves; m++)
    {
      from=rand.nextInt(s); // Get one
      while ((to=rand.nextInt(s))==from); // Slow way to get distinct
      b.transfer(from,to,1);
    }
  }
}

class closeAndWithdrawTest implements Runnable{
  Bank b;

  public closeAndWithdrawTest(Bank b){
    this.b = b;
  }

  @Override
  public void run() {
    for(int i = 0; i < 200; i++){
      b.withdraw(i, 1);
      b.closeAccount(i);
    }
    
  }
}

class BankTest {
  public static void main(String[] args) throws InterruptedException {

    Bank b = new Bank();
    for(int i = 0; i < 200; i++){
      b.createAccount(100);
    }

    Thread t1 = new Thread(new closeAndWithdrawTest(b));
    Thread t2 = new Thread(new closeAndWithdrawTest(b));
    t1.start();
    t2.start();

  }
}
