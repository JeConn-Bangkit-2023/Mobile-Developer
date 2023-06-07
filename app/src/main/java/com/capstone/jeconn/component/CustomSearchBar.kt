package com.capstone.jeconn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.jeconn.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    enabled: MutableState<Boolean>,
    history: SnapshotStateList<String>,

    ) {
    val context = LocalContext.current
    SearchBar(
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
        ),
        query = text.value,
        onQueryChange = { text.value = it },
        onSearch = { enabled.value = false },
        active = enabled.value,
        onActiveChange = {
            enabled.value = it
        },
        placeholder = {
            Text(
                text = context.getString(R.string.search_vacancies),
                style = TextStyle(
                    fontFamily = Font.QuickSand,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search, contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(history) { textHistory ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = textHistory,
                            style = TextStyle(
                                fontFamily = Font.QuickSand,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                    },
                    leadingContent = {
                        Icon(
                            painterResource(id = R.drawable.ic_history),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    modifier = Modifier.clickable {
                        text.value = textHistory
                        enabled.value = false
                    }
                )
            }
        }
    }
}