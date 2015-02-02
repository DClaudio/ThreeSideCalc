import java.util.HashMap;
import java.util.Map;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class ThreeSideCalc {

    private Map<Tennant, Integer> tennantPaymentsMapping;

    public ThreeSideCalc(Map<Tennant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
    }

    public Integer computeContribution(){
        Integer total = 0;
        for(Integer payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return total/tennantPaymentsMapping.size();
    }
}
