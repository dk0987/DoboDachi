package com.devdk.socialmedia.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.*

@Composable
fun StandardTextField(
    roundedCornerShape: Dp = 10.dp,
    placeholder : String,
    text : String,
    onValueChange : (String) -> Unit,
    keyboardType: KeyboardType,
    passwordVisible : Boolean = false,
    togglePassword : () -> Unit = {},
    error : String = "",
    isError : Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = text,
            label = { Text(placeholder , fontSize = 14.sp , fontStyle = FontStyle.Italic)},
            onValueChange = {onValueChange(it)} ,
            modifier = Modifier
                .fillMaxWidth(),
            visualTransformation =
            if(keyboardType == KeyboardType.Password && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType =  keyboardType
            ),
            trailingIcon = {
                if (keyboardType == KeyboardType.Password && passwordVisible){
                    IconButton(onClick = togglePassword ) {
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.invisible)
                        )
                    }
                }
                else if (keyboardType == KeyboardType.Password && !passwordVisible){
                    IconButton(onClick = togglePassword) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = stringResource(id = R.string.visible)
                        )
                    }
                }
            },
            maxLines = 1 ,
            singleLine = true,
            shape = RoundedCornerShape(roundedCornerShape),
            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = container,
                textColor = primaryText,
                focusedLabelColor = containerText,
                unfocusedLabelColor = containerText,
                trailingIconColor = onBackground,
                focusedIndicatorColor = onBackground
            ),
            isError = isError,
        )
        if (isError){
            Text(
                text = error,
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.fillMaxWidth().padding(end = 5.dp)
            )
        }
    }

}