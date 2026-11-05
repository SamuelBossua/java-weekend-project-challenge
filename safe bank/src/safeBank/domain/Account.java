package safeBank.domain;

import safeBank.exceptions.InsufficientFundsException;
import safeBank.exceptions.InvalidAmountException;
import safeBank.exceptions.WithdrawalLimitExceededException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountHolder;
    private BigDecimal balance;
    private final List<Transaction> transactionHistory = new ArrayList<>();

    public Account(String accountHolder, BigDecimal openingBalance) throws InvalidAmountException {

            if(accountHolder == null || accountHolder.isEmpty() ){
                throw new IllegalArgumentException(
                        "Account holder name cannot be empty"
                );
            }
            if(openingBalance == null || openingBalance.compareTo(BigDecimal.ZERO) <= 0){
                throw new InvalidAmountException("Opening balance cannot be negative");

            }

            this.accountHolder = accountHolder.trim();
            this.balance = openingBalance;


    }

    public String getAccountHolder() {
        return accountHolder;

    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return List.copyOf(transactionHistory);
    }

    public void deposit(BigDecimal amount) throws InvalidAmountException{
    if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
    throw new InvalidAmountException("Deposit amount must be greater than 0");}

        balance = balance.add(amount);
        transactionHistory.add(new Transaction(Transaction.Type.DEPOSIT, amount));

    }

    public BigDecimal WITHDRAWAL_LIMIT = new BigDecimal("100000");

    public void withdraw(BigDecimal amount) throws InvalidAmountException, InsufficientFundsException, WithdrawalLimitExceededException
    {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException("Withdrawal amount must be greater than 0");
        }

        if(amount.compareTo(balance) > 0){
            throw new InsufficientFundsException("Insufficient Funds");
        }

        if(amount.compareTo(WITHDRAWAL_LIMIT) > 0){
            throw new WithdrawalLimitExceededException("Withdrawal limit of 100000 exceeded");
        }

        balance = balance.subtract(amount);
        transactionHistory.add(new Transaction(Transaction.Type.WITHDRAWAL, amount));

    }
}



