package com.example.dismobileproject.ui.widgets

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TextFieldDate(
    modifier: Modifier = Modifier,
    value: LocalDate,
    label: @Composable () -> Unit,
    onValueChange: (LocalDate) -> Unit = {},
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "dd-MM-yyyy",
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val dialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onValueChange(LocalDate.of(year, month + 1, dayOfMonth))
        },
        value.year,
        value.monthValue - 1,
        value.dayOfMonth,
    )

    OutlinedTextField(
        value = value.format(formatter),
        label = { label() },
        onValueChange = {},
        enabled = false,
        modifier = modifier.clickable { dialog.show() },
        colors = colors,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}