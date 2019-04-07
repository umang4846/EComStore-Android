package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.customer.BillingAddress;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.model.customer.CustomerRoles;
import com.appprocessors.ecomstore.model.customer.GenericAttributes;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.appprocessors.ecomstore.utils.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {


    public static final String TAG = SignUp.class.getSimpleName();
    String phone;
    IEStoreAPI mServices;
    String encPass;
    // User Session Manager Class
    UserSessionManager session;
    @BindView(R.id.input_first_name)
    TextInputEditText inputFirstName;
    @BindView(R.id.input_layout_first_name)
    TextInputLayout inputLayoutFirstName;
    @BindView(R.id.input_last_name)
    TextInputEditText inputLastName;
    @BindView(R.id.input_layout_last_name)
    TextInputLayout inputLayoutLastName;
    @BindView(R.id.input_email)
    AppCompatEditText inputEmail;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.input_password)
    AppCompatEditText inputPassword;
    @BindView(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.btn_signup)
    MaterialButton btnSignup;
    @BindView(R.id.layout3)
    LinearLayout layout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            phone = getIntent().getStringExtra("phone");
        }
        mServices = Common.getAPI();

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitData();
            }
        });


    }

    private void SubmitData() {

        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        if (validateGender()) {
            return;
        }
        inputFirstName.setError(null);
        inputLastName.setError(null);
        inputLayoutEmail.setError(null);
        inputLayoutPassword.setError(null);
        inputLayoutLastName.clearFocus();
        inputLayoutLastName.clearFocus();
        inputLayoutEmail.clearFocus();
        inputLayoutPassword.clearFocus();
        inputFirstName.clearFocus();
        inputLastName.clearFocus();
        inputEmail.clearFocus();
        inputPassword.clearFocus();
        StartRegistering(phone);

    }
    private boolean validateGender() {

        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(SignUp.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private boolean validateFirstName() {
        if (inputFirstName.getText().toString().trim().isEmpty()) {
            inputLayoutFirstName.setError(getString(R.string.err_msg_first_name));
            requestFocus(inputFirstName);
            return false;
        } else {
            inputLayoutFirstName.setError(null);
        }

        return true;
    }

    private boolean validateLastName() {
        if (inputLastName.getText().toString().trim().isEmpty()) {
            inputLayoutLastName.setError(getString(R.string.err_msg_last_name));
            requestFocus(inputLastName);
            return false;
        } else {
            inputLayoutLastName.setError(null);
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
            inputLayoutPassword.setError(null);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //Start Registering
    private void StartRegistering(final String phone) {

        final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(SignUp.this).build();
        alertDialog.show();
        alertDialog.setTitle("Trying to being part of you !");
        alertDialog.setMessage("Please wait");
        Customer customer = new Customer();
        customer.set_id("");
        customer.setActive(true);
        customer.setCustomerRoles(new ArrayList<>());
        customer.setDeleted(false);
        customer.setSystemAccount(false);
        customer.setEmail(inputEmail.getText().toString().trim());
        customer.setHasContributions(true);
        customer.setUrlReferrer("");
        String ipAddress = Utils.getIpAddress(this);
        customer.setLastIpAddress(ipAddress != null ? ipAddress : "");
        //Password Hasing & Salt Creation
        customer.setPasswordFormatId(1);
        customer.setPasswordSalt("");
        customer.setStoreId("");
        List<GenericAttributes> genericAttributes = new ArrayList<>();
        genericAttributes.add(new GenericAttributes("FirstName", inputFirstName.getText().toString().trim(), ""));
        genericAttributes.add(new GenericAttributes("LastName", inputLastName.getText().toString().trim(), ""));
        String gender = ((RadioButton) findViewById(rgGender.getCheckedRadioButtonId())).getText().toString().trim();
        genericAttributes.add(new GenericAttributes("Gender", gender.equalsIgnoreCase("Male") ? "M" : "F", ""));
        genericAttributes.add(new GenericAttributes("Phone", phone, ""));
        customer.setGenericAttributes(genericAttributes);
        customer.setPassword(inputPassword.getText().toString().trim());
        customer.setUsername(inputEmail.getText().toString().trim());
        customer.setCreatedOnUtc("");
        customer.setShoppingCartItems(new ArrayList<>());
        customer.setBillingAddress(null);
        customer.setShippingAddress(null);
        customer.setAddresses(new ArrayList<>());
        customer.setCustomerTags(new ArrayList<>());
        customer.setLastActivityDateUtc("");
        customer.setLastLoginDateUtc("");
        customer.setLastPurchaseDateUtc("");
        customer.setPasswordChangeDateUtc("");
        customer.setLastUpdateCartDateUtc("");
        customer.setLastUpdateWishListDateUtc("");
        StringBuilder PhoneNumberWithCC = new StringBuilder();
        if (phone.length() == 10 && !phone.contains("+91")) {
            PhoneNumberWithCC.append("+91").append(phone);
        } else {
            PhoneNumberWithCC.append(phone);
        }

        mServices.createNewCustomer(PhoneNumberWithCC.toString(), customer)
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Customer customerResponse = response.body();
                        if (customerResponse != null) {
                            //Save user Credentials in Shared Preference
                            String phone = null, firstName = null, lastName = null, gender = null;
                            for (int i = 0; i < customerResponse.getGenericAttributes().size(); i++) {
                                if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("FirstName")) {
                                    firstName = customerResponse.getGenericAttributes().get(i).getValue();
                                }
                                if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("LastName")) {
                                    lastName = customerResponse.getGenericAttributes().get(i).getValue();
                                }
                                if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("phone")) {
                                    phone = customerResponse.getGenericAttributes().get(i).getValue();
                                }
                                if (customerResponse.getGenericAttributes().get(i).getKey().equalsIgnoreCase("Gender")) {
                                    gender = customerResponse.getGenericAttributes().get(i).getValue();
                                }
                            }
                            if (phone != null && firstName != null && lastName != null && gender != null) {
                                //Save user Credentials in Shared Preference
                                session.createUserLoginSession(phone,
                                        firstName,
                                        lastName,
                                        customerResponse.getEmail(),
                                        customerResponse.getPassword(),
                                        gender
                                );
                                alertDialog.dismiss();
                                //Open next activity
                                Intent intent = new Intent(SignUp.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        alertDialog.dismiss();
                        Toast.makeText(SignUp.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: SignUp Activity ::" + t.getMessage());
                    }

                });


    }

}


