package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardButtons
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.containerText
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import kotlin.math.sin

@Composable
fun EditProfile(
    navController: NavController,
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    val userNameState = editProfileViewModel.userNameTextFieldState.value
    val bioState = editProfileViewModel.bioTextFieldState.value

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.mhawallparer),
                    contentDescription = stringResource(id = R.string.banner),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.FillWidth ,
                    alpha = 0.7f
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    IconButton(onClick = {
                        navController.navigateUp()
                    },
                        modifier = Modifier
                            .size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.back),
                            tint = primaryText
                        )
                    }
                    IconButton(onClick = {

                    },
                        modifier = Modifier
                            .size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddCircle,
                            contentDescription = stringResource(id = R.string.edit),
                            tint = primaryText
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp)
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic),
                    contentDescription = stringResource(id = R.string.profile_pic),
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                    ,
                    contentScale = ContentScale.Crop ,
                )
                Text(
                    text = stringResource(id = R.string.change_profile_pic),
                    color = containerText,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center ,
                    modifier = Modifier.clickable {

                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.username),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = primaryText,
                )
                BasicTextField(
                    value = userNameState.text ,
                    onValueChange = {
                        editProfileViewModel.onEvent(EditProfileEvents.Username(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle.Default.copy(
                        color = primaryText ,
                        fontSize = 18.sp ,
                        fontWeight = FontWeight.SemiBold
                    ),
                    cursorBrush = SolidColor(primaryText)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryText)
                    .height(0.5.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.Bio),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = primaryText,
                )
                BasicTextField(
                    value = bioState.text ,
                    onValueChange = {
                        editProfileViewModel.onEvent(EditProfileEvents.Bio(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle.Default.copy(
                        color = primaryText ,
                        fontSize = 18.sp ,
                        fontWeight = FontWeight.SemiBold
                    ),
                    cursorBrush = SolidColor(primaryText)
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryText)
                    .height(0.5.dp))

            }

        }
        StandardButtons(
            text = stringResource(id = R.string.save),
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp
                )
        )
    }

}