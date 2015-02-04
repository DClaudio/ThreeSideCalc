/**
 * Created by claudio.david on 02/02/2015.
 */

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PaymentsManagementCalculatorTest {

    private Map<Tenant,Double> setupPaymentMapFor3Tenants(double paidByBogdan, double paidByClaudio, double paidByDan){
        Map<Tenant,Double> mapping = new HashMap<Tenant,Double>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        mapping.put(Tenant.DAN, paidByDan);
        return mapping;
    }

    private Map<Tenant,Double> setupPaymentMapFor2Tenants(double paidByBogdan, double paidByClaudio){
        Map<Tenant,Double> mapping = new HashMap<Tenant,Double>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        return mapping;
    }

    @Test
    public void computeForEqualPaymentsTest(){
        Map<Tenant,Double> payments = setupPaymentMapFor3Tenants(20, 20, 20);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeForEqualPayments", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor2NonEqualPaymentTest(){
        Map<Tenant,Double> payments = setupPaymentMapFor2Tenants(30, 20);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(5.0));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for two tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForNonEqualPaymentsTest(){
        Map<Tenant,Double> payments = setupPaymentMapFor3Tenants(30, 20, 40);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10.0));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }


    @Test
         public void computeFor3NonEqualPaymentTest(){
        Map<Tenant,Double> payments = setupPaymentMapFor3Tenants(20, 20, 50);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10.0));
        expectedPayments.add(new Payment().addPaymentSender(Tenant.BOGDAN).addPaymentReceiver(Tenant.DAN).addAmount(10.0));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor3NonEqualPaymentTest2(){
        Map<Tenant,Double> payments = setupPaymentMapFor3Tenants(40, 10, 40);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(10.0));
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(10.0));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants 2 - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void realValuesTest(){
        Map<Tenant,Double> payments = setupPaymentMapFor3Tenants(203, 93, 150);
        Set<Payment> expectedPayments = new HashSet<Payment>();
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.DAN).addAmount(1.33));
        expectedPayments.add(new Payment().addPaymentSender(Tenant.CLAUDIO).addPaymentReceiver(Tenant.BOGDAN).addAmount(54.33));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("real values test", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

}
