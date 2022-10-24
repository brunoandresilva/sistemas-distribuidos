import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
            l.lock();
            if (value > balance)
                return false;
            
            try{
                balance -= value;
                return true;
            }finally{
                l.unlock();
            }
        
        }
    }

    private Map<Integer, Account> map = new HashMap<Integer, Account>();
    private int nextId = 0;
    private ReentrantReadWriteLock lock_bank;
    private Lock rl = lock_bank.readLock();
    private Lock wl = lock_bank.writeLock();

    public Bank(){
        this.lock_bank = new ReentrantLock();
    }

    // create account and return account id
    public int createAccount(int balance) {
        Account c = new Account(balance);
        lock_bank.lock();
        try{
            int id = nextId;
            nextId += 1;
            map.put(id, c);
            return id;
        }finally{
            lock_bank.unlock();
        }
        
    }

    // close account and return balance, or 0 if no such account
    public int closeAccount(int id) {
        lock_bank.lock();
        if(map.containsKey(id)){
            Account c = map.remove(id);
            c.l.lock();
            lock_bank.unlock();
            try{
                return c.balance();
            }finally{
                c.l.unlock();
            }
        }
        else{  //account doesn't exist
            lock_bank.unlock();
            return 0;
        }
        
    }

    // account balance; 0 if no such account
    public int balance(int id) {
        Account c = map.get(id);
        rl.lock();
        if (c == null)
            return 0;
        return c.balance();
    }

    // deposit; fails if no such account
    public boolean deposit(int id, int value) {
        Account c = map.get(id);
        if (c == null)
            return false;
        return c.deposit(value);
    }

    // withdraw; fails if no such account or insufficient balance
    public boolean withdraw(int id, int value) {
        Account c = map.get(id);
        if (c == null)
            return false;
        return c.withdraw(value);
    }

    // transfer value between accounts;
    // fails if either account does not exist or insufficient balance
    public boolean transfer(int from, int to, int value) {
        Account cfrom, cto;
        cfrom = map.get(from);
        cto = map.get(to);
        if (cfrom == null || cto ==  null)
            return false;
        return cfrom.withdraw(value) && cto.deposit(value);
    }

    // sum of balances in set of accounts; 0 if some does not exist
    public int totalBalance(int[] ids) {
        int total = 0;
        for (int i : ids) {
            Account c = map.get(i);
            if (c == null)
                return 0;
            total += c.balance();
        }
        return total;
  }

  

}
