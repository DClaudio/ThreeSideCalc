package logic;

import domain.Payment;
import domain.Tenant;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, BigDecimal> tenantPaymentsMapping;

    private Map<Tenant, BigDecimal> paymentsToSend = new ConcurrentHashMap<>();
    private Map<Tenant, BigDecimal> paymentsToReceive = new ConcurrentHashMap<>();

    public PaymentsManagementCalculator(Map<Tenant, BigDecimal> tenantPaymentsMapping) {
        this.tenantPaymentsMapping = tenantPaymentsMapping;
    }

    private Optional<BigDecimal> computeContribution(Map<Tenant, BigDecimal> tenantPaymentsMapping){
        BigDecimal totalPaymentsPerProperty = tenantPaymentsMapping.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Optional<BigDecimal> paymentPerTennant = totalPaymentsPerProperty.equals(BigDecimal.ZERO)
                ? Optional.empty()
                : Optional.of(totalPaymentsPerProperty.divide(valueOf(tenantPaymentsMapping.size()), 2, BigDecimal.ROUND_HALF_DOWN));
        System.out.println("Total Pay: " + totalPaymentsPerProperty);
        System.out.println("Total per tenant: " + paymentPerTennant);
        return paymentPerTennant;
    }

    private void computeRemainingPayments(Map<Tenant, BigDecimal> tenantPaymentsMapping, Optional<BigDecimal> contributionNeeded){
        for(Map.Entry<Tenant, BigDecimal> payment: tenantPaymentsMapping.entrySet()){
            BigDecimal remainingPayment = contributionNeeded
                    .map(bd -> bd.subtract(payment.getValue()))
                    .orElse(ZERO);
            if(remainingPayment.compareTo(BigDecimal.ZERO) == 1){
                paymentsToSend.put(payment.getKey(), remainingPayment);
            }
            if(remainingPayment.compareTo(BigDecimal.ZERO)  == -1){
                paymentsToReceive.put(payment.getKey(), remainingPayment.abs());
            }
        }
    }

    public Set<Payment> computePaymentsList(){
        Optional<BigDecimal> contributionNeeded = computeContribution(tenantPaymentsMapping);
        computeRemainingPayments(tenantPaymentsMapping, contributionNeeded);
        Set<Payment> payments = new HashSet<>();
        if(!contributionNeeded.isPresent() || (paymentsToReceive.isEmpty() && paymentsToSend.isEmpty() )){
            return payments;
        }
        for(Map.Entry<Tenant, BigDecimal> paymentToSend : paymentsToSend.entrySet()){
            for(Map.Entry<Tenant, BigDecimal> paymentToReceive : paymentsToReceive.entrySet()){
                Payment payment = new Payment().withSender(paymentToSend.getKey())
                                                .withReceiver(paymentToReceive.getKey());
                paymentsToSend.remove(paymentToSend.getKey());
                paymentsToReceive.remove(paymentToReceive.getKey());

                BigDecimal remainingBalance = paymentToSend.getValue().subtract(paymentToReceive.getValue());
                if(remainingBalance.compareTo(BigDecimal.ZERO) >= 0){
                    payment.withAmount(paymentToReceive.getValue().setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    if(!remainingBalance.equals(BigDecimal.ZERO))
                        paymentsToSend.put(paymentToSend.getKey(), remainingBalance);
                }
                if(remainingBalance.compareTo(BigDecimal.ZERO) == -1){
                    payment.withAmount(paymentToSend.getValue().setScale(2, BigDecimal.ROUND_HALF_DOWN));
                    paymentsToReceive.put(paymentToReceive.getKey(), remainingBalance.abs());
                }
                payments.add(payment);
            }
        }
        return payments;
    }
}