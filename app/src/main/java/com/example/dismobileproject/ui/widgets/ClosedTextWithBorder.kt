package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R

@Composable
fun ClosedTextWithBorder(
    text: String,
    innerPadding: PaddingValues = PaddingValues(15.dp, 7.dp),
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    textMaxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
    onCloseButtonClick: () -> Unit = {}
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color.Magenta, CircleShape)
            .background(Color.White)
    ){
        Row(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,

        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                maxLines = textMaxLines,
                overflow = textOverflow,
                style = TextStyle(color = Color.Black, fontSize = fontSize)
            )
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = { onCloseButtonClick() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.close_icon),
                    tint = Color.Magenta,
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}