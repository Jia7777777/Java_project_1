package persistence;

import model.Course;
import model.CourseList;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads course list from JSON data stored in file
// Code is influenced by the JsonSerizalizationDemo.
// JsonSerizalizationDemo link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads course list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CourseList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Load course list from file!"));
        return parseCourseList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses course list from JSON object and returns it
    private CourseList parseCourseList(JSONObject jsonObject) {
        CourseList cl = new CourseList();
        addCourses(cl, jsonObject);
        return cl;
    }

    // MODIFIES: cl
    // EFFECTS: parses courses from JSON object and adds them to course list
    private void addCourses(CourseList cl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courseList");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(cl, nextCourse);
        }
    }

    // MODIFIES: cl
    // EFFECTS: parses course from JSON object and adds it to course list
    private void addCourse(CourseList cl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int credit = jsonObject.getInt("credit");
        boolean status = jsonObject.getBoolean("status");
        Course course = new Course(name, credit);
        if (course.getStatus() == status) {
            cl.addCourse(course);
        } else {
            course.markCourseAsRegistered();
            cl.addCourse(course);
        }

    }

}
