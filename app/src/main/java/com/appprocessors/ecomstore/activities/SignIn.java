package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.User;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.ui.SkinManager;
import com.facebook.accountkit.ui.UIManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    Button signin;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    private AppCompatEditText inputPhone, inputPassword;
    private TextInputLayout inputLayoutPhone, inputLayoutPassword;
    public static final String TAG = SignUp.class.getSimpleName();
    IEStoreAPI mServices;
    String decPass;
    GoogleApiClient googleApiClient;
    private final static int RESOLVE_HINT = 1011;
    Boolean isPhoneAPILoaded = false;
    // User Session Manager Class
    UserSessionManager session;

    public static int REQUEST_CODE = 1000;
    UIManager uiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mServices = Common.getAPI();

        inputPhone = (AppCompatEditText) findViewById(R.id.input_mobile);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputPassword = findViewById(R.id.input_password);
        signin = findViewById(R.id.btn_signin);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.CREDENTIALS_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        googleApiClient.connect();

        uiManager = new SkinManager(
                SkinManager.Skin.TRANSLUCENT, getResources().getColor(R.color.blue_adv), (R.drawable.intro_back), SkinManager.Tint.WHITE, 50.0);

        //Textview signp clicked
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLoginPage();
            }
        });

        inputPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPhoneAPILoaded)
                    loadPhoneSelectorAPI();
            }

        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData();
            }
        });


    }

    public void StartLoginPage() {

        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);


        builder.setUIManager(uiManager);
        // builder.setSMSWhitelist(new String[]{"IN"});
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, builder.build());
        startActivityForResult(intent, REQUEST_CODE);

    }

    // Construct a request for phone numbers and show the picker
    private void loadPhoneSelectorAPI() {


        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        isPhoneAPILoaded = true;
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0, null);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            Log.d(TAG, "loadPhoneSelectorAPI: ERROR :: " + e.getMessage());
        }
    }


    private void SubmitData() {

        if (!validatePhone()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        inputLayoutPhone.setError(null);
        inputLayoutPhone.setErrorEnabled(false);
        inputLayoutPassword.setError(null);
        inputLayoutPassword.setErrorEnabled(false);
        inputLayoutPhone.clearFocus();
        inputPhone.clearFocus();
        inputLayoutPassword.clearFocus();
        inputPassword.clearFocus();
        StartLogin(inputPhone.getText().toString());

    }

    private void StartLogin(String phone) {
        StringBuilder PhoneNumberWithCC = new StringBuilder();
        if (phone.length()==10){
            PhoneNumberWithCC = PhoneNumberWithCC.append("+91").append(phone);
        }
        else {
            PhoneNumberWithCC = PhoneNumberWithCC.append(phone);
        }
        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(SignIn.this).build();
        alertDialog.show();
        alertDialog.setTitle("Trying to being part of you !");
        alertDialog.setMessage("Please wait");


        mServices.checkUserExists(String.valueOf(PhoneNumberWithCC))
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, @NonNull Response<User> response) {
                        User userResponse = response.body();

                        if (userResponse != null) {

                            try {
                                decPass = decrypt(userResponse.getPassword(), "password");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            //Perform Password matching
                            if (inputPassword.getText().toString().equals(decPass)) {
                                alertDialog.dismiss();
                                Common.currentUser = response.body();

                                //Save user Credentials in Shared Preference
                                session.createUserLoginSession(userResponse.getPhone(),
                                        userResponse.getName(),
                                        userResponse.getEmail(),
                                        userResponse.getPassword(),
                                        userResponse.getGender()
                                );
                                Toast.makeText(SignIn.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else if (!(inputPassword.getText().toString().equals(decPass))) {
                                alertDialog.dismiss();
                                Toast.makeText(SignIn.this, "Incorrect Password !", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            //User not exists
                            alertDialog.dismiss();
                            Toast.makeText(SignIn.this, "Phone number is not registered !", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        alertDialog.dismiss();
                        Toast.makeText(SignIn.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                        Log.d("checkUserExists :", t.getMessage() + "\n");

                    }
                });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                if (credential != null) {
                    inputPhone.setText(credential.getId());
                } else {
                    Toast.makeText(this, "No phone number available\nPlease Add Number Manually", Toast.LENGTH_SHORT).show();

                }
            }
        }

        if (requestCode == REQUEST_CODE) {

            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (result.getError() != null) {
                Toast.makeText(this, result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            } else if (result.wasCancelled()) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            } else {
                if (result.getAccessToken() != null) {
                    final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(this).build();
                    alertDialog.show();
                    alertDialog.setMessage("Please Wait...");

                    //Get User phone and check exists on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            StringBuilder PhoneNumberWithCC = new StringBuilder();
                            if (account.getPhoneNumber().toString().length()==10){
                                PhoneNumberWithCC = PhoneNumberWithCC.append("+91").append(account.getPhoneNumber().toString());
                            }
                            else {
                                PhoneNumberWithCC = PhoneNumberWithCC.append(account.getPhoneNumber().toString());
                            }
                            mServices.checkUserExists(String.valueOf(PhoneNumberWithCC))
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, @NonNull Response<User> response) {
                                            User userResponse = response.body();

                                            if (userResponse!=null) {

                                                Common.currentUser = response.body();
                                                alertDialog.dismiss();
                                                //User Already registered
                                                Toast.makeText(SignIn.this, "Phone number is already exist !", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                //User not exists
                                                alertDialog.dismiss();
                                                StartRegister(account.getPhoneNumber().toString());
                                            }

                                        }


                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            alertDialog.dismiss();
                                            Toast.makeText(SignIn.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                                            Log.d("checkUserExists :", t.getMessage()+"\n");

                                        }
                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Log.d("ERROR :", accountKitError.getErrorType().getMessage());
                        }
                    });

                }
            }


        }
    }

    public void StartRegister(final String phone) {
        StringBuilder PhoneNumberWithCC = new StringBuilder();
        if (phone.length()==10){
            PhoneNumberWithCC = PhoneNumberWithCC.append("+91").append(phone);
        }
        else {
            PhoneNumberWithCC = PhoneNumberWithCC.append(phone);
        }
        String phoneNew = String.valueOf(PhoneNumberWithCC);
        Intent intent = new Intent(this, SignUp.class);
        intent.putExtra("phone", phoneNew);
        startActivity(intent);
    }
    private boolean validatePhone() {
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhone);
            return false;
        } else if (inputPhone.getText().toString().trim().length() < 9 || inputPhone.getText().toString().trim().length() > 13) {
            inputLayoutPhone.setError(getString(R.string.err_msg_lenth_phone));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setError(null);
            inputLayoutPhone.setErrorEnabled(false);
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
        } else {
            inputLayoutPhone.setError(null);
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
