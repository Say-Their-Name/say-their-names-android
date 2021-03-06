package io.saytheirnames.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.saytheirnames.R;
import io.saytheirnames.fragments.AboutFragment;
import io.saytheirnames.fragments.DonationFragment;
import io.saytheirnames.fragments.HomeFragment;
import io.saytheirnames.fragments.PetitionsFragment;

public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM = "arg_selected_item";
    private static final String HOME_TAG = "HomeFragment";
    private static final String DONATIONS_TAG = "DonationFragment";
    private static final String PETITIONS_TAG = "PetitionsFragment";
    private static final String ABOUT_TAG = "AboutFragment";

    private BottomNavigationView mBottomNav;
    private MenuItem menuItem;
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getBottomNavigation();
        checkSavedInstanceState(savedInstanceState);
    }

    private void getBottomNavigation() {
        mBottomNav = findViewById(R.id.nav_view);
        mBottomNav.setOnNavigationItemSelectedListener(item -> {
            selectFragment(item);
            return true;
        });

        setBottomNavHomeFragment();
    }

    private void setBottomNavHomeFragment() {
        mBottomNav.setSelectedItemId(R.id.navigation_home);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, HomeFragment.newInstance(), HOME_TAG);
        transaction.commit();
    }

    private void checkSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            menuItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            menuItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(menuItem);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        menuItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != menuItem.getItemId()) {
            selectFragment(menuItem);
            updateBottomNavBasedOnTag(HOME_TAG);
        } else {
            super.onBackPressed();
        }
    }

    public void updateBottomNavBasedOnTag(String fragmentTag) {
        switch (fragmentTag) {
            case HOME_TAG:
                mSelectedItem = R.id.navigation_home;
                break;
            case DONATIONS_TAG:
                mSelectedItem = R.id.navigation_donation;
                break;
            case PETITIONS_TAG:
                mSelectedItem = R.id.navigation_petitions;
                break;
            case ABOUT_TAG:
                mSelectedItem = R.id.navigation_about;
                break;
        }
        mBottomNav.setSelectedItemId(mSelectedItem);
    }

    private void selectFragment(MenuItem item) {
        mSelectedItem = item.getItemId();
        Fragment fragment = null;
        String tag = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = HomeFragment.newInstance();
                tag = HOME_TAG;
                break;
            case R.id.navigation_donation:
                fragment = DonationFragment.newInstance();
                tag = DONATIONS_TAG;
                break;
            case R.id.navigation_petitions:
                fragment = PetitionsFragment.newInstance();
                tag = PETITIONS_TAG;
                break;
            case R.id.navigation_about:
                fragment = AboutFragment.newInstance();
                tag = ABOUT_TAG;
                break;
            default:
        }

        mSelectedItem = item.getItemId();

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, fragment, tag);
            ft.commit();
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onDestroy() {
        super.onDestroy();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Glide.get(getApplicationContext()).clearDiskCache();
                return null;
            }
        };
    }
}