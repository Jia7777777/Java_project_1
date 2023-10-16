package ui;

import model.Course;
import model.CourseList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Course Planning application
public class CoursePlanning {

    private static final String JSON_STORE = "./data/courseList.json";
    private Scanner input;
    private CourseList courseList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    // EFFECTS: run Course Planning application
    public CoursePlanning() {
        runCoursePlanning();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runCoursePlanning() {
        boolean keepGoing = true;
        String command = null;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nBye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes course list
    private void initialize() {
        courseList = new CourseList();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("c")) {
            doCalculate();
        } else if (command.equals("1")) {
            doRegister();
        } else if (command.equals("0")) {
            doUnregister();
        } else if (command.equals("s")) {
            doSave();
        } else if (command.equals("l")) {
            doLoad();
        } else {
            System.out.println("Selection invalid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tv -> view course list");
        System.out.println("\ta -> add course");
        System.out.println("\tr -> remove course");
        System.out.println("\t1 -> register course");
        System.out.println("\t0 -> unregister course");
        System.out.println("\tc -> calculate total credits of register courses");
        System.out.println("\ts -> save course list to file");
        System.out.println("\tl -> load course list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: view the course list
    public void doView() {
        if (this.courseList.getCourseList().isEmpty()) {
            System.out.println("No course in the course list.");
        } else {
            for (Course next : this.courseList.getCourseList()) {
                String result = null;
                if (next.getStatus()) {
                    result = "registered";
                } else {
                    result = "unregistered";
                }
                System.out.println(next.getName() + " " + next.getCredit() + " " + result);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts addCourse
    public void doAdd() {
        System.out.println("Enter course name:");
        String name = input.next();
        System.out.println("Enter course credit:");
        int credit = input.nextInt();
        if (credit > 0) {
            Course newCourse = new Course(name, credit);
            boolean result1 = this.courseList.addCourse(newCourse);
            if (result1) {
                System.out.println("Course has been added!");
            } else {
                System.out.println("Course is failed to add and nothing changes in the course list.");
            }
        } else {
            System.out.println("Credit should be positive and nothing changes in the course list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts removeCourse
    public void doRemove() {
        System.out.println("Enter course name:");
        String name = input.next();
        System.out.println("Enter course credit:");
        int credit = input.nextInt();
        if (credit > 0) {
            Course newCourse = new Course(name, credit);
            boolean result1 = this.courseList.removeCourse(newCourse);
            if (result1) {
                System.out.println("Course has been removed!");
            } else {
                System.out.println("Course is failed to remove and nothing changes in the course list.");
            }
        } else {
            System.out.println("Credit should be positive and nothing changes in the course list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts totalCreditOfRegisteredCourse
    public void doCalculate() {
        System.out.println("total credits of registered courses: " + this.courseList.totalCreditOfRegisteredCourse());
    }

    // MODIFIES: this
    // EFFECTS: conducts markCourseAsRegistered
    public void doRegister() {
        System.out.println("Enter course name:");
        String name = input.next();
        System.out.println("Enter course credit:");
        int credit = input.nextInt();
        String result = null;
        for (Course next : this.courseList.getCourseList()) {
            if (next.getName().equals(name) && credit == next.getCredit()) {
                result = next.markCourseAsRegistered();
            }
        }
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("There is no such course in the course list and nothing changes in the course list.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts markCourseAsUnregistered
    public void doUnregister() {
        System.out.println("Enter course name:");
        String name = input.next();
        System.out.println("Enter course credit:");
        int credit = input.nextInt();
        String result = null;
        for (Course next : this.courseList.getCourseList()) {
            if (next.getName().equals(name) && credit == next.getCredit()) {
                result = next.markCourseAsUnregistered();
            }
        }
        if (result != null) {
            System.out.println(result);
        } else {
            System.out.println("There is no such course in the course list and nothing changes in the course list.");
        }
    }

    // EFFECTS: saves course list to file
    public void doSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(courseList);
            jsonWriter.close();
            System.out.println("Saved " + "to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads course list to file
    public void doLoad() {
        try {
            courseList = jsonReader.read();
            System.out.println("Loaded " + "from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
