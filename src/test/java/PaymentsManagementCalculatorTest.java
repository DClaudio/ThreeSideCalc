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
    public void computeForEqualPaymentsTest(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(20, 20, 20);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeForEqualPayments", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor2NonEqualPaymentTest(){
        Map<Tenant,Integer> payments = setupPaymentMapFor2Tenants(30, 20);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(5));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for two tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForNonEqualPaymentsTest(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(30, 20, 40);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }


    @Test
         public void computeFor3NonEqualPaymentTest(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(20, 20, 50);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10));
        expectedPayments.add(new Payment().addPaymentSender(Tenant.BOGDAN).addPaymentReceiver(Tenant.DAN).addAmount(10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor3NonEqualPaymentTest2(){
        Map<Tenant,Integer> payments = setupPaymentMapFor3Tenants(40, 10, 40);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10));
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants 2 - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

}
