package com.example.recyclermulti.models.req.answer;

import com.google.gson.annotations.SerializedName;

public class AnswerItem{

	@SerializedName("questions_id")
	private String questionsId;
	@SerializedName("questions")
	private String questions;


	public AnswerItem(String questionsId, String questions, String optionId, String answer) {
		this.questionsId = questionsId;
		this.questions = questions;
		this.optionId = optionId;
		this.answer = answer;
	}

	@SerializedName("option_id")
	private String optionId;

	@SerializedName("answer")
	private String answer;



	public String getAnswer(){
		return answer;
	}

	public String getQuestionsId(){
		return questionsId;
	}

	public String getQuestions(){
		return questions;
	}

	public String getOptionId(){
		return optionId;
	}
}