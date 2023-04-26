package com.example.dismobileproject.ui.screens.profile.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.OrderModel

@Composable
fun OrderCard(
    order: OrderModel
){
    var productVisible = remember { mutableStateOf(false) }

    var productCount = 0;
    order.productModels?.forEach {
        productCount += it?.Count ?: 0
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp),
                text = "Заказ №${order.id ?: " "}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "Общая стоимость: ${order.grandTotal}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.ruble_icon),
                    modifier = Modifier
                        .height(22.dp)
                        .width(23.dp),
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
                text = "Статус: ${order.orderStatus ?: " "}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(bottom = 10.dp, start = 10.dp),
                text = "Дата заказа: ${order.orderDate ?: " "}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if(!productVisible.value){
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 10.dp)
                        .clickable { productVisible.value = true },
                    text = "Показать товары",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.action_element_color)
                )
            }
            else{
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 10.dp),
                    text = "Товары:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                order.productModels?.forEachIndexed() { index, order ->
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp,start = 10.dp, end = 10.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(8f),
                            text = "${index + 1}. ${order?.Name} (${order?.Count})",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Row(
                            modifier = Modifier.weight(2f).padding(start = 10.dp, bottom = 5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier,
                                text = ((order?.Count ?: 0) * (order?.Price ?: 0)).toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ruble_icon),
                                modifier = Modifier
                                    .height(15.dp)
                                    .width(16.dp),
                                contentDescription = ""
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp, start = 10.dp)
                        .clickable { productVisible.value = false },
                    text = "Скрыть товары",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.action_element_color)
                )
            }
        }
    }
}