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


                APIUtils.getAPI(mLogin.getText().toString(), mPassword.getText().toString()).registration(user).enqueue(new retrofit2.Callback<RegModel>() {
                    final Handler handler = new Handler(getActivity().getMainLooper());

                    @Override
                    public void onResponse(@NonNull Call<RegModel> call, @NonNull final Response<RegModel> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "run:  Successful Response" + response.code());

                            Toast.makeText(getContext(), "??????????????", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        } else {
                            switch (response.code()) {
                                case 400:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "???????????? ??????????????????", Toast.LENGTH_SHORT).show();
                                            Gson gson = new Gson();
                                            RegModel regModel = null;
                                            try {
                                                regModel = gson.fromJson(response.errorBody().string(), RegModel.class);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (regModel.getErrors().getEmail() != null) {
                                                if (regModel.getErrors().getEmail().get(0).equals("?????????? ???????????????? ???????? email ?????? ????????????????????.")) {
                                                    // ???????????????????? ???????? ?? email
                                                    mLogin.setBackgroundResource(R.drawable.border); // ?????????????? ??????????????
                                                    //mLogin.setTextColor(getResources().getColor(R.color.red));
                                                }
                                            }
                                            if (regModel.getErrors().getPassword() != null) {
                                                if (regModel.getErrors().getPassword().get(0).equals("???????????????????? ???????????????? ?? ???????? ???????????? ???????? ???? ?????????? 8.")) {
                                                    // ???????????????????? ???????? ?? ????????????????
                                                    mPassword.setBackgroundResource(R.drawable.border);
                                                }
                                            }
                                            if (regModel.getErrors().getName() != null) {
                                                if (regModel.getErrors().getName().get(0).equals("???????? ?????????????????????? ?????? ????????????????????.")) {
                                                    // ???????????????????? ???????? ?? ????????????
                                                    mName.setBackgroundResource(R.drawable.border);
                                                }
                                            }


                                        }
                                    }, 10);
                                    break;
                                case 500:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "???????????????????? ???????????? ??????????????", Toast.LENGTH_SHORT).show();
                                        }
                                    }, 10);
                                    break;
                            }
                            Log.i(TAG, "run:  Fail Response" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegModel> call, @NonNull Throwable t) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run:  Failure");
                            }
                        });
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
