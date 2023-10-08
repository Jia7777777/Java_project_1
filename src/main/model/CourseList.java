package model;

import java.util.ArrayList;
import java.util.List;

// Represent a list of courses added to the course list
public class CourseList {

    private List<Course> courseList;

    // EFFECTS: create an empty course list
    public CourseList() {
        this.courseList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the course is not in the course list,
    //          then add a course to the course list and keep order in which they add. otherwise, do nothing.
    public void addCourse(Course c) {
        if (!courseList.contains(c)) {
            courseList.add(c);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the course in the course list, then remove a course from the course list. otherwise, do nothing.
    public void removeCourse(Course c) {
        if (courseList.contains(c)) {
            courseList.remove(c);
        }
    }

    // EFFECTS: calculate total course credit of registered course in the course list
    public int totalCreditOfRegisteredCourse() {
        int result = 0;
        for (Course next : this.courseList) {
            if (next.getStatus()) {
                result = result + next.getCredit();
            }
        }
        return result;
    }

    public List<Course> getCourseList() {
        return this.courseList;
    }
}
