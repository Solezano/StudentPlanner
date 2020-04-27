package ca.cmpt213.as5courseplanner.Model;

import java.util.Arrays;

/** Brandon Yip 301294186
 * Bland class that essentially just provides an object to store an Instructors name, along with
 * cross-checking whether the .csv file slot for the instructor name has been set to one of two
 * possibilities in below mentioned String[] nullInstructor.
 */


public class Instructor {
    private String[] nullInstructor = new String[]{"<null>", "(null)"};
    private String instructorName;


    public Instructor(String instructorName) {
        if (Arrays.asList(nullInstructor).contains(instructorName)) {
            this.instructorName = "";
        } else {
            this.instructorName = instructorName;
        }
    }

    public String getInstructorName() {
        return instructorName;
    }
}