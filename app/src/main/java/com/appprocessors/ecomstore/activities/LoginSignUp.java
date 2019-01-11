package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.User;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;
import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignUp extends AppCompatActivity {

    public static int REQUEST_CODE = 1000;
    FButton signin, signup;
    IEStoreAPI mServices;
    UIManager uiManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        mServices = Common.getAPI();

        uiManager = new SkinManager(
                SkinManager.Skin.TRANSLUCENT, getResources().getColor(R.color.blue_adv), (R.drawable.intro_back), SkinManager.Tint.WHITE, 50.0);

        signin = findViewById(R.id.btn_signin);
        signup = findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartLoginPage();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignUp.this, SignIn.class));
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
                    final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(this).build();
                    alertDialog.show();
                    alertDialog.setMessage("Please Wait...");

                    //Get User phone and check exists on server
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            mServices.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, @NonNull Response<User> response) {
                                            User userResponse = response.body();

                                            if (userResponse != null) {

                                                Common.currentUser = response.body();
                                                alertDialog.dismiss();
                                                //User Already registered
                                                Toast.makeText(LoginSignUp.this, "Phone number is already exist !", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //User not exists
                                                alertDialog.dismiss();
                                                StartRegister(account.getPhoneNumber().toString());
                                            }

                                        }


                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {
                                            alertDialog.dismiss();
                                            Toast.makeText(LoginSignUp.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                                            Log.d("checkUserExists :", t.getMessage() + "\n");

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
        if (phone.length() == 10) {
            PhoneNumberWithCC = PhoneNumberWithCC.append("+91").append(phone);
        } else {
            PhoneNumberWithCC = PhoneNumberWithCC.append(phone);
        }
        String phoneNew = String.valueOf(PhoneNumberWithCC);
        Intent intent = new Intent(this, SignUp.class);
        intent.putExtra("phone", phoneNew);
        startActivity(intent);
    }
}
