package com.example.recyclermulti.models.res

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CategoryResponse(

	@field:SerializedName("data")
	val data: List<CategoryItem?>? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
@Parcelize
data class CategoryItem(

	@field:SerializedName("disp_order")
	val dispOrder: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("name_hn")
	val nameHn: String? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable {
	companion object{
		fun empty()= CategoryItem(
			dispOrder = "",
			name = "",
			nameHn = "",
			active = "",
			id = "",
		)
	}


}
