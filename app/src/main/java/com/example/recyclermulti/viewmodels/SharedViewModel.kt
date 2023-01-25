package com.example.recyclermulti.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclermulti.Domain.Repo.DashBoardRepository
import com.example.recyclermulti.helper.Resource
import com.example.recyclermulti.models.req.Categoryreq
import com.example.recyclermulti.models.req.GetAnswerReq
import com.example.recyclermulti.models.res.CategoryResponse
import com.example.recyclermulti.models.res.QuestionsResponse
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
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val dashBoardRepository: DashBoardRepository) :
    ViewModel() {
    var categoryResponse: MutableStateFlow<Resource<CategoryResponse>> =
        MutableStateFlow(Resource.Empty())
    var questionsResponse: MutableStateFlow<Resource<QuestionsResponse>> =
        MutableStateFlow(Resource.Empty())
    var answerResponse: MutableStateFlow<Resource<QuestionsResponse>> =
        MutableStateFlow(Resource.Empty())

    var singleUploadResponse: MutableStateFlow<Resource<SingleUploadResponse>> =
        MutableStateFlow(Resource.Empty())

    var multiUploadResponse: MutableStateFlow<Resource<MultiUploadResponse>> =
        MutableStateFlow(Resource.Empty())

    var videoUploadResponse: MutableStateFlow<Resource<SingleUploadResponse>> =
        MutableStateFlow(Resource.Empty())
    var configresponse: MutableStateFlow<Resource<ConfigResponse>> =
        MutableStateFlow(Resource.Empty())
    var blockresponse: MutableStateFlow<Resource<BlockResponse>> =
        MutableStateFlow(Resource.Empty())
    var panchayatresponse: MutableStateFlow<Resource<PanchayatResponse>> =
        MutableStateFlow(Resource.Empty())

    var addAnswerResponse: MutableStateFlow<Resource<AnswerResponse>> =
        MutableStateFlow(Resource.Empty())

    var getHistoryResponse: MutableStateFlow<Resource<HistoryResponse>> =
        MutableStateFlow(Resource.Empty())










    fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryResponse.value = Resource.Loading()
            val res = dashBoardRepository.getCategories();
            if (res.isSuccessful) {
                res.body()?.let {
                    categoryResponse.value = Resource.Success(it)

                }
            } else {
                categoryResponse.value = Resource.Error(res.message())
            }

        }
    }


    fun getQuestions(categoryreq: Categoryreq) {
        viewModelScope.launch(Dispatchers.IO) {
            questionsResponse.value = Resource.Loading()
            val res = dashBoardRepository.getAllQuestions(categoryreq);
            if (res.isSuccessful) {
                res.body()?.let {
                    questionsResponse.value = Resource.Success(it)
                }
            } else {
                questionsResponse.value = Resource.Error(res.message())
            }

        }
    }


    fun getAnswers(answerReq: GetAnswerReq) {
        viewModelScope.launch(Dispatchers.IO) {
            answerResponse.value = Resource.Loading()
            val res = dashBoardRepository.getAnswer(answerReq);
            if (res.isSuccessful) {
                res.body()?.let {
                    answerResponse.value = Resource.Success(it)
                }
            } else {
                answerResponse.value = Resource.Error(res.message())
            }

        }
    }

/*    fun getDependentQuestions(dependentquesreq: Dependentquesreq) {
        viewModelScope.launch(Dispatchers.IO) {
            dependentQuestionResponse.value = Resource.Loading()
            val res = dashBoardRepository.getDependent(dependentquesreq);
            if (res.isSuccessful) {
                res.body()?.let {
                    dependentQuestionResponse.value = Resource.Success(it)
                }
            } else {
                dependentQuestionResponse.value = Resource.Error(res.message())
            }

        }
    }*/


    fun uploadImage(requestBody: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            singleUploadResponse.value = Resource.Loading()
            val res = dashBoardRepository.uploadImage(requestBody);
            if (res.isSuccessful) {
                res.body()?.let {
                    singleUploadResponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                singleUploadResponse.value = Resource.Error(res.message())
            }

        }
    }

    fun uploadMultipleimage(requestBody: MutableList<MultipartBody.Part>) {
        viewModelScope.launch(Dispatchers.IO) {
            multiUploadResponse.value = Resource.Loading()
            val res = dashBoardRepository.multipleImage(requestBody);
            if (res.isSuccessful) {
                res.body()?.let {
                    multiUploadResponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                multiUploadResponse.value = Resource.Error(res.message())
            }

        }
    }
    fun uploadVideo(requestBody: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            videoUploadResponse.value = Resource.Loading()
            val res = dashBoardRepository.uploadVideo(requestBody);
            if (res.isSuccessful) {
                res.body()?.let {
                    videoUploadResponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                videoUploadResponse.value = Resource.Error(res.message())
            }

        }
    }


    fun getConfig() {
        viewModelScope.launch(Dispatchers.IO) {
            configresponse.value = Resource.Loading()
            val res = dashBoardRepository.getConfig();
            if (res.isSuccessful) {
                res.body()?.let {
                    configresponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                configresponse.value = Resource.Error(res.message())
            }

        }
    }
    fun getBlock(blockReq: BlockReq){
        viewModelScope.launch(Dispatchers.IO) {
            blockresponse.value = Resource.Loading()
            val res = dashBoardRepository.getBlock(blockReq);
            if (res.isSuccessful) {
                res.body()?.let {
                    blockresponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                blockresponse.value = Resource.Error(res.message())
            }

        }
    }

    fun getPanchayat(panchayatReq: PanchayatReq){
        viewModelScope.launch(Dispatchers.IO) {
            panchayatresponse.value = Resource.Loading()
            val res = dashBoardRepository.getPanchayat(panchayatReq);
            if (res.isSuccessful) {
                res.body()?.let {
                    panchayatresponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                panchayatresponse.value = Resource.Error(res.message())
            }

        }
    }

    fun addAnswer(answerReq: AnswerReq){
        viewModelScope.launch(Dispatchers.IO) {
            addAnswerResponse.value = Resource.Loading()
            val res = dashBoardRepository.addAnswer(answerReq);
            if (res.isSuccessful) {
                res.body()?.let {
                    addAnswerResponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                addAnswerResponse.value = Resource.Error(res.message())
            }

        }
    }

    fun getHistory(answerReq: HistoryReq){
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryResponse.value = Resource.Loading()
            val res = dashBoardRepository.getHistory(answerReq);
            if (res.isSuccessful) {
                res.body()?.let {
                    getHistoryResponse.value = Resource.Success(it)
                }
            } else {
                Log.d("error_message", Gson().toJson(res.errorBody()))
                getHistoryResponse.value = Resource.Error(res.message())
            }

        }
    }






}