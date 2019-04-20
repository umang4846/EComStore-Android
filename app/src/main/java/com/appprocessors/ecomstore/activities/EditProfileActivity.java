package com.appprocessors.ecomstore.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.customer.Customer;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

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
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.btn_save_profile)
    MaterialButton btnSaveProfile;

    // User Session Manager Class
    UserSessionManager session;

    IEStoreAPI mServices;

    ProgressDialog loadingOrdersdialog;

    Customer customerResponse;

    private static final String TAG = "EditProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mServices = Common.getAPI(this);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        btnSaveProfile.setEnabled(false);

        getCustomerDetails();

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               validateData();
            }
        });


    }

    private void validateData() {

        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (validateGender()) {
            return;
        }
        inputFirstName.setError(null);
        inputLastName.setError(null);
        inputLayoutEmail.setError(null);
        inputLayoutLastName.clearFocus();
        inputLayoutLastName.clearFocus();
        inputLayoutEmail.clearFocus();
        inputFirstName.clearFocus();
        inputLastName.clearFocus();
        inputEmail.clearFocus();
        submitData();

    }

    private void submitData() {

        String phone = session.getUserDetails().get(UserSessionManager.KEY_PHONE);

        if (phone!=null) {
            StringBuilder PhoneNumberWithCC = new StringBuilder();
            if (phone.length() == 10 && !phone.contains("+91")) {
                PhoneNumberWithCC.append("+91").append(phone);
            } else {
                PhoneNumberWithCC.append(phone);
            }

         /*   loadingdialog = new ProgressDialog(EditProfileActivity.this);
            loadingdialog.setMessage("Please wait");
            loadingdialog.setCancelable(true);
            loadingdialog.show();*/


        }
        else {
            Toast.makeText(this, "Phone Number is Null !!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateGender() {

        if (rgGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(EditProfileActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void getCustomerDetails() {

        loadingOrdersdialog = new ProgressDialog(this);
        loadingOrdersdialog.setMessage("Please wait");
        loadingOrdersdialog.setCancelable(false);
        loadingOrdersdialog.show();


        mServices.checkUserExists(Objects.requireNonNull(session.getUserDetails().get(UserSessionManager.KEY_PHONE)).replace("+", ""))
                .enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, @NonNull Response<Customer> response) {

                        if (response.isSuccessful()) {
                             customerResponse = response.body();

                            if (customerResponse != null) {

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
                                    Toast.makeText(EditProfileActivity.this, "Successfully Got Customer Details", Toast.LENGTH_SHORT).show();
                                    inputFirstName.setText(firstName);
                                    inputLastName.setText(lastName);
                                    inputEmail.setText(customerResponse.getEmail());
                                    if (gender.equals("M"))
                                        rgGender.check(R.id.rb_male);
                                    else
                                        rgGender.check(R.id.rb_female);
                                    loadingOrdersdialog.dismiss();
                                    btnSaveProfile.setEnabled(true);
                                } else {
                                    btnSaveProfile.setEnabled(false);
                                    loadingOrdersdialog.dismiss();
                                    Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // Add new Flag to start new Activity
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(EditProfileActivity.this, "Unable to get Customer Details", Toast.LENGTH_SHORT).show();

                                }


                            }
                        } else {
                            //User not exists
                            loadingOrdersdialog.dismiss();
                            btnSaveProfile.setEnabled(false);
                            Intent intent = new Intent(EditProfileActivity.this, LoginSignUp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // Add new Flag to start new Activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            session.logoutUser();
                            Toast.makeText(EditProfileActivity.this, "Phone number is not registered !", Toast.LENGTH_SHORT).show();

                        }

                    }


                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        loadingOrdersdialog.dismiss();
                        btnSaveProfile.setEnabled(false);
                        Toast.makeText(EditProfileActivity.this, "Something went wrong !" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("checkUserExists :", t.getMessage() + "\n");

                    }
                });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
