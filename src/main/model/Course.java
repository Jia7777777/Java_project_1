package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent a course having a course name, a course credits and a course registration status
public class Course implements Writable {
    public static final String SENTENCE1 = "Course is successfully registered!";
    public static final String SENTENCE2 = "Course was registered in the past and nothing changes in the course list.";
    public static final String SENTENCE3 = "Course is unregistered.";
    public static final String SENTENCE4 =
            "Course was unregistered in the past and nothing changes in the course list.";

    private String name;
    private int credit;
    private boolean status;  // false is unregistered and true is registered

    // REQUIRES: credit > 0, name must be a non-zero length
    // EFFECTS: create a course with given name and credit, with unregistered status applied (false)
    public Course(String name, int credit) {
        this.name = name;
        this.credit = credit;
        this.status = false;
    }

    // MODIFIES: this
    // EFFECTS: If status is unregistered, then change to registered and return "Course is successfully registered!".
    //          Otherwise, return "Course was registered in the past and nothing changes now.".
    public String markCourseAsRegistered() {
        if (!this.status) {
            this.status = true;
            return SENTENCE1;
        } else {
            return SENTENCE2;
        }
    }

    // MODIFIES: this
    // EFFECTS: If status is registered, then change to unregistered and return "Course is unregistered.".
    //          Otherwise, return "Course was unregistered in the past and nothing changes now.".
    public String markCourseAsUnregistered() {
        if (this.status) {
            this.status = false;
            return SENTENCE3;
        } else {
            return SENTENCE4;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getCredit() {
        return this.credit;
    }

    public boolean getStatus() {
        return this.status;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("credit", credit);
        json.put("status", status);
        return json;
    }
}
