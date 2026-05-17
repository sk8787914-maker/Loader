package top.niunaijun.licensing;

/**
 * Hook this panel from your SDK entrypoints.
 * Keep SDK APIs blocked until validateOrThrow passes.
 */
public class LicenseGuardPanel {

  private final AarLicenseGate gate;

  public LicenseGuardPanel(String sdkSecret) {
    this.gate = new AarLicenseGate(sdkSecret);
  }

  public void validateOrThrow(String clientId, String inputKey) {
    if (!gate.validate(clientId, inputKey)) {
      throw new SecurityException(gate.invalidKeyMessage());
    }
  }

  public String generateValidKeyForPanel(String clientId) {
    return gate.createKey(clientId);
  }
}
