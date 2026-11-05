package safeBank.ui;

import safeBank.domain.Account;
import safeBank.exceptions.InvalidAmountException;

import java.math.BigDecimal;
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
