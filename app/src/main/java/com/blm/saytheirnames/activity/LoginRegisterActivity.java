package com.blm.saytheirnames.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.adapters.AuthenticationPagerAdapter;
import com.blm.saytheirnames.fragments.LoginFragment;
import com.blm.saytheirnames.fragments.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    ViewPager mViewPager;
    AuthenticationPagerAdapter mAuthenticationPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initWidgets();
    }

    private void initWidgets() {
        mViewPager = findViewById(R.id.view_pager);
        mAuthenticationPagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager(), 0);
        mAuthenticationPagerAdapter.addFragment(new LoginFragment());
        mAuthenticationPagerAdapter.addFragment(new RegisterFragment());

        mViewPager.setAdapter(mAuthenticationPagerAdapter);
    }
}
