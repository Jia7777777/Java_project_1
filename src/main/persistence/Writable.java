package persistence;

import org.json.JSONObject;

// Code is influenced by the JsonSerizalizationDemo.
// JsonSerizalizationDemo link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
