package com.example.dismobileproject.ui.screens.selection.evaluation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.viewmodels.*
import com.example.dismobileproject.ui.widgets.RadioButtonWithText
import com.example.dismobileproject.ui.widgets.TextRadioButton

@Composable
fun EvaluationListScreen(
    viewModel: SelectionEvaluationViewModel
){
    var contraindicationEnabled = viewModel.contraindicationEnabledState
    var priceEnabled = viewModel.priceEnabledState
    var assessmentEnabled = viewModel.assessmentEnabledState
    var reviewsEnabled = viewModel.reviewsEnabledState

    val evaluationContraindicationStates = viewModel.evaluationContraindicationStates
    val evaluationPriceStates = viewModel.evaluationPriceStates
    val evaluationAssessmentStates = viewModel.evaluationAssessmentStates
    val evaluationReviewsStates = viewModel.evaluationReviewsStates

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        if(contraindicationEnabled) {
            item{
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Blue),
                    elevation = 10.dp
                ){
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "Противопоказания", fontSize = 25.sp, fontWeight = FontWeight.Medium)
                        Divider(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                itemsIndexed(evaluationContraindicationStates){
                                        _, item ->
                                    TextRadioButton(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .height(35.dp),
                                        text = item.Number.toString(),
                                        checkedButton = item.isSelected,
                                        onButtonCheckChange = {viewModel.onEvaluationContraindicationCheck(item.Number)}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if(priceEnabled){
            item{
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Blue),
                    elevation = 10.dp
                ){
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "Цена", fontSize = 25.sp, fontWeight = FontWeight.Medium)
                        Divider(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                itemsIndexed(evaluationPriceStates){
                                        _, item ->
                                    TextRadioButton(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .height(35.dp),
                                        text = item.Number.toString(),
                                        checkedButton = item.isSelected,
                                        onButtonCheckChange = {viewModel.onEvaluationPriceCheck(item.Number)}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if(assessmentEnabled){
            item{
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Blue),
                    elevation = 10.dp
                ){
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "Оценка", fontSize = 25.sp, fontWeight = FontWeight.Medium)
                        Divider(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                itemsIndexed(evaluationAssessmentStates){
                                        _, item ->
                                    TextRadioButton(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .height(35.dp),
                                        text = item.Number.toString(),
                                        checkedButton = item.isSelected,
                                        onButtonCheckChange = {viewModel.onEvaluationAssessmentCheck(item.Number)}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if(reviewsEnabled){
            item{
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 2.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Color.Blue),
                    elevation = 10.dp
                ){
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(text = "Отзывы", fontSize = 25.sp, fontWeight = FontWeight.Medium)
                        Divider(
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ){
                                itemsIndexed(evaluationReviewsStates){
                                        _, item ->
                                    TextRadioButton(
                                        modifier = Modifier
                                            .width(35.dp)
                                            .height(35.dp),
                                        text = item.Number.toString(),
                                        checkedButton = item.isSelected,
                                        onButtonCheckChange = {viewModel.onEvaluationReviewsCheck(item.Number)}
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}