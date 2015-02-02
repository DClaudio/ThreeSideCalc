import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Integer> tennantPaymentsMapping;

    public PaymentsManagementCalculator(Map<Tenant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
    }

    private Integer computeContribution(){
        Integer total = 0;
        for(Integer payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return total/tennantPaymentsMapping.size();
    }

    public Map<Tenant, Integer> computeRemainingPayments(){
        Map<Tenant, Integer> remainingPayments = new HashMap<Tenant, Integer>();
        for(Map.Entry<Tenant, Integer> payment: tennantPaymentsMapping.entrySet()){
            remainingPayments.put(payment.getKey(), computeContribution() - payment.getValue());

        }
        return remainingPayments;
    }

    public List<Payment> computePaymentsList(){
        List<Payment> payments = new ArrayList<Payment>();
        return payments;
    }


}
