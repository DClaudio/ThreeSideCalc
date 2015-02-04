package domain;

/**
 * Created by claudio.david on 02/02/2015.
 */
public enum Tenant {
    BOGDAN ("Bogdan"),
    CLAUDIO ("Claudio"),
    DAN ("Dan");

    private String name;

    Tenant(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
