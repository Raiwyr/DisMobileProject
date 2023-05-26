package com.example.dismobileproject.ui.screens.produtclist.product.detail

import android.graphics.BitmapFactory
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dismobileproject.data.model.ProductModel
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.screens.produtclist.product.getBitmapFromImage
import com.example.dismobileproject.ui.viewmodels.ProductDescriptionViewModel
import com.example.dismobileproject.ui.viewmodels.models.PrdouctDescriptionModel
import com.example.dismobileproject.ui.widgets.ExpandableCard
import com.example.dismobileproject.ui.widgets.SimpleTextWithBorder
import java.math.RoundingMode

@ExperimentalMaterialApi
@Composable
fun ProductDescriptionScreen(
    product: PrdouctDescriptionModel,
    startReviewCount: Int = 3,
    onWriteReviewAction: () -> Unit
){
    var viewModel: ProductDescriptionViewModel = viewModel(factory = ProductDescriptionViewModel.Factory)

    if(!viewModel.initViewModel)
        viewModel.initViewModel(product.imageName)

    var allReviewVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        val bitmap = BitmapFactory.decodeByteArray(viewModel.byteImageState, 0, viewModel.byteImageState.size)
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            bitmap = try {
                bitmap.asImageBitmap()
            }
            catch (e: Exception) {
                getBitmapFromImage(LocalContext.current, R.drawable.empty_product_icon).asImageBitmap()
            },
            contentDescription = ""
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
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
                    .padding(top = 10.dp, bottom = 20.dp, start = 20.dp, end = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = stringResource(id = R.string.specifications),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.composition),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Text(
                        text = product.composition ?: "",
                        fontSize = 20.sp
                    )
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.indications),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        product.indication?.let {
                            for(i in it.indices){
                                Text(
                                    text = "${i+1}: ${it.elementAt(i)}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.contraindications),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        product.contraindication?.let {
                            for(i in it.indices){
                                Text(
                                    text = "${i+1}: ${it.elementAt(i)}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.sideEffects),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        product.sideEffect?.let {
                            for(i in it.indices){
                                Text(
                                    text = "${i+1}: ${it.elementAt(i)}",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.dosage),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Text(
                        text = product.dosage ?: "",
                        fontSize = 20.sp
                    )
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.release_form),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Text(
                        text = product.releaseForm ?: "",
                        fontSize = 20.sp
                    )
                }

                Divider(
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    color = colorResource(id = R.color.pale_gray),
                    thickness = 1.dp
                )

                ExpandableCard(
                    title = stringResource(id = R.string.manufacturer),
                    padding = PaddingValues(0.dp),
                    titleFontSize = 20.sp,
                    titleFontWeight = FontWeight.SemiBold,
                    backgroundColor = Color.White,
                    borderColor = Color.White
                ){
                    Text(
                        text = product.manufacturer ?: "",
                        fontSize = 20.sp
                    )
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
                    text = stringResource(id = R.string.reviews),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                SimpleTextWithBorder(
                    modifier = Modifier
                        .clickable { onWriteReviewAction() }
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                        .height(40.dp),
                    text = stringResource(id = R.string.add_review_button),
                    textColor = colorResource(id = R.color.action_element_color),
                    fontSize = 20.sp
                )

                if((product.review?.count() ?: 0) > 0){
                    product.review?.forEachIndexed lit@ { index, item ->
                        item?.let {
                            if(!allReviewVisible.value && index >= startReviewCount)
                                return@lit

                            ReviewCard(review = item)
                        }
                    }

                    if(!allReviewVisible.value){
                        Text(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 10.dp)
                                .clickable { allReviewVisible.value = true },
                            text = stringResource(id = R.string.show_all_review_text),
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.action_element_color)
                        )
                    }
                }
                else{
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center).padding(top = 30.dp, bottom = 30.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            text = stringResource(id = R.string.text_no_reviews)
                        )
                    }
                }

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
            }
        }
    }
}