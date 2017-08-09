/**
 * Created by claudio.david on 02/02/2015.
 */

import domain.Payment;
import domain.Tenant;
import logic.PaymentsManagementCalculator;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static domain.Payment.newPayment;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

public class PaymentsManagementCalculatorTest {

    private Map<Tenant,BigDecimal> setupPaymentMapFor3Tenants(BigDecimal paidByBogdan, BigDecimal paidByClaudio, BigDecimal paidByDan){
        Map<Tenant,BigDecimal> mapping = new HashMap<>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        mapping.put(Tenant.DAN, paidByDan);
        return mapping;
    }

    private Map<Tenant,BigDecimal> setupPaymentMapFor2Tenants(BigDecimal paidByBogdan, BigDecimal paidByClaudio){
        Map<Tenant,BigDecimal> mapping = new HashMap<>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        return mapping;
    }

    @Test
    public void computeForEqualPaymentsTest(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor3Tenants(valueOf(20).setScale(2), valueOf(20).setScale(2), valueOf(20).setScale(2));
        Set<Payment> expectedPayments = new HashSet<>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("computeForEqualPayments", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor2NonEqualPaymentTest(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor2Tenants(valueOf(30).setScale(2), valueOf(20).setScale(2));
        Set<Payment> expectedPayments = new HashSet<>();
        expectedPayments.add(newPayment()
                .withSender(Tenant.CLAUDIO).withReceiver(Tenant.BOGDAN).withAmount(valueOf(5).setScale(2)));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for two tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForNonEqualPaymentsTest(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor3Tenants(valueOf(30).setScale(2).setScale(2), valueOf(20).setScale(2).setScale(2), valueOf(40).setScale(2).setScale(2).setScale(2));
        Set<Payment> expectedPayments = new HashSet<>();
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.DAN).withAmount(valueOf(10).setScale(2)));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }


    @Test
         public void computeFor3NonEqualPaymentTest(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor3Tenants(valueOf(20).setScale(2), valueOf(20).setScale(2), valueOf(50).setScale(2));
        Set<Payment> expectedPayments = new HashSet<>();
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.DAN).withAmount(valueOf(10).setScale(2)));
        expectedPayments.add(newPayment().withSender(Tenant.BOGDAN).withReceiver(Tenant.DAN).withAmount(valueOf(10).setScale(2)));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeFor3NonEqualPaymentTest2(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor3Tenants(valueOf(40).setScale(2).setScale(2), valueOf(10).setScale(2), valueOf(40).setScale(2).setScale(2));
        Set<Payment> expectedPayments = new HashSet<>();
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.DAN).withAmount(valueOf(10).setScale(2)));
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.BOGDAN).withAmount(valueOf(10).setScale(2)));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("Test non equal payments for 3 tenants 2 - computePaymentsList", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void realValuesTest(){
        Map<Tenant,BigDecimal> payments = setupPaymentMapFor3Tenants(valueOf(203), valueOf(93), valueOf(150));
        Set<Payment> expectedPayments = new HashSet<>();
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.DAN).withAmount(valueOf(1.33)));
        expectedPayments.add(newPayment().withSender(Tenant.CLAUDIO).withReceiver(Tenant.BOGDAN).withAmount(valueOf(54.33)));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("real values test", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

}
