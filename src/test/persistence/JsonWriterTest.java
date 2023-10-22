package persistence;

import model.Course;
import model.CourseList;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CourseList cl = new CourseList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail();
        } catch (IOException e) {
            // do nothing
        }
    }

    @Test
    void testWriterEmptyCourseList() {
        try {
            CourseList cl = new CourseList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCourseList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCourseList.json");
            cl = reader.read();
            List<Course> resultCourseList = cl.getCourseList();
            assertTrue(resultCourseList.isEmpty());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testWriterGeneralCourseList() {
        try {
            CourseList cl = new CourseList();
            cl.addCourse(new Course("MATH101", 3));
            cl.addCourse(new Course("CPSC121",4));
            cl.getCourseList().get(0).markCourseAsRegistered();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCourseList.json");
            writer.open();
            writer.write(cl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCourseList.json");
            cl = reader.read();
            List<Course> resultCourseList = cl.getCourseList();
            Course resultCourse1 = resultCourseList.get(0);
            Course resultCourse2 = resultCourseList.get(1);
            assertEquals(2, resultCourseList.size());
            assertEquals("MATH101", resultCourse1.getName());
            assertEquals(3, resultCourse1.getCredit());
            assertEquals(true, resultCourse1.getStatus());
            assertEquals("CPSC121", resultCourse2.getName());
            assertEquals(4, resultCourse2.getCredit());
            assertEquals(false, resultCourse2.getStatus());
        } catch (IOException e) {
            fail();
        }
    }
}
