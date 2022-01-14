package com.devdk.socialmedia.feature_activity.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_activity.presentation.component.ActivityItem
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun Activity(
    navController: NavController,
    activityViewModel: ActivityViewModel = hiltViewModel() ,
    scaffoldState: ScaffoldState
) {

    LaunchedEffect(key1 = true) {
        activityViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar((event.message ?: ""))
                }
                else -> Unit
            }
        }
    }
    val paginatedActivity = activityViewModel.paginatedActivityState.value
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
                .fillMaxHeight(0.92f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (paginatedActivity.activity.size < 5 || paginatedActivity.activity.isEmpty())Arrangement.Center else Arrangement.Top
        ){
            items(paginatedActivity.activity.size) { i ->
                val activity = paginatedActivity.activity[i]
                if ( i >= paginatedActivity.activity.size - 1 && !paginatedActivity.endReached && !paginatedActivity.isLoading){
                       activityViewModel.loadActivities()
                }
                ActivityItem(
                    activity = activity,
                    OnClick = {
                        navController.navigate(Routes.Profile.screen + "?userId=${activity.actionPerformedBy}")
                    },
                    onProfilePic = {
                        navController.navigate(Routes.Profile.screen + "?userId=${activity.actionPerformedBy}")
                    },
                    onPostText = {
                        navController.navigate(Routes.PostDetail.screen + "?postId=${activity.actionPerformedOn}")
                    },
                    onCommentText = {
                        navController.navigate(Routes.PostDetail.screen + "?postId=${activity.actionPerformedOn}")
                    }
                )
            }

            if (paginatedActivity.isLoading){
                item { CircularProgressIndicator(color = bottomNavigationItem) }
            }
            else if (!paginatedActivity.isLoading && paginatedActivity.activity.isEmpty()){
                item {
                    Text(
                        text = stringResource(id = R.string.nothing_to_show),
                        color = primaryText ,
                        fontWeight = FontWeight.Bold ,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}