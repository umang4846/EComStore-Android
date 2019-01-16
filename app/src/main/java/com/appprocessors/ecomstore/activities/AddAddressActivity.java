package com.appprocessors.ecomstore.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.appprocessors.ecomstore.model.Address;
import com.appprocessors.ecomstore.model.SubDistrict;
import com.appprocessors.ecomstore.retrofit.IEStoreAPI;
import com.appprocessors.ecomstore.retrofit.RetrofitClient;
import com.appprocessors.ecomstore.utils.Common;
import com.appprocessors.ecomstore.utils.UserSessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
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

public class AddAddressActivity extends AppCompatActivity {


    String TAG = "AddAddressActivity";
    static int subDistrictFinalposition;

    //List of SubDistrict
    List<SubDistrict> subDistrictList;

    IEStoreAPI mService;
    //Rx java
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    //Buterknife Injections
    @BindView(R.id.input_address_sub_district)
    TextInputEditText inputAddressSubDistrict;
    @BindView(R.id.input_layout_address_sub_district)
    TextInputLayout inputLayoutAddressSubDistrict;
    @BindView(R.id.input_address_city_town)
    TextInputEditText inputAddressCityTown;
    @BindView(R.id.input_layout_address_city_town)
    TextInputLayout inputLayoutAddressCityTown;
    @BindView(R.id.input_address_home_no)
    TextInputEditText inputAddressHomeNo;
    @BindView(R.id.input_layout_address_home_no)
    TextInputLayout inputLayoutAddressHomeNo;
    @BindView(R.id.input_address_locality_area_street)
    TextInputEditText inputAddressLocalityAreaStreet;
    @BindView(R.id.input_layout_address_locality_area_street)
    TextInputLayout inputLayoutAddressLocalityAreaStreet;
    @BindView(R.id.input_address_person_name)
    TextInputEditText inputAddressPersonName;
    @BindView(R.id.input_layout_address_person_name)
    TextInputLayout inputLayoutAddressPersonName;
    @BindView(R.id.input_address_mobile)
    TextInputEditText inputAddressMobile;
    @BindView(R.id.input_layout_address_mobile)
    TextInputLayout inputLayoutAddressMobile;
    @BindView(R.id.input_address_alter_mobile)
    TextInputEditText inputAddressAlterMobile;
    @BindView(R.id.input_layout_address_alter_mobile)
    TextInputLayout inputLayoutAddressAlterMobile;
    @BindView(R.id.tv_address_type)
    TextView tvAddressType;
    @BindView(R.id.rb_address_type_home)
    RadioButton rbAddressTypeHome;
    @BindView(R.id.rb_address_type_work)
    RadioButton rbAddressTypeWork;
    @BindView(R.id.rg_address_type)
    RadioGroup rgAddressType;
    @BindView(R.id.switch_default_address)
    MaterialCheckBox switchDefaultAddress;
    @BindView(R.id.btn_address_save_continue)
    MaterialButton btnAddressSaveContinue;


