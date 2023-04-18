package com.example.dismobileproject.ui.screens.produtclist.product.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.ReviewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(10.dp),
    messageTextSize: TextUnit = 20.sp,
    review: ReviewModel
){
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for(i in 1..5){
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = if(i <= (review.assessment ?: 0)) R.drawable.star_full_icon else R.drawable.star_icon),
                        contentDescription = ""
                    )
                }
            }
            if(!review.message.isNullOrEmpty()){
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = review.let { it.message ?: "" },
                    fontSize = messageTextSize
                )
            }
            Row(
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                review.userNsme?.let {
                    Text(
                        text = it
                    )
                }
                review.dateReview?.let {
                    val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = outputFormatter.format(it)
                    )
                }
            }
        }
    }
}