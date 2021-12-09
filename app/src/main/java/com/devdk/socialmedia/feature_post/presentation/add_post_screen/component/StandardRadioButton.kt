package com.devdk.socialmedia.feature_post.presentation.add_post_screen.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@Composable
fun StandardRadioButton(
    title : String ,
    selected : Boolean = false ,
    selectedColor : Color = bottomNavigationItem,
    unSelectedColor : Color = containerText,
    onClick : () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(3.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = selectedColor,
                unselectedColor = unSelectedColor
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = primaryText,
        )

    }
}