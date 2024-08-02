import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankingApplication {
    private static Map<String, User> users = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));

        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                String username = sc.next();
                System.out.print("Enter password: ");
                String password = sc.next();

                User user = users.get(username);
                if (user != null && user.authenticate(password)) {
                    currentUser = user;
                    System.out.println("Login successful.");
                } else {
                    System.out.println("Invalid username or password.");
                }
                break;
            case 2:
                System.out.println("Exiting the application.");
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void showMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Check Balance");
        System.out.println("4. View Transaction History");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Apply Interest");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter amount to deposit: Rs.");
                double depositAmount = sc.nextDouble();
                currentUser.getAccount().deposit(depositAmount);
                currentUser.addTransaction("Deposited: $" + depositAmount);
                break;
            case 2:
                System.out.print("Enter amount to withdraw: Rs.");
                double withdrawAmount = sc.nextDouble();
                currentUser.getAccount().withdraw(withdrawAmount);
                currentUser.addTransaction("Withdrew: Rs." + withdrawAmount);
                break;
            case 3:
                System.out.println("Current balance: Rs." + currentUser.getAccount().checkBalance());
                break;
            case 4:
                System.out.println("Transaction History:");
                for (String transaction : currentUser.getTransactionHistory()) {
                    System.out.println(transaction);
                }
                break;
            case 5:
                transferFunds();
                break;
            case 6:
                System.out.print("Enter interest rate (%): ");
                double rate = sc.nextDouble();
                currentUser.getAccount().applyInterest(rate);
                currentUser.addTransaction("Interest applied: " + rate + "%");
                break;
            case 7:
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void transferFunds() {
        System.out.print("Enter recipient username: ");
        String recipientUsername = sc.next();
        User recipient = users.get(recipientUsername);

        if (recipient == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter amount to transfer: Rs.");
        double transferAmount = sc.nextDouble();

        if (currentUser.getAccount().checkBalance() >= transferAmount) {
            currentUser.getAccount().withdraw(transferAmount);
            recipient.getAccount().deposit(transferAmount);
            currentUser.addTransaction("Transferred Rs." + transferAmount + " to " + recipientUsername);
            recipient.addTransaction("Received Rs." + transferAmount + " from " + currentUser.getUsername());
            System.out.println("Successfully transferred Rs." + transferAmount + " to " + recipientUsername);
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }
}
