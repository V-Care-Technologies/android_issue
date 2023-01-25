package com.example.recyclermulti.models.res

import android.graphics.Bitmap
import com.example.recyclermulti.models.MultiEtModel.MultiModel
import com.example.recyclermulti.models.req.answer.AnswerDataItem
import com.google.gson.annotations.SerializedName

data class QuestionsResponse(

	@field:SerializedName("data")
	val data: List<QuestionsDataItem?>? = null,

	@field:SerializedName("no_steps")
	val noSteps: String? = null,

	@field:SerializedName("no_ques")
	val noQues: String? = null,

	@field:SerializedName("messages")
	val messages: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null




)

data class QuestionsDataItem(

	var passData:String,
	var passData2:String,
	var passData3:String,
	var passId:String,
	var position: String,
	var questionName:String,
	var signatureData:MutableList<Bitmap>,
	var optionsList:MutableList<String>,
	var answerDataItem: AnswerDataItem,
	var selected_option_id:String,
	var multiModel: MultiModel,

	@field:SerializedName("ques_option_id")
	val quesOptionId: String? = null,

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("question_hn")
	val questionHn: String? = null,

	@field:SerializedName("dependent_ques_id")
	val dependentQuesId: String? = null,

	@field:SerializedName("categories_id")
	val categoriesId: String? = null,

	@field:SerializedName("display_order")
	val displayOrder: String? = null,

	@field:SerializedName("options")
	val options: MutableList<OptionsItem>? = null,

	@field:SerializedName("active")
	val active: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("is_compulsory")
	val isCompulsory: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("answer")
	val answer: String? = null
)

data class OptionsItem(
	var passData:String,
	var passId:String,
	var position: String,
	var isChecked:Boolean,


	@field:SerializedName("questions_id")
	val questionsId: String? = null,

	@field:SerializedName("is_dependent")
	val isDependent: String? = null,

	@field:SerializedName("options")
	val options: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("options_hn")
	val optionsHn: String? = null
)
