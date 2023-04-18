package com.example.dismobileproject.ui.widgets

import androidx.annotation.MainThread
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R

@Composable
fun SearchField(
    text: String,
    onValueChanged: (String) -> Unit = { },
    onDoneAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    isEnable: Boolean = true,
    shape: Shape = CircleShape,
    autoCorrect: Boolean = false,
    innerPadding: PaddingValues = PaddingValues(15.dp, 7.dp),
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val fontSize = 16.sp

    BasicTextField(
        value = text,
        onValueChange = onValueChanged,
        modifier
            .clip(shape)
            .border(1.dp, Color.Gray, shape)
            .background(Color.White),
        textStyle = TextStyle(color = Color.Black, fontSize = fontSize),
        enabled = isEnable,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            KeyboardCapitalization.None,
            autoCorrect,
            keyboardType,
            ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onDone = {focusManager.clearFocus()},
            onGo = {focusManager.clearFocus()},
            onNext = {focusManager.clearFocus()},
            onPrevious = {focusManager.clearFocus()},
            onSearch = {
                focusManager.clearFocus()
                onDoneAction()
            },
            onSend = {focusManager.clearFocus()}
//        onAny = {focusManager.clearFocus()}
        ),
        decorationBox = {

            Row(
                modifier = Modifier.padding(innerPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.search_icon),
                    tint = Color.Black,
                    contentDescription = "",
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                )
                if(text.isBlank()) {
                    Text(
                        text = "Search",
                        style = TextStyle(color = Color.Black, fontSize = fontSize)
                    )
                }
                it()
            }

        }
    )
}