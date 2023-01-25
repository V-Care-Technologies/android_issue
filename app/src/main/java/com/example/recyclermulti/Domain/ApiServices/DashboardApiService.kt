package com.example.recyclermulti.Domain.ApiServices

import com.example.recyclermulti.models.req.Categoryreq
import com.example.recyclermulti.models.req.Dependentquesreq
import com.example.recyclermulti.models.req.GetAnswerReq
import com.example.recyclermulti.models.res.CategoryResponse
import com.example.recyclermulti.models.res.QuestionsResponse
import com.example.recyclermulti.models.res.dependentquestionres.DependentQuestionResponse
import com.example.recyclermulti.models.res.historyresponse.HistoryResponse
import com.example.recyclermulti.models.req.answer.AnswerReq
import com.example.recyclermulti.models.req.getBlock.BlockReq
import com.example.recyclermulti.models.req.getHistory.HistoryReq
import com.example.recyclermulti.models.req.getpanchayat.PanchayatReq
import com.example.recyclermulti.models.res.anserresponse.AnswerResponse
import com.example.recyclermulti.models.res.blockdata.BlockResponse
import com.example.recyclermulti.models.res.configdata.ConfigResponse
import com.example.recyclermulti.models.res.multiimageupload.MultiUploadResponse
import com.example.recyclermulti.models.res.panchayatresponse.PanchayatResponse
import com.example.recyclermulti.models.res.singleupload.SingleUploadResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface DashboardApiService {




    @GET("getcategories")
    suspend fun getAllCategories():Response<CategoryResponse>

    @POST("getallquestions")
    suspend fun getAllQuestions(@Body categoryreq: Categoryreq):Response<QuestionsResponse>

    @POST("getdependent")
    suspend fun getDependentQuestion(@Body dependentquesreq: Dependentquesreq):Response<DependentQuestionResponse>

    @Multipart
    @POST("upload")
   suspend fun uploadImage(@Part part: MultipartBody.Part): Response<SingleUploadResponse>

    @Multipart
    @POST("multiupload")
    suspend fun uploadMultiImage(@Part part:MutableList<MultipartBody.Part>): Response<MultiUploadResponse>



    @Multipart
    @POST("video")
    suspend fun uploadVideo(@Part part: MultipartBody.Part): Response<SingleUploadResponse>

    @POST("config")
    suspend fun getConfigData():Response<ConfigResponse>


    @POST("getblock")
    suspend fun getBlock(@Body blockReq: BlockReq):Response<BlockResponse>


    @POST("getpanchayat")
    suspend fun getPanchayat(@Body panchayatReq: PanchayatReq):Response<PanchayatResponse>


    @POST("answer")
    suspend fun addAnswer(@Body panchayatReq: AnswerReq):Response<AnswerResponse>

    @POST("gethistory")
    suspend fun getHistory(@Body historyReq: HistoryReq) : Response<HistoryResponse>


    @POST("getanswer")
    suspend fun getAnswer(@Body answerReq: GetAnswerReq) : Response<QuestionsResponse>





}