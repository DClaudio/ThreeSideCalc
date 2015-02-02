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

    private Map<Tenant,Integer> setupMap(int bogdan, int claudio, int dan){
        Map<Tenant,Integer> mapping = new HashMap<Tenant,Integer>();
        mapping.put(Tenant.BOGDAN, bogdan);
        mapping.put(Tenant.CLAUDIO, claudio);
        mapping.put(Tenant.DAN, dan);
        return mapping;
    }

    @Test
    public void computeForEqualPayments(){
        Map<Tenant,Integer> payments = setupMap(20, 20, 20);
        Map<Tenant,Integer> expectedPaymentRemaining = setupMap(0, 0, 0);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
        assertEquals("", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForInequalPayments(){
        Map<Tenant,Integer> payments = setupMap(30, 20, 40);
        Map<Tenant,Integer> expectedPaymentRemaining = setupMap(0, 10, -10);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        expectedPayments.add(new Payment(Tenant.DAN, Tenant.CLAUDIO, 10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
    }

}
