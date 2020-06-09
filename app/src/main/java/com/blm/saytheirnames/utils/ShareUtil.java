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

public class ShareUtil {

    public static void shareBaseLink(Context context, String url) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Share via...");
            intent.putExtra(Intent.EXTRA_TEXT, "#SayTheirNames\n" + url);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Error with link", Toast.LENGTH_SHORT).show();
        }
    }

}
