package com.example.recyclermulti.models.res.configdata;

import com.google.gson.annotations.SerializedName;

public class StatesItem{

	@SerializedName("code")
	private Object code;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("country_id")
	private String countryId;

	public Object getCode(){
		return code;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getCountryId(){
		return countryId;
	}
}