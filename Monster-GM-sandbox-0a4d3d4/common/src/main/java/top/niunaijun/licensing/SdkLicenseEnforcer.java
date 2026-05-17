package top.niunaijun.licensing;

import android.content.Context;

/**
 * Real panel-based license enforcement.
 * Blocks SDK unless key is verified by your panel endpoint.
 */
public class SdkLicenseEnforcer {

  public static final String DEFAULT_PANEL_CONNECT_URL = "https://enginehost.org/connect";

  private final PanelApiClient panelApiClient;

  public SdkLicenseEnforcer() {
    this(DEFAULT_PANEL_CONNECT_URL);
  }

  public SdkLicenseEnforcer(String panelConnectUrl) {
    this.panelApiClient = new PanelApiClient(panelConnectUrl);
  }

  public void enforceOrThrow(Context context, String packageName, String clientId, String sdkKey) {
    PanelValidationResult result = panelApiClient.validateKey(packageName, clientId, sdkKey);
    if (!result.valid) {
      TelegramSupport.openSellerDm(context);
      throw new SecurityException("License invalid or not activated by panel. " + result.message);
    }
  }
}
