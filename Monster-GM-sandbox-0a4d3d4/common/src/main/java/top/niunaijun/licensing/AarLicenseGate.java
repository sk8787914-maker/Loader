package top.niunaijun.licensing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * A lightweight license gate for AAR SDK consumers.
 *
 * <p>Usage flow:
 * 1) Generate key from your private panel/backend with the same secret + client id format.
 * 2) App passes received key into {@link #validate(String, String)}.
 * 3) If invalid, host app should block SDK features and show custom message.
 */
public class AarLicenseGate {

  private final String sdkSecret;

  public AarLicenseGate(String sdkSecret) {
    this.sdkSecret = sdkSecret;
  }

  public boolean validate(String clientId, String inputKey) {
    if (isBlank(clientId) || isBlank(inputKey) || isBlank(sdkSecret)) {
      return false;
    }
    String expected = createKey(clientId);
    return expected.equalsIgnoreCase(inputKey.trim());
  }

  public String createKey(String clientId) {
    String payload = clientId.trim() + ":" + sdkSecret.trim();
    return sha256(payload).substring(0, 24).toUpperCase(Locale.US);
  }

  public String invalidKeyMessage() {
    return "SDK key buy go to telegram dm:- @zoro0687";
  }

  private static boolean isBlank(String value) {
    return value == null || value.trim().isEmpty();
  }

  private static String sha256(String value) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder(hash.length * 2);
      for (byte b : hash) {
        sb.append(String.format(Locale.US, "%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 algorithm missing", e);
    }
  }
}
