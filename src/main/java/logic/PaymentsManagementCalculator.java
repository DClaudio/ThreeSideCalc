package logic;

import domain.Payment;
import domain.Tenant;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.math.BigDecimal.valueOf;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, BigDecimal> tenantPaymentsMapping;
    private BigDecimal contributionNeeded;

    public Map<Tenant, BigDecimal> getTenantPaymentsMapping() {return tenantPaymentsMapping; }
    public BigDecimal getContributionNeeded() {return contributionNeeded;}

    private Map<Tenant, BigDecimal> paymentsToSend = new ConcurrentHashMap<>();
    private Map<Tenant, BigDecimal> paymentsToReceive = new ConcurrentHashMap<>();

    public PaymentsManagementCalculator(Map<Tenant, BigDecimal> tenantPaymentsMapping) {
        this.tenantPaymentsMapping = tenantPaymentsMapping;
        if(tenantPaymentsMapping != null && !tenantPaymentsMapping.isEmpty()){
            contributionNeeded = computeContribution();
            computeRemainingPayments();
        }else{
            contributionNeeded = new BigDecimal(0);
        }
    }

    private BigDecimal computeContribution(){
        BigDecimal totalPaymentsPerProperty = tenantPaymentsMapping.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal paymentPerTennant = totalPaymentsPerProperty.equals(BigDecimal.ZERO)
                ? BigDecimal.ZERO
                : totalPaymentsPerProperty.divide(valueOf(tenantPaymentsMapping.size()), 2, BigDecimal.ROUND_HALF_DOWN);
        System.out.println("Total Pay: " + totalPaymentsPerProperty);
        System.out.println("Total per tenant: " + paymentPerTennant);
        return paymentPerTennant;
    }

    private void computeRemainingPayments(){
        for(Map.Entry<Tenant, BigDecimal> payment: tenantPaymentsMapping.entrySet()){
            BigDecimal remainingPayment = contributionNeeded.subtract(payment.getValue());
            if(remainingPayment.compareTo(BigDecimal.ZERO) == 1){
                paymentsToSend.put(payment.getKey(), remainingPayment);
            }
            if(remainingPayment.compareTo(BigDecimal.ZERO)  == -1){
                paymentsToReceive.put(payment.getKey(), remainingPayment.abs());
            }
        }
    }

    public Set<Payment> computePaymentsList(){
        Set<Payment> payments = new HashSet<>();
        if(contributionNeeded.equals(BigDecimal.ZERO) || (paymentsToReceive.isEmpty() && paymentsToSend.isEmpty() )){
            return payments;
        }
        for(Map.Entry<Tenant, BigDecimal> paymentToSend : paymentsToSend.entrySet()){
            for(Map.Entry<Tenant, BigDecimal> paymentToReceive : paymentsToReceive.entrySet()){
                Payment payment = new Payment().addPaymentSender(paymentToSend.getKey())
                                                .addPaymentReceiver(paymentToReceive.getKey());
                paymentsToSend.remove(paymentToSend.getKey());
                paymentsToReceive.remove(paymentToReceive.getKey());

                BigDecimal remainingBalance = paymentToSend.getValue().subtract(paymentToReceive.getValue());
                if(remainingBalance.compareTo(BigDecimal.ZERO) >= 0){
                    payment.addAmount(paymentToReceive.getValue().setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    if(!remainingBalance.equals(BigDecimal.ZERO))
                        paymentsToSend.put(paymentToSend.getKey(), remainingBalance);
                }
                if(remainingBalance.compareTo(BigDecimal.ZERO) == -1){
                    payment.addAmount(paymentToSend.getValue().setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    paymentsToReceive.put(paymentToReceive.getKey(), remainingBalance.abs());
                }
                payments.add(payment);
            }
        }
        return payments;
    }
}