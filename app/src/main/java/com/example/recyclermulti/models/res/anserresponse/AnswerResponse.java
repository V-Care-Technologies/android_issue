package com.example.recyclermulti.models.res.anserresponse;

import com.google.gson.annotations.SerializedName;

public class AnswerResponse{

	@SerializedName("data")
	private int data;

	@SerializedName("messages")
	private String messages;

	@SerializedName("error")
	private boolean error;

	@SerializedName("status")
	private int status;

	public int getData(){
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