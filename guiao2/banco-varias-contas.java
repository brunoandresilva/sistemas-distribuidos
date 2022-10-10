import java.util.concurrent.locks.ReentrantLock;

class Bank {

  private static class Account {
    private int balance;
    private ReentrantLock l;
    Account(int balance) { 
      this.balance = balance; 
      this.l = new ReentrantLock();
    }


    int balance() { 
      l.lock();
      try{
        return balance;
      }finally{
        l.unlock();
      }
    }


    boolean deposit(int value) {
      l.lock();
      try{
        balance += value;
        return true;
      }finally{
        l.unlock();
      }
      
    }


    boolean withdraw(int value) {
      if (value > balance)
        return false;

      l.lock();
      try{
        balance -= value;
        return true;
      }finally{
        l.unlock();
      }
      
    }
  }

  // Bank slots and vector of accounts
  private int slots;
  private Account[] av; 

  private ReentrantLock l;

  public Bank(int n){
    slots=n;
    av=new Account[slots];
    for (int i=0; i<slots; i++) av[i]=new Account(0);
    this.l = new ReentrantLock();
  }

  // Account balance
  public int balance(int id) {
    if (id < 0 || id >= slots)
      return 0;

    return av[id].balance();
  }

  // Deposit
  boolean deposit(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    
    return av[id].deposit(value);
  }

  // Withdraw; fails if no such account or insufficient balance
  public boolean withdraw(int id, int value) {
    if (id < 0 || id >= slots)
      return false;
    
    return av[id].withdraw(value);
  }

  public boolean transfer (int from, int to, int value){
    if (from < 0 || from >= slots || to < 0 || to >= slots)
      return false;

    l.lock();
    try{
      return this.withdraw(from, value) && this.deposit(to, value);
    }finally{
      l.unlock();
    }
  }

  int totalBalance(){
    int total_balance = 0;
    l.lock();
    try{
      for(Account a: this.av){
        total_balance += a.balance();
      }
    }finally{
      l.unlock();
    }
    
    return total_balance;
  }
}
