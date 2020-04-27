package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiOfferingDataWrapper {
    private int semester;
    private String subjectName;
    private String catalogNumber;
    private String location;
    private int enrollmentCap;
    private int enrollmentTotal;
    private String component;
    private String instructor;


    public ApiOfferingDataWrapper(int semester, String subjectName, String catalogNumber, String location,
                                 int enrollmentCap, int enrollmentTotal, String component, String instructor) {
        this.semester = semester;
        this.subjectName = subjectName;
        this.catalogNumber = catalogNumber;
        this.location = location;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
        this.component = component;
        this.instructor = instructor;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
