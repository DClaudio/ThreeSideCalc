import java.util.*;

/**
 * Created by claudio.david on 02/02/2015.
 */
public class PaymentsManagementCalculator {

    private Map<Tenant, Integer> tennantPaymentsMapping;

    public PaymentsManagementCalculator(Map<Tenant, Integer> tennantPaymentsMapping) {
        this.tennantPaymentsMapping = tennantPaymentsMapping;
    }

    private Integer computeContribution(){
        Integer total = 0;
        for(Integer payment: tennantPaymentsMapping.values()){
            total+=payment;
        }
        return total/tennantPaymentsMapping.size();
    }

    public Map<Tenant, Integer> computeRemainingPayments(){
        Map<Tenant, Integer> remainingPayments = new HashMap<Tenant, Integer>();
        for(Map.Entry<Tenant, Integer> payment: tennantPaymentsMapping.entrySet()){
            remainingPayments.put(payment.getKey(), computeContribution() - payment.getValue());
        }

        return sortByValue(remainingPayments);
    }

    public Set<Payment> computePaymentsList(){
        Set<Payment> payments = new HashSet<Payment>();
        Map<Tenant, Integer> remainingPayments = computeRemainingPayments();


        return payments;
    }

    private Map<Tenant,Integer> sortByValue(Map<Tenant,Integer> map){
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator(){
            @Override
            public int compare(Object o1, Object o2){
                return ( (Comparable) ((Map.Entry)(o2)).getValue()).compareTo( ((Map.Entry)(o1)).getValue() );
            }
        });

        Map result = new LinkedHashMap();
        for(Iterator it = list.iterator(); it.hasNext();){
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}