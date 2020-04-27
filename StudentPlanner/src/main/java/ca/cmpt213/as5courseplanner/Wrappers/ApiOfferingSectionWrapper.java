package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiOfferingSectionWrapper {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;


    public ApiOfferingSectionWrapper(String type, int enrollmentCap, int enrollmentTotal) {
        this.type = type;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
    }
}
