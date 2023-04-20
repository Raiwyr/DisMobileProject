package com.example.dismobileproject.ui.screens.produtclist.product.detail

import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun ReviewWriteScreen(
    onWriteReview: (assessment: Int, name: String, message: String) -> Unit
){
    var selectedScore by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    var listScore = listOf(1..5).flatten()
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.heightIn(min = 0.dp, max = 440.dp)
            .padding(25.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = stringResource(id = R.string.new_review),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listScore.forEachIndexed { index, item ->
                Image(
                    modifier = Modifier
                        .clickable { selectedScore = item }
                        .size(50.dp),
                    painter = painterResource(id =
                        if(index < selectedScore)
                             R.drawable.star_full_icon
                        else
                            R.drawable.star_icon
                    ),
                    contentDescription = ""
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { if(!it.isFocused) keyboardController?.hide() }
                .clip(RoundedCornerShape(15))
                .padding(top = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray),
            shape = RoundedCornerShape(15),
            value = name,
            onValueChange = { value ->
                name = value },
            placeholder = { Text(text = stringResource(id = R.string.basic_text_name)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .clip(RoundedCornerShape(15))
                .padding(top = 15.dp)
                .fillMaxWidth()
                .height(130.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray),
            shape = RoundedCornerShape(15),
            value = message,
            onValueChange = { value ->
                message = value },
            placeholder = { Text(text = stringResource(id = R.string.basic_text_message)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()})
        )

        Button(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
            shape = CircleShape,
            onClick = {
                onWriteReview(selectedScore, name, message)
            }
        ) {
            Text(
                text = stringResource(id = R.string.send_review),
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}