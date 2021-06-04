package com.elegion.myfirstapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.Model.RegModel;
import com.elegion.myfirstapplication.Model.User;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {
    public static final String TAG = RegistrationFragment.class.getSimpleName();

    private EditText mLogin;
    private EditText mName;
    private EditText mPassword;
    private EditText mPasswordAgain;

    private Drawable defaultEditTextStateLogin;
    private Drawable defaultEditTextStateName;
    private Drawable defaultEditTextStatePas;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private final View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputValid()) {

                User user = new User(
                        mLogin.getText().toString(),
                        mName.getText().toString(),
                        mPassword.getText().toString()
                );

                APIUtils.setEmail(mLogin.getText().toString());
                APIUtils.setPassword(mPassword.getText().toString());
                APIUtils.getAPI().registration(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action() {
                            @Override
                            public void run() throws Exception {
                                Log.i(TAG, "run:  Successful Response");

                                Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showMessage(R.string.register_erro);
                            }
                        });
            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_registration, container, false);


        mLogin = view.findViewById(R.id.etLogin);
        mName = view.findViewById(R.id.etName);
        mPassword = view.findViewById(R.id.etPassword);
        mPasswordAgain = view.findViewById(R.id.tvPasswordAgain);
        Button mRegistration = view.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);
        defaultEditTextStateLogin = mLogin.getBackground();
        defaultEditTextStateName = mName.getBackground();
        defaultEditTextStatePas = mPassword.getBackground();
        mLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mLogin.setBackground(defaultEditTextStateLogin);
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mName.setBackground(defaultEditTextStateName);
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPassword.setBackground(defaultEditTextStatePas);
            }
        });


        return view;
    }

    private boolean isInputValid() {
        String email = mLogin.getText().toString();
        return isEmailValid(email) && isPasswordsValid();
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordsValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

}
