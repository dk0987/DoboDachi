package com.devdk.socialmedia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.core.presentation.ui.theme.button
import com.devdk.socialmedia.core.presentation.ui.theme.onBackground
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StandardButtons(
    text : String ,
    onClick : () -> Unit ,
    color : List<Color> = button ,
    roundedCornerShape: Dp = 20.dp ,
    elevation: Dp = 10.dp,
    isLoading : Boolean = false
) {
    Card(
        onClick = onClick ,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(roundedCornerShape),
        elevation = elevation
    ) {
        Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center ){
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        color,
                        startX = 500f
                    )
                ))
            if (!isLoading){
                Text(
                    text = text,
                    color = primaryText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 3.sp,
                    textAlign = TextAlign.Center,
                )
            }
            else{
                CircularProgressIndicator(
                    color = primaryText
                )
            }

        }

    }
    
}