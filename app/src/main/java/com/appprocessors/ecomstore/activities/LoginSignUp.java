package com.appprocessors.ecomstore.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
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
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignUp extends AppCompatActivity {

    private static final String TAG = "MapActivity";
    private static final String mREAD_SMS = Manifest.permission.READ_SMS;
    private static final String mRECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    private static final int SMS_PERMISSION_REQUEST_CODE = 1234;
    public static int REQUEST_CODE = 1000;
    IEStoreAPI mServices;
    UIManager uiManager;
    @BindView(R.id.ls_LL)
    LinearLayout lsLL;
    @BindView(R.id.btn_signin)
    MaterialButton btnSignin;
    @BindView(R.id.btn_signup)
    MaterialButton btnSignup;

    ProgressDialog loadingdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        ButterKnife.bind(this);

        mServices = Common.getAPI(this);

        uiManager = new SkinManager(
                SkinManager.Skin.TRANSLUCENT, getResources().getColor(R.color.blue_adv), (R.drawable.intro_back), SkinManager.Tint.WHITE, 50.0);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLoginPage();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginSignUp.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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
        //Asks for Sms Permission
        getSmsPermission();
        //Enable to Detect SMS
        builder.setReadPhoneStateEnabled(true);
        builder.setReceiveSMS(true);
    }


    private void getSmsPermission() {
        Log.d(TAG, "getSmsPermission: getting sms permissions");
        String[] permissions = {Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                mREAD_SMS) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    mRECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                //onActivityResult();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        SMS_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    SMS_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if (result.getError() != null) {
                Toast.makeText(this, result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            } else if (result.wasCancelled()) {
                // Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            } else {
                if (result.getAccessToken() != null) {
                    loadingdialog = new ProgressDialog(LoginSignUp.this);
                    loadingdialog.setMessage("Please wait");
                    loadingdialog.setCancelable(true);
                    loadingdialog.show();

                    //Get User phone and check exists on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            mServices.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<Customer>() {
                                        @Override
                                        public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {
                                            if (response.isSuccessful()) {
                                                Customer customerResponse = response.body();
                                                if (customerResponse != null) {
                                                    String phone = null;
                                                    for (int i = 0; i < customerResponse.getGenericAttributes().size(); i++) {
                                                        if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("phone")) {
                                                            phone = customerResponse.getGenericAttributes().get(i).getValue();
                                                        }
                                                    }
                                                    if (Objects.requireNonNull(phone).contains(account.getPhoneNumber().toString())) {
                                                        loadingdialog.dismiss();
                                                        //User Already registered
                                                        Toast.makeText(LoginSignUp.this, "Already registered on entered Phone !", Toast.LENGTH_SHORT).show();

                                                        //Open next activity
                                                        Intent intent = new Intent(LoginSignUp.this, SignIn.class);
                                                        // Add new Flag to start new Activity
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);

                                                        finish();
                                                    }
                                                    else {
                                                        loadingdialog.dismiss();
                                                        Toast.makeText(LoginSignUp.this, "Error : LoginSignUp : Null Phone Number", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                                else {
                                                    //User not exists
                                                    loadingdialog.dismiss();
                                                    StartRegister(account.getPhoneNumber().toString());
                                                }

                                            }

                                            if (!response.isSuccessful()){
                                                loadingdialog.dismiss();
                                                Toast.makeText(LoginSignUp.this, "Response Failed !"+response.errorBody(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Customer> call, Throwable t) {
                                            loadingdialog.dismiss();
                                            Toast.makeText(LoginSignUp.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.d("checkUserExists :", t.getMessage() + "\n");
                                        }
                                    });
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Toast.makeText(LoginSignUp.this, "Error Customer:"+accountKitError.getUserFacingMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginSignUp.this, "Error Developer:"+accountKitError.getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
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

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }
}
