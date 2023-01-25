package com.example.recyclermulti.models.res.multiimageupload;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MultiUploadResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("messages")
	private String messages;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private int status;

	public List<DataItem> getData(){
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