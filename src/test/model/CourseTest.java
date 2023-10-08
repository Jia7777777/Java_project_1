package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course testCourse1;
    private Course testCourse2;

    @BeforeEach
    void runBefore() {
        testCourse1 = new Course("CPSC110", 4);
        testCourse2 = new Course("CPSC121", 4);
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC110", testCourse1.getName());
        assertEquals(4, testCourse1.getCredit());
        assertFalse(testCourse1.getStatus());
    }

    @Test
    void testMarkCourseAsRegisteredOnce() {
        String result1 = testCourse1.markCourseAsRegistered();
        assertTrue(testCourse1.getStatus());
        assertEquals("Course is successfully registered!", result1);
    }

    @Test
    void testMarkCourseAsRegisteredTwice() {
        String result1 = testCourse1.markCourseAsRegistered();
        String result2 = testCourse2.markCourseAsRegistered();
        assertTrue(testCourse1.getStatus());
        assertTrue(testCourse2.getStatus());
        assertEquals("Course is successfully registered!", result1);
        assertEquals("Course is successfully registered!", result2);
    }

    @Test
    void testMarkCourseAsRegisteredForRegisteredCourse() {
        testCourse1.markCourseAsRegistered();
        String result1 = testCourse1.markCourseAsRegistered();
        assertTrue(testCourse1.getStatus());
        assertEquals("Course was registered in the past and nothing changes now.", result1);
    }

    @Test
    void testMarkCourseAsUnregisteredOnce() {
        testCourse1.markCourseAsRegistered();
        String result1 = testCourse1.markCourseAsUnregistered();
        assertFalse(testCourse1.getStatus());
        assertEquals("Course is unregistered.", result1);
    }

    @Test
    void testMarkCourseAsUnregisteredTwice() {
        testCourse1.markCourseAsRegistered();
        testCourse2.markCourseAsRegistered();
        String result1 = testCourse1.markCourseAsUnregistered();
        String result2 = testCourse2.markCourseAsUnregistered();
        assertFalse(testCourse1.getStatus());
        assertFalse(testCourse2.getStatus());
        assertEquals("Course is unregistered.", result1);
        assertEquals("Course is unregistered.", result2);
    }

    @Test
    void testMarkCourseAsUnregisteredForUnregisteredCourse() {
        String result1 = testCourse1.markCourseAsUnregistered();
        assertFalse(testCourse1.getStatus());
        assertEquals("Course was unregistered in the past and nothing changes now.", result1);
    }

}