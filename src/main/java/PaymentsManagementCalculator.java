import java.util.*;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Integer> tennantPaymentsMapping;
    private Integer contributionNeeded;

    public PaymentsManagementCalculator(Map<Tenant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
        if(tennantPaymentsMapping != null && !tennantPaymentsMapping.isEmpty()){
            contributionNeeded = computeContribution();
        }else{
            contributionNeeded = new Integer(0);
        }
    }

    private Integer computeContribution(){
        Integer total = 0;
        for(Integer payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return (total == 0) ? new Integer(0) : total/tennantPaymentsMapping.size();
    }

    public Map<Tenant, Integer> computeRemainingPayments(){
        Map<Tenant, Integer> remainingPayments = new HashMap<Tenant, Integer>();
        for(Map.Entry<Tenant, Integer> payment: tennantPaymentsMapping.entrySet()){
            remainingPayments.put(payment.getKey(), contributionNeeded - payment.getValue());
        }
        return remainingPayments;
    }

    public Set<Payment> computePaymentsList(){
        Set<Payment> payments = new HashSet<Payment>();
        Tenant paymentReceiver = null;
        Map<Tenant, Integer> remainingPayments = computeRemainingPayments();
        for(Map.Entry<Tenant, Integer> remainingPayment: remainingPayments.entrySet()){
            if(remainingPayment.getValue() > 0){
                payments.add(new Payment().addAmount(remainingPayment.getValue()).addPaymentSender(remainingPayment.getKey()));
            }
            if(remainingPayment.getValue() < 0){
                paymentReceiver = remainingPayment.getKey();
            }
        }
        for(Payment payment: payments){
            payment.addPaymentReceiver(paymentReceiver);
        }
        return payments;
    }
}