package ca.cmpt213.as5courseplanner.Model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.List;

/** Brandon Yip 301294186
 * Class that effectively manages present Department that may be arise in a provided .csv file,
 * or by means of being dynamically added via REST API interface. Additionally stores list of Courses
 * that may fall underneath each respective department (i.e. CMPT 213, 276, etc...).
 */


public class Department {
    private long deptId;
    private String name;
    private List<Course> allCourses = new ArrayList<>();
    private AtomicInteger courseId = new AtomicInteger(111);

    public Department(String name, int deptId) {
        this.name = name;
        this.deptId = deptId;
    }

    public long getDepartmentID() {
        return deptId;
    }

    public String getDepartmentName() {
        return name;
    }

    public List<Course> getAllCourses() {
        return allCourses;
    }

    public AtomicInteger getCourseID() {
        return courseId;
    }
}
