package top.niunaijun.licensing;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public final class TelegramSupport {

  private TelegramSupport() {}

  public static void openSellerDm(Context context) {
    String message = "SDK key buy telegram dm kro main :- @zoro0687";
    Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=zoro0687"));
    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    try {
      context.startActivity(appIntent);
      return;
    } catch (ActivityNotFoundException ignored) {
      // fallthrough to browser url
    }

    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/zoro0687"));
    webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(webIntent);
  }
}
