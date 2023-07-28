package com.myschoolproject.androidchatapp.presentation.components.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.R
import com.myschoolproject.androidchatapp.core.common.Constants
import com.myschoolproject.androidchatapp.core.common.Utils.changeStatusBarColor
import com.myschoolproject.androidchatapp.core.utils.CustomSharedPreference
import com.myschoolproject.androidchatapp.core.utils.CustomThemeEffect.clickableWithoutRipple
import com.myschoolproject.androidchatapp.data.source.remote.firebase.MyChatListPreview
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.presentation.components.login.CustomButton2
import com.myschoolproject.androidchatapp.presentation.components.messages.MessagesBottomSheetContent
import com.myschoolproject.androidchatapp.presentation.state.MyChatListState
import com.myschoolproject.androidchatapp.presentation.state.UserListState
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    userListState: UserListState,
    myChatListState: MyChatListState,
    onEvent: (MainUiEvent) -> Unit,
    onNavigate: (String) -> Unit,
    getMyChatList: () -> Unit,
) {

    val context = LocalContext.current
    val view = LocalView.current

    val scrollState = rememberLazyListState()
    var isStatusBarColorChanged by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onEvent(MainUiEvent.Refresh)
        })
    var isUserClick by remember { mutableStateOf(false) }
    var selectedUserData by remember { mutableStateOf(UserStatus()) }
    val scaffoldState = rememberScaffoldState()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    val myName = CustomSharedPreference(context).getUserPrefs(Constants.PREFERENCE_USERNAME)
    val excludeList = listOf(myName, "Admin") // exclude users(Me, Admin)
    val userList = userListState.userList.filterNot {
        it.nickname in excludeList
    }

    LaunchedEffect(key1 = !isStatusBarColorChanged) {
        changeStatusBarColor(context, view, Color.White)
        isStatusBarColorChanged = true
    }

    LaunchedEffect(userListState) {
        if (!userListState.loading)
            isRefreshing = false
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Box(
                Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(.92f)
                    .background(Color.White)
            ) {
                MessagesBottomSheetContent(
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        coroutineScope.launch {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    },
                    myChatListState = myChatListState,
                    getMyChatList = { getMyChatList() },
                    onNavigateChat = { onNavigate(it.friendName) }
                )
            }
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            floatingActionButton = {
                FloatingActionButton(
                    backgroundColor = MyPrimaryColor,
                    contentColor = Color.White,
                    onClick = {
                        coroutineScope.launch {
                            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Message,
                        contentDescription = "receive messages"
                    )
                }
            },
        ) { paddingValues ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    MainTopBar(Modifier.fillMaxWidth())
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.5.dp,
                        color = Color(0xFFDDDDDD)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp, horizontal = 10.dp),
                        text = "내 닉네임: $myName",
                        textAlign = TextAlign.End,
                        fontSize = 12.sp
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.5.dp,
                        color = Color(0xFFDDDDDD)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(start = 20.dp, end = 20.dp, bottom = 110.dp)
                            .pullRefresh(pullRefreshState),
                    ) {
                        if (!userListState.loading && userListState.userList.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.align(Alignment.TopCenter),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                contentPadding = PaddingValues(vertical = 5.dp),
                                state = scrollState
                            ) {
                                items(userList) { user ->
                                    UserCardView(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                if (selectedUserData.hashCode() == user.hashCode() ||
                                                    selectedUserData.nickname.isEmpty()
                                                ) {
                                                    isUserClick = !isUserClick
                                                }
                                                if (isUserClick) {
                                                    selectedUserData = user
                                                } else {
                                                    selectedUserData = UserStatus()
                                                }
                                            },
                                        nickname = user.nickname,
                                        isChatting = user.isChatting
                                    )
                                }
                            }
                        } else {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(100.dp)
                                    .padding(20.dp),
                                strokeWidth = 2.dp,
                                color = MyPrimaryColor
                            )
                        }

                        PullRefreshIndicator(
                            modifier = Modifier
                                .align(Alignment.TopCenter),
                            refreshing = isRefreshing,
                            state = pullRefreshState
                        )
                    }
                }

                AnimatedVisibility(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    visible = isUserClick,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    CustomButton2(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(29.dp),
                        text = R.string.main_btn_text,
                        onClick = { onNavigate(selectedUserData.nickname) },
                        backgroundColor = MyPrimaryColor,
                        borderEnabled = false
                    )
                }
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        userListState = UserListState(),
        onEvent = {},
        onNavigate = {},
        myChatListState = MyChatListState(),
        getMyChatList = {}
    )
}