package com.blm.saytheirnames.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blm.saytheirnames.R;
import com.blm.saytheirnames.activity.MainActivity;
import com.blm.saytheirnames.util.KeyboardUtilities;
import com.blm.saytheirnames.util.StringValidation;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "RegisterFragment";

    //Widgets
    private TextInputLayout mUserFullName;
    private TextInputLayout mUserEmail;
    private TextInputLayout mUserPassword;
    private TextInputLayout mUserRePassword;
    private Button mRegisterBtn;
    private LinearLayout mRegisterLayout;

    private boolean mIsNameValid, mIsEmailValid, mIsPasswordValid;
    private Context mContext;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = view.getContext();

        initWidgets();
        initListeners();
        setupTextListeners();
    }

    private void initWidgets() {
        mUserFullName = getView().findViewById(R.id.input_register_name);
        mUserEmail = getView().findViewById(R.id.input_register_email);
        mUserPassword = getView().findViewById(R.id.input_register_password);
        mUserRePassword = getView().findViewById(R.id.input_register_repassword);
        mRegisterBtn = getView().findViewById(R.id.btn_register);
        mRegisterLayout = getView().findViewById(R.id.register_layout);

        Log.d(TAG, "initWidgets: all widgets initialized");
    }

    private void initListeners() {
        mRegisterBtn.setOnClickListener(this);

        mRegisterLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    KeyboardUtilities.hideSoftKeyboard(getActivity());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        String username = mUserFullName.getEditText().getText().toString();
        String email = mUserEmail.getEditText().getText().toString();
        String password = mUserPassword.getEditText().getText().toString();

        if (!mIsNameValid) {
            mUserFullName.setErrorEnabled(true);
            mUserFullName.setError(getString(R.string.error_name));
        }

        if (!mIsEmailValid) {
            mUserEmail.setErrorEnabled(true);
            mUserEmail.setError(getString(R.string.error_email));
        }

        if (password.length() < 6) {
            mUserPassword.setErrorEnabled(true);
            mUserRePassword.setErrorEnabled(true);
            mUserPassword.setError(getString(R.string.error_password_length));
            mUserRePassword.setError(getString(R.string.error_password_length));
            return;
        }

        if (mIsNameValid && mIsEmailValid && mIsPasswordValid) {
            Log.d(TAG, "onClick: Registering new user");
            //TODO: implement registration and use person's model to create the new user
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public void setupTextListeners() {

        mUserFullName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateUsername(s.toString());
            }
        });
        mUserEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail(s.toString());
            }
        });
        mUserPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIsPasswordValid = doesPasswordMatch(s.toString(),
                        mUserRePassword.getEditText().getText().toString());
            }
        });
        mUserRePassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mIsPasswordValid = doesPasswordMatch(mUserPassword.getEditText().getText().toString(),
                        s.toString());
            }
        });
    }

    public void validateUsername(String username) {
        if (username.isEmpty()) {
            mUserFullName.setErrorEnabled(true);
            mUserFullName.setError(getString(R.string.error_name));
            mIsNameValid = false;
        } else {
            mUserFullName.setError(null);
            mUserFullName.setErrorEnabled(false);
            mIsNameValid = true;
        }
    }

    public void validateEmail(String email) {
        if (!StringValidation.isValidEmail(email)) {
            mUserEmail.setErrorEnabled(true);
            mUserEmail.setError(getString(R.string.error_email));
            mIsEmailValid = false;
        } else {
            mUserEmail.setError(null);
            mUserEmail.setErrorEnabled(false);
            mIsEmailValid = true;
        }
    }

    public boolean doesPasswordMatch(String password1, String password2) {
        if (!StringValidation.isPasswordEqual(password1, password2)) {
            mUserPassword.setErrorEnabled(true);
            mUserRePassword.setErrorEnabled(true);
            mUserPassword.setError(getString(R.string.error_password_do_not_match));
            mUserRePassword.setError(getString(R.string.error_password_do_not_match));
            return false;
        } else {
            mUserPassword.setError(null);
            mUserRePassword.setError(null);
            mUserPassword.setErrorEnabled(false);
            mUserRePassword.setErrorEnabled(false);
            return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }
}
