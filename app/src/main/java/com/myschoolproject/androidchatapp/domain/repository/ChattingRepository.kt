package com.myschoolproject.androidchatapp.domain.repository

import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.data.source.remote.firebase.MyChatListPreview
import kotlinx.coroutines.flow.Flow

interface ChattingRepository {

    suspend fun sendMessage(myName: String, friendName: String, message: String)

    suspend fun getChatMessages(myName: String, friendName: String): Flow<Resource<List<Chat>>>

    suspend fun getMyChatList(myName: String): Flow<Resource<List<MyChatListPreview>>>

    suspend fun initializeChat(myName: String, friendName: String): Flow<Resource<Boolean>>

    suspend fun startChatting(myName: String)

    suspend fun quitChatting(myName: String, friendName: String)
}