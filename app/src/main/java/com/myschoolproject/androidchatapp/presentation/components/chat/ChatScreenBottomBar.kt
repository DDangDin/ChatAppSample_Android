package com.myschoolproject.androidchatapp.presentation.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.ui.theme.SpacerCustomColor
import com.myschoolproject.androidchatapp.ui.theme.TextHint
import com.myschoolproject.androidchatapp.ui.theme.blackColor

@Composable
fun ChatScreenBottomBar(
    modifier: Modifier = Modifier,
    inputMessage: String,
    inputMessageChanged: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(SpacerCustomColor)
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(9f)
                    .border(
                        1.dp,
                        Color.Transparent
                    ),
                value = inputMessage,
                onValueChange = { inputMessageChanged(it) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    textColor = blackColor
                ),
                placeholder = {
                    Text(
                        text = "메세지를 입력하세요...",
                        fontWeight = FontWeight.Light,
                        fontSize = 17.sp,
                        color = TextHint
                    )
                },
                singleLine = false,
                maxLines = 6
            )

            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp),
                onClick = onSend
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Send,
                    contentDescription = "send",
                    tint = Color(0xFFA8A8A8)
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatScreenBottomBar() {
    ChatScreenBottomBar(
        inputMessage = "",
        inputMessageChanged = {},
        onSend = {}
    )
}