/**
 * Created by claudio.david on 02/02/2015.
 */

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PaymentsManagementCalculatorTest {

    private Map<Tenant,Integer> setupPaymentMapFor3Tenants(int paidByBogdan, int paidByClaudio, int paidByDan){
        Map<Tenant,Integer> mapping = new HashMap<Tenant,Integer>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        mapping.put(Tenant.DAN, paidByDan);
        return mapping;
    }

    private Map<Tenant,Integer> setupPaymentMapFor2Tenants(int paidByBogdan, int paidByClaudio){
        Map<Tenant,Integer> mapping = new HashMap<Tenant,Integer>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        return mapping;
    }

    @Test
    public void computeForEqualPayments(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(20, 20, 20);
        Map<Tenant,Integer> expectedPaymentRemaining = setupPaymentMapFor3Tenants(0, 0, 0);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeForEqualPayments",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
        assertEquals("computeForEqualPayments", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForNonEqualPayments(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(30, 20, 40);
        Map<Tenant,Integer> expectedPaymentRemaining = setupPaymentMapFor3Tenants(0, 10, -10);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeForNonEqualPayments",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
    }

    @Test
    public void computeFor2NonEqualPayment(){
        Map<Tenant,Integer> payments = setupPaymentMapFor2Tenants(30, 20);
        Map<Tenant,Integer> expectedPaymentRemaining = setupPaymentMapFor2Tenants(-5, 5);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(5));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeFor2NonEqualPayment",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
        //assertEquals("computeFor2NonEqualPayment", expectedPayments, paymentsManagementCalculator.computePaymentsList());

    }

}
