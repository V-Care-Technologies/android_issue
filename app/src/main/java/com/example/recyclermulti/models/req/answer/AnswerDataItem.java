package com.example.recyclermulti.models.req.answer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AnswerDataItem{

	@SerializedName("questions_id")
	private String questionsId;
	@SerializedName("answer")
	private List<AnswerItem> answer;

	public AnswerDataItem(String questionsId, List<AnswerItem> answer) {
		this.questionsId = questionsId;
		this.answer = answer;
	}

	public AnswerDataItem() {
	}

	public void setAnswer(List<AnswerItem> answer) {
		this.answer = answer;
	}

	public void setQuestionsId(String questionsId) {
		this.questionsId = questionsId;
	}

	public List<AnswerItem> getAnswer(){
		return answer;
	}

	public String getQuestionsId(){
		return questionsId;
	}
}