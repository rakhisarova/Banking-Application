import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private BankAccount account;
    private List<String> transactionHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.account = new BankAccount();
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }

    public BankAccount getAccount() {
        return account;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }
}
