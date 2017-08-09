package domain;

import java.math.BigDecimal;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class Payment {

    private Tenant sender;
    private Tenant receiver;
    private BigDecimal amount;

    public Payment() {
        super();
    }

    public static Payment newPayment(){
        return new Payment();
    }

    private Payment(Tenant sender, Tenant receiver, BigDecimal amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public Payment withSender(Tenant sender) {
        this.sender = sender;
        return new Payment(sender, this.receiver, this.amount);
    }

    public Payment withReceiver(Tenant receiver) {
        this.receiver = receiver;
        return new Payment(this.sender, receiver, this.amount);
    }

    public Payment withAmount(BigDecimal amount) {
        this.amount = amount;
        return new Payment(this.sender, this.receiver, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;

        Payment payment = (Payment) o;

        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        if (receiver != payment.receiver) return false;
        if (sender != payment.sender) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "{ " + sender.getName() + " has to pay " + receiver.getName() +
                " " + amount + '}';
    }
}
