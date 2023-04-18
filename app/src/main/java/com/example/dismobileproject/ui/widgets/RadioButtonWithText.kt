package com.example.dismobileproject.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
fun RadioButtonWithText(
    text: String,
    checkedButton: Boolean,
    onButtonCheckChange: () -> Unit = {}
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = checkedButton,
            onClick = { onButtonCheckChange() },
            role = Role.RadioButton
        ).padding(8.dp)
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(
                if (checkedButton) {
                    R.drawable.checked_icon
                } else {
                    R.drawable.unchecked_icon
                }
            ),
            contentDescription = null
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 5.dp).fillMaxWidth()
        )
    }
}