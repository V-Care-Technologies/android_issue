package com.example.recyclermulti.models.res.panchayatresponse;

import java.util.List;

import com.example.recyclermulti.models.res.configdata.PanchayatItem;
import com.google.gson.annotations.SerializedName;

public class PanchayatResponse{

	@SerializedName("data")
	private List<PanchayatItem> data;

	@SerializedName("messages")
	private String messages;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private int status;

	public List<PanchayatItem> getData(){
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