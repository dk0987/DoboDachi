package com.devdk.socialmedia.core.presentation.images_screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.images_screen.util.isPermissionDenied
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@Composable
fun Images(
    navController: NavController,
    imagesViewModel: ImagesViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val imagesState = imagesViewModel.images.value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val permissionState = rememberMultiplePermissionsState(permissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    ))

    LaunchedEffect(key1 = true ){
        imagesViewModel.getImages(context)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner ){
        val observer = LifecycleEventObserver{_,event ->
            if (event == Lifecycle.Event.ON_START){
                permissionState.launchMultiplePermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Box(modifier = Modifier.fillMaxSize()){
        permissionState.permissions.forEach { perm ->
            when(perm.permission){
                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    when{
                        perm.hasPermission -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp , top = 10.dp),
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

                                    Text(
                                        text = stringResource(id = R.string.gallery),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp ,
                                        color = primaryText ,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                                    items(imagesState.images){ image ->
                                        Image(
                                            painter = rememberImagePainter(data = "file:///${image.contentURI}"),
                                            contentDescription = stringResource(id = R.string.images) ,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clickable(
                                                    onClick = {
                                                        image.contentURI?.let { uri ->
                                                            navController.popBackStack()
                                                            navController.navigate(imagesState.route + "?croppedImageUri=file:///${uri}&imageType=${imagesState.imageType}&userId=${imagesState.userId}")
                                                        }
                                                    }
                                                )
                                                .size(width = 150.dp, height = 100.dp),
                                        )
                                    }
                                }
                            }

                        }
                        perm.isPermissionDenied() -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Storage permission was permanently denied. You can enable it in the app settings.")
                                delay(3000L)
                                navController.navigateUp()
                            }
                        }
                    }
                }
            }
        }

    }

}
