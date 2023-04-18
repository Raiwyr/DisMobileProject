package com.example.dismobileproject.ui.screens.produtclist.product.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.R
import java.math.RoundingMode

@ExperimentalMaterialApi
@Composable
fun ProductDescriptionScreen(
    product: ProductModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            painter = painterResource(id = R.drawable.empty_product_icon),
            contentDescription = ""
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val sumAssessments = product.review?.sumOf { it?.assessment ?: 0 }?.toFloat()
                val countAssessments = product.review?.count()
                val finalAssessment = if(sumAssessments == null || countAssessments == null) null else sumAssessments / countAssessments
                val starCount = finalAssessment?.toBigDecimal()?.setScale(0,RoundingMode.HALF_UP)?.toInt() ?: 0

                for(i in 1..5){
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = if(i <= starCount) R.drawable.star_full_icon else R.drawable.star_icon),
                        contentDescription = ""
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                text = product.name ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            product.price?.let {
                Row(
                    modifier = Modifier.padding(top = 15.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        text = it.toString(),
                        fontSize = 25.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ruble_icon),
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp),
                        contentDescription = ""
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = stringResource(id = R.string.specifications),
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp
            )


        }
    }
}