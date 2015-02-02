/**
 * Created by claudio.david on 02/02/2015.
 */
public class Payment {

    private Tenant from;
    private Tenant to;
    private Integer amount;

    public Payment(Tenant from, Tenant to, Integer amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Tenant getFrom() {
        return from;
    }

    public void setFrom(Tenant from) {
        this.from = from;
    }

    public Tenant getTo() {
        return to;
    }

    public void setTo(Tenant to) {
        this.to = to;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
