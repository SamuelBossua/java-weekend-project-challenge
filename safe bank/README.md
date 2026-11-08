# SafeBank Transaction Simulator

## Overview

A Java console application that simulates a single bank account. It supports
deposits, withdrawals, balance checks, and viewing successful transaction
history.

## Features and rules

- The account holder's name must not be blank.
- The opening balance cannot be negative.
- Deposits and withdrawals must be greater than zero.
- A withdrawal cannot exceed the available balance or ₦100,000.
- Rejected transactions do not change the balance or successful transaction
  history.
- Invalid menu choices and non-numeric amounts show helpful messages.
- The menu continues running after a rejected transaction.

## Design

- `Account` owns the balance and enforces the banking rules.
- Money is represented with `BigDecimal`.
- Each successful transaction is stored as a `Transaction` with its type,
  amount, and time.
- `SafeBankApp` handles console input, menu choices, and user-facing messages.
- Custom exceptions distinguish invalid amounts, insufficient funds, and
  withdrawals over the limit.

## Running

Run `safeBank.ui.SafeBankApp` from IntelliJ to start the console application.

To run the automated tests, use the Maven `test` lifecycle in IntelliJ, or run
`mvn test` from this project folder.

## Verification

| Scenario | Expected result | Actual result |
|---|---|---|
| Create an account with a valid name and opening balance | Account is created with the supplied details | Passed |
| Deposit a valid amount | Balance increases and a deposit is recorded | Passed |
| Enter non-numeric deposit input, then retry with a valid amount | Helpful message appears; valid retry succeeds | Passed |
| Withdraw more than the available balance | Withdrawal is rejected; a later valid withdrawal can succeed | Passed |
| Withdraw ₦120,000 from an account with ₦200,000 | Withdrawal is rejected because it exceeds the limit; balance and history stay unchanged | Passed |
| View the transaction history | Successful deposits and withdrawals are displayed | Passed |
| Enter an invalid menu choice | Helpful message appears and the menu continues | Passed |

Maven `test` completed successfully, and the JUnit tests passed.

## Sample interaction

Sample inputs:

```text
Sam
20000
1
1000
4
5