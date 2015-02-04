import java.util.*;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Integer> tennantPaymentsMapping;
    private Integer contributionNeeded;

    private Map<Tenant, Integer> paymentsToSend = new HashMap<Tenant, Integer>();
    private Map<Tenant, Integer> paymentsToReceive = new HashMap<Tenant, Integer>();

    public PaymentsManagementCalculator(Map<Tenant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
        if(tennantPaymentsMapping != null && !tennantPaymentsMapping.isEmpty()){
            contributionNeeded = computeContribution();
            computeRemainingPayments();
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

    private void computeRemainingPayments(){
        for(Map.Entry<Tenant, Integer> payment: tennantPaymentsMapping.entrySet()){
            Integer remainingPayment = contributionNeeded - payment.getValue();
            if(remainingPayment > 0){
                paymentsToSend.put(payment.getKey(), remainingPayment);
            }
            if(remainingPayment < 0){
                paymentsToReceive.put(payment.getKey(), Math.abs(remainingPayment));
            }
        }
    }

    public Set<Payment> computePaymentsList(){
        Set<Payment> payments = new HashSet<Payment>();
        if(contributionNeeded == 0 || (paymentsToReceive.isEmpty() && paymentsToSend.isEmpty() )){
            return payments;
        }
        for(Map.Entry<Tenant, Integer> paymentToSend : paymentsToSend.entrySet()){
            for(Map.Entry<Tenant, Integer> paymentToReceive : paymentsToReceive.entrySet()){
                Integer balance = paymentToSend.getValue() - paymentToReceive.getValue();
                if(balance == 0){
                    payments.add(new Payment()
                            .addAmount(paymentToSend.getValue())
                            .addPaymentSender(paymentToSend.getKey())
                            .addPaymentReceiver(paymentToReceive.getKey()));
                    paymentsToSend.remove(paymentToSend.getKey());
                    paymentsToReceive.remove(paymentToReceive.getKey());
                }
            }
        }
        return payments;
    }
}