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

    // REQUIRES: c.getStatus() == false
    // MODIFIES: this
    // EFFECTS: if the course is not in the course list,
    //          then add a course to the course list and keep order in which they add and return true.
    //          otherwise, do nothing and return false.
    public boolean addCourse(Course c) {
        boolean result = false;
        for (Course next : this.courseList) {
            if (next.getName().equals(c.getName()) && next.getCredit() == c.getCredit()) {
                result = true;
            }
        }
        if (!result) {
            this.courseList.add(c);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: c.getStatus() == false
    // MODIFIES: this
    // EFFECTS: if the course in the course list and unregistered,
    //          then remove a course from the course list and return true.
    //          otherwise, do nothing and return false.
    public boolean removeCourse(Course c) {
        boolean result = false;
        for (Course next : this.courseList) {
            if (next.getName().equals(c.getName()) && next.getCredit() == c.getCredit() && !next.getStatus()) {
                result = true;
            }
        }
        if (result) {
            this.courseList.remove(c);
            return true;
        } else {
            return false;
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
