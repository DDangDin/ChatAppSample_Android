package com.myschoolproject.androidchatapp.domain.repository

import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface ChattingRepository {

    suspend fun sendMessage(myName: String, friendName: String, message: String)

    suspend fun getChatMessages(myName: String, friendName: String): Flow<Resource<List<Chat>>>

    suspend fun startChatting(myName: String)

    suspend fun quitChatting(myName: String)
}