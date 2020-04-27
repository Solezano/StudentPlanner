package ca.cmpt213.as5courseplanner.Wrappers;

public class ApiAddWatcherWrapper {
    private long deptId;
    private long courseId;

    public ApiAddWatcherWrapper(long deptId, long courseId) {
        this.deptId = deptId;
        this.courseId = courseId;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
