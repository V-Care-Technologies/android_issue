package com.example.recyclermulti.models.res.panchayatresponse;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("block_id")
	private String blockId;

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getBlockId(){
		return blockId;
	}
}