
public class Main {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("     BANK MANAGEMENT SYSTEM");
        System.out.println("================================");

        Money overdraft = new Money(100000, "INR");

        BankAccount accountA =
            new BankAccount("ACC1001", overdraft);

        BankAccount accountB =
            new BankAccount("ACC1002", overdraft);

        // Deposit money
        accountA.deposit(new Money(1000000, "INR"));
        accountB.deposit(new Money(500000, "INR"));

        System.out.println("\nInitial Balances:");
        System.out.println("Account A: " + accountA.getBalance());
        System.out.println("Account B: " + accountB.getBalance());

        // Withdraw money
        System.out.println("\n--- Withdrawal ---");
        accountA.withdraw(new Money(100000, "INR"));

        // Transfer money
        System.out.println("\n--- Money Transfer ---");

        FundTransfer transfer =
            new FundTransfer(
                accountA,
                accountB,
                new Money(200000, "INR")
            );

        transfer.transfer();

        // Display balances
        System.out.println("\n--- Final Balances ---");
        System.out.println("Account A: " + accountA.getBalance());
        System.out.println("Account B: " + accountB.getBalance());

        // Freeze account
        System.out.println("\n--- Account Status ---");
        accountA.freeze();
        System.out.println("Account A: " + accountA.getStatus());

        accountA.unfreeze();
        System.out.println("Account A: " + accountA.getStatus());

        // Display ledger
        System.out.println("\n--- Transaction History ---");

        for (LedgerEntry entry : accountA.getLedger()) {
            System.out.println(entry);
        }

        for (LedgerEntry entry : accountB.getLedger()) {
            System.out.println(entry);
        }

        System.out.println("\nBanking Application Completed!");
    }
}
