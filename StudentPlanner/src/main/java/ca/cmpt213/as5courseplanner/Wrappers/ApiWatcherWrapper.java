package ca.cmpt213.as5courseplanner.Wrappers;

import ca.cmpt213.as5courseplanner.Model.Department;
import ca.cmpt213.as5courseplanner.Model.Course;

import java.util.ArrayList;
import java.util.List;

public class ApiWatcherWrapper {
    private long id;
    private ApiDepartmentWrapper department;
    private ApiCourseWrapper course;
    private List<String> events = new ArrayList<>();


    public ApiWatcherWrapper(long watcherId, long departmentId, long courseId,
                             List<Department> allDepartments) {
        this.id = watcherId;
        department = new ApiDepartmentWrapper(departmentId,
                findDepartment(allDepartments, departmentId));
        course = new ApiCourseWrapper(courseId,
                findCourse(allDepartments, departmentId, courseId));
    }

    public String findDepartment(List<Department> allDepartments, long departmentId) {
        for (Department currentDepartment : allDepartments) {
            if (currentDepartment.getDepartmentID() == departmentId) {
                return currentDepartment.getDepartmentName();
            }
        }
        return null;
    }

    public String findCourse(List<Department> allDepartments, long departmentId,
                           long courseId) {
        for (Department currentDepartment : allDepartments) {
            for (Course currentCourse : currentDepartment.getAllCourses()) {
                if (currentDepartment.getDepartmentID() == departmentId &&
                        currentCourse.getCourseID() == courseId) {
                    return currentCourse.getCatalogNumber();
                }
            }
        }
        return null;
    }

    public long getId() {
        return id;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApiDepartmentWrapper getDepartment() {
        return department;
    }

    public void setDepartment(ApiDepartmentWrapper department) {
        this.department = department;
    }

    public ApiCourseWrapper getCourse() {
        return course;
    }

    public void setCourse(ApiCourseWrapper course) {
        this.course = course;
    }
}
