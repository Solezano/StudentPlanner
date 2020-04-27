package ca.cmpt213.as5courseplanner.Model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

/** Brandon Yip 301294186
 * Course class manages the identification numbers for each one of its corresponding Offerings,
 * along with providing functionality to access its various fields, such as its available Course
 * Offerings and so forth.
 */


public class Course {
    private String catalogNumber;
    private long courseId;
    private AtomicInteger courseOfferingID = new AtomicInteger(1111);
    private List<CourseOffering> allCourseOfferings = new ArrayList<>();


    public Course(int courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }

    public long getCourseID() {
        return courseId;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<CourseOffering> getAllCourseOfferings() {
        return allCourseOfferings;
    }

    public AtomicInteger getCourseOfferingID() {
        return courseOfferingID;
    }
}
