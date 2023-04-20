package com.example.dismobileproject.ui.widgets

import android.graphics.Paint.Align
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R

@Composable
fun SimpleTextWithBorder(
    text: String,
    innerPadding: PaddingValues = PaddingValues(15.dp, 7.dp),
    modifier: Modifier = Modifier,
    borderColor: Color = colorResource(id = R.color.action_element_color),
    textColor: Color = Color.Black,
    textAlign: Alignment = Alignment.Center,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.SemiBold,
    textMaxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis
){
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, borderColor, CircleShape)
            .background(Color.White)
    ){
        Text(
            modifier = Modifier.align(textAlign).padding(innerPadding),
            text = text,
            maxLines = textMaxLines,
            overflow = textOverflow,
            style = TextStyle(
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
        )
    }
}