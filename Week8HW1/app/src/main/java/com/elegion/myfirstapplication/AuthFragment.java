package com.elegion.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
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

import com.elegion.myfirstapplication.Model.NewUser;
import com.elegion.myfirstapplication.albums.AlbumsActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class AuthFragment extends Fragment {
    public static final String TAG = AuthFragment.class.getSimpleName();
    private EditText mLogin;
    private EditText mName;
    private EditText mPassword;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {
                APIUtils.setEmail(mLogin.getText().toString());
                APIUtils.setPassword(mPassword.getText().toString());
                APIUtils.getAPI().getUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newUser -> {
                                    Log.i(TAG, "run: Successful Response " + " (THERE SHOULD STATUS CODE)");
//
//                                    Intent startProfileIntent =
//                                            new Intent(getActivity(), ProfileActivity.class);
//                                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, newUser);
//                                    startActivity(startProfileIntent);
//                                    getActivity().finish();
                                    startActivity(new Intent(getActivity(), AlbumsActivity.class));
                                    getActivity().finish();
                                    },
                                throwable -> Log.i(TAG, "run: Failure Response"));
//                        .enqueue(new retrofit2.Callback<NewUser>() {
//                            @Override
//                            public void onResponse(@NonNull Call<NewUser> call, @NonNull Response<NewUser> response) {
//                                Log.i(TAG, "run: THERE IS A Response" + response.code());
//
//                                if (response.isSuccessful()) {
//                                    Log.i(TAG, "run: Successful Response " + response.code());
//                                    NewUser user = response.body();
//                                    Intent startProfileIntent =
//                                            new Intent(getActivity(), ProfileActivity.class);
//                                    startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
//                                    startActivity(startProfileIntent);
//                                    getActivity().finish();
//                                } else {
//                                    Log.i(TAG, "run:  Fail Auth" + response.code());
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(@NonNull Call<NewUser> call, @NonNull Throwable t) {
//                                Log.i(TAG, "run:  Failure ");
//
//                            }
//                        });

            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private final View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                    .commit();
        }
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mLogin.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container, false);

        mLogin = v.findViewById(R.id.etLogin);
        mName = v.findViewById(R.id.etName);
        mPassword = v.findViewById(R.id.etPassword);
        Button mEnter = v.findViewById(R.id.buttonEnter);
        Button mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);

        return v;
    }
}
