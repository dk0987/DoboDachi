package com.devdk.socialmedia.core.presentation.util

sealed class Routes(val screen : String) {
    object Splash : Routes("Splash Screen")
    object Login : Routes("Login Screen")
    object Register : Routes("Register Screen")
    object Feed : Routes("Main Feed Screen")
    object Search : Routes("Search Screen")
    object AddPost : Routes("Add Post Screen")
    object Chat : Routes("Chat Screen")
    object Activity : Routes("Activity screen")
    object PostDetail : Routes("Post Detail Screen")
    object Messages : Routes("Message Screen")
    object PersonList : Routes("Person List Screen")
    object Profile : Routes("Profile Screen")
    object EditProfile : Routes("Edit Profile Screen")
    object Images : Routes("Images Screen")
}