    // User Session Manager Class
    UserSessionManager session;

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
                Address editAddress = getIntent().getExtras().getParcelable("editAddress");
                if (editAddress != null) {
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

    private void setAddressDataToEdit(Address editAddress) {

        if (editAddress != null) {
            inputAddressSubDistrict.setText(editAddress.getSubDistrict());
            inputAddressCityTown.setText(editAddress.getCityTown());
            inputAddressHomeNo.setText(editAddress.getHomeNoBuildingName());
            inputAddressLocalityAreaStreet.setText(editAddress.getLocalityAreaStreet());
            inputAddressPersonName.setText(editAddress.getFullName());
            inputAddressMobile.setText(editAddress.getMobileNo());
            inputAddressAlterMobile.setText(editAddress.getAlternateMobileNumber());
            if (editAddress.getIsDefaultAddress()) {
                switchDefaultAddress.setChecked(true);
            } else {
                switchDefaultAddress.setChecked(false);
            }
            if (editAddress.getAddressType().equalsIgnoreCase("home")) {
                rgAddressType.check(R.id.rb_address_type_home);
            } else {
                rgAddressType.check(R.id.rb_address_type_work);
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
            sub[i] = Arrays.asList(subDistricts).get(i).subDistrict;
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
                final String arr[] = new String[subDistrict.villages.size()];
                for (int i = 0; i < subDistrict.villages.size(); i++) {
                    arr[i] = subDistrict.villages.get(i).villageName;
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

        if (!validateSubDistrict()) {
            return;
        }
        if (!validateCityOrTown()) {
            return;
        }
        if (!validateHomenoOrBuildingName()) {
            return;
        }
        if (!validateStreetOrArea()) {
            return;
        }
        if (!validateFullName()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validateAlterPhone()) {
            return;
        }
        if (!validateAddressType()) {
            return;
        }
        submitAddress();
    }

    private void submitAddress() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Saving Address");
        dialog.setCancelable(false);
        dialog.show();
        List<Address> addressList = new ArrayList<>();
        Address address = new Address();
        address.setSubDistrict(inputAddressSubDistrict.getText().toString());
        address.setCityTown(inputAddressCityTown.getText().toString());
        address.setHomeNoBuildingName(inputAddressHomeNo.getText().toString());
        address.setLocalityAreaStreet(inputAddressLocalityAreaStreet.getText().toString());
        address.setFullName(inputAddressPersonName.getText().toString());
        address.setMobileNo(inputAddressMobile.getText().toString());
        address.setAlternateMobileNumber(inputAddressAlterMobile.getText().toString());
        address.setAddressType(((RadioButton) findViewById(rgAddressType.getCheckedRadioButtonId())).getText().toString());
        address.setIsDefaultAddress(switchDefaultAddress.isChecked());
        addressList.add(address);
        String phone = session.getUserDetails().get(UserSessionManager.KEY_PHONE);

        Call<List<Address>> call = RetrofitClient.getRestClient().addNewAddress(phone, addressList);
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Log.d(TAG, "onResponse: Address Added Successfully !");
                    Intent myAddressIntent = new Intent(AddAddressActivity.this, MyAddressActivity.class);
                    myAddressIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAddressIntent);
                    finish();
                } else {
                    Toast.makeText(AddAddressActivity.this, "Failed to Save Address !", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Failed to save address " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(AddAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure:: " + t.getMessage());
            }
        });
    }

    private boolean validateAddressType() {
        if (rgAddressType.getCheckedRadioButtonId() == -1) {
            rgAddressType.setFocusable(true);
            rgAddressType.setFocusableInTouchMode(true);
            rgAddressType.requestFocus();
            Toast.makeText(AddAddressActivity.this, "Please Select Address Type", Toast.LENGTH_SHORT).show();
            return false;    //This should be false to select address type of not selected
        } else {
            return true;
        }

    }

    private boolean validateAlterPhone() {
        if (inputAddressAlterMobile.getText().length() < 10) {
            inputLayoutAddressAlterMobile.setError(getString(R.string.err_msg_address_valid_mobile_number));
            requestFocus(inputAddressAlterMobile);
            return false;
        } else if (!(Patterns.PHONE.matcher(inputAddressAlterMobile.getText().toString()).matches())) {
            inputLayoutAddressAlterMobile.setError(getString(R.string.err_msg_address_valid_mobile_number));
            requestFocus(inputAddressAlterMobile);
            return false;
        } else {
            inputLayoutAddressAlterMobile.setErrorEnabled(false);
        }

        return true;

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
            inputLayoutAddressMobile.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateFullName() {

        if (inputAddressPersonName.getText().toString().trim().isEmpty()) {
            inputLayoutAddressPersonName.setError(getString(R.string.err_msg_address_full_name));
            requestFocus(inputAddressPersonName);
            return false;
        } else {
            inputLayoutAddressPersonName.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validateStreetOrArea() {

        if (inputAddressLocalityAreaStreet.getText().toString().trim().isEmpty()) {
            inputLayoutAddressLocalityAreaStreet.setError(getString(R.string.err_msg_address_area));
            requestFocus(inputAddressLocalityAreaStreet);
            return false;
        } else {
            inputLayoutAddressLocalityAreaStreet.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validateHomenoOrBuildingName() {
        if (inputAddressHomeNo.getText().toString().trim().isEmpty()) {
            inputLayoutAddressHomeNo.setError(getString(R.string.err_msg_address_home_no));
            requestFocus(inputAddressHomeNo);
            return false;
        } else {
            inputLayoutAddressHomeNo.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCityOrTown() {
        if (inputAddressCityTown.getText().toString().trim().isEmpty()) {
            inputLayoutAddressCityTown.setError(getString(R.string.err_msg_address_city_or_town));
            requestFocus(inputAddressCityTown);
            return false;
        } else {
            inputLayoutAddressCityTown.setErrorEnabled(false);
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
            inputLayoutAddressSubDistrict.setErrorEnabled(false);
            inputLayoutAddressSubDistrict.setError(null);
        }

        return true;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    protected void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }


}