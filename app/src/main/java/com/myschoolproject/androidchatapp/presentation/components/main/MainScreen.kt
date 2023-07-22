package com.myschoolproject.androidchatapp.presentation.components.main

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.core.view.WindowCompat
import com.myschoolproject.androidchatapp.R
import com.myschoolproject.androidchatapp.core.common.Utils.changeStatusBarColor
import com.myschoolproject.androidchatapp.core.utils.CustomThemeEffect.clickableWithoutRipple
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.presentation.components.login.CustomButton2
import com.myschoolproject.androidchatapp.presentation.state.UserListState
import com.myschoolproject.androidchatapp.ui.theme.MyPrimaryColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    userListState: UserListState,
    onEvent: (MainUiEvent) -> Unit
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

    val userList = userListState.userList

    LaunchedEffect(key1 = !isStatusBarColorChanged) {
        changeStatusBarColor(context, view, Color.White)
        isStatusBarColorChanged = true
    }

    LaunchedEffect(userListState) {
        if (!userListState.loading)
            isRefreshing = false
    }

    Box(modifier = modifier.fillMaxSize()) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
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
                                    .clickableWithoutRipple(
                                        onClick = {
                                            isUserClick = !isUserClick
                                            selectedUserData = user
                                        },
                                        interactionSource = MutableInteractionSource()
                                    ),
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
                onClick = { /*TODO*/ },
                backgroundColor = MyPrimaryColor,
                borderEnabled = false
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        userListState = UserListState(),
        onEvent = {}
    )
}