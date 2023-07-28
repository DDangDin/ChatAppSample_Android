package com.myschoolproject.androidchatapp.presentation.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.core.common.Constants
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.core.utils.CustomThemeEffect.clickableWithoutRipple
import com.myschoolproject.androidchatapp.data.source.remote.firebase.MyChatListPreview
import com.myschoolproject.androidchatapp.presentation.state.MyChatListState
import com.myschoolproject.androidchatapp.ui.theme.MyOnBackgroundColor
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor

@Composable
fun MessagesBottomSheetContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    myChatListState: MyChatListState,
    getMyChatList: () -> Unit,
    onNavigateChat: (MyChatListPreview) -> Unit
) {

    val context = LocalContext.current
    val myName = CustomSharedPreference(context).getUserPrefs(Constants.PREFERENCE_USERNAME)

    LaunchedEffect(key1 = Unit) {
        getMyChatList()
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp)
                .clickableWithoutRipple(
                    interactionSource = MutableInteractionSource(),
                    onClick = { onClick() }
                ),
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = "drop down",
            tint = Color.Gray
        )
        Column(
            modifier = Modifier
                .padding(top = 7.dp),
            verticalArrangement = Arrangement.spacedBy(
                5.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(1.dp)
//                    .background(Color.LightGray)
//            )
            Text(text = "채팅 목록", fontSize = 12.sp)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
            )
        }

        Box(modifier = Modifier) {
            if (myChatListState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp)
                        .padding(20.dp),
                    strokeWidth = 2.dp,
                    color = MyPrimaryColor
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        5.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    contentPadding = PaddingValues(5.dp)
                ) {
                    items(myChatListState.myChatList) { friend ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .clickableWithoutRipple(
                                    interactionSource = MutableInteractionSource(),
                                    onClick = { onNavigateChat(friend) }
                                ),
                            horizontalArrangement = Arrangement.spacedBy(
                                20.dp,
                                alignment = Alignment.Start
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = friend.friendName,
                                fontSize = 25.sp,
                                color = MyOnBackgroundColor
                            )
                            Text(
                                text = friend.lastMessage.message,
                                fontSize = 20.sp,
                                color = MyOnBackgroundColor.copy(.5f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagesBottomSheetContentPreview() {
    MessagesBottomSheetContent(
        onClick = {},
        myChatListState = MyChatListState(),
        getMyChatList = {},
        onNavigateChat = {}
    )
}

@Preview(showBackground = true)
@Composable
fun itemPreview() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "홍길동",
            fontSize = 25.sp,
            color = MyOnBackgroundColor
        )
        Text(
            text = "안녕하세요",
            fontSize = 20.sp,
            color = MyOnBackgroundColor.copy(.5f)
        )
    }
}