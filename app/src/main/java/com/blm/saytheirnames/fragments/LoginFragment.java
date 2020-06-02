package com.blm.saytheirnames.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.MainActivity;
import com.blm.saytheirnames.util.KeyboardUtilities;
import com.blm.saytheirnames.util.StringValidation;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";

    //Widgets
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mLoginBtn;
    private ProgressBar mProgressBar;
    private RelativeLayout mLoginLayout;

    private Context mContext;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();
        initWidgets();
        initListeners();
    }

    private void initWidgets() {
        mEmail = getView().findViewById(R.id.input_login_email);
        mPassword = getView().findViewById(R.id.input_login_password);
        mLoginBtn = getView().findViewById(R.id.btn_login);
        mProgressBar = getView().findViewById(R.id.login_progressbar);
        mLoginLayout = getView().findViewById(R.id.login_layout);
        Log.w(TAG, "initWidgets: widgets initialized");

    }

    private void initListeners() {
        mLoginBtn.setOnClickListener(this);

        //Hides keyboard if clicked outside of the edit text views
        mLoginLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyboardUtilities.hideSoftKeyboard(getActivity());
                }
            }
        });
    }

    /**
     * Uses email and password to try and sign in to a database
     *
     * @param email
     * @param password
     */
    private void loginWithEmailPassword(String email, String password) {
        //TODO: needs implementation
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onClick(View v) {
        String email = mEmail.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();

        if (StringValidation.isValidInput(email, password) && StringValidation.isValidEmail(email)) {
            mProgressBar.setVisibility(View.VISIBLE);
            Log.w(TAG, "onClick: attempting to log in");
            loginWithEmailPassword(email, password);
        } else {
            Log.w(TAG, "onClick: either email or password is not valid");
            Toast.makeText(mContext, "Please make sure to enter a valid email and password", Toast.LENGTH_SHORT).show();
        }
    }
}
