package safeBankTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import safeBank.domain.Account;
import safeBank.domain.Transaction;
import safeBank.exceptions.InsufficientFundsException;
import safeBank.exceptions.InvalidAmountException;
import safeBank.exceptions.WithdrawalLimitExceededException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() throws InvalidAmountException {
        account = new Account("sam", new BigDecimal("200000"));
    }
    @Test
    public void testThaAccount_CanBeCreated() throws InvalidAmountException {
        Account testAccount = new Account("Ryan", new BigDecimal("10000"));
        assertNotNull(testAccount);
        assertEquals("Ryan", testAccount.getAccountHolder());
        assertEquals(0, new BigDecimal("10000").compareTo(testAccount.getBalance()));

    }

    @Test
    public void testThatAccount_CanWithdrawMoney() throws InvalidAmountException
            , InsufficientFundsException
            , WithdrawalLimitExceededException {
        account.withdraw(new BigDecimal("1000"));
        assertEquals(0, new BigDecimal("199000").compareTo(account.getBalance()));

    }

    @Test
    public void testThatWithdrawal_CannotBeNegative() throws InvalidAmountException, WithdrawalLimitExceededException, InsufficientFundsException {
        assertThrows(InvalidAmountException.class, () -> account.withdraw(new BigDecimal("-1000")));

    }

    @Test
    public void testThatWithdrawalLimit_CannotBeExceeded() throws WithdrawalLimitExceededException{
        assertThrows(WithdrawalLimitExceededException.class, ()-> account.withdraw(new BigDecimal("150000")));

    }

    @Test
    public void testThatAccountHolder_CannotWithdrawMoreThanAccountBalance() throws InsufficientFundsException, WithdrawalLimitExceededException {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(new BigDecimal("25000000000000")));
    }

    @Test
    public void testThatMoney_CanBeDeposited() throws InvalidAmountException{
        account.deposit(new BigDecimal("10000"));
        assertEquals(0, new BigDecimal("210000").compareTo(account.getBalance()));
    }

    @Test
    public void testThatNegativeAmount_CannotBeDeposited() throws InvalidAmountException{
        assertThrows(InvalidAmountException.class, () -> account.deposit(new BigDecimal("-1000")));
    }

    @Test
    public void testThatMoneyCanBeDepositedConsecutively() throws InvalidAmountException{
        account.deposit(new BigDecimal("10000"));
        account.deposit(new BigDecimal("200000"));
        account.deposit(new BigDecimal("300000"));
        assertEquals(0, new BigDecimal("710000").compareTo(account.getBalance()));
    }

    @Test
    public void testThatMoneyCanBeWithdrawnConsecutively() throws InvalidAmountException
            , InsufficientFundsException
            , WithdrawalLimitExceededException {
        account.withdraw(new BigDecimal("10000"));
        account.withdraw(new BigDecimal("20000"));
        account.withdraw(new BigDecimal("30000"));
        assertEquals(0, new BigDecimal("140000").compareTo(account.getBalance()));
    }

    @Test
    public void testThatAccountTransactionHistory_IsShown() throws InsufficientFundsException
            , WithdrawalLimitExceededException
            , InvalidAmountException {
        account.deposit(new BigDecimal("100000"));
        account.withdraw(new BigDecimal("50000"));

        Transaction deposit = account.getTransactionHistory().get(0);
        assertEquals(Transaction.Type.DEPOSIT, deposit.getType());
        assertEquals(0, new BigDecimal("100000").compareTo(deposit.getAmount()));

        Transaction withdrawal = account.getTransactionHistory().get(1);
        assertEquals(Transaction.Type.WITHDRAWAL, withdrawal.getType());
        assertEquals(0, new BigDecimal("50000").compareTo(withdrawal.getAmount()));

    }
}
