package io.saytheirnames.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);*/

        mBottomNav = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mBottomNav.setOnNavigationItemSelectedListener(item -> {
            selectFragment(item);
            return true;
        });

        setDefaultFragment();
        // updateToolbarText();

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);

            //this will also highlight the home menu item icon
            updateBottomNavBasedOnTag(HOME_TAG);
        } else {
            super.onBackPressed();
        }
    }

    private void setDefaultFragment() {
        mBottomNav.setSelectedItemId(R.id.navigation_home);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, HomeFragment.newInstance(), HOME_TAG);
        transaction.commit();
    }

    // method to update selected item in bottom nav when navigating b/t fragments in the same activity
    public void updateBottomNavBasedOnTag(String fragmentTag) {
        switch (fragmentTag) {
            case DONATIONS_TAG:
                mSelectedItem = R.id.navigation_donation;
                break;
            case PETITIONS_TAG:
                mSelectedItem = R.id.navigation_petitions;
                break;
            case ABOUT_TAG:
                mSelectedItem = R.id.navigation_about;
                break;
            default:
                mSelectedItem = R.id.navigation_home;
                break;
        }
        mBottomNav.setSelectedItemId(mSelectedItem);
    }

    private void selectFragment(MenuItem item) {

        mSelectedItem = item.getItemId();
        Fragment frag = null;
        String tag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.navigation_home:
                frag = HomeFragment.newInstance();
                tag = HOME_TAG;
                break;
            case R.id.navigation_donation:
                frag = DonationFragment.newInstance();
                tag = DONATIONS_TAG;
                break;
            case R.id.navigation_petitions:
                frag = PetitionsFragment.newInstance();
                tag = PETITIONS_TAG;
                break;
            case R.id.navigation_about:
                frag = AboutFragment.newInstance();
                tag = ABOUT_TAG;
                break;
            default:
        }

        // update selected item
        mSelectedItem = item.getItemId();

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, tag);
            ft.commit();
        }
    }
}