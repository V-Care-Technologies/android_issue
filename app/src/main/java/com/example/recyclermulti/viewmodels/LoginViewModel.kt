package com.example.recyclermulti.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclermulti.Domain.Repo.LoginRepository
import com.example.recyclermulti.helper.Resource
import com.example.recyclermulti.models.req.Loginreq
import com.example.recyclermulti.models.res.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var loginFlow: MutableStateFlow<Resource<LoginResponse>> = MutableStateFlow(Resource.Empty())

    fun login(loginreq: Loginreq) {
        viewModelScope.launch(Dispatchers.IO) {
            loginFlow.value = Resource.Loading()
            val res = loginRepository.login(loginreq)
            if (res.isSuccessful) {
                res.body()?.let {
                    loginFlow.value = Resource.Success(it)
                }
            } else {
                loginFlow.value = Resource.Error(res.message())
            }
        }
    }


}