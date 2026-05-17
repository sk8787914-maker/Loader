package top.niunaijun.licensing;

public class PanelValidationResult {
  public final boolean valid;
  public final String message;
  public final int statusCode;
  public final String rawBody;

  private PanelValidationResult(boolean valid, String message, int statusCode, String rawBody) {
    this.valid = valid;
    this.message = message;
    this.statusCode = statusCode;
    this.rawBody = rawBody;
  }

  public static PanelValidationResult ok(String message) {
    return new PanelValidationResult(true, message, 200, "");
  }

  public static PanelValidationResult fail(String message, int statusCode, String rawBody) {
    return new PanelValidationResult(false, message, statusCode, rawBody);
  }
}
