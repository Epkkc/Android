package com.elegion.myfirstapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.elegion.myfirstapplication.Model.NewUser;
import com.elegion.myfirstapplication.Model.RegModel;
import com.elegion.myfirstapplication.Model.User;
import com.google.gson.Gson;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {
    public static final String TAG = RegistrationFragment.class.getSimpleName();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private EditText mLogin;
    private EditText mName;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistration;

    private Drawable defaultEditTextState;

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    private View.OnClickListener mOnRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputValid()) {

                User user = new User(
                        mLogin.getText().toString(),
                        mName.getText().toString(),
                        mPassword.getText().toString()
                );


                APIUtils.getAPI().registration(user).enqueue(new retrofit2.Callback<RegModel>() {
                    Handler handler = new Handler(getActivity().getMainLooper());

                    @Override
                    public void onResponse(Call<RegModel> call, final Response<RegModel> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "run:  Successful Response" + response.code());

                            getFragmentManager().popBackStack();
                        } else {
                            switch (response.code()) {
                                case 400:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), "Ошибка валидации", Toast.LENGTH_SHORT).show();
                                            Gson gson = new Gson();
                                            RegModel regModel = null;
                                            try {
                                                regModel = gson.fromJson(response.errorBody().string(), RegModel.class);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if (regModel.getErrors().getEmail() != null){
                                                if (regModel.getErrors().getEmail().get(0).equals("Такое значение поля email уже существует.")){
                                                    // Подсветить поле с email
                                                   mLogin.setBackgroundResource(R.drawable.border); // Рабочий костыль
                                                    //mLogin.setTextColor(getResources().getColor(R.color.red));
                                                }
                                            }
                                            if (regModel.getErrors().getPassword() != null){
                                                if (regModel.getErrors().getPassword().get(0).equals("Количество символов в поле должно быть не менее 8.")){
                                                    // Подсветить поля с паролями
                                                    mPassword.setBackgroundResource(R.drawable.border);
                                                }
                                            }
                                            if (regModel.getErrors().getName() != null){
                                                if (regModel.getErrors().getName().get(0).equals("Поле обязательно для заполнения.")){
                                                    // Подсветить поле с именем
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
                                            Toast.makeText(getActivity(), "Внутренняя ошибка сервера", Toast.LENGTH_SHORT).show();
                                        }
                                    }, 10);
                                    break;
                            }
                            // todo детальна обработка ошибок
                            Log.i(TAG, "run:  Fail Response" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<RegModel> call, Throwable t) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run:  Failure");
                            }
                        });
                    }
                });

/*                    Handler handler = new Handler(getActivity().getMainLooper());
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "run:  Failure");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            Log.i(TAG, "run:  Successful Response"  + response.code());

                            getFragmentManager().popBackStack();
                        } else {
                            // todo детальна обработка ошибок
                            Log.i(TAG, "run:  Fail Response" + response.code());
                        }
                    }
                });*/
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
        mRegistration = view.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);
        defaultEditTextState = mLogin.getBackground();
        mLogin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mLogin.setBackground(defaultEditTextState);
            }
        });

        mName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mName.setBackground(defaultEditTextState);
            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mPassword.setBackground(defaultEditTextState);
            }
        });


        return view;
    }

    private boolean isInputValid() {
        String email = mLogin.getText().toString();
        if (isEmailValid(email) && isPasswordsValid()) {
            return true;
        }

        return false;
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
