/**
 * Created by claudio.david on 02/02/2015.
 */
public class Payment {

    private Tenant sender;
    private Tenant receiver;
    private Integer amount;

    public Payment() {
        super();
    }

    public Tenant getSender() {
        return sender;
    }

    public Tenant getReceiver() {
        return receiver;
    }

    public Integer getAmount() {
        return amount;
    }

    public Payment addPaymentSender(Tenant sender){
        this.sender = sender;
        return this;
    }

    public Payment addPaymentReceiver(Tenant receiver){
        this.receiver = receiver;
        return this;
    }

    public Payment addAmount(Integer amount){
        this.amount = amount;
        return this;
    }
}
