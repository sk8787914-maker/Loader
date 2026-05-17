# AAR License Workflow (Hinglish, Real Panel Based)

## Aapka required flow
- AAR tabhi chale jab `https://enginehost.org/connect` panel key ko valid bole.
- Fake key ya invalid click case me popup aaye: `SDK key buy telegram dm kro main :- @zoro0687`
- User click kare to direct Telegram DM open ho.

## Server contract (panel response)
SDK `POST https://enginehost.org/connect` pe JSON bhejta hai:
```json
{
  "packageName": "com.client.app",
  "clientId": "client_123",
  "sdkKey": "USER_ENTERED_KEY"
}
```

Panel se expected response:
```json
{ "valid": true, "message": "Activated" }
```
Ya invalid case:
```json
{ "valid": false, "message": "Key not found" }
```

## App/SDK integration
```java
SdkLicenseEnforcer enforcer = new SdkLicenseEnforcer("https://enginehost.org/connect");
enforcer.enforceOrThrow(context, context.getPackageName(), clientId, enteredKey);
// iske baad hi SDK features enable karo
```

## Important
- Agar panel unavailable ho, SDK ko blocked rakho (fail-closed).
- Client app me koi local bypass mat do.
- Key issue/activate sirf aapke panel se ho.
