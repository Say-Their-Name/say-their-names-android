package com.blm.saytheirnames.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.customTabs.CustomTabActivityHelper;
import com.blm.saytheirnames.customTabs.WebViewActivity;

public class CustomTabUtil {

    public static void openCustomTabForUrl(Context context, String url) {
        if (validateUrl(url)) {
            Uri uri = Uri.parse(url);
            if (uri != null) {
                openCustomChromeTab(context, uri);
            }
        } else {
            Toast.makeText(context, "Error with link", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean validateUrl(String url) {
        return url != null && url.length() > 0 && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private static void openCustomChromeTab(Context context, Uri uri) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        CustomTabActivityHelper.openCustomTab(context, customTabsIntent, uri, (activity, uri1) -> openWebView(context, uri1));
    }

    private static void openWebView(Context context, Uri uri) {
        Intent webViewIntent = new Intent(context, WebViewActivity.class);
        webViewIntent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(webViewIntent);
    }

}
