package com.example.recyclermulti.models.res.configdata;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ConfigResponse {

    @SerializedName("district")
    private List<DistrictItem> district;

    @SerializedName("block")
    private List<BlockItem> block;

    @SerializedName("panchayat")
    private List<PanchayatItem> panchayat;

    @SerializedName("categories")
    private List<CategoriesItem> categories;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("signature_path")
    private String signature_path;
    @SerializedName("image_path")
    private String image_path;
    @SerializedName("video_path")
    private String video_path;

    public String getSignature_path() {
        return signature_path;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getVideo_path() {
        return video_path;
    }

    @SerializedName("status")
    private int status;

    @SerializedName("states")
    private List<StatesItem> states;

    public List<DistrictItem> getDistrict() {
        return district;
    }

    public List<BlockItem> getBlock() {
        return block;
    }

    public List<PanchayatItem> getPanchayat() {
        return panchayat;
    }

    public List<CategoriesItem> getCategories() {
        return categories;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public List<StatesItem> getStates() {
        return states;
    }
}