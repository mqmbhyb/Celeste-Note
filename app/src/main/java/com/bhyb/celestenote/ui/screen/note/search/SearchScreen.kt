package com.bhyb.celestenote.ui.screen.note.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bhyb.celestenote.ui.component.bottomnavbar.ROUTE_ADD_EDIT_NOTE_SCREEN
import com.bhyb.celestenote.ui.component.bottomnavbar.ROUTE_SEARCH_SCREEN
import com.bhyb.celestenote.ui.screen.note.NoteItem

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val notes by viewModel.items.collectAsState()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotBlank()){
            viewModel.onSearch()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search") },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            trailingIcon = {
                TextButton(
                    onClick = {
                        keyboardController?.hide()
                        navController.navigateUp()
                    }
                ) {
                    Text("取消")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            itemsIndexed(notes) { index, note ->
                NoteItem(
                    note = note,
                    isLastItem = index == notes.lastIndex,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            keyboardController?.hide()

                            val navOptions = NavOptions.Builder()
                                .setPopUpTo(ROUTE_SEARCH_SCREEN, true)
                                .setLaunchSingleTop(true)
                                .build()

                            navController.navigate(
                                ROUTE_ADD_EDIT_NOTE_SCREEN + "?noteId=${note.id}",
                                navOptions
                            )
                        }
                )
            }
        }
    }
}