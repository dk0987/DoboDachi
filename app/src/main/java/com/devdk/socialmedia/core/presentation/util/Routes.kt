package com.devdk.socialmedia.core.presentation.util

import com.devdk.socialmedia.R

sealed class Routes(val screen : String) {
    object Login : Routes(R.string.login_screen.toString())
    object Register : Routes(R.string.register_screen.toString())
    object Feed : Routes(R.string.feed_screen.toString())
    object Search : Routes(R.string.search_screen.toString())
    object AddPost : Routes(R.string.add_screen.toString())
    object Chat : Routes(R.string.chat_screen.toString())
    object Activity : Routes(R.string.activity_screen.toString())
    object PostDetail : Routes(R.string.post_detail_screen.toString())
    object Messages : Routes(R.string.message_screen.toString())
    object PersonList : Routes(R.string.person_list_screen.toString())
    object Profile : Routes(R.string.profile_screen.toString())
    object EditProfile : Routes(R.string.edit_profile_screen.toString())
}
