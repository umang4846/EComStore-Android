package com.appprocessors.ecomstore.database.modeldb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "cart")
public class Cart
{
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "productId")
    public String productId;

    @ColumnInfo(name = "brand")
    public String brand;

    @ColumnInfo(name = "brandCertifiedSeller")
    public boolean brandCertifiedSeller;

    @ColumnInfo(name = "capacity")
    public String capacity;

    @ColumnInfo(name = "estoreAssured")
    public boolean estoreAssured;

    @ColumnInfo(name = "genuineProduct")
    public boolean genuineProduct;

    @ColumnInfo(name = "idealFor")
    public String idealFor;

    @ColumnInfo(name = "imageMain")
    public String imageMain;

    @ColumnInfo(name = "menuid")
    public String menuid;

    @ColumnInfo(name = "mrp")
    public String mrp;

    @ColumnInfo(name = "price")
    public String price;

    @ColumnInfo(name = "productCode")
    public String productCode;

    @ColumnInfo(name = "productName")
    public String productName;

    @ColumnInfo(name = "shippingFee")
    public String shippingFee;

    @ColumnInfo(name = "soldBy")
    public String soldBy;

    @ColumnInfo(name = "type")
    public String type;


}
