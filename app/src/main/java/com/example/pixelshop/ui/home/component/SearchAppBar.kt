package com.example.pixelshop.ui.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.pixelshop.ui.theme.Gray40
import com.example.pixelshop.ui.theme.LightGrey40

@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    value: String,
    onInputValueChange: (String) -> Unit,
    onCloseIconClicked: () -> Unit,
    onSearchIconClicked: () -> Unit
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onInputValueChange,
        textStyle = TextStyle(color = Gray40, fontSize = 16.sp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Gray40.copy(alpha = 0.7f)
            )
        },
        placeholder = {
            Text(text = "Search...", color = Gray40.copy(alpha = 0.7f))
        },
        trailingIcon = {
            IconButton(onClick = {
                if (value.isNotEmpty()) onInputValueChange("")
                else onCloseIconClicked()
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close Icon",
                    tint = Gray40
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchIconClicked() }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = LightGrey40,
            unfocusedContainerColor = LightGrey40,
            cursorColor = Gray40,
            focusedIndicatorColor = Gray40
        )
    )
}
@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(
        value = "",
        onInputValueChange = {},
        onCloseIconClicked = { /*TODO*/ },
        onSearchIconClicked = {  }
    )
}