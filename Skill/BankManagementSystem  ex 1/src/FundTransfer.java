
import java.util.UUID;

public class FundTransfer {

    private final String transferId;
    private final BankAccount source;
    private final BankAccount destination;
    private final Money amount;

    public FundTransfer(BankAccount source,
                        BankAccount destination,
                        Money amount) {

        if (source == null || destination == null) {
            throw new IllegalArgumentException("Accounts are required");
        }

        if (source == destination) {
            throw new IllegalArgumentException(
                "Cannot transfer to the same account"
            );
        }

        if (amount == null || !amount.isPositive()) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        if (!source.getBalance().getCurrency()
                .equals(amount.getCurrency())
            || !destination.getBalance().getCurrency()
                .equals(amount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }

        this.transferId = UUID.randomUUID().toString();
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public synchronized void transfer() {

        Money oldSourceBalance = source.getBalance();
        Money oldDestinationBalance = destination.getBalance();

        int sourceLedgerSize = source.getLedger().size();
        int destinationLedgerSize = destination.getLedger().size();

        try {
            source.debitForTransfer(amount);
            destination.creditForTransfer(amount);

            System.out.println("Transfer successful!");
            System.out.println("Transfer ID: " + transferId);

        } catch (RuntimeException e) {

            source.restoreState(oldSourceBalance, sourceLedgerSize);
            destination.restoreState(
                oldDestinationBalance,
                destinationLedgerSize
            );

            System.out.println("Transfer failed. Rollback completed.");
            throw e;
        }
    }

    public String getTransferId() {
        return transferId;
    }
}
