package se.yrgo.schedule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * A class for formatting a list of assignments as JSON.
 */
public class JsonFormatter implements Formatter {

  @Override
  public String format(List<Assignment> assignments) {
    if (assignments.isEmpty()) {
      return "[]";
    } else {
      JSONArray jsonArray = new JSONArray();
      for (Assignment assignment : assignments) {
        jsonArray.put(JSONAssignment(assignment));
      }
      return jsonArray.toString(2);
    }
  }

  private JSONObject JSONAssignment(Assignment assignment) {
    JSONObject JSONAssignment = new JSONObject();

    JSONAssignment.put("date", assignment.date());

    // Create a JSON object for substitute
    JSONObject JSONSubstitute = new JSONObject();
    JSONSubstitute.put("name", assignment.teacher().name());
    JSONAssignment.put("substitute", JSONSubstitute);

    // Create a JSON object for school
    JSONObject JSONSchool = new JSONObject();
    JSONSchool.put("school_name", assignment.school().name());
    JSONSchool.put("address", assignment.school().address());
    JSONAssignment.put("school", JSONSchool);

    return JSONAssignment;
  }
}