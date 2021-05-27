package com.elegion.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.elegion.myfirstapplication.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

public class AuthFragment extends Fragment {
	public static final String TAG = AuthFragment.class.getSimpleName();
    private EditText mLogin;
    private EditText mName;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isEmailValid() && isPasswordValid()) {


				APIUtils.getAPI().getUser().enqueue(new retrofit2.Callback<NewUser>() {
                    @Override
                    public void onResponse(Call<NewUser> call, Response<NewUser> response) {
                        Log.i(TAG, "run: THERE IS A Response"  + response.code());

                        if (response.isSuccessful()){
							Log.i(TAG, "run: Successful Response"  + response.code());
                            Gson gson = new Gson();
                            NewUser user = gson.fromJson(response.body().toString(), NewUser.class);
                            Intent startProfileIntent =
                                    new Intent(getActivity(), ProfileActivity.class);
                            startProfileIntent.putExtra(ProfileActivity.USER_KEY, user.getData());
                            startActivity(startProfileIntent);
                            getActivity().finish();
                        } else {
							// todo детальна обработка ошибок
							Log.i(TAG, "run:  Fail Auth" + response.code());
						}
                    }

                    @Override
                    public void onFailure(Call<NewUser> call, Throwable t) {
                        Log.i(TAG, "run:  Failure ");

                    }
                });

//					Handler handler = new Handler(getActivity().getMainLooper());
//					@Override
//					public void onFailure(final Call call, IOException e) {
//						handler.post(new Runnable() {
//							@Override
//							public void run() {
//								Log.i(TAG, "run:  Failure ");
//							}
//						});
//					}
//
//					@Override
//					public void onResponse(Call call, Response response) throws IOException {
//						if (response.isSuccessful()){
//							Log.i(TAG, "run:  Successful Response"  + response.code());
//							try {
//								Gson gson = new Gson();
//								JsonObject json = gson.fromJson(response.body().string(), JsonObject.class);
//								User user = gson.fromJson(json.get("data"), User.class);
//								Intent startProfileIntent =
//										new Intent(getActivity(), ProfileActivity.class);
//								startProfileIntent.putExtra(ProfileActivity.USER_KEY, user);
//								startActivity(startProfileIntent);
//								getActivity().finish();
//							}catch (IOException e){
//								e.printStackTrace();
//							}
//						} else {
//							// todo детальна обработка ошибок
//							Log.i(TAG, "run:  Fail Auth" + response.code());
//						}
//					}
//				});

            } else {
                showMessage(R.string.input_error);
            }
        }
    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
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
        mEnter = v.findViewById(R.id.buttonEnter);
        mRegister = v.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);

        return v;
    }
}