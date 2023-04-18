package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.R

@Composable
fun SearchBar(
    searchText: String,
    isEnabledSearch: Boolean = true,
    isDisplayFilter: Boolean = false,
    isDisplayBackButton: Boolean = true,
    onSearchTextChanged: (String) -> Unit = {},
    onSearchFieldClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onDoneAction: () -> Unit = {}
){
    TopAppBar(backgroundColor = Color.White) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(isDisplayBackButton){
                IconButton(
                    onClick = { onBackClick() },
                    Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .width(20.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        tint = Color.Black,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            SearchField(
                text = searchText,
                onValueChanged = onSearchTextChanged,
                onDoneAction = onDoneAction,
                isEnable = isEnabledSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(if (isDisplayFilter) 8f else 9f)
                    .clickable { onSearchFieldClick() }
            )
            if(isDisplayFilter){
                IconButton(
                    onClick = { onFilterClick() },
                    Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .width(20.dp)
                        .weight(1f)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.filter_icon),
                        tint = Color.Black,
                        contentDescription = "",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}