/**
 * Created by claudio.david on 02/02/2015.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PaymentsManagementCalculatorTest {

    private Map<Tenant,Integer> setupMapFor3Tenants(int paidByBogdan, int paidByClaudio, int paidByDan){
        Map<Tenant,Integer> mapping = new HashMap<Tenant,Integer>();
        mapping.put(Tenant.BOGDAN, paidByBogdan);
        mapping.put(Tenant.CLAUDIO, paidByClaudio);
        mapping.put(Tenant.DAN, paidByDan);
        return mapping;
    }

    @Test
    public void computeForEqualPayments(){
        Map<Tenant,Integer> payments = setupMapFor3Tenants(20, 20, 20);
        Map<Tenant,Integer> expectedPaymentRemaining = setupMapFor3Tenants(0, 0, 0);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
        assertEquals("", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForInequalPayments(){
        Map<Tenant,Integer> payments = setupMapFor3Tenants(30, 20, 40);
        Map<Tenant,Integer> expectedPaymentRemaining = setupMapFor3Tenants(0, 10, -10);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        expectedPayments.add(new Payment(Tenant.DAN, Tenant.CLAUDIO, 10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
    }

}
