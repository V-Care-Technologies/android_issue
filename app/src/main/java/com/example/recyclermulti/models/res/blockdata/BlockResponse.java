package com.example.recyclermulti.models.res.blockdata;

import java.util.List;

import com.example.recyclermulti.models.res.configdata.BlockItem;
import com.google.gson.annotations.SerializedName;

public class BlockResponse{

	@SerializedName("data")
	private List<BlockItem> data;

	@SerializedName("messages")
	private String messages;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private int status;

	public List<BlockItem> getData(){
		return data;
	}

	public String getMessages(){
		return messages;
	}

	public boolean isError(){
		return error;
	}

	public int getStatus(){
		return status;
	}
}