package com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardButtons
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes

@Composable
fun EditProfile(
    navController: NavController,
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    val userNameState = editProfileViewModel.userNameTextFieldState.value
    val bioState = editProfileViewModel.bioTextFieldState.value
    val editProfileStates = editProfileViewModel.editProfileStates.value

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = rememberImagePainter(data = editProfileStates.bannerUrl , builder = {
                        crossfade(true)
                        size(OriginalSize)
                    }),
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
                        navController.navigate(Routes.Images.screen + "?route=${Routes.EditProfile.screen}&imageType={Banner}")
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
                    painter = rememberImagePainter(data = editProfileStates.profileUrl , builder = {
                        crossfade(true)
                        size(OriginalSize)
                    }),
                    contentDescription = stringResource(id = R.string.profile_pic),
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(Routes.Images.screen + "?route=${Routes.EditProfile.screen}&imageType={Profile}")
                        }
                    ,
                    contentScale = ContentScale.Crop ,
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
                    .background(if (editProfileStates.isError) Color.Red else primaryText)
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
                    .background(if (editProfileStates.isError) Color.Red else primaryText)
                    .height(0.5.dp))

            }

        }
        StandardButtons(
            text = stringResource(id = R.string.save),
            onClick = {
                 editProfileViewModel.onEvent(EditProfileEvents.Save)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp
                ),
            isLoading = editProfileStates.isUpdating
        )
    }

}