package com.devdk.socialmedia.feature_search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
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
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.feature_search.presentation.component.SearchViewModel
import com.devdk.socialmedia.feature_search.presentation.component.SearchedItem

@ExperimentalMaterialApi
@Composable
fun Search(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchStates = searchViewModel.search.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.search) + "..",
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
            color = primaryText,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        StandardTextField(
            value = searchStates.text,
            isError = searchStates.isError,
            placeholder = stringResource(id = R.string.search_by),
            error = searchStates.error,
            onValueChange = {searchViewModel.onQueryTextChange(it)},
            trailingIcon = Icons.Outlined.Search
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)){
            items(20){
                SearchedItem(
                    profileURL = painterResource(id = R.drawable.profile_pic),
                    username = "Izuku Midoriya",
                    description = "used in various expressions indicating that a description or amount being stated is not exact",
                    following = false
                )
                SearchedItem(
                    profileURL = painterResource(id = R.drawable.profile_pic),
                    username = "Izuku Midoriya",
                    description = "used in various expressions indicating that a description or amount being stated is not exact",
                    following = true
                )
            }

        }
    }
}