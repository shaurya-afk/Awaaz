package com.project.whistleblower.viewmodel

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.project.whistleblower.retrofit.RetrofitInstance
import com.project.whistleblower.util.toRequestBody
import com.project.whistleblower.util.uriToMultipart
import kotlinx.coroutines.launch

class ComplaintViewModel(
    application: Application
): AndroidViewModel(application) {
    private val _responseMessage = mutableStateOf<String?>(null)
    val responseMessage: State<String?> = _responseMessage

    private val _loading = mutableStateOf<Boolean?>(null)
    val loading: State<Boolean?> = _loading

    private val _isSuccessful = mutableStateOf<Boolean?>(null)
    val isSuccessful: State<Boolean?> = _isSuccessful

    private var _isHealthy = mutableStateOf<Boolean?>(null)
    var isHealthy: State<Boolean?> = _isHealthy

    fun submitComplaint(description: String, fileUris: List<Uri>){
        _loading.value = true
        viewModelScope.launch {
            val context = getApplication<Application>().applicationContext
            val descriptionBody = description.toRequestBody()
            val fileParts = fileUris.map { uri -> uriToMultipart(context, uri) }
            val response = RetrofitInstance.api.submitComplaint(descriptionBody, fileParts)
            if (response.isSuccessful) {
                _responseMessage.value = "Complaint submitted successfully!"
                _isSuccessful.value = true
            } else {
                _responseMessage.value = "Failed to submit complaint: ${response.message()}"
                _isSuccessful.value = false
            }
            _loading.value = false
        }
    }

    fun checkHealth(){
        viewModelScope.launch {
            val response = RetrofitInstance.api.checkHealth()
            _isHealthy.value = response.isSuccessful
        }
    }
}