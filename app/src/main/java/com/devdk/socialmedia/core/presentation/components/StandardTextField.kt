package com.devdk.socialmedia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText

@Composable
fun StandardTextField(
    value : String ,
    onValueChange : (String) -> Unit ,
    trailingIcon : ImageVector? = null,
    roundedCornerShape : Dp = 25.dp,
    placeholder : String = stringResource(id = R.string.comment_on_post)
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = containerText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(start = 5.dp)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = primaryText,
            backgroundColor = container,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clip(RoundedCornerShape(roundedCornerShape)),
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                (trailingIcon)?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = stringResource(id = R.string.comment),
                        tint = bottomNavigationItem
                    )
                }
            }
        }
    )
}