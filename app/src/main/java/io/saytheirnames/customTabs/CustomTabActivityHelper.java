package io.saytheirnames.customTabs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

public class CustomTabActivityHelper {

    public interface CustomTabFallback {
        void openUri(Context activity, Uri uri);
    }

    public static void openCustomTab(Context activity, CustomTabsIntent customTabsIntent,
                                     Uri uri, CustomTabFallback fallback) {
        String packageName = CustomTabsHelper.getPackageNameToUse(activity);

        if (packageName == null) {
            if (fallback != null)
                fallback.openUri(activity, uri);
        } else {
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            customTabsIntent.launchUrl(activity, uri);
        }
    }
}
