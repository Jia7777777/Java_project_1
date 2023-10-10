package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseListTest {
    private CourseList testCourseList;
    private Course testCourse1;
    private Course testCourse2;
    private Course testCourse3;
    private Course testCourse4;
    private Course testCourse5;

    @BeforeEach
    void runBefore() {
        testCourseList = new CourseList();
        testCourse1 = new Course("MATH100", 3);
        testCourse2 = new Course("CPSC210", 4);
        testCourse3 = new Course("MATH220", 3);
        testCourse4 = new Course("MATH100", 3);
        testCourse5 = new Course("CPSC210", 3);
    }

    @Test
    void testConstructor() {
        List<Course> courses = testCourseList.getCourseList();
        assertTrue(courses.isEmpty());
    }

    @Test
    void testAddCourseOnce() {
        boolean result1 = testCourseList.addCourse(testCourse1);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(1, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertTrue(result1);
    }

    @Test
    void testAddCourseTwiceTotallyDifferent() {
        boolean result1 = testCourseList.addCourse(testCourse1);
        boolean result2 = testCourseList.addCourse(testCourse2);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(2, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertEquals(testCourse2, courses.get(1));
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    void testAddCourseTwiceNameDifferent() {
        boolean result1 = testCourseList.addCourse(testCourse1);
        boolean result2 = testCourseList.addCourse(testCourse3);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(2, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertEquals(testCourse3, courses.get(1));
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    void testAddCourseTwiceCreditDifferent() {
        boolean result1 = testCourseList.addCourse(testCourse2);
        boolean result2 = testCourseList.addCourse(testCourse5);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(2, courses.size());
        assertEquals(testCourse2, courses.get(0));
        assertEquals(testCourse5, courses.get(1));
        assertTrue(result1);
        assertTrue(result2);
    }

    @Test
    void testAddCourseSameTwice() {
        boolean result1 = testCourseList.addCourse(testCourse1);
        boolean result3 = testCourseList.addCourse(testCourse4);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(1, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertTrue(result1);
        assertFalse(result3);
    }
    @Test
    void testAddCourseGenerally() {
        boolean result1 = testCourseList.addCourse(testCourse1);
        boolean result2 = testCourseList.addCourse(testCourse2);
        List<Course> upToNow = testCourseList.getCourseList();
        upToNow.get(0).markCourseAsRegistered();
        boolean result3 = testCourseList.addCourse(testCourse4);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(2, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertEquals(testCourse2, courses.get(1));
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void testRemoveCourseEmpty() {
        boolean result1 = testCourseList.removeCourse(testCourse1);
        List<Course> courses = testCourseList.getCourseList();
        assertTrue(courses.isEmpty());
        assertFalse(result1);
    }

    @Test
    void testRemoveCourseNameCreditDifferentStatusF() {
        testCourseList.addCourse(testCourse1);
        boolean result1 = testCourseList.removeCourse(testCourse2);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(1, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertFalse(result1);
    }

    @Test
    void testRemoveCourseNameSameCreditDifferentStatusF() {
        testCourseList.addCourse(testCourse2);
        boolean result1 = testCourseList.removeCourse(testCourse5);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(1, courses.size());
        assertEquals(testCourse2, courses.get(0));
        assertFalse(result1);
    }

    @Test
    void testRemoveCourseNameCreditSameStatusF() {
        testCourseList.addCourse(testCourse1);
        boolean result1 = testCourseList.removeCourse(testCourse1);
        List<Course> courses = testCourseList.getCourseList();
        assertTrue(courses.isEmpty());
        assertTrue(result1);
    }

    @Test
    void testRemoveCourseNameCreditSameStatusT() {
        testCourseList.addCourse(testCourse1);
        List<Course> upToNow = testCourseList.getCourseList();
        upToNow.get(0).markCourseAsRegistered();
        boolean result1 = testCourseList.removeCourse(testCourse4);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(1, courses.size());
        assertEquals(testCourse1, courses.get(0));
        assertFalse(result1);
    }

    @Test
    void testRemoveCourseGenerally() {
        testCourseList.addCourse(testCourse3);
        testCourseList.addCourse(testCourse2);
        testCourseList.addCourse(testCourse1);
        List<Course> upToNow = testCourseList.getCourseList();
        upToNow.get(0).markCourseAsRegistered();
        boolean result1 = testCourseList.removeCourse(testCourse2);
        boolean result2 = testCourseList.removeCourse(testCourse3);
        List<Course> courses = testCourseList.getCourseList();
        assertEquals(2, courses.size());
        assertEquals(testCourse3, courses.get(0));
        assertEquals(testCourse1, courses.get(1));
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void testTotalCreditOfRegisteredCourseForEmpty() {
        int result1 = testCourseList.totalCreditOfRegisteredCourse();
        assertEquals(0, result1);
    }

    @Test
    void testTotalCreditOfRegisteredCourseForAllUnregistered() {
        testCourseList.addCourse(testCourse3);
        testCourseList.addCourse(testCourse2);
        int result1 = testCourseList.totalCreditOfRegisteredCourse();
        assertEquals(0, result1);
    }

    @Test
    void testTotalCreditOfRegisteredCourseForAllRegistered() {
        testCourse1.markCourseAsRegistered();
        testCourse2.markCourseAsRegistered();
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);
        int result1 = testCourseList.totalCreditOfRegisteredCourse();
        assertEquals(7, result1);
    }

    @Test
    void testTotalCreditOfRegisteredCourseForPartOfRegisteredAndPartOfUnregistered() {
        testCourse1.markCourseAsRegistered();
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse3);
        int result1 = testCourseList.totalCreditOfRegisteredCourse();
        assertEquals(3, result1);
    }

    @Test
    void testTotalCreditOfRegisteredCourseGenerally() {
        testCourse1.markCourseAsRegistered();
        testCourse2.markCourseAsRegistered();
        testCourseList.addCourse(testCourse3);
        testCourseList.addCourse(testCourse1);
        int result1 = testCourseList.totalCreditOfRegisteredCourse();
        testCourseList.addCourse(testCourse2);
        int result2 = testCourseList.totalCreditOfRegisteredCourse();
        assertEquals(3, result1);
        assertEquals(7, result2);
    }
}
