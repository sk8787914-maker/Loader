package top.niunaijun.licensing;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/** Connects SDK with remote panel and verifies license key server-side. */
public class PanelApiClient {

  private static final int CONNECT_TIMEOUT_MS = 10_000;
  private static final int READ_TIMEOUT_MS = 10_000;

  private final String panelConnectUrl;

  public PanelApiClient(String panelConnectUrl) {
    this.panelConnectUrl = panelConnectUrl;
  }

  public PanelValidationResult validateKey(String packageName, String clientId, String sdkKey) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(panelConnectUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
      connection.setReadTimeout(READ_TIMEOUT_MS);
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);

      JSONObject payload = new JSONObject();
      payload.put("packageName", packageName);
      payload.put("clientId", clientId);
      payload.put("sdkKey", sdkKey);

      try (OutputStream os = connection.getOutputStream()) {
        os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
      }

      int code = connection.getResponseCode();
      InputStream stream = code >= 200 && code < 300
          ? connection.getInputStream()
          : connection.getErrorStream();

      String body = readBody(stream);
      if (code < 200 || code >= 300) {
        return PanelValidationResult.fail("Panel request failed", code, body);
      }

      JSONObject json = new JSONObject(body);
      boolean valid = json.optBoolean("valid", false);
      String message = json.optString("message", "");
      return valid
          ? PanelValidationResult.ok(message)
          : PanelValidationResult.fail(message.isEmpty() ? "Invalid key" : message, code, body);
    } catch (Exception e) {
      return PanelValidationResult.fail("Panel connection error: " + e.getMessage(), -1, "");
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  private static String readBody(InputStream inputStream) throws Exception {
    if (inputStream == null) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    }
    return sb.toString();
  }
}
