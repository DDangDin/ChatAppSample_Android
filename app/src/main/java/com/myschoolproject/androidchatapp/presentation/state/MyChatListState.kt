package com.myschoolproject.androidchatapp.presentation.state

import com.myschoolproject.androidchatapp.data.source.remote.firebase.MyChatListPreview

data class MyChatListState(
    val myChatList: List<MyChatListPreview> = emptyList(),
    val loading: Boolean = false,
    val error: String = ""
)
