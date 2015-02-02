/**
 * Created by claudio.david on 02/02/2015.
 */

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ThreeSideCalcTest {

    @Test
    public void computeForZeroContribution(){

        Map<Tennant,Integer> payments = new HashMap<Tennant,Integer>();
        payments.put(Tennant.CLAUDIO,0);
        payments.put(Tennant.BOGDAN,0);
        payments.put(Tennant.DAN,0);
        Map<Tennant,Integer> expectedContrib = new HashMap<Tennant,Integer>();
        expectedContrib.put(Tennant.CLAUDIO,0);
        expectedContrib.put(Tennant.BOGDAN,0);
        expectedContrib.put(Tennant.DAN,0);

        ThreeSideCalc threeSideCalc = new ThreeSideCalc(payments);

        assertEquals(expectedContrib,threeSideCalc.computeResult());

    }

    @Test
    public void computeForEqualContribution(){

    }

}
