package com.example.recyclermulti.models.res.singleupload;

import com.google.gson.annotations.SerializedName;

public class SingleUploadResponse{

	@SerializedName("data")
	private String data;

	@SerializedName("messages")
	private String messages;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private int status;

	public String getData(){
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