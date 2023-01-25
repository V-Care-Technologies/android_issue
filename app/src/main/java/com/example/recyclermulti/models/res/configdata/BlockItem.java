package com.example.recyclermulti.models.res.configdata;

import com.google.gson.annotations.SerializedName;

public class BlockItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("district_id")
	private String districtId;

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getDistrictId(){
		return districtId;
	}
}