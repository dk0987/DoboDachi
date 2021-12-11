package com.devdk.socialmedia.feature_search.presentation.component

import android.widget.SearchView
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devdk.socialmedia.core.presentation.states.TextFieldStates
import com.devdk.socialmedia.feature_search.presentation.SearchEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

): ViewModel() , SearchView.OnQueryTextListener {
    private val _search = mutableStateOf(TextFieldStates())
    val search : State<TextFieldStates> = _search

    fun onEvent(event : SearchEvents) {
        when(event) {
            is SearchEvents.Search -> {

            }
        }
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        _search.value = search.value.copy(
            text = newText ?: ""
        )
        return false
    }
}