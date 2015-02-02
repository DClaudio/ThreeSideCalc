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

    public Map<Tennant, Integer> computeResult(){
        Map<Tennant, Integer> contributions = new HashMap<Tennant, Integer>();
        contributions.put(Tennant.CLAUDIO,0);
        contributions.put(Tennant.BOGDAN,0);
        contributions.put(Tennant.DAN,0);
        return contributions;
    }
}
