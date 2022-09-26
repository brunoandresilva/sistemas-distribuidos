public interface Bank {
    int open();
    int balance(int id);
    boolean deposit(int id, int value);
    boolean withdraw(int id, int value);
}
