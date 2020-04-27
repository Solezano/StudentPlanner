package ca.cmpt213.as5courseplanner.Model;

import java.util.ArrayList;
import java.util.List;

/** Brandon Yip 301294186
 * CourseOfferings class manages the identification numbers for each one of its corresponding
 * Sections, along with providing functionality to access its various fields,
 * such as its available Sections and so forth. Also properly manages the instructors present.
 */


public class CourseOffering {
    private long courseOfferingId;
    private long semesterCode;
    private int year;
    private String term;
    private Semester semester;
    private String location;
    private List<Instructor> allInstructors = new ArrayList<>();
    private List<Section> allSections = new ArrayList<>();
    String instructors = "";


    public CourseOffering(int courseOfferingId, int semesterCode, String location) {
        this.courseOfferingId = courseOfferingId;
        this.semesterCode = semesterCode;
        this.location = location;
        this.semester = new Semester(semesterCode);
//        this.year = this.semester.getYearOfSemester();
        this.year = alterYear();
        this.term = this.semester.getCurrentSemester().name();
    }

    public int alterYear() {
        int yearOfSemester = this.semester.getYearOfSemester();
        int hundreds = 100 * ((yearOfSemester / 100) % 10);
        int tens = 10 * ((yearOfSemester / 10) % 10);
        int ones = 1 * ((yearOfSemester / 1) % 10);
        int finalYear = 1900 + hundreds + tens + ones;
        return finalYear;
    }

    public long getCourseOfferingID() {
        return courseOfferingId;
    }

    public long getSemesterCode() {
        return semesterCode;
    }

    public String getLocation() {
        return location;
    }

    public List<Instructor> getInstructorList() {
        return allInstructors;
    }

    public List<Section> getAllSections() {
        return allSections;
    }

    public String getInstructorString() {
        return instructors;
    }

    public void setInstructorString(String instructors) {
        this.instructors = instructors.replace("\"", "");
    }

    public int getYear() {
        return year;
    }

    public String getTerm() {
        return term;
    }

    @Override
    public String toString() {
        return "CourseOffering{" +
                "courseOfferingId=" + courseOfferingId +
                ", semesterCode=" + semesterCode +
                ", year=" + year +
                ", term='" + term + '\'' +
                ", location='" + location + '\'' +
                ", instructors='" + instructors + '\'' +
                '}';
    }
}