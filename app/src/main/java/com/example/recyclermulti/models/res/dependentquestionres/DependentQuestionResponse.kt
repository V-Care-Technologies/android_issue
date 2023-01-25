package com.example.recyclermulti.models.res.dependentquestionres

import com.example.recyclermulti.models.res.QuestionsDataItem
import com.google.gson.annotations.SerializedName

data class DependentQuestionResponse(

	@field:SerializedName("data")
	val data: List<QuestionsDataItem?>? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class OptionsItem(

	@field:SerializedName("questions_id")
	val questionsId: String? = null,

	@field:SerializedName("is_dependent")
	val isDependent: String? = null,

	@field:SerializedName("options")
	val options: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("options_hn")
	val optionsHn: Any? = null
)

data class DataItem(

	@field:SerializedName("ques_option_id")
	val quesOptionId: String? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("question_hn")
	val questionHn: Any? = null,

	@field:SerializedName("dependent_ques_id")
	val dependentQuesId: String? = null,

	@field:SerializedName("categories_id")
	val categoriesId: String? = null,

	@field:SerializedName("display_order")
	val displayOrder: String? = null,

	@field:SerializedName("options")
	val options: List<OptionsItem?>? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("is_compulsory")
	val isCompulsory: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
