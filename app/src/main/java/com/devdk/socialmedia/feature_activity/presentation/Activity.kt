package com.devdk.socialmedia.feature_activity.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.feature_activity.presentation.component.ActivityItem
import com.devdk.socialmedia.feature_activity.presentation.util.Action
import com.devdk.socialmedia.feature_activity.presentation.util.COMMENT
import com.devdk.socialmedia.feature_activity.presentation.util.POST

@ExperimentalMaterialApi
@Composable
fun Activity(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.activity),
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = primaryText,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
        ){
            items(100){
                ActivityItem(
                    profileURL = painterResource(id = R.drawable.profile_pic),
                    username = "Yuji Itadori",
                    action = Action.Liked(COMMENT)
                )
                ActivityItem(
                    profileURL = painterResource(id = R.drawable.post_pic),
                    username = "Itachi Ucchia",
                    action = Action.Liked(POST)
                )
                ActivityItem(
                    profileURL = painterResource(id = R.drawable.mhawallparer),
                    username = "Itachi Ucchia",
                    action = Action.Followed
                )
            }
        }
    }
}