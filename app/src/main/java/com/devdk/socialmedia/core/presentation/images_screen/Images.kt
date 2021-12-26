package com.devdk.socialmedia.core.presentation.images_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.util.Routes

@ExperimentalFoundationApi
@Composable
fun Images(
    navController: NavController,
    imagesViewModel: ImagesViewModel = hiltViewModel()
) {
    val imagesState = imagesViewModel.images.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true ){
        imagesViewModel.getImages(context)
    }

    Box(modifier = Modifier.fillMaxSize()){
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
           items(imagesState.images){ image ->
               Image(
                   painter = rememberImagePainter(data = "file:///${image.contentURI}"),
                   contentDescription = stringResource(id = R.string.images) ,
                   contentScale = ContentScale.Crop,
                   modifier = Modifier.clickable(
                       onClick = {
                           image.contentURI?.let { uri ->
                               navController.popBackStack()
                               navController.navigate(imagesState.route + "?croppedImageUri=file:///${uri}&imageType=${imagesState.imageType}&userId=${imagesState.userId}")
                           }
                       }
                   ).size(width = 150.dp , height = 100.dp),
               )
           }
        }
    }

}