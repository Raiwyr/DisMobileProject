package com.example.dismobileproject.ui.screens.profile.shopcart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.ui.viewmodels.ProductShopCart
import com.example.dismobileproject.ui.widgets.CheckboxWithText

@Composable
fun ShopCartCard(
    product: ProductShopCart,
    modifier: Modifier = Modifier,
    maxCount: Int = 10,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    onCheckedChange: () -> Unit
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
            .requiredHeight(150.dp),
        elevation = 8.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(10.dp)){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.empty_product_icon),
                    contentDescription = ""
                )
                Row(
                    modifier = Modifier.align(Alignment.BottomStart),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.star_full_icon),
                        contentDescription = "",
                        tint = Color.Yellow
                    )
                    Text(text = product.assessment.toString())
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                product.name?.let {
                    Text(
                        text = it,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 4.dp, bottom = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    product.price?.let {
                        Row(
                            modifier = Modifier.padding(top = 4.dp, start = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                fontWeight = FontWeight.Bold,
                                text = it.toString()
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(3f).fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable { onMinusClick() }
                                    .size(25.dp),
                                painter = painterResource(id = R.drawable.minus_icon),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                                text = product.selectedCount.toString()
                            )
                            Icon(
                                modifier = Modifier
                                    .clickable { onPlusClick() }
                                    .size(25.dp),
                                painter = painterResource(id = R.drawable.plus_icon),
                                contentDescription = ""
                            )
                        }
                        Spacer(modifier = Modifier.weight(2f))
                        CheckboxWithText(
                            modifier = Modifier.weight(1f),
                            text = "",
                            checkedButton = product.isSelected,
                            onButtonCheckChange = { onCheckedChange() },
                            imageOnLeft = false
                        )
                    }
                }
            }
        }
    }
}