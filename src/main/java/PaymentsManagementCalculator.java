import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Integer> tennantPaymentsMapping;
    private Integer contributionNeeded;

    private Map<Tenant, Integer> paymentsToSend = new ConcurrentHashMap<Tenant, Integer>();
    private Map<Tenant, Integer> paymentsToReceive = new ConcurrentHashMap<Tenant, Integer>();

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
                Payment payment = new Payment().addPaymentSender(paymentToSend.getKey())
                                                .addPaymentReceiver(paymentToReceive.getKey());
                paymentsToSend.remove(paymentToSend.getKey());
                paymentsToReceive.remove(paymentToReceive.getKey());

                Integer remainingBalance = paymentToSend.getValue() - paymentToReceive.getValue();
                if(remainingBalance >= 0){
                    payment.addAmount(paymentToReceive.getValue());
                    if(remainingBalance != 0)
                        paymentsToSend.put(paymentToSend.getKey(), remainingBalance);
                }
                if(remainingBalance < 0){
                    payment.addAmount(paymentToSend.getValue());
                    paymentsToReceive.put(paymentToReceive.getKey(), Math.abs(remainingBalance));
                }
                payments.add(payment);
            }
        }
        return payments;
    }
}