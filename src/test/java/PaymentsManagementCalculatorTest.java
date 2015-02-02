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

    private Map<Tennant,Integer> setupMap(int bogdan, int claudio, int dan){
        Map<Tennant,Integer> mapping = new HashMap<Tennant,Integer>();
        mapping.put(Tennant.BOGDAN, bogdan);
        mapping.put(Tennant.CLAUDIO, claudio);
        mapping.put(Tennant.DAN, dan);
        return mapping;
    }

    @Test
    public void computeForEqualPayments(){
        Map<Tennant,Integer> payments = setupMap(20, 20, 20);
        Map<Tennant,Integer> expectedPaymentRemaining = setupMap(0, 0, 0);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
        assertEquals("", expectedPayments, paymentsManagementCalculator.computePaymentsList());
    }

    @Test
    public void computeForInequalPayments(){
        Map<Tennant,Integer> payments = setupMap(30, 20, 40);
        Map<Tennant,Integer> expectedPaymentRemaining = setupMap(0, 10, -10);
        List<Payment> expectedPayments = new ArrayList<Payment>();
        expectedPayments.add(new Payment(Tennant.DAN,Tennant.CLAUDIO, 10));
        PaymentsManagementCalculator paymentsManagementCalculator = new PaymentsManagementCalculator(payments);
        assertEquals("",expectedPaymentRemaining, paymentsManagementCalculator.computeRemainingPayments());
    }

}