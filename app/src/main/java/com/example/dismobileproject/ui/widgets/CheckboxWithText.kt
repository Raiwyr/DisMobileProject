package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.dismobileproject.R

@Composable
fun CheckboxWithText(
    text: String,
    modifier: Modifier = Modifier,
    checkedButton: Boolean,
    onButtonCheckChange: () -> Unit = {},
    imageOnLeft: Boolean = false
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.selectable(
            selected = checkedButton,
            onClick = { onButtonCheckChange() },
            role = Role.RadioButton
        ).padding(8.dp)
    ) {
        if(!imageOnLeft){
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    if (checkedButton) {
                        R.drawable.square_checked_icon
                    } else {
                        R.drawable.square_unchecked_icon
                    }
                ),
                contentDescription = null
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(start = 5.dp).fillMaxWidth()
        )
        if(imageOnLeft){
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(
                    if (checkedButton) {
                        R.drawable.square_checked_icon
                    } else {
                        R.drawable.square_unchecked_icon
                    }
                ),
                contentDescription = null
            )
        }
    }
}