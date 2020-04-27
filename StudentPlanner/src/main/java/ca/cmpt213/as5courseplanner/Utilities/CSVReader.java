package ca.cmpt213.as5courseplanner.Utilities;

import java.util.concurrent.atomic.AtomicInteger;
import ca.cmpt213.as5courseplanner.Model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.*;

/** Brandon Yip 301294186
 * Primary class that is solely responsible for extracting various rows of data from a given .csv
 * file, with structural design around it being a file containing Simon Fraser University's past
 * Sections, CourseOfferings for those Sections, Courses for those CourseOfferings, Departments for
 * those Courses, and etc.. Stores a list of Departments for convenient processing of data whilst
 * iterating through the file.
 */


public class CSVReader {
    public enum COURSE_STATUS {
        NEW_COURSE,
        SAME_COURSE_DIFFERENT_OFFERING,
        SAME_OFFERING_DIFFERENT_SECTION,
        SAME_SECTION
    }
    private enum COLUMNS {A, B, C, D, E, F, G, H}
    Map<COLUMNS, Integer> colToArrayIndexValue = new HashMap<>();
    private List<Instructor> currentRowInstructors = new ArrayList<>();
    private List<Department> allDepartments = new ArrayList<>();
    private String[] CSV_Array = new String[]{};

    private String courseDataFile = "data/course_data_2018.csv";
//    private String courseDataFile = "data/small_data.csv";
    private String departmentName;
    private String catalogNumber;
    private String campusLocation;
    private String instructorName;
    private String componentCode;

    AtomicInteger departmentID = new AtomicInteger(0);
    private final int COLUMNS_IF_SINGLE_INSTRUCTOR = 8;
    private int enrollmentCapacity = 0;
    private int enrollmentTotal = 0;
    private int semesterCode = 0;

    private CourseOffering currentRowCourseOffering;
    private CourseOffering newCourseOffering;
    private Department currentRowDepartment;
    private Section currentRowSection;
    private Section newSection;
    private Course newCourse;


    public CSVReader() {
        initializeColumnLetterToArrayIndexValue();
        parseData();
    }

    public void initializeColumnLetterToArrayIndexValue() {
        colToArrayIndexValue.put(COLUMNS.A, 0);
        colToArrayIndexValue.put(COLUMNS.B, 1);
        colToArrayIndexValue.put(COLUMNS.C, 2);
        colToArrayIndexValue.put(COLUMNS.D, 3);
        colToArrayIndexValue.put(COLUMNS.E, 4);
        colToArrayIndexValue.put(COLUMNS.F, 5);
        colToArrayIndexValue.put(COLUMNS.G, 6);
        colToArrayIndexValue.put(COLUMNS.H, 7);
    }

