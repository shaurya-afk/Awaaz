package com.project.whistleblower

import com.project.whistleblower.retrofit.ComplaintApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: ComplaintApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComplaintApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testSubmitComplaintEndpoint_Success() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("{\"success\":true}"))
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), "Test complaint description")
        val fileBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), byteArrayOf(1,2,3))
        val filePart = MultipartBody.Part.createFormData("files", "test.txt", fileBody)
        val files = listOf(filePart)
        kotlinx.coroutines.runBlocking {
            val response = api.submitComplaint(description, files)
            val request = mockWebServer.takeRequest()
            assertEquals("/api/complaints", request.path)
            assertTrue(response.isSuccessful)
        }
    }

    @Test
    fun testSubmitComplaintEndpoint_Error() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{\"error\":\"Bad Request\"}"))
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), "Test complaint description")
        val fileBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), byteArrayOf(1,2,3))
        val filePart = MultipartBody.Part.createFormData("files", "test.txt", fileBody)
        val files = listOf(filePart)
        kotlinx.coroutines.runBlocking {
            val response = api.submitComplaint(description, files)
            val request = mockWebServer.takeRequest()
            assertEquals("/api/complaints", request.path)
            assertFalse(response.isSuccessful)
            assertEquals(400, response.code())
        }
    }

    @Test
    fun testSubmitComplaintEndpoint_EmptyResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        val description = RequestBody.create("text/plain".toMediaTypeOrNull(), "Test complaint description")
        val fileBody = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), byteArrayOf(1,2,3))
        val filePart = MultipartBody.Part.createFormData("files", "test.txt", fileBody)
        val files = listOf(filePart)
        kotlinx.coroutines.runBlocking {
            val response = api.submitComplaint(description, files)
            val request = mockWebServer.takeRequest()
            assertEquals("/api/complaints", request.path)
            assertTrue(response.isSuccessful)
            assertEquals("", response.body()?.string())
        }
    }

    @Test
    fun testCheckHealth_Success() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200))
        kotlinx.coroutines.runBlocking {
            val response = api.checkHealth()
            val request = mockWebServer.takeRequest()
            assertEquals("/actuator/health", request.path)
            assertTrue(response.isSuccessful)
            assertEquals(200, response.code())
        }
    }

    @Test
    fun testCheckHealth_Failure() {
        mockWebServer.enqueue(MockResponse().setResponseCode(503))
        kotlinx.coroutines.runBlocking {
            val response = api.checkHealth()
            val request = mockWebServer.takeRequest()
            assertEquals("/actuator/health", request.path)
            assertFalse(response.isSuccessful)
            assertEquals(503, response.code())
        }
    }

    @Test
    fun testCheckHealth_EmptyResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(""))
        kotlinx.coroutines.runBlocking {
            val response = api.checkHealth()
            val request = mockWebServer.takeRequest()
            assertEquals("/actuator/health", request.path)
            assertTrue(response.isSuccessful)
            assertEquals(200, response.code())
        }
    }
}
