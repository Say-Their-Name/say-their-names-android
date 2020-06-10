package io.saytheirnames.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ShareUtil {

    public static void shareBaseLink(Context context, String url) {
        if (url != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "#SayTheirNames\n" + url);
            context.startActivity(Intent.createChooser(intent,"Share via..."));
        } else {
            Toast.makeText(context, "Error with link", Toast.LENGTH_SHORT).show();
        }
    }

}
