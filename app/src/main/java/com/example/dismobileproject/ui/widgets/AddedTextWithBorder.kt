package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun AddedTextWithBorder(
    text: String,
    innerPadding: PaddingValues = PaddingValues(10.dp, 7.dp),
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    textMaxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
    onAddedButtonClick: () -> Unit = {}
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color.Magenta, CircleShape)
            .background(Color.White)
            .clickable { onAddedButtonClick() }
    ){
        Row(
            modifier = Modifier.padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.add_icon),
                tint = Color.Magenta,
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                maxLines = textMaxLines,
                overflow = textOverflow,
                style = TextStyle(color = Color.Black, fontSize = fontSize)
            )
        }
    }
}