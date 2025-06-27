package com.project.whistleblower.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

fun String.toRequestBody() = RequestBody.create("text/plain".toMediaTypeOrNull(), this)

fun uriToMultipart(context: Context, uri: Uri, partName: String = "files"): MultipartBody.Part{
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File.createTempFile("upload", null, context.cacheDir)
    val outputStream = FileOutputStream(file)
    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()
    val reqFile = RequestBody.create("application/octet-stream".toMediaTypeOrNull(), file)
    return MultipartBody.Part.createFormData(partName, file.name, reqFile)
}