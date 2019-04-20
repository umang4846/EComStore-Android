package com.appprocessors.ecomstore.activities;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.customer.Customer;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    public static final String TAG = SignUp.class.getSimpleName();
    IEStoreAPI mServices;
    GoogleApiClient googleApiClient;
    private final static int RESOLVE_HINT = 1011;
    Boolean isPhoneAPILoaded = false;
    // User Session Manager Class
    UserSessionManager session;

    public static int REQUEST_CODE = 1000;
    UIManager uiManager;
    @BindView(R.id.input_phone)
    AppCompatEditText inputPhone;
    @BindView(R.id.input_layout_phone)
    TextInputLayout inputLayoutPhone;
    @BindView(R.id.input_password)
    AppCompatEditText inputPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    @BindView(R.id.layout3)
    LinearLayout layout3;
    @BindView(R.id.btn_login)
    MaterialButton btnLogin;

    ProgressDialog loadingdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mServices = Common.getAPI(this);


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


        btnLogin.setOnClickListener(new View.OnClickListener() {
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
        if (phone.length() == 10 && !phone.contains("+91")) {
            PhoneNumberWithCC.append("+91").append(phone);
        } else {
            PhoneNumberWithCC.append(phone);
        }

        loadingdialog = new ProgressDialog(SignIn.this);
        loadingdialog.setMessage("Please wait");
        loadingdialog.setCancelable(true);
        loadingdialog.show();


        mServices.checkUserExists(PhoneNumberWithCC.toString())
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {

                        if (response.isSuccessful()) {
                            Customer customerResponse = response.body();

                            if (customerResponse != null) {
                                if (inputPassword != null && !TextUtils.isEmpty(inputPassword.getText())) {
                                    //Perform Password matching
                                    if (customerResponse.getPassword().equals(CreatePasswordHash(inputPassword.getText().toString().trim(), customerResponse.getPasswordSalt().trim(), "SHA1"))) {
                                        loadingdialog.dismiss();
                                        String phone = null, firstName = null, lastName = null, gender = null;
                                        for (int i = 0; i < customerResponse.getGenericAttributes().size(); i++) {
                                            if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("FirstName")) {
                                                firstName = customerResponse.getGenericAttributes().get(i).getValue();
                                            }
                                            if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("LastName")) {
                                                lastName = customerResponse.getGenericAttributes().get(i).getValue();
                                            }
                                            if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("phone")) {
                                                phone = customerResponse.getGenericAttributes().get(i).getValue() != null ? customerResponse.getGenericAttributes().get(i).getValue() : PhoneNumberWithCC.toString();
                                            }
                                            if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("Gender")) {
                                                gender = customerResponse.getGenericAttributes().get(i).getValue();
                                            }
                                        }
                                        if (phone != null && firstName != null && lastName != null && gender != null) {

                                            // User Session Manager
                                            session = new UserSessionManager(getApplicationContext());
                                            //Save user Credentials in Shared Preference
                                            session.createUserLoginSession(
                                                    !phone.contains("+91") && phone.length() == 10 ? "+91" + phone.trim() : phone,
                                                    firstName,
                                                    lastName,
                                                    customerResponse.getEmail(),
                                                    customerResponse.getPassword(),
                                                    gender
                                            );
                                            Toast.makeText(SignIn.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignIn.this, HomeActivity.class);
                                            // Add new Flag to start new Activity
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            loadingdialog.dismiss();
                                            Toast.makeText(SignIn.this, "Unable to store Customer Details", Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (!customerResponse.getPassword().equals(CreatePasswordHash(inputPassword.getText().toString().trim(), customerResponse.getPasswordSalt().trim(), "SHA1"))) {
                                        loadingdialog.dismiss();
                                        Toast.makeText(SignIn.this, "Incorrect Password !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                //User not exists
                                loadingdialog.dismiss();
                                Toast.makeText(SignIn.this, "Phone number is not registered !", Toast.LENGTH_SHORT).show();


                            }
                        }

                        if (!response.isSuccessful()) {
                            loadingdialog.dismiss();
                            Toast.makeText(SignIn.this, "Response Failed !" + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        loadingdialog.dismiss();
                        Toast.makeText(SignIn.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    loadingdialog = new ProgressDialog(SignIn.this);
                    loadingdialog.setMessage("Please wait");
                    loadingdialog.setCancelable(true);
                    loadingdialog.show();

                    //Get User phone and check exists on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            StringBuilder PhoneNumberWithCC = new StringBuilder();
                            if (account.getPhoneNumber().toString().length() == 10) {
                                PhoneNumberWithCC = PhoneNumberWithCC.append("+91").append(account.getPhoneNumber().toString());
                            } else {
                                PhoneNumberWithCC = PhoneNumberWithCC.append(account.getPhoneNumber().toString());
                            }
                            mServices.checkUserExists(String.valueOf(PhoneNumberWithCC))
                                    .enqueue(new Callback<Customer>() {
                                        @Override
                                        public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {
                                            Customer customerResponce = response.body();

                                            if (customerResponce != null) {
                                                String phone = null;
                                                for (int i = 0; i < customerResponce.getGenericAttributes().size(); i++) {
                                                    if (customerResponce.getGenericAttributes().get(i).getKey().equalsIgnoreCase("phone")) {
                                                        phone = customerResponce.getGenericAttributes().get(i).getValue();
                                                    }
                                                }
                                                if (phone != null) {
                                                    loadingdialog.dismiss();
                                                    //User Already registered
                                                    Toast.makeText(SignIn.this, "Already registered on entered Phone !", Toast.LENGTH_SHORT).show();

                                                    //Open next activity
                                                    Intent intent = new Intent(SignIn.this, SignIn.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                    // Add new Flag to start new Activity
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);

                                                    finish();
                                                } else {
                                                    //User not exists
                                                    loadingdialog.dismiss();
                                                    StartRegister(account.getPhoneNumber().toString());
                                                }


                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<Customer> call, Throwable t) {
                                            loadingdialog.dismiss();
                                            Toast.makeText(SignIn.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("checkUserExists :", t.getMessage() + "\n");

                                        }

                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Toast.makeText(SignIn.this, "Error :" + accountKitError.getErrorType().toString(), Toast.LENGTH_SHORT).show();
                            Log.d("ERROR :", accountKitError.getErrorType().getMessage());
                        }
                    });

                }


            }
        }
    }

    public void StartRegister(final String phone) {
        StringBuilder PhoneNumberWithCC = new StringBuilder();
        if (phone.length() == 10 && !phone.contains("+91")) {
            PhoneNumberWithCC.append("+91").append(phone);
        } else {
            PhoneNumberWithCC.append(phone);
        }
        Intent intent = new Intent(this, SignUp.class);
        intent.putExtra("phone", String.valueOf(PhoneNumberWithCC));
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

    public String CreatePasswordHash(String password, String saltkey, String sha11) {

        if (sha11.isEmpty())
            sha11 = "SHA1";
        String saltAndPassword = password.concat(saltkey);
        String sha1 = "";

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(saltAndPassword.getBytes(StandardCharsets.UTF_8));
            sha1 = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha1.toUpperCase();

    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
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
