/**
 * Created by claudio.david on 02/02/2015.
 */
public class Payment {

    private Tennant from;
    private Tennant to;
    private Integer amount;

    public Payment(Tennant from, Tennant to, Integer amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Tennant getFrom() {
        return from;
    }

    public void setFrom(Tennant from) {
        this.from = from;
    }

    public Tennant getTo() {
        return to;
    }

    public void setTo(Tennant to) {
        this.to = to;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
