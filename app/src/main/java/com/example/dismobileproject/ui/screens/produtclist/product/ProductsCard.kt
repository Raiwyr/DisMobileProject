package com.example.dismobileproject.ui.screens.produtclist.product

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.ProductHeaderModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.viewmodels.DataUiState
import com.example.dismobileproject.ui.viewmodels.ProductCardViewModel
import com.example.dismobileproject.ui.viewmodels.ProductsViewModel
import com.example.dismobileproject.ui.viewmodels.models.ProductListModel
import com.example.dismobileproject.ui.widgets.SimpleTextWithBorder

@Composable
fun ProductsCard(
    product: ProductListModel,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    onCardClick: () -> Unit
){
    var viewModel: ProductCardViewModel = viewModel(factory = ProductCardViewModel.Factory)

    if(!viewModel.initViewModel)
        viewModel.initViewModel(product.imageName)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
            .requiredHeight(150.dp)
            .clickable(onClick = { onCardClick() }),
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
//                Image(
//                    modifier = Modifier.fillMaxSize(),
//                    painter = painterResource(id = R.drawable.empty_product_icon),
//                    contentDescription = ""
//                )
                when(viewModel.imageLoadState){
                    is DataUiState.Loading -> {
                        LoadingScreen()
                    }
                    is DataUiState.Success -> {
                        val bitmap = BitmapFactory.decodeByteArray(viewModel.byteImageState, 0, viewModel.byteImageState.size)
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            bitmap = try {
                                bitmap.asImageBitmap()
                            }
                            catch (e: Exception) {
                                getBitmapFromImage(LocalContext.current, R.drawable.empty_product_icon).asImageBitmap()
                            },
                            contentDescription = ""
                        )
                    }
                    is DataUiState.Error -> {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.empty_product_icon),
                            contentDescription = ""
                        )
                    }
                }
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
                    .padding(10.dp)
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
                    modifier = Modifier.weight(1f),
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
                    if(product.isAddToShopCart){
                        SimpleTextWithBorder(
                            modifier = Modifier
                                .requiredHeight(40.dp)
                                .width(120.dp),
                            text = "В корзине",
                            textColor = colorResource(id = R.color.action_element_color),
                            fontSize = 16.sp
                        )
                    }
                    else{
                        Button(
                            onClick = { onButtonClick() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                            shape = CircleShape,
                            modifier = Modifier
                                .requiredHeight(40.dp)
                                .width(120.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_cart),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

public fun getBitmapFromImage(context: Context, drawable: Int): Bitmap {

    // on below line we are getting drawable
    val db = ContextCompat.getDrawable(context, drawable)

    // in below line we are creating our bitmap and initializing it.
    val bit = Bitmap.createBitmap(
        db!!.intrinsicWidth, db.intrinsicHeight, Bitmap.Config.ARGB_8888
    )

    // on below line we are
    // creating a variable for canvas.
    val canvas = Canvas(bit)

    // on below line we are setting bounds for our bitmap.
    db.setBounds(0, 0, canvas.width, canvas.height)

    // on below line we are simply
    // calling draw to draw our canvas.
    db.draw(canvas)

    // on below line we are
    // returning our bitmap.
    return bit
}