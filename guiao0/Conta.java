public class Conta {
    private String name;
    private int id;
    private int balance;
    private int cc;

    public Conta(String name, int id, int balance, int cc){
        this.name = name;
        this.id = id;
        this.balance = balance;
        this.cc = cc;
    }

    public void deposit(int amount){
        this.balance += amount;
    }

    public void withdraw(int amount){
        this.balance -= amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }


    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    

    


    
}
