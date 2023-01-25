package com.example.recyclermulti.models.res.configdata;

import com.google.gson.annotations.SerializedName;

public class PanchayatItem{

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