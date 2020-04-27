package ca.cmpt213.as5courseplanner.Controller;

import ca.cmpt213.as5courseplanner.Utilities.CSVReader;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.*;
import ca.cmpt213.as5courseplanner.Wrappers.*;
import ca.cmpt213.as5courseplanner.Model.*;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Brandon Yip 301294186
 * Controller for REST API interface, enabling front and back-end to communicate with one another.
 */

@RestController
public class StudentPlannerController {
    CSVReader newReader = new CSVReader();
    List<Department> allDepartments = newReader.getAllDepartments();
    List<ApiWatcherWrapper> allWatchers = new ArrayList<>();
    AtomicInteger watcherId = new AtomicInteger(11);


    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/about")
    public ApiAboutWrapper getAbout() {
        return new ApiAboutWrapper("Brandon's Awesome App", "Brandon Yip");
    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/dump-model")
    public void printModel() {

        List<Course> allCourses;
        List<CourseOffering> allCourseOfferings;
        List<Section> allSections;
//        List<Instructor> allInstructors;
//        int instructorList_Length = 0;
//        int instructorList_Index = 0;

        for (Department currentDepartment : allDepartments) {
            allCourses = currentDepartment.getAllCourses();

            for (Course currentCourse : allCourses) {
                System.out.println(currentDepartment.getDepartmentName() +
                        " " + currentCourse.getCatalogNumber());
                allCourseOfferings = currentCourse.getAllCourseOfferings();

                for (CourseOffering currentCourseOffering : allCourseOfferings) {
                    //allInstructors = currentCourseOffering.getInstructorList();
                    //instructorList_Length = allInstructors.size() - 1;
                    System.out.print("\t" + currentCourseOffering.getSemesterCode() +
                            " in " + currentCourseOffering.getLocation() + " by " +
                            currentCourseOffering.getInstructorString());

//                    for (Instructor currentInstructor : allInstructors) {
//                        System.out.print(currentInstructor.getInstructorName());
//                        if (instructorList_Index < instructorList_Length) {
//                            System.out.print(", ");
//                        }
//                        instructorList_Index++;
//                    }
                    System.out.println();
//                    instructorList_Index = 0;
                    allSections = currentCourseOffering.getAllSections();

                    for (Section currentSection : allSections) {
                        System.out.println("\t\tType=" + currentSection.getType() +
                                ", " + "Enrollment=" + currentSection.getEnrollmentTotal() +
                                "/" + currentSection.getEnrollmentCapacity());
                    }
                }
            }
        }
    }

//    @ResponseStatus(value = HttpStatus.OK) //200
//    @GetMapping("/api/dump-model")
//    public void printModelWithTextWriter() {
//        File textFile = new File("data/output_dump.txt");
//        try {
//            FileWriter textFileWriter = new FileWriter(textFile);
//
//            List<Course> allCourses;
//            List<CourseOffering> allCourseOfferings;
//            List<Instructor> allInstructors;
//            List<Section> allSections;
//            int instructorList_Length = 0;
//            int instructorList_Index = 0;
//
//            for (Department currentDepartment : allDepartments) {
//                allCourses = currentDepartment.getAllCourses();
//
//                for (Course currentCourse : allCourses) {
//                    System.out.println(currentDepartment.getDepartmentName() +
//                            " " + currentCourse.getCatalogueNumber());
//                    textFileWriter.write(currentDepartment.getDepartmentName() +
//                            " " + currentCourse.getCatalogueNumber() + "\n");
//                    allCourseOfferings = currentCourse.getAllCourseOfferings();
//
//                    for (CourseOffering currentCourseOffering : allCourseOfferings) {
//                        allInstructors = currentCourseOffering.getInstructorList();
//                        instructorList_Length = allInstructors.size() - 1;
//                        System.out.print("\t" + currentCourseOffering.getSemesterCode() +
//                                " in " + currentCourseOffering.getLocation() + " by ");
//                        textFileWriter.write("\t" + currentCourseOffering.getSemesterCode() +
//                                " in " + currentCourseOffering.getLocation() + " by ");
//
//                        for (Instructor currentInstructor : allInstructors) {
//                            System.out.print(currentInstructor.getInstructorName());
//                            textFileWriter.write(currentInstructor.getInstructorName());
//                            if (instructorList_Index < instructorList_Length) {
//                                System.out.print(", ");
//                                textFileWriter.write(", ");
//                            }
//                            instructorList_Index++;
//                        }
//
//                        System.out.println();
//                        textFileWriter.write("\n");
//                        instructorList_Index = 0;
//                        allSections = currentCourseOffering.getAllSections();
//
//                        for (Section currentSection : allSections) {
//                            System.out.println("\t\tType=" + currentSection.getComponentCode() +
//                                    ", " + "Enrollment=" + currentSection.getEnrollmentTotal() +
//                                    "/" + currentSection.getEnrollmentCapacity());
//                            textFileWriter.write("\t\tType=" + currentSection.getComponentCode() +
//                                    ", " + "Enrollment=" + currentSection.getEnrollmentTotal() +
//                                    "/" + currentSection.getEnrollmentCapacity());
//                            textFileWriter.write("\n");
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new IndexOutOfBoundsException();
//        }
//    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/departments")
    public List<ApiDepartmentWrapper> getDepartments() {
        List<ApiDepartmentWrapper> allDepartmentsWrapped = new ArrayList<>();
        for (Department currentDepartment : allDepartments) {
            ApiDepartmentWrapper newDepartmentWrapper =
                    new ApiDepartmentWrapper(currentDepartment.getDepartmentID(),
                            currentDepartment.getDepartmentName());
            allDepartmentsWrapped.add(newDepartmentWrapper);
        }
        return allDepartmentsWrapped;
    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/departments/{deptID}/courses")
    public List<ApiCourseWrapper> getCourses(@PathVariable("deptID") int deptID) {
        List<ApiCourseWrapper> allCoursesWrapped = new ArrayList<>();
        for (Department currentDepartment : allDepartments) {
            if (currentDepartment.getDepartmentID() == deptID) {

                for (Course currentCourse : currentDepartment.getAllCourses()) {
                    ApiCourseWrapper newCourseWrapper =
                            new ApiCourseWrapper(currentCourse.getCourseID(),
                                    currentCourse.getCatalogNumber());
                    allCoursesWrapped.add(newCourseWrapper);
                }
                return allCoursesWrapped;
            }
        }
        throw new IndexOutOfBoundsException(); //404
    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/departments/{deptID}/courses/{courseID}/offerings")
    public List<ApiCourseOfferingWrapper> getCourseOfferings(@PathVariable("deptID") int deptID,
                                                             @PathVariable("courseID") int courseID) {
        List<ApiCourseOfferingWrapper> allCourseOfferingsWrapped = new ArrayList<>();
        Department currentDepartment = findDepartment(deptID);
        Course currentCourse = findCourseObject(currentDepartment, courseID);

        if (currentDepartment == null || currentCourse == null) {
            throw new IndexOutOfBoundsException(); //404
        }

        for (CourseOffering currentCourseOffering : currentCourse.getAllCourseOfferings()) {
            ApiCourseOfferingWrapper newCourseOfferingWrapper =
                    new ApiCourseOfferingWrapper(currentCourseOffering.getCourseOfferingID(),
                            currentCourseOffering.getLocation(),
                            currentCourseOffering.getInstructorString(),
                            currentCourseOffering.getTerm(),
                            currentCourseOffering.getSemesterCode(),
                            currentCourseOffering.getYear());
            allCourseOfferingsWrapped.add(newCourseOfferingWrapper);
        }
        return allCourseOfferingsWrapped;

    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/departments/{deptID}/courses/{courseID}/offerings/{courseOfferingID}")
    public List<ApiOfferingSectionWrapper> getSections(@PathVariable("deptID") int deptID,
                                                       @PathVariable("courseID") int courseID,
                                                       @PathVariable("courseOfferingID") int courseOfferingID) {
        List<ApiOfferingSectionWrapper> allSectionsWrapped = new ArrayList<>();
        Department currentDepartment = findDepartment(deptID);
        Course currentCourse = findCourseObject(currentDepartment, courseID);
        CourseOffering currentCourseOffering = findCourseOfferingObject(currentCourse, courseOfferingID);

        if (currentDepartment == null ||
                currentCourse == null ||
                currentCourseOffering == null) {
            throw new IndexOutOfBoundsException(); //404
        }
        for (Section currentSection : currentCourseOffering.getAllSections()) {
            ApiOfferingSectionWrapper newSectionWrapper = new ApiOfferingSectionWrapper(
                    currentSection.getType(), currentSection.getEnrollmentCapacity(),
                    currentSection.getEnrollmentTotal());
            allSectionsWrapped.add(newSectionWrapper);
        }
        return allSectionsWrapped;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("api/stats/student-per-semester?deptID={courseID}")
    public void getGraphData(@PathVariable("courseID") int courseID) {
        //NOT VOID; do later
        //return array sorted by semesterCode; chronological order

        throw new IndexOutOfBoundsException();
    }

    @ResponseStatus(value = HttpStatus.CREATED) //201
    @PostMapping("/api/addoffering")
    public void addOffering(@RequestBody ApiOfferingDataWrapper newOfferingWrapper) {
        int enrollmentTotal = newOfferingWrapper.getEnrollmentTotal();
        int enrollmentCap = newOfferingWrapper.getEnrollmentCap();
        int semester = newOfferingWrapper.getSemester();
        String catalogNumber = newOfferingWrapper.getCatalogNumber();
        String subjectName = newOfferingWrapper.getSubjectName();
        String instructor = newOfferingWrapper.getInstructor();
        String component = newOfferingWrapper.getComponent();
        String location = newOfferingWrapper.getLocation();
        Department currentRowDepartment = newReader.addDepartmentToListIf_DNE(subjectName);

        CSVReader.COURSE_STATUS courseStatus = newReader.checkIfCourseExists(currentRowDepartment, catalogNumber,
                semester, location, component);
        newReader.REST_evaluateCourse(courseStatus, currentRowDepartment, subjectName, catalogNumber, semester,
                location, enrollmentTotal, enrollmentCap, component, instructor);

        if (!allWatchers.isEmpty()) {
            for (ApiWatcherWrapper currentWatcherWrapper : allWatchers) {
                if (currentWatcherWrapper.getDepartment().getName().trim().equals(subjectName) &&
                        currentWatcherWrapper.getCourse().getCatalogNumber().equals(catalogNumber)) {
                    Date currentDate = new Date();
                    String event = currentDate + ": Added section " + component +
                            " with enrollment (" + enrollmentTotal + " / " + enrollmentCap +
                            ") to offering " + newReader.getCurrentRowCourseOffering().getTerm() +
                            " " + newReader.getCurrentRowCourseOffering().getYear();
                    currentWatcherWrapper.getEvents().add(event);
                    Collections.sort(currentWatcherWrapper.getEvents());
                }
            }
        }
    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/watchers")
    public List<ApiWatcherWrapper> getWatchers() {
        return allWatchers;
    }

    @ResponseStatus(value = HttpStatus.CREATED) //201
    @PostMapping("/api/watchers")
    public void addWatcher(@RequestBody ApiAddWatcherWrapper currentWatcherWrapper) {
        ApiWatcherWrapper newWatcherWrapper = new ApiWatcherWrapper(watcherId.getAndIncrement(),
                currentWatcherWrapper.getDeptId(),
                currentWatcherWrapper.getCourseId(),
                allDepartments);
        allWatchers.add(newWatcherWrapper);
    }

    @ResponseStatus(value = HttpStatus.OK) //200
    @GetMapping("/api/watchers/{watcherID}")
    public List<String> getWatcher(@PathVariable("watcherID") int watcherID) {
        for (ApiWatcherWrapper currentWatcherWrapper : allWatchers) {
            if (currentWatcherWrapper.getId() == watcherID) {
                return currentWatcherWrapper.getEvents();
            }
        }
        throw new IndexOutOfBoundsException();
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT) //204
    @DeleteMapping("/api/watchers/{watcherID}")
    public void deleteWatcher(@PathVariable("watcherID") int watcherID) {
        boolean watcherRemoved = false;
        int watcherIndex = 0;
        for (ApiWatcherWrapper currentWatcherWrapper : allWatchers) {
            if (currentWatcherWrapper.getId() == watcherID) {
                allWatchers.remove(watcherIndex);
                watcherRemoved = true;
                break;
            }
            watcherIndex++;
        }
        if (!watcherRemoved) {
            throw new IndexOutOfBoundsException(); //404
        }
    }

    public Department findDepartment(long departmentID) {
        for (Department currentDepartment : allDepartments) {
            if (currentDepartment.getDepartmentID() == departmentID) {
                return currentDepartment;
            }
        }
        return null;
    }

    public Course findCourseObject(Department currentDepartment, long courseID) {
        for (Course currentCourse : currentDepartment.getAllCourses()) {
            if (currentCourse.getCourseID() == courseID) {
                return currentCourse;
            }
        }
        return null;
    }

    public CourseOffering findCourseOfferingObject(Course currentCourse, long courseOfferingID) {
        for (CourseOffering currentCourseOffering : currentCourse.getAllCourseOfferings()) {
            if (currentCourseOffering.getCourseOfferingID() == courseOfferingID) {
                return currentCourseOffering;
            }
        }
        return null;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND) //404
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public void IndexOutOfBoundsException() {
        //nothing to do here
    }
}
