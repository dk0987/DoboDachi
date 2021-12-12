package com.devdk.socialmedia.feature_persons_list.person_list_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devdk.socialmedia.R
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.feature_search.presentation.component.SearchedItem

@ExperimentalMaterialApi
@Composable
fun PersonList(
    navController : NavController,
    personLIstViewModel: PersonLIstViewModel = hiltViewModel()
) {
    val personListState = personLIstViewModel.personListScreenStates.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally ,
    ) {
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

            Text(
                text = personListState.screenName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp ,
                color = primaryText ,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn{
            items(20){
                SearchedItem(
                    profileURL = painterResource(id = R.drawable.post_pic),
                    username = "Yuji Itadori",
                    following = true,
                    OnClick = {
                        navController.navigate(Routes.Profile.screen)
                    },
                )
                SearchedItem(
                    profileURL = painterResource(id = R.drawable.post_pic),
                    username = "Yuji Itadori",
                    following = false ,
                )
            }
        }
    }

}













