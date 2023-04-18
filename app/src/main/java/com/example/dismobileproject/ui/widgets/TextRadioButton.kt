package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.R

@Composable
fun TextRadioButton(
    text: String,
    checkedButton: Boolean,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues = PaddingValues(8.dp, 0.dp),
    fontSize: TextUnit = 16.sp,
    textMaxLines: Int = 1,
    textOverflow: TextOverflow = TextOverflow.Ellipsis,
    onButtonCheckChange: () -> Unit = {}
){

    var textColor = if(checkedButton) Color.White else Color.Magenta

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(CircleShape)
            .border(1.dp, Color.Magenta, CircleShape)
            .background(
                if (checkedButton) {
                    Color.Magenta
                } else {
                    Color.White
                }
            )
            .selectable(
                selected = checkedButton,
                onClick = { onButtonCheckChange() },
                role = Role.RadioButton
            )
    ){
        Text(
            modifier = Modifier.padding(innerPadding),
            text = text,
            maxLines = textMaxLines,
//            overflow = textOverflow,
            style = TextStyle(
                color = textColor,
                fontSize = fontSize
            ),
            fontWeight = FontWeight.Bold
        )
    }
}