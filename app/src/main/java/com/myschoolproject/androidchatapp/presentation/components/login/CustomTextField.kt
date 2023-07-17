package com.myschoolproject.androidchatapp.presentation.components.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(50.dp))
            .border(border = BorderStroke(1.34.dp, Color.White), shape = RoundedCornerShape(50.dp)),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            textColor = Color.White,
            containerColor = Color.Transparent
        ),
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "닉네임 설정",
                color = Color.White.copy(alpha = .5f),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        },
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic
        ),
        value = text,
        onValueChange = { onTextChanged(it) },
        singleLine = true,
        maxLines = 1,
        isError = false,
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        text = "홍길동",
        onTextChanged = { }
    )
}