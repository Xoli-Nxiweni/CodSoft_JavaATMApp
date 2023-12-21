import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Transaction {
    private String description;
    private double amount;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private static char symbol = 'R';
    private double balance;
    private ArrayList<Transaction> transactions;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: " + symbol + balance);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal failed.");
            return false;
        } else {
            balance -= amount;
            transactions.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal successful. New balance: " + symbol + balance);
            return true;
        }
    }

    public void printStatement() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDescription() + ": " + transaction.getAmount());
        }
        System.out.println("Current Balance: " + symbol + balance);
    }
}

class ATM {
    private char symbol = 'R';
    private BankAccount bankAccount;
    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void displayMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Print Statement");
        System.out.println("5. Exit");
    }
    public void performTransaction(int option, Scanner scanner) {
        try {
            switch (option) {
                case 1:
                    System.out.println("Current Balance: " + symbol +bankAccount.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    bankAccount.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    bankAccount.withdraw(withdrawalAmount);
                    break;
                case 4:
                    bankAccount.printStatement();
                    break;
                case 5:
                    System.out.println("Exiting ATM. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
        }
    }
}
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isTrue = true;
        while (isTrue){
            System.out.println("Card Inserted. Loading.. ");
            System.out.print("Please enter your 5 digit Pin: ");
            String pin = scanner.next();
            System.out.print("Enter Pin again to verify: ");
            String confirmPin = scanner.next();

            if(pin.length() < 5 && confirmPin.length() < 5){
                System.out.println("Please enter a more secure Pin..");
                System.out.println("Card Removed.");
            }
            else {
                if (pin.equals(confirmPin)){
                    isTrue = !isTrue;
                }
                else {
                    System.out.println("Pin Mismatch, try again...");
                    System.out.println("Card Removed.");
                }
            }

        }
        double initialBalance = 100;

        BankAccount userAccount = new BankAccount(initialBalance);
        ATM atm = new ATM(userAccount);

        while (!isTrue) {
            atm.displayMenu();
            System.out.print("Enter your option: ");

            try {
                int choice = scanner.nextInt();
                atm.performTransaction(choice, scanner);
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please enter a valid value.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}