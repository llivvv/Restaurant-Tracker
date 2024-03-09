package persistence;

import org.json.JSONObject;

// referenced JsonSerializationDemo
// references https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// interface used by JsonWriter
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
