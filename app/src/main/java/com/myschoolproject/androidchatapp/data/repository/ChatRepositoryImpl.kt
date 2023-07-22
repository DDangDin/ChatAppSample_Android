package com.myschoolproject.androidchatapp.data.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_INITIALZE_MESSAGE
import com.myschoolproject.androidchatapp.core.common.Constants.FIREBASE_DATABASE_USER_TABLE
import com.myschoolproject.androidchatapp.core.utils.Resource
import com.myschoolproject.androidchatapp.data.source.remote.firebase.Chat
import com.myschoolproject.androidchatapp.data.source.remote.firebase.UserStatus
import com.myschoolproject.androidchatapp.domain.repository.ChatRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ChatRepositoryImpl(
    private val chatRef: DatabaseReference
) : ChatRepository {

    override suspend fun initializeUserChat(myName: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        var errorMessage = ""

        try {
            // 중복 검사
            chatRef.child(FIREBASE_DATABASE_USER_TABLE).addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    // 여기서 오류남 snapshot 다루는 법 보기.
//                    val value = snapshot.getValue(UserStatus::class.java)
//                    if (value != null) {
//                        errorMessage = FIREBASE_DATABASE_INITIALIZE_EXISTS_ERROR
//                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    errorMessage = error.message
                }
            })

            if (errorMessage.isNotEmpty()) {
                emit(Resource.Error(errorMessage))
            } else {
                // 초기화
                chatRef
                    .child(FIREBASE_DATABASE_USER_TABLE)
                    .child(myName)
                    .setValue(UserStatus(isChatting = false))
                emit(Resource.Success(true))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    override suspend fun sendMessage(myName: String, friendName: String, chatData: Chat) {
//        TODO("Not yet implemented")
    }

    override suspend fun getChatMessages(
        myName: String,
        friendName: String
    ): Flow<Resource<List<Chat>>> = callbackFlow {
            trySend(Resource.Success(listOf(Chat("",""))))
        }
}