# AAR License Workflow (Hinglish)

## Goal
AAR ko tab tak run na hone do jab tak valid key panel se issue na ho.

## Step-by-step workflow
1. **Panel/Backend** me har client ka `clientId` store karo.
2. Backend same secret use karke `generateValidKeyForPanel(clientId)` jaisa logic se key nikale.
3. Client app onboarding ke time key paste kare.
4. SDK entrypoint pe `validateOrThrow(clientId, inputKey)` call karo.
5. Agar key fake/invalid ho to SDK feature block karo aur error dikhao:
   - `SDK key buy go to telegram dm:- @zoro0687`

## Integration sample (pseudo)
```java
LicenseGuardPanel panel = new LicenseGuardPanel("YOUR_PRIVATE_SECRET");
panel.validateOrThrow(clientId, userProvidedKey);
// yahan se SDK ka real flow allow karo
```

## Security notes
- Secret ko plain text me app ke andar hardcode mat rakho; backend verify best hai.
- Key issue/rotation panel se karo.
- Invalid attempts ka rate-limit/logging add karo.
