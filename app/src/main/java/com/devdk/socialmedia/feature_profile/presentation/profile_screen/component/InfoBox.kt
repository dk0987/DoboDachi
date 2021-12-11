package com.devdk.socialmedia.feature_profile.presentation.profile_screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@ExperimentalMaterialApi
@Composable
fun InfoBox(
    text : String ,
    number : Int,
    onClick : () -> Unit = {},
    roundedCornerShape : Dp = 15.dp ,
    elevation : Dp = 10.dp,
    textColor : Color = primaryText
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(roundedCornerShape),
        elevation = elevation,
        modifier = Modifier
            .size(width = 115.dp , height = 65.dp)
            .padding(5.dp)
            .border(BorderStroke(1.dp , textColor) , RoundedCornerShape(roundedCornerShape)),
        backgroundColor = container
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = number.toString(),
                color = textColor ,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = text,
                color = textColor ,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}