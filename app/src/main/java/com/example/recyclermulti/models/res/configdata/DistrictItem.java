package com.example.recyclermulti.models.res.configdata;

import com.google.gson.annotations.SerializedName;

public class DistrictItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("state_id")
	private String stateId;

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getStateId(){
		return stateId;
	}
}