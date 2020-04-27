package ca.cmpt213.as5courseplanner.Model;

/** Brandon Yip 301294186
 * Class that encapsulates the given semesterCode (e.g. 1197) and converts it to a relative
 * season (Spring, Summer, Fall), a corresponding integer year (e.g. 2010), and various other
 * commodities relevant to Simon Fraser University's naming system.
 */


public class Semester {
    enum SEMESTER {Spring, Summer, Fall, DOES_NOT_EXIST}
    private SEMESTER currentSemester;
    private int lengthOfYearCode = 3;
    private int lengthOfSeasonCode = 1;
    private int lengthOfSemesterCode = lengthOfYearCode + lengthOfSeasonCode;
    private int yearOfSemester;
    private int digitOfSeason;
    private int Spring = 1;
    private int Summer = 4;     // 1 = Spring, 4 = Summer, 7 = Fall
    private int Fall = 7;


    public Semester(int semesterCode) {
        parseYearAndSemester(semesterCode);
        assignSeason(digitOfSeason);
    }

    public void parseYearAndSemester(int semesterCode) {
        String semesterCodeAsString = Integer.toString(semesterCode);
        String yearCodeParsedToString = "";
        String seasonCodeToString = "";
        Integer seasonCodeParsed = 0;
        Integer yearCodeParsed = 0;
        int iterator = 0;

        while (iterator < lengthOfYearCode) {
            yearCodeParsedToString += semesterCodeAsString.charAt(iterator);
            iterator++;
        }
        while (iterator < lengthOfSemesterCode) {
            seasonCodeToString += semesterCodeAsString.charAt(iterator);
            iterator++;
        }
        yearCodeParsed = Integer.parseInt(yearCodeParsedToString);
        seasonCodeParsed = Integer.parseInt(seasonCodeToString);
        yearOfSemester = yearCodeParsed;
        digitOfSeason = seasonCodeParsed;
    }

    public void assignSeason(int seasonOfSemester) {
        if (seasonOfSemester == Spring) {
            this.currentSemester = SEMESTER.Spring;
        } else if (seasonOfSemester == Summer) {
            this.currentSemester = SEMESTER.Summer;
        } else if (seasonOfSemester == Fall) {
            this.currentSemester = SEMESTER.Fall;
        } else {
            this.currentSemester = SEMESTER.DOES_NOT_EXIST;
        }
    }

    public int getYearOfSemester() {
        return yearOfSemester;
    }

    public SEMESTER getCurrentSemester() {
        return currentSemester;
    }
}