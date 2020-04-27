package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiCourseWrapper {
    public long courseId;
    public String catalogNumber;


    public ApiCourseWrapper(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }
}
