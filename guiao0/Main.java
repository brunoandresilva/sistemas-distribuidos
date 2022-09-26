public class Main {
    public static void main(String[] args) {
        BankImp bank = new BankImp();
        System.out.println(bank.open());
        System.out.println(bank.open());
        System.out.println(bank.open());
        for(int i = 0; i < bank.contas_obj.size(); i++){
            System.out.println("Nome: " + bank.contas_obj.get(i).getName());
            System.out.println("ID: " + bank.contas_obj.get(i).getId());
            System.out.println("CC: " + bank.contas_obj.get(i).getCc());
            System.out.println("Balance: " + bank.contas_obj.get(i).getBalance());
        }
        System.out.println("Total balance: " + bank.get_total_balance());
    }
}
