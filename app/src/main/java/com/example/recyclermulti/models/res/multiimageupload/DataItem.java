package com.example.recyclermulti.models.res.multiimageupload;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("images")
	private String images;

	public String getImages(){
		return images;
	}
}