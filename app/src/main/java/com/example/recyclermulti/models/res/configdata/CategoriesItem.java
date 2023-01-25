package com.example.recyclermulti.models.res.configdata;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem{

	@SerializedName("disp_order")
	private String dispOrder;

	@SerializedName("name")
	private String name;

	@SerializedName("name_hn")
	private String nameHn;

	@SerializedName("active")
	private String active;

	@SerializedName("id")
	private String id;

	public String getDispOrder(){
		return dispOrder;
	}

	public String getName(){
		return name;
	}

	public String getNameHn(){
		return nameHn;
	}

	public String getActive(){
		return active;
	}

	public String getId(){
		return id;
	}
}