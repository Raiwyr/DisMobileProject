package com.example.dismobileproject.ui.screens.selection.parameter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.ui.viewmodels.SelectionParameterViewModel
import com.example.dismobileproject.ui.widgets.CheckboxWithText

@Composable
fun SideEffectListScreen(
    viewModel: SelectionParameterViewModel
){
    var selectionViewModel = viewModel

    var list = selectionViewModel.sideEffectStates

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Divider(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp, bottom = 15.dp, start = 25.dp, end = 25.dp),
            thickness = 3.dp,
            color = Color.LightGray)
        LazyColumn(modifier = Modifier.fillMaxSize()){
            itemsIndexed(list){
                    _, item ->
                CheckboxWithText(
                    text = item.Name,
                    checkedButton = item.isSelected,
                    onButtonCheckChange = { selectionViewModel.onSideEffectCheck(item.Id) }
                )
            }
        }
    }
}