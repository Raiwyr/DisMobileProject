package com.example.dismobileproject.ui.screens.selection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.data.model.IndicationModel
import com.example.dismobileproject.ui.screens.produtclist.product.ProductsCard
import com.example.dismobileproject.ui.viewmodels.AscDescUiState
import com.example.dismobileproject.ui.viewmodels.SelectionProductViewModel
import com.example.dismobileproject.ui.widgets.RadioButtonWithText

@Composable
fun IndicationListScreen(
    viewModel: SelectionProductViewModel
){
    var selectionViewModel = viewModel

    var list = selectionViewModel.indicationStates

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
                RadioButtonWithText(
                    text = item.Name,
                    checkedButton = item.isSelected,
                    onButtonCheckChange = { selectionViewModel.onIndicationCheck(item.Id) }
                )
            }
        }
    }
}