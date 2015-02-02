import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tennant, Integer> tennantPaymentsMapping;

    public PaymentsManagementCalculator(Map<Tennant, Integer> tennantPaymentsMapping) {
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

    public List<Payment> computePaymentsList(){
        List<Payment> payments = new ArrayList<Payment>();
        Map<Tennant, Integer> remainingPayments = computeRemainingPayments();

        for(Map.Entry<Tennant, Integer> remainingPayment: tennantPaymentsMapping.entrySet()){
            if(remainingPayment.getValue() != 0){

            }
        }
        return payments;
    }


}
