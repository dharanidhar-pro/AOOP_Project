
public class Money {

    private final long amountInPaise;
    private final String currency;

    public Money(long amountInPaise, String currency) {
        if (amountInPaise < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency is required");
        }

        this.amountInPaise = amountInPaise;
        this.currency = currency;
    }

    public long getAmountInPaise() {
        return amountInPaise;
    }

    public String getCurrency() {
        return currency;
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(
            Math.addExact(amountInPaise, other.amountInPaise),
            currency
        );
    }

    public Money subtract(Money other) {
        checkCurrency(other);

        if (amountInPaise < other.amountInPaise) {
            throw new IllegalArgumentException("Insufficient money");
        }

        return new Money(amountInPaise - other.amountInPaise, currency);
    }

    public boolean isPositive() {
        return amountInPaise > 0;
    }

    private void checkCurrency(Money other) {
        if (other == null || !currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    @Override
    public String toString() {
        return currency + " " + String.format("%.2f", amountInPaise / 100.0);
    }
}
