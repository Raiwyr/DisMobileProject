package com.example.dismobileproject.ui.screens.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dismobileproject.data.model.CategoryModel
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductsCard

@Composable
fun CategoryListScreen(
    navController: NavController,
    categories: List<CategoryModel>,
    modifier: Modifier = Modifier
){
    val categoryIdRoute = stringResource(R.string.route_param_category_id)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(
//                rememberScrollState()
//            )
    ){
        itemsIndexed(categories){
                _, category ->
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 2.dp, start = 5.dp, end = 5.dp)
                    .requiredHeight(60.dp),
                elevation = 4.dp
            ){
                Row(
                    modifier = Modifier.clickable(onClick = {navController.navigate(Screen.Products.screenName + "?$categoryIdRoute=${category.id}")}),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(8f).padding(start = 10.dp),
                        text = category.name ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 25.sp
                    )
                    Icon(
                        modifier = Modifier.weight(1f),
                        painter = painterResource(id = R.drawable.arrow_angle_next),
                        contentDescription = "",
                        tint = Color.LightGray
                    )
                }
            }
        }
    }
}