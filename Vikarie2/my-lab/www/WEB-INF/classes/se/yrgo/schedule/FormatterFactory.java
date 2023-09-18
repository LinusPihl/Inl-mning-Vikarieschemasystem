package se.yrgo.schedule;

/**
 * A factory to get a formatter for different content types
 */
public class FormatterFactory {

  private static Formatter HTML_FORMATTER = new HtmlFormatter();
  private static Formatter JSON_FORMATTER = new JsonFormatter();
  private static Formatter XML_FORMATTER = new XmlFormatter();

  /**
   * Returns a formatter for the given contentType
   * @param contentType The content type you want to format to (HTML, JSON, XML)
   * @return A Formatter of the correct type, depending on the provided content type.
   * @throws IllegalArgumentException If the provided content type is not supported.
   */
  public static Formatter getFormatter(String contentType) {
    if (contentType != null) {
      contentType = contentType.toLowerCase(); // Konvertera till små bokstäver
      switch (contentType) {
        case "html":
          return HTML_FORMATTER;
        case "json":
          return JSON_FORMATTER;
        case "xml":
          return XML_FORMATTER;
        default:
          throw new IllegalArgumentException("Format not supported");
      }
    }

    throw new IllegalArgumentException("Format not supported");
  }
}
