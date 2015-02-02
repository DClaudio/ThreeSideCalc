/**
 * Created by claudio.david on 02/02/2015.
 */

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ThreeSideCalcTest {

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
        Integer expectedContribution = 20;
        ThreeSideCalc threeSideCalc = new ThreeSideCalc(payments);
        assertEquals("",expectedContribution,threeSideCalc.computeContribution());

    }

    @Test
    public void computeForInequalPayments(){
        Map<Tennant,Integer> payments = setupMap(30, 20, 40);
        Integer expectedContribution = 30;
        ThreeSideCalc threeSideCalc = new ThreeSideCalc(payments);
        assertEquals("",expectedContribution,threeSideCalc.computeContribution());
    }

}
