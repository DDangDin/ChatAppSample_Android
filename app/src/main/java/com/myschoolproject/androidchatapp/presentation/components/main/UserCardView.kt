package com.myschoolproject.androidchatapp.presentation.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.ui.theme.MyOnBackgroundColor

@Composable
fun UserCardView(
    modifier: Modifier = Modifier,
    nickname: String,
    isChatting: Boolean
) {

    val iconTint = if (isChatting) Color(0xFFF05656) else Color.Transparent

    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 3.dp),
            text = nickname,
            fontSize = 25.sp,
            color = MyOnBackgroundColor
        )
        Icon(
            modifier = Modifier.size(25.dp),
            imageVector = Icons.Default.Circle,
            contentDescription = "isChatting Toggle",
            tint = iconTint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardViewPreview() {
    UserCardView(nickname = "우디", isChatting = true)
}