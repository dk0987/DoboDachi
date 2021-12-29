package com.devdk.socialmedia.core.presentation.util

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.annotation.ExperimentalCoilApi
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.components.StandardScaffold
import com.devdk.socialmedia.core.presentation.images_screen.Images
import com.devdk.socialmedia.feature_activity.presentation.Activity
import com.devdk.socialmedia.feature_auth.presentation.login_screen.Login
import com.devdk.socialmedia.feature_auth.presentation.registration_screen.Register
import com.devdk.socialmedia.feature_auth.presentation.splash_screen.Splash
import com.devdk.socialmedia.feature_post.presentation.add_post_screen.AddPost
import com.devdk.socialmedia.feature_post.presentation.feed_screen.Feed
import com.devdk.socialmedia.feature_persons_list.person_list_screen.PersonList
import com.devdk.socialmedia.feature_post.presentation.post_detail_screen.PostDetail
import com.devdk.socialmedia.feature_profile.presentation.edit_profile_screen.EditProfile
import com.devdk.socialmedia.feature_profile.presentation.profile_screen.Profile
import com.devdk.socialmedia.feature_search.presentation.Search
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

     val bottomNavigation = listOf(
        BottomNavigationItem(
            Icons.Outlined.Home,
            stringResource(id = R.string.home),
            Routes.Feed.screen
        ) ,
        BottomNavigationItem(
            Icons.Outlined.Search,
            stringResource(id = R.string.search),
            Routes.Search.screen
        ) ,
        BottomNavigationItem(
            Icons.Outlined.AddAPhoto,
            stringResource(id = R.string.add),
            Routes.AddPost.screen
        ),
        BottomNavigationItem(
            Icons.Outlined.Chat,
            stringResource(id = R.string.chat),
            Routes.Chat.screen
        ) ,
        BottomNavigationItem(
            Icons.Outlined.Notifications,
            stringResource(id = R.string.notification),
            Routes.Activity.screen
        ) ,
    )

    StandardScaffold(
        navController = navController,
        isVisible = navBackStackEntry?.destination?.route in listOf(
            Routes.Feed.screen,
            Routes.Search.screen,
            Routes.Chat.screen,
            Routes.Activity.screen,
        ),
        scaffoldState = scaffoldState,
        bottomNavigationItems = bottomNavigation,
        content = {
            NavHost(navController,
                startDestination = Routes.Splash.screen
            ) {
                composable(Routes.Splash.screen){
                    Splash(navController = navController)
                }
                composable(Routes.Login.screen) {
                    Login(navController = navController , scaffoldState = scaffoldState)
                }
                composable(Routes.Register.screen) {
                    Register(navController = navController , scaffoldState = scaffoldState)
                }
                composable(
                    route = Routes.Feed.screen,
                ){
                    Feed(navController = navController , scaffoldState = scaffoldState )
                }
                composable(Routes.AddPost.screen+ "?croppedImageUri={croppedImageUri}" ,
                    arguments = listOf(
                        navArgument("croppedImageUri") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ){
                    AddPost(navController = navController , scaffoldState = scaffoldState)
                }
                composable(
                    route = Routes.PostDetail.screen + "?postId={postId}",
                    arguments = listOf(
                        navArgument("postId"){
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    ),
                    deepLinks = listOf(
                        navDeepLink {
                            action = Intent.ACTION_VIEW
                            uriPattern = "https://dobodachi.com/{postId}"
                        }
                    )
                ){
                    println("Post Id : ${it.arguments?.get("postId")}")
                    PostDetail(navController = navController , scaffoldState = scaffoldState)
                }
                composable(Routes.Search.screen){
                    Search(navController = navController , scaffoldState = scaffoldState)
                }
                composable(
                    route = Routes.PersonList.screen + "?personList={personList}&parentId={parentId}&userId={userId}" ,
                    arguments = listOf(
                        navArgument("personList"){
                            type = NavType.StringType
                            defaultValue = "LikedBy"
                        } ,
                        navArgument("parentId"){
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("userId"){
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ){
                    PersonList(navController = navController)
                }
                composable(
                    route = Routes.Profile.screen + "?userId={userId}",
                    arguments = listOf(
                        navArgument("userId") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ){
                    Profile(navController = navController , scaffoldState = scaffoldState)
                }
                composable(Routes.Activity.screen){
                    Activity(navController = navController , scaffoldState = scaffoldState)
                }
                composable(
                    route = Routes.EditProfile.screen + "?userId={userId}&imageType={imageType}&croppedImageUri={croppedImageUri}",
                    arguments = listOf(
                        navArgument("userId") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("imageType") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("croppedImageUri") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ){
                    EditProfile(navController = navController , scaffoldState = scaffoldState)
                }
                composable(
                    route = Routes.Images.screen + "?&route={route}&imageType={imageType}&userId={userId}",
                    arguments = listOf(
                        navArgument("route") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("imageType") {
                            type = NavType.StringType
                            defaultValue = ""
                        },
                        navArgument("userId") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )
                ) {
                    Images(navController = navController , scaffoldState = scaffoldState )
                }
            }
        }
    )
}