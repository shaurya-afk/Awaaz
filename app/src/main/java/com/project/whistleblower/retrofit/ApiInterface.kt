package com.project.whistleblower.retrofit

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ComplaintApiService {
    @Multipart
    @POST("/api/complaints")
    suspend fun submitComplaint(
        @Part("description") description: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Response<ResponseBody>

    @GET("actuator/health")
    suspend fun checkHealth(): Response<Unit>
}
