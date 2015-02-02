import java.util.HashMap;
import java.util.Map;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class ThreeSideCalc {

    private Map<Tennant, Integer> tennantPaymentsMapping;

    public ThreeSideCalc(Map<Tennant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
    }

    private Integer computeContribution(){
        Integer total = 0;
        for(Integer payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return total/tennantPaymentsMapping.size();
    }

    public Map<Tennant, Integer> computeRemainingPayments(){
        Map<Tennant, Integer> remainingPayments = new HashMap<Tennant, Integer>();
        for(Map.Entry<Tennant, Integer> payment: tennantPaymentsMapping.entrySet()){
            remainingPayments.put(payment.getKey(), computeContribution() - payment.getValue());

        }
        return remainingPayments;
    }
}
