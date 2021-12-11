package com.devdk.socialmedia.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    roundedCornerShape : Dp = 10.dp,
    placeholder : String = stringResource(id = R.string.comment_on_post),
    isError : Boolean = false,
    error : String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = containerText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
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
                (trailingIcon)?.let {
                IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = it,
                            contentDescription = stringResource(id = R.string.comment),
                            tint = bottomNavigationItem
                        )
                    }
                }
            }
        )
        if (isError){
            error?.let{ error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp)
                )
            }

        }
    }

}