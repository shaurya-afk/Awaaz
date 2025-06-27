package com.project.whistleblower.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.whistleblower.R
import com.project.whistleblower.viewmodel.ComplaintViewModel

@Composable
fun ComplaintScreen() {
    var description by remember { mutableStateOf("") }
    val complaintViewModel: ComplaintViewModel = viewModel()

    val context = LocalContext.current
    val fileUris = remember { mutableStateOf<List<Uri>>(emptyList()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris -> fileUris.value = uris }

    val responseMessage by complaintViewModel.responseMessage
    val isLoading = complaintViewModel.loading.value
    val isHealthy = complaintViewModel.isHealthy.value

    LaunchedEffect(Unit) {
        complaintViewModel.checkHealth()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            isHealthy == null -> {
                CircularProgressIndicator(
                    color = OneUIColors.Primary
                )
            }
            isHealthy != true -> {
                ServerDownScreen()
                return
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(OneUIColors.Background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "File Your Complaint",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = OneUIColors.OnBackground,
            fontFamily = OneUIFontFamily
        )

        Spacer(Modifier.height(28.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = {
                Text(
                    text = "Describe your situation",
                    color = OneUIColors.TextSecondary,
                    fontFamily = OneUIFontFamily
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OneUIColors.Primary,
                unfocusedBorderColor = OneUIColors.TextSecondary,
                cursorColor = OneUIColors.Primary
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = OneUIFontFamily,
                fontWeight = FontWeight.Normal,
                color = OneUIColors.OnBackground
            )
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { launcher.launch("*/*") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OneUIColors.Surface,
                contentColor = OneUIColors.OnSurface
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(1.dp)
        ) {
            Text(
                text = "Attach Evidence",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = OneUIFontFamily
            )
        }

        Spacer(Modifier.height(8.dp))

        fileUris.value.forEach { uri ->
            Text(
                text = "Selected: ${uri.lastPathSegment ?: uri}",
                fontSize = 13.sp,
                color = OneUIColors.TextSecondary,
                fontFamily = OneUIFontFamily
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Note: Provide complete details of your situation.",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = OneUIColors.TextSecondary,
            fontFamily = OneUIFontFamily,
            modifier = Modifier
                .fillMaxWidth()
                .background(OneUIColors.Surface.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                .padding(8.dp)
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (description.isBlank() || fileUris.value.isEmpty()) {
                    android.widget.Toast.makeText(
                        context,
                        "Please provide a description and attach at least one evidence file.",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    complaintViewModel.submitComplaint(description, fileUris.value)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OneUIColors.Primary,
                contentColor = OneUIColors.OnPrimary
            ),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(2.dp)
        ) {
            Text(
                text = "Submit Complaint",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = OneUIFontFamily
            )
        }

        if (responseMessage != null) {
            Text(
                text = responseMessage!!,
                fontSize = 14.sp,
                color = if (responseMessage!!.contains("success", ignoreCase = true))
                    Color(0xFF4CAF50) else Color(0xFFD32F2F),
                fontFamily = OneUIFontFamily,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        if (isLoading == true) {
            CircularProgressIndicator(
                color = OneUIColors.Primary,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun ServerDownScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OneUIColors.Background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    color = OneUIColors.Surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(32.dp)
        ) {
            // Icon or illustration (optional, replace with your own asset if available)
            Icon(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.cloudoff),
                contentDescription = "Server Down",
                tint = OneUIColors.Error,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Server Down",
                color = OneUIColors.Error,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = OneUIFontFamily
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "We are unable to connect to the server right now.\nPlease try again later.\nTry checking your network connection.",
                color = OneUIColors.TextSecondary,
                fontSize = 16.sp,
                fontFamily = OneUIFontFamily,
                modifier = Modifier.padding(horizontal = 8.dp),
                lineHeight = 22.sp
            )
            Button(
                onClick = {
                    kotlin.system.exitProcess(0)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OneUIColors.Error,
                    contentColor = OneUIColors.OnPrimary
                ),
                shape = RoundedCornerShape(28.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 6.dp
                )
            ) {
                Text(
                    text = "Close App",
                    fontSize = 18.sp,
                    fontFamily = OneUIFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComplaintScreen(){
    ComplaintScreen()
}