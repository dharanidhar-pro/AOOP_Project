
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BankAccount {

    private final String accountNumber;
    private Money balance;
    private final Money overdraftLimit;
    private AccountStatus status;
    private final List<LedgerEntry> ledger;

    public BankAccount(String accountNumber, Money overdraftLimit) {

        if (accountNumber == null || accountNumber.isBlank()
                || overdraftLimit == null) {
            throw new IllegalArgumentException("Invalid account details");
        }

        this.accountNumber = accountNumber;
        this.overdraftLimit = overdraftLimit;
        this.balance = new Money(0, overdraftLimit.getCurrency());
        this.status = AccountStatus.ACTIVE;
        this.ledger = new ArrayList<>();
    }

    public void deposit(Money amount) {
        validateAmount(amount);
        checkActive();

        balance = balance.add(amount);
        addLedgerEntry("DEPOSIT", amount);

        System.out.println("Deposit successful!");
    }

    public void withdraw(Money amount) {
        validateAmount(amount);
        checkActive();

        if (amount.getAmountInPaise() >
                balance.getAmountInPaise()
                + overdraftLimit.getAmountInPaise()) {
            throw new IllegalArgumentException("Overdraft limit exceeded");
        }

        balance = new Money(
            Math.subtractExact(
                balance.getAmountInPaise(),
                amount.getAmountInPaise()
            ),
            balance.getCurrency()
        );

        addLedgerEntry("WITHDRAWAL", amount);
        System.out.println("Withdrawal successful!");
    }

    public void freeze() {
        checkActive();
        status = AccountStatus.FROZEN;
    }

    public void unfreeze() {
        if (status != AccountStatus.FROZEN) {
            throw new IllegalStateException("Account is not frozen");
        }
        status = AccountStatus.ACTIVE;
    }

    public void close() {
        checkActive();

        if (balance.getAmountInPaise() != 0) {
            throw new IllegalStateException("Balance must be zero");
        }

        status = AccountStatus.CLOSED;
    }

    public Money getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public List<LedgerEntry> getLedger() {
        return Collections.unmodifiableList(ledger);
    }

    void debitForTransfer(Money amount) {
        withdrawInternal(amount, "TRANSFER_DEBIT");
    }

    void creditForTransfer(Money amount) {
        validateAmount(amount);
        checkActive();

        balance = balance.add(amount);
        addLedgerEntry("TRANSFER_CREDIT", amount);
    }

    private void withdrawInternal(Money amount, String type) {
        validateAmount(amount);
        checkActive();

        if (amount.getAmountInPaise() >
                balance.getAmountInPaise()
                + overdraftLimit.getAmountInPaise()) {
            throw new IllegalArgumentException("Overdraft limit exceeded");
        }

        balance = new Money(
            Math.subtractExact(
                balance.getAmountInPaise(),
                amount.getAmountInPaise()
            ),
            balance.getCurrency()
        );

        addLedgerEntry(type, amount);
    }

    // Transfer rollback support
    void restoreState(Money previousBalance, int previousLedgerSize) {
        balance = previousBalance;

        while (ledger.size() > previousLedgerSize) {
            ledger.remove(ledger.size() - 1);
        }
    }

    private void validateAmount(Money amount) {
        if (amount == null || !amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!balance.getCurrency().equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    private void checkActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
    }

    private void addLedgerEntry(String type, Money amount) {
        ledger.add(new LedgerEntry(
            UUID.randomUUID().toString(),
            type,
            amount,
            java.time.LocalDateTime.now().toString()
        ));
    }
}
