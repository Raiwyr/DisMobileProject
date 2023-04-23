package com.example.dismobileproject.ui.screens.produtclist.product.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.viewmodels.GetProductState
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import com.example.dismobileproject.ui.widgets.TextRadioButton

@ExperimentalLayoutApi
@Composable
fun FilterScreen(
    viewModel: ProductsViewModel,
    text: String?,
    category: Int?
){
    var minPrice = viewModel.minPriceState
    var maxPrice = viewModel.maxPriceState
    var indications = viewModel.indicationsState
    var releaseForms = viewModel.releaseFormsState
    var quantityPackage = viewModel.quantityPackageState
    var manufacturers = viewModel.manufacturersState

    var indicationCount by remember { mutableStateOf(5) }
    var quantityPackageCount by remember { mutableStateOf(5) }
    var releaseFormCount by remember { mutableStateOf(5) }
    var manufacturerCount by remember { mutableStateOf(5) }

    LazyColumn(
        modifier = Modifier
            .heightIn(min = 0.dp, max = 600.dp)
            .padding(start = 30.dp, end = 30.dp, top = 25.dp, bottom = 25.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        item {
            Row(
                modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.filters),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    modifier = Modifier
                        .clickable {
                            when(viewModel.getProductState){
                                is GetProductState.Search -> viewModel.getProducts(text!!)
                                is GetProductState.Category -> viewModel.getProducts(category!!)
                                is GetProductState.NoInit -> {}
                            }
                        },
                    text = "Сбросить",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.action_element_color)
                )
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Цена",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(4f),
                        shape = RoundedCornerShape(30),
                        label = {Text("От")},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id =R.color.action_element_color),
                            unfocusedBorderColor = colorResource(id =R.color.action_element_color)),
                        value = minPrice,
                        onValueChange = { value ->
                            if (value.length <= 6 && (value.toIntOrNull() != 0)) {
                                viewModel.setMinPrice(value.filter { it.isDigit() })
                            }
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(4f),
                        shape = RoundedCornerShape(30),
                        label = {Text("До")},
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = colorResource(id =R.color.action_element_color),
                            unfocusedBorderColor = colorResource(id =R.color.action_element_color)),
                        value = maxPrice,
                        onValueChange = { value ->
                            if (value.length <= 6 && (value.toIntOrNull() != 0)) {
                                viewModel.setMaxPrice(value.filter { it.isDigit() })
                            }
                        }
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier,
                    text = "Показания",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    modifier = Modifier.padding(top =10.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    indications?.forEachIndexed lit@ { index, item ->
                        if(index < indicationCount){
                            TextRadioButton(
                                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp),
                                text = item.Name,
                                fontSize = 18.sp,
                                innerPadding = PaddingValues(12.dp, 4.dp),
                                checkedButton = item.isSelected,
                                onButtonCheckChange = {viewModel.onIndicationCheck(index)}
                            )
                        }
                        else{
                            return@lit
                        }
                    }
                }
                if(indications.count() > indicationCount){
                    Button(
                        onClick = { indicationCount+=5 },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Ещё",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier,
                    text = "Форма выпуска",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    modifier = Modifier.padding(top =10.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    releaseForms?.forEachIndexed lit@ { index, item ->
                        if(index < releaseFormCount){
                            TextRadioButton(
                                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp),
                                text = item.Name,
                                fontSize = 18.sp,
                                innerPadding = PaddingValues(12.dp, 4.dp),
                                checkedButton = item.isSelected,
                                onButtonCheckChange = {viewModel.onReleaseFormCheck(index)}
                            )
                        }
                        else{
                            return@lit
                        }
                    }
                }
                if(releaseForms.count() > releaseFormCount){
                    Button(
                        onClick = { releaseFormCount+=5 },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Ещё",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier,
                    text = "Количество в упаковке",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    modifier = Modifier.padding(top =10.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    quantityPackage?.forEachIndexed lit@ { index, item ->
                        if(index < quantityPackageCount){
                            TextRadioButton(
                                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp),
                                text = item.Name,
                                fontSize = 18.sp,
                                innerPadding = PaddingValues(12.dp, 4.dp),
                                checkedButton = item.isSelected,
                                onButtonCheckChange = {viewModel.onQuantityPackageCheck(index)}
                            )
                        }
                        else{
                            return@lit
                        }
                    }
                }
                if(quantityPackage.count() > quantityPackageCount){
                    Button(
                        onClick = { quantityPackageCount+=5 },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Ещё",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    modifier = Modifier,
                    text = "Производитель",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                FlowRow(
                    modifier = Modifier.padding(top =10.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    manufacturers?.forEachIndexed lit@ { index, item ->
                        if(index < manufacturerCount){
                            TextRadioButton(
                                modifier = Modifier.padding(start = 5.dp, bottom = 2.dp),
                                text = item.Name,
                                fontSize = 18.sp,
                                innerPadding = PaddingValues(12.dp, 4.dp),
                                checkedButton = item.isSelected,
                                onButtonCheckChange = {viewModel.onManufacturerCheck(index)}
                            )
                        }
                        else{
                            return@lit
                        }
                    }
                }
                if(manufacturers.count() > manufacturerCount){
                    Button(
                        onClick = { manufacturerCount+=5 },
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Ещё",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        item{
            Button(
                onClick = {
                    when(viewModel.getProductState){
                        is GetProductState.Search -> viewModel.getProductsByFilter(text!!)
                        is GetProductState.Category -> viewModel.getProductsByFilter(category!!)
                        is GetProductState.NoInit -> {}
                    }
                },
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                shape = CircleShape
            ) {
                Text(
                    text = "Применить",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}