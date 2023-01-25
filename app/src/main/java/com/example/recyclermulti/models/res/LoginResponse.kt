package com.example.recyclermulti.models.res

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: List<LoginDataItem?>? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("group")
	val group: String? = null
)

data class LoginDataItem(

	@field:SerializedName("activation_code")
	val activationCode: Any? = null,

	@field:SerializedName("gst")
	val gst: String? = null,

	@field:SerializedName("device_type")
	val deviceType: String? = null,

	@field:SerializedName("isdeleted")
	val isdeleted: String? = null,

	@field:SerializedName("pancard")
	val pancard: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("remember_selector")
	val rememberSelector: Any? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("state_id")
	val stateId: String? = null,

	@field:SerializedName("landmark")
	val landmark: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("forgotten_password_code")
	val forgottenPasswordCode: Any? = null,

	@field:SerializedName("area")
	val area: String? = null,

	@field:SerializedName("pincode")
	val pincode: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("last_login")
	val lastLogin: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("ip_address")
	val ipAddress: String? = null,

	@field:SerializedName("forgotten_password_selector")
	val forgottenPasswordSelector: Any? = null,

	@field:SerializedName("block_id")
	val blockId: Any? = null,

	@field:SerializedName("forgotten_password_time")
	val forgottenPasswordTime: Any? = null,

	@field:SerializedName("created_on")
	val createdOn: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("device_token")
	val deviceToken: String? = null,

	@field:SerializedName("activation_selector")
	val activationSelector: Any? = null,

	@field:SerializedName("remember_code")
	val rememberCode: Any? = null,

	@field:SerializedName("district_id")
	val districtId: String? = null,

	@field:SerializedName("country_id")
	val countryId: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
