package persistence;

import model.Course;
import model.CourseList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CourseList cl = reader.read();
            fail();
        } catch (IOException e) {
            // do nothing
        }
    }

    @Test
    void testReaderEmptyCourseList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCourseList.json");
        try {
            CourseList cl = reader.read();
            List<Course> resultCourseList = cl.getCourseList();
            assertTrue(resultCourseList.isEmpty());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testReaderGeneralCourseList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCourseList.json");
        try {
            CourseList cl = reader.read();
            List<Course> resultCourseList = cl.getCourseList();
            Course resultCourse1 = resultCourseList.get(0);
            Course resultCourse2 = resultCourseList.get(1);
            assertEquals(2, resultCourseList.size());
            assertEquals("CPSC110", resultCourse1.getName());
            assertEquals(4, resultCourse1.getCredit());
            assertEquals(true, resultCourse1.getStatus());
            assertEquals("MATH100", resultCourse2.getName());
            assertEquals(3, resultCourse2.getCredit());
            assertEquals(false, resultCourse2.getStatus());
        } catch (IOException e) {
            fail();
        }
    }
}
