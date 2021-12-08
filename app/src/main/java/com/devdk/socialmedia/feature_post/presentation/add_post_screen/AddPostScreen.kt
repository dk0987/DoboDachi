package com.devdk.socialmedia.feature_post.presentation.add_post_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardButtons
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes

@Composable
fun AddPost(
    navController: NavController
) {
    LazyColumn(
        Modifier.padding(10.dp)
    ){
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {
                    navController.navigateUp()
                },
                    modifier = Modifier
                        .size(30.dp)
                        .padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowLeft,
                        contentDescription = stringResource(id = R.string.back),
                        tint = primaryText
                    )
                }

                Text(text = stringResource(id = R.string.post),
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp ,
                color = primaryText ,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(1500.dp))
                    .defaultMinSize(minHeight = 200.dp)
                    .background(container)
            ){
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = stringResource(id = R.string.upload_pic),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(50.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            StandardTextField(
                value = "",
                onValueChange = {},
                roundedCornerShape = 4.dp,
                placeholder = stringResource(id = R.string.description_for_post)
            )
            Spacer(modifier = Modifier.height(10.dp))
            StandardButtons(
                text = stringResource(id = R.string.post),
                onClick = { /*TODO*/ },
            )

        }
    }

}