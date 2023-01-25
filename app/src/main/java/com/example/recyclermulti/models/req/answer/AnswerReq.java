package com.example.recyclermulti.models.req.answer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AnswerReq{



	@SerializedName("id")
	private String id;


	@SerializedName("user_id")
	private String userId;


	@SerializedName("categories_id")
	private String categoriesId;


	@SerializedName("district_id")
	private String districtId;
	@SerializedName("block_id")
	private String blockId;


	@SerializedName("panchayat_id")
	private String panchayatId;




	@SerializedName("stage")
	private String stage;
	@SerializedName("final_submit")
	private String finalSubmit;

	@SerializedName("latitude")
	private String latitude;


	@SerializedName("longitude")
	private String longitude;


	@SerializedName("multi_images")
	private String multiImages;


	@SerializedName("multi_videos")
	private String multiVideos;

	@SerializedName("answer_data")
	private List<AnswerDataItem> answerData;

	public void setAnswerData(List<AnswerDataItem> answerData) {
		this.answerData = answerData;
	}

	public AnswerReq(String id, String userId, String categoriesId, String districtId, String blockId, String panchayatId, String stage, String finalSubmit, String latitude, String longitude, String multiImages, String multiVideos, List<AnswerDataItem> answerData) {
		this.id = id;
		this.userId = userId;
		this.categoriesId = categoriesId;
		this.districtId = districtId;
		this.blockId = blockId;
		this.panchayatId = panchayatId;
		this.stage = stage;
		this.finalSubmit = finalSubmit;
		this.latitude = latitude;
		this.longitude = longitude;
		this.multiImages = multiImages;
		this.multiVideos = multiVideos;
		this.answerData = answerData;
	}

	public String getPanchayatId() {
		return panchayatId;
	}

	public String getMultiImages(){
		return multiImages;
	}

	public String getStage(){
		return stage;
	}

	public String getUserId(){
		return userId;
	}

	public String getCategoriesId(){
		return categoriesId;
	}

	public String getLatitude(){
		return latitude;
	}

	public String getId(){
		return id;
	}

	public String getDistrictId(){
		return districtId;
	}

	public String getMultiVideos(){
		return multiVideos;
	}

	public String getFinalSubmit(){
		return finalSubmit;
	}

	public List<AnswerDataItem> getAnswerData(){
		return answerData;
	}

	public String getBlockId(){
		return blockId;
	}

	public String getLongitude(){
		return longitude;
	}
}