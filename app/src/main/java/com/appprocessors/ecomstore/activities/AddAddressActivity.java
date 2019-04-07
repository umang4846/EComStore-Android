package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.appprocessors.ecomstore.R;
import com.appprocessors.ecomstore.model.SubDistrict;
import com.appprocessors.ecomstore.model.customer.Addresses;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.retrofit.RetrofitClient;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.CommonOptionMenu;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends CommonOptionMenu {


    String TAG = "AddAddressActivity";
    static int subDistrictFinalposition;

    //List of SubDistrict
    List<SubDistrict> subDistrictList;

    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //Buterknife Injections


    // User Session Manager Class
    UserSessionManager session;

    Addresses editAddress;

    boolean isEditAddress = false;
    @BindView(R.id.input_fname)
    TextInputEditText inputFname;
    @BindView(R.id.input_layout_fname)
    TextInputLayout inputLayoutFname;
    @BindView(R.id.input_lname)
    TextInputEditText inputLname;
    @BindView(R.id.input_layout_lname)
    TextInputLayout inputLayoutLname;
    @BindView(R.id.input_address_home_no)
    TextInputEditText inputAddressHomeNo;
    @BindView(R.id.input_layout_address_home_no)
    TextInputLayout inputLayoutAddressHomeNo;
    @BindView(R.id.input_address_locality_area_street)
    TextInputEditText inputAddressLocalityAreaStreet;
    @BindView(R.id.input_layout_address_locality_area_street)
    TextInputLayout inputLayoutAddressLocalityAreaStreet;
    @BindView(R.id.input_address_sub_district)
    TextInputEditText inputAddressSubDistrict;
    @BindView(R.id.input_layout_address_sub_district)
    TextInputLayout inputLayoutAddressSubDistrict;
    @BindView(R.id.input_address_city_town)
    TextInputEditText inputAddressCityTown;
    @BindView(R.id.input_layout_address_city_town)
    TextInputLayout inputLayoutAddressCityTown;
    @BindView(R.id.input_address_mobile)
    TextInputEditText inputAddressMobile;
    @BindView(R.id.input_layout_address_mobile)
    TextInputLayout inputLayoutAddressMobile;
    @BindView(R.id.input_address_email)
    TextInputEditText inputAddressEmail;
    @BindView(R.id.input_layout_address_email)
    TextInputLayout inputLayoutAddressEmail;
    @BindView(R.id.btn_address_save_continue)
    MaterialButton btnAddressSaveContinue;
    @BindView(R.id.SV_Register_child)
    ScrollView SVRegisterChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        setTitle("Add new Address");
        ButterKnife.bind(this);


        //Set Back Button to Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        mService = Common.getAPI();
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //Disable some Edittexts
        inputLayoutAddressCityTown.setEnabled(false);
        inputAddressCityTown.setEnabled(false);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("editAddress")) {
                editAddress = getIntent().getExtras().getParcelable("editAddress");
                if (editAddress != null) {
                    isEditAddress = true;
                    setAddressDataToEdit(editAddress);
                    setTitle("Edit Address");
                }
            }
        }

        //Pin Code Set on click listner and open Dialog
        loadAllPincodes();
        //Load All Sub Districts
        inputAddressSubDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAllSubDistricts();
            }
        });
        //Load all City or Twon by SubDistricts
        inputAddressCityTown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputAddressCityTown.isEnabled()) {
                    Toast.makeText(AddAddressActivity.this, "Please Select Sub-District First.", Toast.LENGTH_SHORT).show();
                } else if (inputAddressCityTown.isEnabled()) {
                    loadTownBySubDistrict();
                }

            }

        });

        btnAddressSaveContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void setAddressDataToEdit(Addresses editAddress) {

        if (editAddress != null) {
            String[] output = editAddress.getCity().split("-");
            if (output.length == 2 && editAddress.getZipPostalCode() != null) {
                inputAddressSubDistrict.setText(output[0]);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(output[1]).append(" ").append("(").append(editAddress.getZipPostalCode()).append(")");
                inputAddressCityTown.setText(stringBuilder.toString());
            } else {
                inputAddressSubDistrict.setText(editAddress.getCity());
                inputAddressCityTown.setText(editAddress.getCity());
            }

            inputAddressHomeNo.setText(editAddress.getAddress1());
            inputAddressLocalityAreaStreet.setText(editAddress.getAddress2());
            inputFname.setText(editAddress.getFirstName());
            inputLname.setText(editAddress.getLastName());
            inputAddressMobile.setText(editAddress.getPhoneNumber());
            inputAddressEmail.setText(editAddress.getEmail().isEmpty()?"":editAddress.getEmail());

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void loadAllPincodes() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();

        compositeDisposable.add(mService.getSubDistricts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SubDistrict>>() {
                    @Override
                    public void accept(List<SubDistrict> subDistricts) throws Exception {
                        Log.d(TAG, "accept: GSON to JSON Converted ::" + new Gson().toJson(subDistricts));
                        if (subDistrictList != null) {
                            subDistrictList = null;
                        }
                        subDistrictList = subDistricts;
                        dialog.dismiss();
                    }
                }));
    }

    private void loadAllSubDistricts() {
        //Disable some Edittexts
        inputLayoutAddressCityTown.setEnabled(false);
        inputAddressCityTown.setEnabled(false);
        inputAddressCityTown.setText("");

        Gson gson = new Gson();
        SubDistrict[] subDistricts = gson.fromJson(new Gson().toJson(subDistrictList), SubDistrict[].class);
        Log.d(TAG, "onClick: " + Arrays.asList(subDistricts));

        final String sub[] = new String[Arrays.asList(subDistricts).size()];
        for (int i = 0; i < Arrays.asList(subDistricts).size(); i++) {
            sub[i] = Arrays.asList(subDistricts).get(i).getSubDistrict();
        }
        Log.d(TAG, "onClick: SubDist ::" + Arrays.asList(sub));

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddAddressActivity.this);
        mBuilder.setTitle("Select Sub District or Taluka");
        mBuilder.setSingleChoiceItems(sub, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                inputAddressSubDistrict.setText(Arrays.asList(sub).get(which));
                subDistrictFinalposition = which;
                inputLayoutAddressCityTown.setEnabled(true);
                inputAddressCityTown.setEnabled(true);

                Log.d(TAG, "Selected Sub District :" + Arrays.asList(sub).get(which));

            }
        });

        Dialog dialog = mBuilder.create();
        dialog.show();
    }

    private void loadTownBySubDistrict() {
        if (!inputLayoutAddressCityTown.isEnabled() || !inputAddressCityTown.isEnabled()) {
            Toast.makeText(AddAddressActivity.this, "Please select Sub District", Toast.LENGTH_SHORT).show();
            inputLayoutAddressCityTown.setError(getString(R.string.err_msg_address_sub_district));
            requestFocus(inputAddressCityTown);
        } else {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddAddressActivity.this);
            mBuilder.setTitle("Select City / Town");

            try {

                String Json = String.valueOf(new JSONArray(new Gson().toJson(subDistrictList)).get(subDistrictFinalposition));
                Gson gson = new Gson();
                SubDistrict subDistrict = gson.fromJson(Json, SubDistrict.class);
                final String arr[] = new String[subDistrict.getVillages().size()];
                for (int i = 0; i < subDistrict.getVillages().size(); i++) {
                    arr[i] = subDistrict.getVillages().get(i).villageName;
                }

                AlertDialog.Builder vBuilder = new AlertDialog.Builder(AddAddressActivity.this);
                vBuilder.setTitle("Select City / Town");
                vBuilder.setSingleChoiceItems(arr, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        inputAddressCityTown.setText(Arrays.asList(arr).get(which));
                        inputAddressSubDistrict.setError(null);
                        inputAddressSubDistrict.clearFocus();
                        inputAddressCityTown.setError(null);
                        inputAddressCityTown.clearFocus();
                        Log.d(TAG, "Selected City / Town :" + Arrays.asList(arr).get(which));

                    }
                });

                Dialog dialog = vBuilder.create();
                dialog.show();

            } catch (JSONException e) {
                Log.d(TAG, "onClick: JSON EXCEPTION " + e.getMessage());
                e.printStackTrace();
            }

        }


    }


    private void validateData() {

        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validateHomenoOrBuildingName()) {
            return;
        }
        if (!validateStreetOrArea()) {
            return;
        }
        if (!validateSubDistrict()) {
            return;
        }
        if (!validateCityOrTown()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validateEmailIfEntered()) {
            return;
        }
        if (isEditAddress)
            submiEditedAddress();
        else
            submitAddress();
    }

    private void submiEditedAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Updating Address");
        dialog.setCancelable(false);
        dialog.show();

        Addresses address = new Addresses();
        address.set_id(editAddress.get_id());
        String city = inputAddressSubDistrict.getText().toString() + " - " + inputAddressCityTown.getText().toString().replaceAll(inputAddressCityTown.getText().toString().substring(inputAddressCityTown.getText().toString().indexOf("(") + 1, inputAddressCityTown.getText().toString().indexOf(")")), "");
        address.setCity(city.replace("()", ""));
        address.setAddress1(inputAddressHomeNo.getText().toString());
        address.setAddress2(inputAddressLocalityAreaStreet.getText().toString());
        address.setZipPostalCode(inputAddressCityTown.getText().toString().substring(inputAddressCityTown.getText().toString().indexOf("(") + 1, inputAddressCityTown.getText().toString().indexOf(")")));
        address.setFirstName(inputFname.getText().toString());
        address.setLastName(inputLname.getText().toString());
        address.setPhoneNumber(inputAddressMobile.getText().toString());
        address.setEmail(inputAddressEmail.getText().toString());
        address.setCreatedOnUtc("");
        address.setCountryId("");
        address.setStateProvinceId("");
        String phone = session.getUserDetails().get(UserSessionManager.KEY_PHONE);

        Call<Addresses> call = RetrofitClient.getRestClient().addNewAddress(phone.replace("+", ""), address);
        call.enqueue(new Callback<Addresses>() {
            @Override
            public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d(TAG, "onResponse: Address Added Successfully !");
                   /* Intent myAddressIntent = new Intent(AddAddressActivity.this, MyAddressActivity.class);
                    myAddressIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAddressIntent);*/
                    Intent intent = new Intent();
                    intent.putExtra(Common.addUpdatedAddress, response.body());
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    dialog.dismiss();
                    Toast.makeText(AddAddressActivity.this, "Failed to Save Address !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Failed to save address " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Addresses> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:: " + t.getMessage());
            }
        });
    }

    private void submitAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving Address");
        dialog.setCancelable(false);
        dialog.show();
        Addresses address = new Addresses();
        address.set_id("");
        String city = inputAddressSubDistrict.getText().toString() + " - " + inputAddressCityTown.getText().toString().replaceAll(inputAddressCityTown.getText().toString().substring(inputAddressCityTown.getText().toString().indexOf("(") + 1, inputAddressCityTown.getText().toString().indexOf(")")), "");
        address.setCity(city.replace("()", ""));
        address.setAddress1(inputAddressHomeNo.getText().toString());
        address.setAddress2(inputAddressLocalityAreaStreet.getText().toString());
        address.setZipPostalCode(inputAddressCityTown.getText().toString().substring(inputAddressCityTown.getText().toString().indexOf("(") + 1, inputAddressCityTown.getText().toString().indexOf(")")));
        address.setFirstName(inputFname.getText().toString());
        address.setLastName(inputLname.getText().toString());
        address.setPhoneNumber(inputAddressMobile.getText().toString());
        address.setEmail(inputAddressEmail.getText().toString());
        address.setCreatedOnUtc("");
        address.setCountryId("");
        address.setStateProvinceId("");
        String phone = session.getUserDetails().get(UserSessionManager.KEY_PHONE);

        Call<Addresses> call = RetrofitClient.getRestClient().addNewAddress(phone.replace("+", ""), address);
        call.enqueue(new Callback<Addresses>() {
            @Override
            public void onResponse(Call<Addresses> call, Response<Addresses> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d(TAG, "onResponse: Address Added Successfully !");
                   /* Intent myAddressIntent = new Intent(AddAddressActivity.this, MyAddressActivity.class);
                    myAddressIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAddressIntent);*/
                    Intent intent = new Intent();
                    intent.putExtra(Common.addNewAddress, response.body());
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    dialog.dismiss();
                    Toast.makeText(AddAddressActivity.this, "Failed to Save Address !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Failed to save address " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Addresses> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:: " + t.getMessage());
            }
        });
    }


    private boolean validatePhone() {
        if (inputAddressMobile.getText().toString().trim().isEmpty()) {
            inputLayoutAddressMobile.setError(getString(R.string.err_msg_address_mobile_number));
            requestFocus(inputAddressMobile);
            return false;
        } else if (inputAddressMobile.getText().length() < 10) {
            inputLayoutAddressMobile.setError(getString(R.string.err_msg_address_valid_mobile_number));
            requestFocus(inputAddressMobile);
            return false;
        } else if (!(Patterns.PHONE.matcher(inputAddressMobile.getText().toString()).matches())) {
            inputLayoutAddressMobile.setError(getString(R.string.err_msg_address_valid_mobile_number));
            requestFocus(inputAddressMobile);
            return false;
        } else {
            inputLayoutAddressMobile.setError(null);
        }

        return true;
    }

    private boolean validateEmailIfEntered() {
        String email = inputAddressEmail.getText().toString().trim();

        if (!email.isEmpty() &&  !isValidEmail(email)) {
            inputLayoutAddressEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputAddressEmail);
            return false;
        } else {
            inputLayoutAddressEmail.setError(null);
        }

        return true;
    }

    private boolean validateFirstName() {

        if (inputFname.getText().toString().trim().isEmpty()) {
            inputLayoutFname.setError(getString(R.string.err_msg_first_name));
            requestFocus(inputFname);
            return false;
        } else {
            inputLayoutFname.setError(null);
        }

        return true;

    }

    private boolean validateLastName() {

        if (inputLname.getText().toString().trim().isEmpty()) {
            inputLayoutLname.setError(getString(R.string.err_msg_last_name));
            requestFocus(inputLname);
            return false;
        } else {
            inputLayoutLname.setError(null);
        }

        return true;

    }

    private boolean validateStreetOrArea() {

        if (inputAddressLocalityAreaStreet.getText().toString().trim().isEmpty()) {
            inputLayoutAddressLocalityAreaStreet.setError(getString(R.string.err_msg_address_area));
            requestFocus(inputAddressLocalityAreaStreet);
            return false;
        } else {
            inputLayoutAddressLocalityAreaStreet.setError(null);
        }

        return true;

    }

    private boolean validateHomenoOrBuildingName() {
        if (inputAddressHomeNo.getText().toString().trim().isEmpty()) {
            inputLayoutAddressHomeNo.setError(getString(R.string.err_msg_address_home_no));
            requestFocus(inputAddressHomeNo);
            return false;
        } else {
            inputLayoutAddressHomeNo.setError(null);
        }

        return true;
    }

    private boolean validateCityOrTown() {
        if (inputAddressCityTown.getText().toString().trim().isEmpty()) {
            inputLayoutAddressCityTown.setError(getString(R.string.err_msg_address_city_or_town));
            requestFocus(inputAddressCityTown);
            return false;
        } else {
            inputLayoutAddressCityTown.setError(null);
        }

        return true;
    }

    //Verify Sub District is not empty
    private boolean validateSubDistrict() {
        if (inputAddressSubDistrict.getText().toString().trim().isEmpty()) {
            inputLayoutAddressSubDistrict.setError(getString(R.string.err_msg_address_sub_district));
            requestFocus(inputAddressSubDistrict);
            return false;
        } else {
            inputLayoutAddressSubDistrict.setError(null);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }


}