    public void parseData() {
        try {
            int currentInstructorIndex = colToArrayIndexValue.get(COLUMNS.G);
            int numberOfColumnsInCSV = 0;
            boolean firstIteration = true;
            COURSE_STATUS courseStatus;
            String currentCSVRow = "";

            BufferedReader newBufferedReader = new BufferedReader(new FileReader(new File(courseDataFile)));

            while ((currentCSVRow = newBufferedReader.readLine()) != null) {
                if (firstIteration) {
                    firstIteration = false;
                    continue;
                }

                CSV_Array = currentCSVRow.split(",");
                numberOfColumnsInCSV = CSV_Array.length;
                extractCSV_Data(numberOfColumnsInCSV, currentInstructorIndex);
                addDepartmentToListIf_DNE(departmentName);

                courseStatus = checkIfCourseExists(currentRowDepartment, catalogNumber,
                        semesterCode, campusLocation, componentCode);
                evaluateCourse(courseStatus);

                currentRowInstructors.clear();
            }
            newBufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void extractCSV_Data(int numberOfColumnsInCSV, int currentInstructorIndex) {
        Instructor currentInstructor;
        semesterCode = Integer.parseInt(CSV_Array[colToArrayIndexValue.get(COLUMNS.A)]);
        departmentName = CSV_Array[colToArrayIndexValue.get(COLUMNS.B)];
        catalogNumber = CSV_Array[colToArrayIndexValue.get(COLUMNS.C)];
        campusLocation = CSV_Array[colToArrayIndexValue.get(COLUMNS.D)];
        enrollmentCapacity = Integer.parseInt(CSV_Array[colToArrayIndexValue.get(COLUMNS.E)]);
        enrollmentTotal = Integer.parseInt(CSV_Array[colToArrayIndexValue.get(COLUMNS.F)]);
        instructorName = CSV_Array[currentInstructorIndex];

        currentInstructor = new Instructor(instructorName);
        currentRowInstructors.add(currentInstructor);

        if (numberOfColumnsInCSV > COLUMNS_IF_SINGLE_INSTRUCTOR) { //more than one instructor
            currentInstructorIndex++;
            while ((currentInstructorIndex - (numberOfColumnsInCSV - 1)) < 0) {
                instructorName = CSV_Array[currentInstructorIndex];
                currentInstructor = new Instructor(instructorName);
                currentRowInstructors.add(currentInstructor);
                currentInstructorIndex++;
            }
            componentCode = CSV_Array[numberOfColumnsInCSV - 1];
        } else {
            componentCode = CSV_Array[colToArrayIndexValue.get(COLUMNS.H)];
        }
    }

    public Department addDepartmentToListIf_DNE(String currentDepartmentName) {
        boolean departmentAlreadyExists = false;
        for (Department currentDepartment : allDepartments) {
            if (currentDepartment.getDepartmentName().equals(currentDepartmentName)) {
                departmentAlreadyExists = true;
                currentRowDepartment = currentDepartment;
                break;
            }
        }
        if (!departmentAlreadyExists) {
            currentRowDepartment = new Department(currentDepartmentName, departmentID.getAndIncrement());
            allDepartments.add(currentRowDepartment);
        }
        return currentRowDepartment;
    }

    public COURSE_STATUS checkIfCourseExists(Department currentRowDepartment, String catalogNumber,
                                             int semesterCode, String campusLocation, String componentCode) {
        COURSE_STATUS courseStatus = COURSE_STATUS.NEW_COURSE;
        List<Course> departmentCourseList = currentRowDepartment.getAllCourses();

        if (!departmentCourseList.isEmpty()) {
            for (Course currentCourse : currentRowDepartment.getAllCourses()) {
                if (currentCourse.getCatalogNumber().equals(catalogNumber)) {
                    courseStatus = COURSE_STATUS.SAME_COURSE_DIFFERENT_OFFERING;

                    for (CourseOffering currentCourseOffering : currentCourse.getAllCourseOfferings()) {
                        if (currentCourseOffering.getSemesterCode() == semesterCode &&
                                currentCourseOffering.getLocation().equals(campusLocation)) {
                            courseStatus = COURSE_STATUS.SAME_OFFERING_DIFFERENT_SECTION;
                            currentRowCourseOffering = currentCourseOffering;

                            for (Section currentSection : currentCourseOffering.getAllSections()) {
                                if (currentSection.getType().equals(componentCode)) {
                                    courseStatus = COURSE_STATUS.SAME_SECTION;
                                    currentRowSection = currentSection;
                                }
                            }
                        }
                    }
                }
            }
        }
        return courseStatus;
    }

    public void evaluateCourse(COURSE_STATUS courseStatus) {
        if (courseStatus.equals(COURSE_STATUS.SAME_SECTION)) {
            aggregateEnrollment(enrollmentTotal, enrollmentCapacity);
            String instructorNamesCommaSeparated = updateInstructorsForSameSection(currentRowInstructors);
            currentRowCourseOffering.setInstructorString(instructorNamesCommaSeparated);

        } else if (courseStatus.equals(COURSE_STATUS.NEW_COURSE)) {
            addCourseToDepartment(currentRowDepartment, catalogNumber, semesterCode, campusLocation,
                    enrollmentTotal, enrollmentCapacity, componentCode);
        } else if (courseStatus.equals(COURSE_STATUS.SAME_COURSE_DIFFERENT_OFFERING)) {
            addCourseOfferingToCourse(departmentName, catalogNumber, enrollmentTotal, enrollmentCapacity,
                    componentCode, semesterCode, campusLocation);
        } else { //SAME_OFFERING_DIFFERENT_SECTION
            addSectionToCourseOffering(enrollmentTotal, enrollmentCapacity, componentCode);
        }
    }

    public void REST_evaluateCourse(COURSE_STATUS courseStatus, Department currentRowDepartment,
                                    String departmentName, String catalogNumber, int semesterCode,
                                    String campusLocation, int enrollmentTotal, int enrollmentCapacity,
                                    String componentCode, String instructorName) {
        if (courseStatus.equals(COURSE_STATUS.SAME_SECTION)) {
            aggregateEnrollment(enrollmentTotal, enrollmentCapacity);
            String instructorNamesCommaSeparated = REST_updateInstructorsForSameSection(instructorName);
            currentRowCourseOffering.setInstructorString(instructorNamesCommaSeparated);

        } else if (courseStatus.equals(COURSE_STATUS.NEW_COURSE)) {
            REST_addCourseToDepartment(currentRowDepartment, catalogNumber, semesterCode, campusLocation,
                    enrollmentTotal, enrollmentCapacity, componentCode, instructorName);
        } else if (courseStatus.equals(COURSE_STATUS.SAME_COURSE_DIFFERENT_OFFERING)) {
            REST_addCourseOfferingToCourse(departmentName, catalogNumber, enrollmentTotal, enrollmentCapacity,
                    componentCode, semesterCode, campusLocation, instructorName);
        } else { //SAME_OFFERING_DIFFERENT_SECTION
            addSectionToCourseOffering(enrollmentTotal, enrollmentCapacity, componentCode);
        }
    }

    public void aggregateEnrollment(int enrollmentTotal, int enrollmentCapacity) {
        int currentSectionEnrollmentCapacity = currentRowSection.getEnrollmentCapacity();
        int currentSectionEnrollmentTotal = currentRowSection.getEnrollmentTotal();
        currentRowSection.setEnrollmentTotal(currentSectionEnrollmentTotal + enrollmentTotal);
        currentRowSection.setEnrollmentCapacity(currentSectionEnrollmentCapacity + enrollmentCapacity);
    }

    public void addCourseToDepartment(Department currentRowDepartment, String catalogNumber,
                                      int semesterCode, String campusLocation, int enrollmentTotal,
                                      int enrollmentCapacity, String componentCode) {
        List<Course> currentRowDepartmentCourses = currentRowDepartment.getAllCourses();
        newCourse = new Course(currentRowDepartment.getCourseID().getAndIncrement(), catalogNumber);
        List<CourseOffering> currentRowCourse_CourseOfferings = newCourse.getAllCourseOfferings();
        newCourseOffering =
                new CourseOffering(newCourse.getCourseOfferingID().getAndIncrement(), semesterCode, campusLocation);
        List<Section> currentRowCourseOfferingSections = newCourseOffering.getAllSections();
        newSection = new Section(enrollmentTotal, enrollmentCapacity, componentCode);

        currentRowCourseOfferingSections.add(newSection);
        newCourseOffering.setInstructorString(assignInstructors());
        currentRowCourse_CourseOfferings.add(newCourseOffering);
        currentRowDepartmentCourses.add(newCourse);
        return;
    }

    public void REST_addCourseToDepartment(Department currentRowDepartment, String catalogNumber,
                                      int semesterCode, String campusLocation, int enrollmentTotal,
                                      int enrollmentCapacity, String componentCode, String instructorName) {
        List<Course> currentRowDepartmentCourses = currentRowDepartment.getAllCourses();
        newCourse = new Course(currentRowDepartment.getCourseID().getAndIncrement(), catalogNumber);
        List<CourseOffering> currentRowCourse_CourseOfferings = newCourse.getAllCourseOfferings();
        newCourseOffering =
                new CourseOffering(newCourse.getCourseOfferingID().getAndIncrement(), semesterCode, campusLocation);
        List<Section> currentRowCourseOfferingSections = newCourseOffering.getAllSections();
        newSection = new Section(enrollmentTotal, enrollmentCapacity, componentCode);

        currentRowCourseOfferingSections.add(newSection);
        newCourseOffering.setInstructorString(instructorName);
        currentRowCourse_CourseOfferings.add(newCourseOffering);
        currentRowDepartmentCourses.add(newCourse);
        return;
    }

    public void addCourseOfferingToCourse(String departmentName, String catalogNumber,
                                          int enrollmentTotal, int enrollmentCapacity,
                                          String componentCode, int semesterCode,
                                          String campusLocation) {
        Course currentRowCourse = findCourseObject(departmentName, catalogNumber);
        newSection = new Section(enrollmentTotal, enrollmentCapacity, componentCode);
        newCourseOffering = new CourseOffering(
                currentRowCourse.getCourseOfferingID().getAndIncrement(),
                semesterCode,
                campusLocation);
        newCourseOffering.setInstructorString(assignInstructors());

        List<Section> currentRowSectionOfferings = newCourseOffering.getAllSections();
        currentRowSectionOfferings.add(newSection);

        List<CourseOffering> currentRowCourseOfferings = currentRowCourse.getAllCourseOfferings();
        currentRowCourseOfferings.add(newCourseOffering);
    }

    public void REST_addCourseOfferingToCourse(String departmentName, String catalogNumber,
                                          int enrollmentTotal, int enrollmentCapacity,
                                          String componentCode, int semesterCode,
                                          String campusLocation, String instructorName) {
        Course currentRowCourse = findCourseObject(departmentName, catalogNumber);
        newSection = new Section(enrollmentTotal, enrollmentCapacity, componentCode);
        newCourseOffering = new CourseOffering(
                currentRowCourse.getCourseOfferingID().getAndIncrement(),
                semesterCode,
                campusLocation);
        newCourseOffering.setInstructorString(instructorName);

        List<Section> currentRowSectionOfferings = newCourseOffering.getAllSections();
        currentRowSectionOfferings.add(newSection);

        List<CourseOffering> currentRowCourseOfferings = currentRowCourse.getAllCourseOfferings();
        currentRowCourseOfferings.add(newCourseOffering);
    }

    public void addSectionToCourseOffering(int enrollmentTotal, int enrollmentCapacity, String componentCode) {
        newSection = new Section(enrollmentTotal, enrollmentCapacity, componentCode);
        List<Section> currentRowSectionOfferings = currentRowCourseOffering.getAllSections();
        currentRowSectionOfferings.add(newSection);
    }

    public String updateInstructorsForSameSection(List<Instructor> currentRowInstructors) {
        List<Instructor> currentCourseOfferingInstructorList = currentRowCourseOffering.getInstructorList();
        List<Instructor> instructorsToBeAdded = new ArrayList<>();
        List<String> instructorNames = new ArrayList<>();
        String instructorNamesCommaSeparated = "";
        boolean instructorAlreadyExists = false;

        for (Instructor currentInstructor : currentRowInstructors) {
            for (Instructor existingInstructor : currentCourseOfferingInstructorList) {
                if (currentInstructor.getInstructorName().equals(existingInstructor.getInstructorName())) {
                    instructorAlreadyExists = true;
                }
            }
            if (!instructorAlreadyExists) {
                instructorsToBeAdded.add(currentInstructor);
            }
            instructorAlreadyExists = false;
        }

        if (!instructorsToBeAdded.isEmpty()) {
            for (Instructor currentInstructor : instructorsToBeAdded) {
                currentCourseOfferingInstructorList.add(currentInstructor);
            }
        }
        for (Instructor currentInstructor : currentCourseOfferingInstructorList) {
            instructorNames.add(currentInstructor.getInstructorName());
        }
        instructorNamesCommaSeparated = String.join(",", instructorNames);
        return instructorNamesCommaSeparated;
    }

    public String REST_updateInstructorsForSameSection(String instructorName) {
        List<Instructor> currentCourseOfferingInstructorList = currentRowCourseOffering.getInstructorList();
        List<Instructor> instructorNameAsList = new ArrayList<>();
        List<String> instructorNamesAsStringList =
                new ArrayList<>(Arrays.asList(instructorName.split(",")));
        List<Instructor> instructorsToBeAdded = new ArrayList<>();
        List<String> instructorNames = new ArrayList<>();
        String instructorNamesCommaSeparated = "";
        boolean instructorAlreadyExists = false;

        for (String currentInstructorName : instructorNamesAsStringList) {
            Instructor newInstructor = new Instructor(currentInstructorName);
            instructorNameAsList.add(newInstructor);
        }

        for (Instructor currentInstructor : instructorNameAsList) {
            for (Instructor existingInstructor : currentCourseOfferingInstructorList) {
                if (currentInstructor.getInstructorName().equals(existingInstructor.getInstructorName())) {
                    instructorAlreadyExists = true;
                }
            }
            if (!instructorAlreadyExists) {
                instructorsToBeAdded.add(currentInstructor);
            }
            instructorAlreadyExists = false;
        }

        if (!instructorsToBeAdded.isEmpty()) {
            for (Instructor currentInstructor : instructorsToBeAdded) {
                currentCourseOfferingInstructorList.add(currentInstructor);
            }
        }
        for (Instructor currentInstructor : currentCourseOfferingInstructorList) {
            instructorNames.add(currentInstructor.getInstructorName());
        }
        instructorNamesCommaSeparated = String.join(",", instructorNames);
        return instructorNamesCommaSeparated;
    }

    public String assignInstructors() {
        List<Instructor> currentCourseOfferingInstructorList = this.newCourseOffering.getInstructorList();
        List<String> instructorNames = new ArrayList<>();
        for (Instructor currentInstructor : currentRowInstructors) {
            instructorNames.add(currentInstructor.getInstructorName());
            currentCourseOfferingInstructorList.add(currentInstructor);
        }
        String instructorNamesCommaSeparated = String.join(",", instructorNames);
        return instructorNamesCommaSeparated;
    }

    public Course findCourseObject(String currentDepartmentName, String catalogNumber) {
        for (Department currentDepartment : allDepartments) {
            if (currentDepartment.getDepartmentName().equals(currentDepartmentName)) {
                for (Course currentCourse : currentDepartment.getAllCourses()) {
                    if (currentCourse.getCatalogNumber().equals(catalogNumber)) {
                        return currentCourse;
                    }
                }
            }
        }
        return null;
    }

    public List<Department> getAllDepartments() {
        return allDepartments;
    }

    public void printInstructors() {
        for (Department currentDepartment : allDepartments) {
            for (Course currentCourse : currentDepartment.getAllCourses()) {
                for (CourseOffering currentCourseOffering : currentCourse.getAllCourseOfferings()) {
                    System.out.println("Instructors for current course offering: " +
                            currentCourseOffering.getInstructorString());
                }
            }
        }
    }

    public void printCourseOfferings() {
        for (Department currentDepartment : allDepartments) {
            for (Course currentCourse : currentDepartment.getAllCourses()) {
                for (CourseOffering currentCourseOffering : currentCourse.getAllCourseOfferings()) {
                    System.out.println("currentDepartment: " + currentDepartment.getDepartmentName() +
                            "currentCourse: " + currentCourse.getCatalogNumber() +
                            "currentCourseOffering: " + currentCourseOffering.toString());
                }
            }
        }
    }

    public void printModelRow() { //maybe .trim() not needed later
        int index = 0;
        while (index < CSV_Array.length) {
            System.out.print(CSV_Array[index].trim());
            if (index != (CSV_Array.length - 1)) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
            index++;
        }
    }

    public CourseOffering getCurrentRowCourseOffering() {
        return currentRowCourseOffering;
    }
}

