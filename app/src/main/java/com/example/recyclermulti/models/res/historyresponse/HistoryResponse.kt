package com.example.recyclermulti.models.res.historyresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<HistoryItem?>? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
@Parcelize
data class HistoryItem(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("category_name")
	val categoryName: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("panchayat_id")
	val panchayatId: String? = null,

	@field:SerializedName("district_auth_by")
	val districtAuthBy: String? = null,

	@field:SerializedName("block_name")
	val blockName: String? = null,

	@field:SerializedName("district_name")
	val districtName: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("block_auth_by")
	val blockAuthBy: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("multi_videos")
	val multiVideos: String? = null,

	@field:SerializedName("final_submit")
	val finalSubmit: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("panchayat_name")
	val panchayatName: String? = null,

	@field:SerializedName("category_name_hn")
	val categoryNameHn: String? = null,

	@field:SerializedName("categories_id")
	val categoriesId: String? = null,

	@field:SerializedName("district_auth_at")
	val districtAuthAt: String? = null,

	@field:SerializedName("created_by")
	val createdBy: String? = null,

	@field:SerializedName("block_id")
	val blockId: String? = null,

	@field:SerializedName("multi_images")
	val multiImages: String? = null,

	@field:SerializedName("block_auth_at")
	val blockAuthAt: String? = null,

	@field:SerializedName("stage")
	val stage: String? = null,

	@field:SerializedName("final_submit_at")
	val finalSubmitAt: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: String? = null,

	@field:SerializedName("district_id")
	val districtId: String? = null
): Parcelable {
	companion object {
		fun empty() = HistoryItem(
			id = "",
			categoryName = "",
			categoryNameHn = "",
			categoriesId = "",
			blockId = "",
			panchayatId = "",
			districtId = "",
		)
	}
}
