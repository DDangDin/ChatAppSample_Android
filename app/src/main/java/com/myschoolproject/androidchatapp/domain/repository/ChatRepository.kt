package com.myschoolproject.androidchatapp.domain.repository

import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun initializeUserChat(myName: String): Flow<Resource<Boolean>>
    suspend fun sendMessage(myName: String, friendName: String, chatData: Chat)
//    suspend fun getChat(myName: String, friendName: String): Flow<Resource<Chat>>
    suspend fun getChatMessages(myName: String, friendName: String): Flow<Resource<List<Chat>>>
}