package com.project.whistleblower.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.whistleblower.Routes

@Composable
fun AboutScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Awaaz",
            fontSize = 42.sp,
            fontFamily = OneUIFontFamily,
            fontWeight = FontWeight.Bold,
            color = OneUIColors.OnBackground,
            letterSpacing = (-0.4).sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Awaaz is a civic platform that empowers citizens to anonymously report police corruption. Our secure, privacy-first design ensures you can submit complaints without ever revealing your identity.\n\n" +
                    "\uD83D\uDD10 Key Features:\n" +
                    "• Anonymous complaint submission\n" +
                    "• No login or sign-up required\n" +
                    "• Zero identity tracking\n" +
                    "• Simple, secure, and user-friendly interface\n\n" +
                    "Your voice matters. Report wrongdoing safely and help build a more accountable society.",
            fontSize = 16.sp,
            fontFamily = OneUIFontFamily,
            fontWeight = FontWeight.Medium,
            color = OneUIColors.TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(32.dp))
        Text(
            text = "Made by Shaurya Sharma",
            fontSize = 10.sp,
            fontFamily = OneUIFontFamily,
            fontWeight = FontWeight.Medium,
            color = OneUIColors.TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
        Spacer(Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Routes.home) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = OneUIColors.Primary,
                contentColor = OneUIColors.OnPrimary
            ),
            shape = RoundedCornerShape(28.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = 6.dp
            )
        ) {
            Text(
                text = "Back",
                fontSize = 18.sp,
                fontFamily = OneUIFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen(){
    AboutScreen(navController = rememberNavController())
}