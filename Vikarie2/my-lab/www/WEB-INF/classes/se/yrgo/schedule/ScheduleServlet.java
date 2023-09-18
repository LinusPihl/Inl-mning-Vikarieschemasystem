package se.yrgo.schedule;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Listens to requests on localhost:8080/v1/ and accepts the following parameters:
 * - none: lists all schedules for all teachers
 * - substitute_id: the ID for a substitute teacher you want to list the schedule for
 * - day: the day (YYYY-mm-dd) you want to see the schedule for
 * 
 * The substitute_id and day parameters can be combined or used alone.
 */
public class ScheduleServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      // Read the request as UTF-8
      request.setCharacterEncoding(UTF_8.name());

      // Parse the arguments - see ParamParser class
      ParamParser parser = new ParamParser(request);
      // Set the content type (using the parser)
      response.setContentType(parser.contentType());
      // To write the response, we're using a PrintWriter
      response.setCharacterEncoding(UTF_8.name());
      PrintWriter out = response.getWriter();
      // Get access to the database, using a factory
      // Assignments is an interface - see Assignments interface
      Assignments db = AssignmentsFactory.getAssignments();
      // Start with an empty list (makes code easier)
      List<Assignment> assignments = new ArrayList<>();
      // Call the correct method, depending on the parser's type value
      try {
        StringBuilder table;
        switch (parser.type()){
          case ALL:
            assignments = db.all();
            break;
          case TEACHER_ID_AND_DAY:
            assignments = db.forTeacherAt(parser.teacherId(), parser.day());
            break;
          case DAY:
            assignments = db.at(parser.day());
            break;
          case TEACHER_ID:
            assignments = db.forTeacher(parser.teacherId());
        }
      } catch (AccessException e) {
        out.println("Error fetching data: " + e.getMessage());
        System.err.println("Error: " +e);
        e.printStackTrace();
      }
      
      // Få formatvärdet från ParamParser
      String format = parser.format();

      try {
          Formatter formatter = FormatterFactory.getFormatter(format);
          // Format the result according to the parser's format
          String result = formatter.format(assignments);
          // Print the result and close the PrintWriter
          out.println(result);
      } catch (IllegalArgumentException e) {
          // Felaktigt format, skapa ett felmeddelande i HTML-format
          out.println("<html><head><title>Format error</title></head>");
          out.println("<body>Format missing or not supported");
          out.println(" - We support xml and json</body>");
          out.println("</html>");
      }
      
      out.close();
    }
}
