package safeBank.ui;

import safeBank.domain.Account;
import safeBank.domain.Transaction;
import safeBank.exceptions.InsufficientFundsException;
import safeBank.exceptions.InvalidAmountException;
import safeBank.exceptions.WithdrawalLimitExceededException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class SafeBankApp {
    private Scanner scanner = new Scanner(System.in);
    private Account account;

    public static void main(String[] args) {
        SafeBankApp safeBankApp = new SafeBankApp();
        safeBankApp.run();



    }

    private void run() {
        createAccount();
        System.out.println("Account created for " + account.getAccountHolder());
        System.out.println("Opening balance (₦): " + account.getBalance().toString());
        runMenu();
    }

    private void runMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("5")) {
                System.out.println("Goodbye!");
                running = false;
            } else if (choice.equals("3")) {
                System.out.println(
                        "Current balance: ₦" + account.getBalance().toPlainString()
                );
            } else if (choice.equals("1")) {
                handleDeposit();

            }  else if (choice.equals("4")) {
                showTransactionHistory();
            }

              else if (choice.equals("2")) {
                handleWithdrawal();
            } else {
                System.out.println("\nInvalid menu selection. Please choose 1-5.");
            }
        }
    }

    private void handleWithdrawal() {
        System.out.print("Enter amount to be withdrawn: ₦");
        try{
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            account.withdraw(amount);

            System.out.println("Withdrawal successful. New balance: ₦"  + account.getBalance().toPlainString());
        }
        catch (InvalidAmountException | WithdrawalLimitExceededException | InsufficientFundsException e){
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("please enter a valid numeric amount: ");
        }


    }

    private void showTransactionHistory() {
        List<Transaction> transactions = account.getTransactionHistory();

        if(transactions.isEmpty()) {
            System.out.println("There are no transactions in the account");
            return;
        }
            System.out.println("------Transaction History------");
            for(Transaction transaction : transactions) {
                System.out.println("₦" + transaction.toString());
            }


    }

    private void handleDeposit() {
        System.out.print("Enter amount to be deposited: ₦");
        try{
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            account.deposit(amount);

            System.out.println("Deposit successful. New balance: ₦"  + account.getBalance().toPlainString());
        }
        catch (InvalidAmountException e){
            System.out.println(e.getMessage());
        }
        catch(NumberFormatException e){
            System.out.println("please enter a valid numeric amount: ");
        }

    }

    private void displayMenu() {

            System.out.println("\n--- SafeBank Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. View balance");
            System.out.println("4. View transaction history");
            System.out.println("5. Exit");

    }

    private void createAccount() {
        while(true){
            try{
                System.out.print("Account holder's name: ");
                String name = scanner.nextLine();

                System.out.println("Opening balance (₦): ");
                BigDecimal openingBalance = new BigDecimal(scanner.nextLine().trim());

                account = new Account(name, openingBalance);
                return;
            }
            catch(NumberFormatException e){
                System.out.println("Please enter the balance as a number");

            }
            catch(InvalidAmountException | IllegalArgumentException e){
                System.out.println(e.getMessage() );
            }
        }
    }


}
