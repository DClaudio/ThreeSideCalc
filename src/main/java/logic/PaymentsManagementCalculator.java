package logic;

import domain.Payment;
import domain.Tenant;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Double> tennantPaymentsMapping;
    private Double contributionNeeded;

    public Map<Tenant, Double> getTennantPaymentsMapping() {return tennantPaymentsMapping; }
    public Double getContributionNeeded() {return contributionNeeded;}

    private Map<Tenant, Double> paymentsToSend = new ConcurrentHashMap<Tenant, Double>();
    private Map<Tenant, Double> paymentsToReceive = new ConcurrentHashMap<Tenant, Double>();

    public PaymentsManagementCalculator(Map<Tenant, Double> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
        if(tennantPaymentsMapping != null && !tennantPaymentsMapping.isEmpty()){
            contributionNeeded = computeContribution();
            computeRemainingPayments();
        }else{
            contributionNeeded = new Double(0);
        }
    }

    private Double computeContribution(){
        Double total = new Double(0);
        for(Double payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return (total == 0) ? new Double(0) : total/tennantPaymentsMapping.size();
    }

    private void computeRemainingPayments(){
        for(Map.Entry<Tenant, Double> payment: tennantPaymentsMapping.entrySet()){
            Double remainingPayment = contributionNeeded - payment.getValue();
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
        for(Map.Entry<Tenant, Double> paymentToSend : paymentsToSend.entrySet()){
            for(Map.Entry<Tenant, Double> paymentToReceive : paymentsToReceive.entrySet()){
                Payment payment = new Payment().addPaymentSender(paymentToSend.getKey())
                                                .addPaymentReceiver(paymentToReceive.getKey());
                paymentsToSend.remove(paymentToSend.getKey());
                paymentsToReceive.remove(paymentToReceive.getKey());

                Double remainingBalance = paymentToSend.getValue() - paymentToReceive.getValue();
                if(remainingBalance >= 0){
                    payment.addAmount(new BigDecimal(String.valueOf(paymentToReceive.getValue())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    if(remainingBalance != 0)
                        paymentsToSend.put(paymentToSend.getKey(), remainingBalance);
                }
                if(remainingBalance < 0){
                    payment.addAmount(new BigDecimal(String.valueOf(paymentToSend.getValue())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    paymentsToReceive.put(paymentToReceive.getKey(), Math.abs(remainingBalance));
                }
                payments.add(payment);
            }
        }
        return payments;
    }
}