import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

abstract class Account {
    private String accountNumber;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public abstract String getAccountType();

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction(new Date(), "Deposit", amount, balance));
    }

    public void withdraw(double amount) {
        if (balance < amount) {
            System.out.println("Invalid balance for making the transaction.");
            return;
        }
        balance -= amount;
        transactions.add(new Transaction(new Date(), "Withdrawal", amount, balance));
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
class SavingsAccount extends Account {
    public SavingsAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}
class CurrentAccount extends Account {
    public CurrentAccount(String accountNumber, double initialBalance) {
        super(accountNumber, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}


class JointAccount extends Account {
    private List<String> accountHolders;

    public JointAccount(String accountNumber, double initialBalance, List<String> accountHolders) {
        super(accountNumber, initialBalance);
        this.accountHolders = accountHolders;
    }
    public String getAccountType() {
        return "Joint";
    }

    public List<String> getAccountHolders() {
        return accountHolders;
    }
}
class Transaction {
    private  final Date date;
    private final String description;
    private  final double amount;
    private  final double currentBalance;

    public Transaction(Date date, String description, double amount, double currentBalance) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.currentBalance = currentBalance;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }
}


public class bank {
    private static Scanner scanner = new Scanner(System.in);
    private static int accountCounter = 5000;
    private static HashMap<String, Account> accounts = new HashMap<>();

    public static void main(String[] args) {
       do {
            System.out.println("Welcome to the Bank.Select the option according to the service you require:");
            System.out.println("1. Create an account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. Check Balance");
            System.out.println("5. Print Statement");
            System.out.println("6. Exit");
       int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:createAccount();
                    break;
                case 2:performTransaction("Deposit");
                    break;
                case 3:performTransaction("Withdraw");
                    break;
                case 4: checkBalance();
                    break;
                case 5:printStatement();
                    break;
                case 6: System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }while(true);
    }

    private static void createAccount() {
        System.out.print("Enter account type (Savings/Current/Joint): ");
        String accountType = scanner.nextLine();
        String accountNumber = "10" + accountCounter++;
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine();  
        Account account;
        switch (accountType.toLowerCase()) {
            case "savings":
                account = new SavingsAccount(accountNumber, initialBalance);
                break;
            case "current":
                account = new CurrentAccount(accountNumber, initialBalance);
                break;
            case "joint":
                System.out.print("Enter account holder names (write names with comma-separation[,]): ");
                String[] holdersArray = scanner.nextLine().split(",");
                List<String> holders = new ArrayList<>();
                for (String holder : holdersArray) {
                    holders.add(holder.trim());
                }
                account = new JointAccount(accountNumber, initialBalance, holders);
                break;
            default:
                System.out.println("Invalid account.");
                return;
        }

        accounts.put(accountNumber, account);
        System.out.println("account is created successfully. account number for your account is : " + accountNumber);
    }

    private static void performTransaction(String transactionType) {
        System.out.print("Enter account number: ");
       String accountNumber = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  
        Account account = accounts.get(accountNumber);
        if (account!=null ) {
            if (transactionType.equals("Deposit")) {
                account.deposit(amount);
                System.out.println("your deposit is successful.");
            } else if (transactionType.equals("Withdraw")) {
                account.withdraw(amount);
                System.out.println("Withdrawal was successful.");
            }
        } else {
            System.out.println("account doesnt exists.");
        }
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        Account account = accounts.get(accountNumber);
        if (account!=null) {
            System.out.println("Current Balance: " + account.getBalance());
        } else {
            System.out.println("account doesnt exists.");
        }
    }

    private static void printStatement() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        Account account = accounts.get(accountNumber);
        if (account!=null) {
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Transactions:");
            System.out.printf("%-20s %-20s %-10s %-10s\n", "Date", "Description", "Amount", "Balance");
            for (Transaction t : account.getTransactions()) {
                System.out.println(String.format("%-20s %-20s %-20.2f %-20.2f", t.getDate(), t.getDescription(), t.getAmount(), t.getCurrentBalance()));
            }
        } else {
            System.out.println("account doesnt exists.");
        }
    }
}

