package com.myschoolproject.androidchatapp.domain.repository

import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun initializeUser(myName: String): Flow<Resource<Boolean>>

    suspend fun checkNickname(myName: String): Flow<Resource<Boolean>>

    suspend fun getUserList(): Flow<Resource<List<UserStatus>>>

}