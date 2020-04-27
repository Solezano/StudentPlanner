package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiCourseOfferingWrapper {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;


    public ApiCourseOfferingWrapper(long courseOfferingId, String location,
                                    String instructors, String term,
                                    long semesterCode, int year) {
        this.courseOfferingId = courseOfferingId;
        this.location = location;
        this.instructors = instructors.trim();
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
    }

    @Override
    public String toString() {
        return "ApiCourseOfferingWrapper{" +
                "courseOfferingId=" + courseOfferingId +
                ", location='" + location + '\'' +
                ", instructors='" + instructors + '\'' +
                ", term='" + term + '\'' +
                ", semesterCode=" + semesterCode +
                ", year=" + year +
                '}';
    }
}
