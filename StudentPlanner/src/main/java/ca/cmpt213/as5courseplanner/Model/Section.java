package ca.cmpt213.as5courseplanner.Model;

/** Brandon Yip 301294186
 * Class that keeps track of enrollmentTotal/enrollmentCapacity for various CourseOfferings that
 * are present in Courses, which are then present in Departments.
 */


public class Section {
    private String type;
    private int enrollmentTotal;
    private int enrollmentCap;


    public Section(int enrollmentTotal, int enrollmentCap, String type) {
        this.enrollmentTotal = enrollmentTotal;
        this.enrollmentCap = enrollmentCap;
        this.type = type;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public void setEnrollmentTotal(int enrollmentTotal) {
        this.enrollmentTotal = enrollmentTotal;
    }

    public int getEnrollmentCapacity() {
        return enrollmentCap;
    }

    public void setEnrollmentCapacity(int enrollmentCapacity) {
        this.enrollmentCap = enrollmentCapacity;
    }

    public String getType() {
        return type;
    }
}
