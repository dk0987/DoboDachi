package com.devdk.socialmedia.feature_search.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
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
import com.devdk.socialmedia.core.presentation.components.StandardTextField
import com.devdk.socialmedia.core.presentation.ui.theme.bottomNavigationItem
import com.devdk.socialmedia.core.presentation.ui.theme.primaryText
import com.devdk.socialmedia.core.presentation.util.Routes
import com.devdk.socialmedia.core.util.UiEvent
import com.devdk.socialmedia.feature_search.presentation.component.SearchedItem
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun Search(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val searchTextFieldState = searchViewModel.searchTextFieldState.value
    val searchStates = searchViewModel.searchState.value

    LaunchedEffect(key1 = true ){
        searchViewModel.eventFLow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message!!)
                }
                else -> Unit
            }
        }
    }
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
            value = searchTextFieldState.text,
            isError = searchTextFieldState.isError,
            placeholder = stringResource(id = R.string.search_by),
            error = searchTextFieldState.error,
            onValueChange = {
                searchViewModel.onQueryTextChange(it)
            },
            trailingIcon = Icons.Outlined.Search,
        )
        Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .padding(horizontal = 5.dp)
            ){
                items(searchStates.searchedResult){ searchedItem ->
                    SearchedItem(
                        searchedUser = searchedItem,
                        OnClick = {
                            navController.navigate(Routes.Profile.screen)
                        },
                        OnFollow = {
                           searchViewModel.onEvent(SearchEvents.Follow(
                               userId = searchedItem.userId,
                               isFollowed = searchedItem.isFollowing
                           ))
                        }
                    )
                }
            }
        }
            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                if (searchStates.searchedResult.isEmpty() && !searchStates.isLoading){
                    Text(
                        text = stringResource(id = R.string.nothing_to_show),
                        color = primaryText ,
                        fontWeight = FontWeight.Bold ,
                        fontSize = 22.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
                if (searchStates.isLoading){
                    CircularProgressIndicator(color = bottomNavigationItem)
                }

            }

}