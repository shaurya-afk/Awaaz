package com.project.whistleblower.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.whistleblower.Routes

object OneUIColors {
    val Primary = Color(0xFF1976D2)
    val PrimaryVariant = Color(0xFF0D47A1)
    val Secondary = Color(0xFF03DAC6)
    val Background = Color(0xFFF8F9FA)
    val Surface = Color(0xFFFFFFFF)
    val OnPrimary = Color(0xFFFFFFFF)
    val Error = Color(0xFFD32F2F)
    val OnBackground = Color(0xFF1C1C1E)
    val OnSurface = Color(0xFF1C1C1E)
    val TextSecondary = Color(0xFF8E8E93)
    val Accent = Color(0xFF007AFF)
}

val OneUIFontFamily = FontFamily.Default

@Composable
fun HomeScreen(
    navController: NavController
) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(true) {
        alpha.animateTo(1f, animationSpec = tween(1200))
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        color = OneUIColors.Background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            OneUIColors.Background,
                            OneUIColors.Background.copy(alpha = 0.96f)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .alpha(alpha.value),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Report corruption anonymously and securely.",
                    fontSize = 16.sp,
                    fontFamily = OneUIFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = OneUIColors.TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(44.dp))

                Button(
                    onClick = { navController.navigate(Routes.complaint) },
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
                        text = "Submit Complaint",
                        fontSize = 18.sp,
                        fontFamily = OneUIFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = OneUIColors.Surface.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔒Your identity is never collected.",
                            fontSize = 14.sp,
                            fontFamily = OneUIFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = OneUIColors.OnSurface,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Learn more about our privacy protection",
                            fontSize = 13.sp,
                            fontFamily = OneUIFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = OneUIColors.Accent,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.about)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen(){
    HomeScreen(navController = rememberNavController())
}