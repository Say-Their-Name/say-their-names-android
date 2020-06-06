package com.blm.saytheirnames.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.customTabs.CustomTabActivityHelper;
import com.blm.saytheirnames.customTabs.WebViewActivity;

public class AboutFragment extends Fragment {

    private View myFragment;

    String twitterLink = "https://twitter.com/SayTheirName_io";
    String slackLink = "https://app.slack.com/client/T014JL5B3SN/C014X92G997";
    String repoLink = "https://github.com/Say-Their-Name/";

    private Button btnTwitter,btnSlack,btnRepo;

    private Toolbar toolbar;


    public static AboutFragment newInstance() {
        return new AboutFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_about, container, false);

        toolbar = myFragment.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        btnRepo = myFragment.findViewById(R.id.btnViewRepo);
        btnTwitter = myFragment.findViewById(R.id.btnFollowUs);
        btnSlack = myFragment.findViewById(R.id.btnSlackChannel);

        btnRepo.setOnClickListener(v -> {
         visitPages(repoLink);
        });
        btnSlack.setOnClickListener(v -> {
        visitPages(slackLink);
        });
        btnTwitter.setOnClickListener(v -> visitPages(twitterLink));

        return myFragment;
    }

    private void visitPages(String link){
        if (validateUrl(link)) {
            Uri uri = Uri.parse(link);
            if (uri != null) {
                openCustomChromeTab(uri);
            }
        } else {
            Toast.makeText(requireActivity(), "Error with link", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateUrl(String url) {
        return url != null && url.length() > 0 && (url.startsWith("http://") || url.startsWith("https://"));
    }

    private void openCustomChromeTab(Uri uri) {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        intentBuilder.setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark));

        CustomTabActivityHelper.openCustomTab(requireActivity(), customTabsIntent, uri, (activity, uri1) -> openWebView(uri1));
    }

    private void openWebView(Uri uri) {
        Intent webViewIntent = new Intent(requireActivity(), WebViewActivity.class);
        webViewIntent.putExtra(WebViewActivity.EXTRA_URL, uri.toString());
        webViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(webViewIntent);
    }
}