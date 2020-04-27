package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiGraphDataPointWrapper {
    public long semesterCode;
    public long totalCoursesTaken;


    public ApiGraphDataPointWrapper(long semesterCode, long totalCoursesTaken) {
        this.semesterCode = semesterCode;
        this.totalCoursesTaken = totalCoursesTaken;
    }
}
