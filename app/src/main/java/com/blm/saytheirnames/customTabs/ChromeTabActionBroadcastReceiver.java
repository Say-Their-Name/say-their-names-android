package com.blm.saytheirnames.customTabs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.blm.saytheirnames.R;

public class ChromeTabActionBroadcastReceiver extends BroadcastReceiver {
    public static final String KEY_ACTION_SOURCE = "org.chromium.customtabsdemos.ACTION_SOURCE";
    public static final int ACTION_MENU_ITEM_1 = 1;
    public static final int ACTION_MENU_ITEM_2 = 2;
    public static final int ACTION_ACTION_BUTTON = 3;

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getDataString();

        if (data != null)
            Toast.makeText(context, getToastText(context, intent.getIntExtra(KEY_ACTION_SOURCE, -1)), Toast.LENGTH_SHORT).show();
    }

    private String getToastText(Context context, int actionSource) {
        switch (actionSource) {
            case ACTION_MENU_ITEM_1:
                return context.getString(R.string.action_back);
            case ACTION_MENU_ITEM_2:
                return context.getString(R.string.action_forward);
            case ACTION_ACTION_BUTTON:
                return context.getString(R.string.action_bookmark);
            default:
                return context.getString(R.string.action_settings);
        }
    }
}