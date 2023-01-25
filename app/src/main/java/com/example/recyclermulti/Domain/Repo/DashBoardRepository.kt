package com.example.recyclermulti.Domain.Repo

import com.example.recyclermulti.Domain.ApiServices.DashboardApiService
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
import javax.inject.Inject

class DashBoardRepository@Inject constructor(private val dashboardApiService: DashboardApiService) {

    suspend fun getCategories():Response<CategoryResponse>{
        return dashboardApiService.getAllCategories()
    }

    suspend fun getAllQuestions(categoryreq: Categoryreq):Response<QuestionsResponse>{
        return dashboardApiService.getAllQuestions(categoryreq)
    }


    suspend fun getDependent(categoryreq: Dependentquesreq):Response<DependentQuestionResponse>{
        return dashboardApiService.getDependentQuestion(categoryreq)
    }



    suspend fun uploadImage(uploadimg: MultipartBody.Part):Response<SingleUploadResponse>{
        return dashboardApiService.uploadImage(uploadimg)
    }


    suspend fun multipleImage(uploadimg: MutableList<MultipartBody.Part>):Response<MultiUploadResponse>{
        return dashboardApiService.uploadMultiImage(uploadimg)
    }



    suspend fun uploadVideo(uploadimg: MultipartBody.Part):Response<SingleUploadResponse>{
        return dashboardApiService.uploadVideo(uploadimg)
    }


    suspend fun getConfig():Response<ConfigResponse>{
        return dashboardApiService.getConfigData()
    }

    suspend fun getBlock(blockReq: BlockReq):Response<BlockResponse>{
        return dashboardApiService.getBlock(blockReq)
    }
    suspend fun getPanchayat(panchayatReq: PanchayatReq):Response<PanchayatResponse>{
        return dashboardApiService.getPanchayat(panchayatReq)
    }
    suspend fun addAnswer(panchayatReq: AnswerReq):Response<AnswerResponse>{
        return dashboardApiService.addAnswer(panchayatReq)
    }
    suspend fun getHistory(panchayatReq: HistoryReq):Response<HistoryResponse>{
        return dashboardApiService.getHistory(panchayatReq)
    }
    suspend fun getAnswer(getAnswerReq: GetAnswerReq):Response<QuestionsResponse>{
        return dashboardApiService.getAnswer(getAnswerReq)
    }




}