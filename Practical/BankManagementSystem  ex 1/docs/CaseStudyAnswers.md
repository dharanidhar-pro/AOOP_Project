
# Case Study: Bank Account and Money Transfers

## Q1. Domain Invariants

1. Account balance must not exceed the overdraft limit.
   Importance: Prevents unauthorized withdrawals.
   Violation: Financial losses.

2. Transaction amount must be positive.
   Importance: Prevents invalid transactions.
   Violation: Incorrect balance calculations.

3. Frozen accounts cannot perform withdrawals.
   Importance: Protects restricted accounts.
   Violation: Unauthorized transactions.

4. Closed accounts cannot perform transactions.
   Importance: Maintains account lifecycle rules.
   Violation: Transactions on inactive accounts.

5. Every transfer must debit and credit both accounts.
   Importance: Prevents money loss.
   Violation: Inconsistent balances.

6. Account balance must match transaction history.
   Importance: Maintains accurate financial records.
   Violation: Incorrect account statements.

7. Account numbers must be unique.
   Importance: Identifies accounts correctly.
   Violation: Account identification errors.

## Q2. Entities and Value Objects

Entity:
- BankAccount: Has a unique identity and lifecycle.
- LedgerEntry: Has a transaction ID and historical identity.
- FundTransfer: Has a unique transfer ID and lifecycle.

Value Objects:
- AccountNumber: Represents an account identifier value.
- Money: Represents an amount and currency.
- OverdraftLimit: Represents a permitted monetary limit.

Value objects are compared by their values, while entities
are identified by their unique identity.

## Q3. Self-Enforcing Objects

The application uses:
- Money.java
- AccountStatus.java
- BankAccount.java
- LedgerEntry.java
- FundTransfer.java

Encapsulation is implemented using private fields.

Invalid amounts are rejected through validation.

Frozen and closed accounts cannot perform withdrawals.

Overdraft limits are checked before withdrawals.

External classes cannot directly modify private account data.

## Q4. Money Transfer and Aggregate Consistency

1. Problems:
   Money may be deducted without being credited.

2. Violated invariants:
   Transfer consistency and balance accuracy.

3. Solution:
   Perform debit and credit as one atomic operation.

4. Failure handling:
   Roll back both account balances and ledger entries
   if the operation fails during execution.

   A database transaction is required to protect against
   actual system crashes.

5. Different banking systems:
   Use distributed transaction mechanisms, transaction
   identifiers, acknowledgements and reconciliation.

## Q5. Ledger Management

1. Ledger entry information:
   - Transaction ID
   - Transaction type
   - Amount
   - Timestamp

2. Existing ledger entries should not be modified or deleted.
   They are permanent financial records.

3. Balance consistency:
   Update account balances and ledger entries together
   within a transaction.

4. Large transaction history:
   Storage, retrieval speed and performance can become
   challenging. Indexing and archiving can help.
