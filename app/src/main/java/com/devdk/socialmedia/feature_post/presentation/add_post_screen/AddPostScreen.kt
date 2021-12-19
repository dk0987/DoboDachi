package com.devdk.socialmedia.feature_post.presentation.add_post_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.container
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.Const.PRIVATE
import com.devdk.socialmedia.core.util.Const.PUBLIC
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_post.presentation.add_post_screen.component.StandardRadioButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddPost(
    navController: NavController,
    addPostViewModel: AddPostViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val descriptionState = addPostViewModel.descriptionTextFieldStates.value
    val private = addPostViewModel.privateMode.value
    val public = addPostViewModel.publicMode.value
    val contentStates = addPostViewModel.contentUri

    LaunchedEffect(key1 = true ){
        addPostViewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message!!)
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    LazyColumn(
        Modifier.padding(10.dp)
    ){
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
            ) {
                StandardRadioButton(title = PRIVATE , selected = private , onClick = {
                    addPostViewModel.onEvent(AddPostDetailEvents.ViewMode(PRIVATE))
                })
                StandardRadioButton(title = PUBLIC , selected = public, onClick = {
                    addPostViewModel.onEvent(AddPostDetailEvents.ViewMode(PUBLIC))
                })
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .defaultMinSize(minHeight = if (contentStates.value.isBlank()) 180.dp else 0.dp)
                    .background(container)
                    .clickable {
                        navController.navigate(Routes.Images.screen)
                    }
            ){
                if (contentStates.value.isBlank()){
                    Icon(
                        imageVector = Icons.Outlined.AddAPhoto,
                        contentDescription = stringResource(id = R.string.upload_pic),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }
                else {
                    Image(
                        painter = rememberImagePainter(data = contentStates.value , builder = {
                            size(OriginalSize)
                        }),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.fillMaxSize()
                    )
                }


            }
            Spacer(modifier = Modifier.height(10.dp))
            StandardTextField(
                value = descriptionState.text,
                onValueChange = {
                    addPostViewModel.onEvent(AddPostDetailEvents.Description(it))
                },
                isError = descriptionState.isError,
                error = descriptionState.error,
                roundedCornerShape = 10.dp,
                placeholder = stringResource(id = R.string.description_for_post)
            )
            Spacer(modifier = Modifier.height(10.dp))
            StandardButtons(
                text = stringResource(id = R.string.post),
                onClick = {
                    addPostViewModel.onEvent(AddPostDetailEvents.Post)
                },
            )

        }
    }

}