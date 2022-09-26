import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BankImp implements Bank{

    int counter;
    Map <Integer, Conta> contas;
    ArrayList<Conta> contas_obj;
    int total_balance;

    public BankImp(){
        this.contas = new HashMap<>();
        this.contas_obj = new ArrayList<>();
    }

    int get_total_balance(){
        this.total_balance = 0;
        for(Conta value: this.contas.values()){
            this.total_balance += value.getBalance();
        }
        return this.total_balance;
    }
    

    @Override
    public int open() {
        this.counter += 1;
        Scanner s = new Scanner(System.in);
        System.out.print("Name: ");
        String name = s.nextLine();
        System.out.print("Balance to create account with: ");
        int balance = s.nextInt();
        System.out.print("cc: ");
        int cc = s.nextInt();
        
        Conta c = new Conta(name, this.counter, balance, cc);
        this.contas_obj.add(c);
        contas.put(this.counter, c);
        return counter;
    }

    @Override
    public int balance(int id) {
        return this.contas.get(id).getBalance();
    }

    @Override
    public boolean deposit(int id, int value) {
        if(this.contas.containsKey(id)){
            this.contas.get(id).deposit(value);
            return true;
        }
        System.out.println("Nao existe conta com esse ID");;
        return false;
    }

    @Override
    public boolean withdraw(int id, int value) {
        if(this.contas.containsKey(id)){
            if(balance(id) >= value)
            this.contas.get(id).withdraw(value);
            return true;
        }
        System.out.println("Nao existe conta com esse ID");;
        return false;
    }
}