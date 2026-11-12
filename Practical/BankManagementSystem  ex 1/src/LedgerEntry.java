
public class LedgerEntry {

    private final String transactionId;
    private final String transactionType;
    private final Money amount;
    private final String timestamp;

    public LedgerEntry(String transactionId,
                       String transactionType,
                       Money amount,
                       String timestamp) {

        this.transactionId = transactionId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return transactionType + " | " + amount
                + " | " + timestamp
                + " | ID: " + transactionId;
    }
}
