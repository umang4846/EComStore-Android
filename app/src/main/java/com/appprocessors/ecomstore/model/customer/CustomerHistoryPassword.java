package com.appprocessors.ecomstore.model.customer;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerHistoryPassword {

    @SerializedName("createdOnUtc")
    private String CreatedOnUtc;

    @SerializedName("passwordSalt")
    private String PasswordSalt;

    @SerializedName("passwordFormatId")
    private int passwordFormatId;

    @SerializedName("password")
    private String password;

    @SerializedName("customerId")
    private String customerId;

    @SerializedName("genericAttributes")
    private List<String> genericAttributes;

    @SerializedName("_id")
    private String _id;




    public String get_id() {
        return _id;
    }


}
