package com.example.recyclermulti.Domain.retrofit;

import com.example.recyclermulti.models.req.Categoryreq;
import com.example.recyclermulti.models.res.QuestionsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("getallquestions")
    Call <QuestionsResponse> getAllQuestions(@Body Categoryreq categoryreq);



}
