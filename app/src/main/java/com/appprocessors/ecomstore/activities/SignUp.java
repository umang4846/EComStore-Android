package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.User;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {


    Button register;
    private AppCompatEditText inputName, inputEmail, inputPassword, inputConfirmPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    RadioGroup gender;
    public static final String TAG = SignUp.class.getSimpleName();
    String phone;
    IEStoreAPI mServices;
    String encPass;
    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (getIntent() != null) {
            phone = getIntent().getStringExtra("phone");
        }
        mServices = Common.getAPI();

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        gender = (RadioGroup) findViewById(R.id.rg_gender);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_confirm_password);
        register = findViewById(R.id.btn_signup_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData();
            }
        });

    }

    private void SubmitData() {

        if (!validateName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (!validateConfirmPassword()) {
            return;
        }
        if (!matchpassword()) {
            return;
        }
        if (validateGender()) {
            return;
        }
        inputLayoutName.setError(null);
        inputLayoutName.setErrorEnabled(false);
        inputLayoutEmail.setError(null);
        inputLayoutEmail.setErrorEnabled(false);
        inputLayoutPassword.setError(null);
        inputLayoutPassword.setErrorEnabled(false);
        inputLayoutConfirmPassword.setError(null);
        inputLayoutConfirmPassword.setErrorEnabled(false);
        inputLayoutName.clearFocus();
        inputLayoutEmail.clearFocus();
        inputLayoutPassword.clearFocus();
        inputLayoutConfirmPassword.clearFocus();
        inputName.clearFocus();
        inputEmail.clearFocus();
        inputEmail.clearFocus();
        inputConfirmPassword.clearFocus();
        StartRegistering(phone);

    }

    private boolean validateGender() {

        if (gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(SignUp.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean matchpassword() {
        if (!inputConfirmPassword.getText().toString().equals(inputPassword.getText().toString())) {
            inputLayoutConfirmPassword.setError(getString(R.string.err_msg_match_password));
            requestFocus(inputConfirmPassword);
            return false;
        } else {
            inputLayoutConfirmPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
            inputLayoutName.setError(null);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
            inputLayoutEmail.setError(null);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else if (inputPassword.getText().length() < 6) {
            inputLayoutPassword.setError(getString(R.string.err_msg_lenth_password));
            requestFocus(inputPassword);
            return false;
        } else if (!Common.PASSWORD_PATTERN.matcher(inputPassword.getText().toString()).matches()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password_weak));
            requestFocus(inputPassword);
            return false;

        } else {
            inputLayoutPassword.setErrorEnabled(false);
            inputLayoutPassword.setError(null);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        if (inputConfirmPassword.getText().toString().trim().isEmpty()) {
            inputLayoutConfirmPassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(inputConfirmPassword);
            return false;
        } else if (inputConfirmPassword.getText().length() < 6) {
            inputLayoutConfirmPassword.setError(getString(R.string.err_msg_lenth_password));
            requestFocus(inputConfirmPassword);
            return false;
        } else {
            inputLayoutConfirmPassword.setErrorEnabled(false);
            inputLayoutConfirmPassword.setError(null);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //Password Encryption Decryption Start
    String AES = "AES";

    String encrypt(String data, String passKey) throws Exception {
        SecretKeySpec key = generateKey(passKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }


    public SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

    public String decrypt(String data, String passKey) throws Exception {
        SecretKeySpec key = generateKey(passKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedValue = Base64.decode(data, Base64.DEFAULT);
        byte[] encVal = c.doFinal(decodedValue);
        String encryptedValue = new String(encVal);
        return encryptedValue;
    }


    //Start Registering
    private void StartRegistering(final String phone) {

        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(SignUp.this).build();
        alertDialog.show();
        alertDialog.setTitle("Trying to being part of you !");
        alertDialog.setMessage("Please wait");

        try {
            encPass = encrypt(inputPassword.getText().toString(), "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setPhone(phone);
        user.setName(inputName.getText().toString());
        user.setEmail(inputEmail.getText().toString());
        user.setPassword(encPass);
        user.setGender(((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString());

        mServices.registerUser(user)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User userResponse = response.body();
                        if (userResponse != null) {
                            alertDialog.dismiss();
                            //Add to current User Variable
                            Common.currentUser = response.body();
                            //Save user Credentials in Shared Preference
                            session.createUserLoginSession(
                                    userResponse.getPhone(),
                                    userResponse.getName(),
                                    userResponse.getEmail(),
                                    userResponse.getPassword(),
                                    userResponse.getGender()
                            );
                            //Open next activity
                            Intent intent = new Intent(SignUp.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        alertDialog.dismiss();
                        Toast.makeText(SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: SignUp Activity ::" + t.getMessage());
                    }
                });


    }

}


