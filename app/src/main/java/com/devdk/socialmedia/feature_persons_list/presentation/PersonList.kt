package com.devdk.socialmedia.feature_persons_list.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.runtime.Composable
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
import com.devdk.socialmedia.feature_persons_list.presentation.component.PersonItem

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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center
        ){
            items(personListState.people){ person ->
                PersonItem(
                    person = person,
                    OnFollow = {
                        personLIstViewModel.follow( person.userId , person.isFollowing)
                    },
                )
            }
            if (personListState.isLoading){
                item { CircularProgressIndicator(color = bottomNavigationItem) }
            }
            else if (!personListState.isLoading && personListState.people.isEmpty()){
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













