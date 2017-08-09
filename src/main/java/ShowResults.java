import domain.Payment;
import domain.Tenant;
import logic.PaymentsManagementCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by claudio.david on 04/02/2015.
 */
public class ShowResults {

    public static void main(String... args) {

        Map<Tenant, BigDecimal> tenantPaymentsMapping = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            for (Tenant tenant : Tenant.values()) {
                BigDecimal value = getValueFromUserInput(tenant, reader);
                tenantPaymentsMapping.put(tenant, value);
            }

            System.out.println("Bill payments are:  " + tenantPaymentsMapping.toString());
            PaymentsManagementCalculator pmc = new PaymentsManagementCalculator(tenantPaymentsMapping);
            Set<Payment> payments = pmc.computePaymentsList();
            if (payments.isEmpty()) {
                System.out.println("No payments required");
            } else {
                System.out.println("The following payments are required: \n " + payments.toString());
            }
        } catch (TooManyUserInputAtemptsException e) {
            System.err.println("Execution failed for the following reason: " + e.getMessage());
        }

    }

    private static BigDecimal getValueFromUserInput(Tenant tenant, BufferedReader reader) throws TooManyUserInputAtemptsException {
        int atemptsRemaining = 3;
        BigDecimal userInput = null;
        while (userInput == null && atemptsRemaining-- > 0) {
            System.out.print("Total payments from " + tenant.getName() + " = ");
            try {
                userInput = new BigDecimal(reader.readLine());
                if (userInput.compareTo(BigDecimal.ZERO) == -1) {
                    userInput = null;
                    throw new NumberFormatException("value can't be negative");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid input from user: " + nfe.getMessage());
            } catch (IOException e) {
                System.err.println("Error occured : " + e.getMessage());
                e.printStackTrace();
            }
        }
        if (userInput == null)
            throw new TooManyUserInputAtemptsException("Too many atempts for reading input value for " + tenant.getName());
        return userInput;
    }
}
